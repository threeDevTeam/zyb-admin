package com.hthyaq.zybadmin.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hthyaq.zybadmin.model.entity.AreaCopy;
import com.hthyaq.zybadmin.mapper.AreaCopyMapper;
import com.hthyaq.zybadmin.service.AreaCopyService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 省市县三级的行政区域数据 服务实现类
 * </p>
 *
 * @author zhangqiang
 * @since 2019-09-04
 */
@Service
public class AreaCopyServiceImpl extends ServiceImpl<AreaCopyMapper, AreaCopy> implements AreaCopyService {
    @Override
    public boolean seletshen() {
        //4个直辖市
        shi();
        //省份、自治区
        sheng();
        return true;
    }



    private void sheng() {
        //1.list=取出所有省,select * from area where adcode like '%0000' and name not like '%市'
        QueryWrapper<AreaCopy> queryWrapper = new QueryWrapper<>();
        queryWrapper.likeLeft("adcode", "0000").notLike("name", "市");
        List<AreaCopy> list = this.list(queryWrapper);
        for (AreaCopy areaCopy : list) {
            areaCopy.setLevel("1");

            Integer id = areaCopy.getId();
            int adcode = areaCopy.getAdcode();
            //通过adcode，得到adcode2
            String adcode1 = adcode + "";
            adcode1 = adcode1.substring(0, 2) + "__00";
            //取出安徽省的下级节点,list2=select * from area where adcode like '340_00' and adcode !=340000
            QueryWrapper<AreaCopy> queryWrapper2 = new QueryWrapper<>();
            queryWrapper2.likeLeft("adcode", adcode1).ne("adcode", adcode);
            List<AreaCopy> list2 = this.list(queryWrapper2);
            for (AreaCopy areaCopy2 : list2) {
                areaCopy2.setPid(id);
                areaCopy2.setLevel("2");

                Integer id2 = areaCopy2.getId();
                String adcode2 = areaCopy2.getAdcode() + "";
                adcode2 = adcode2.substring(0, 4);
                //根据adcode获取合肥市下的所有节点,list3=select * from area where adcode like '3401%' and adcode != 340100
                QueryWrapper<AreaCopy> queryWrapper3 = new QueryWrapper<>();
                queryWrapper3.likeRight("adcode", adcode2).ne("adcode", areaCopy2.getAdcode());
                List<AreaCopy> list3 = this.list(queryWrapper3);
                //遍历list3
                for (AreaCopy areaCopy3 : list3) {
                    //设置pid、level
                    areaCopy3.setPid(id2);
                    areaCopy3.setLevel("3");
                    if (CollectionUtil.isNotEmpty(list3)) {
                        this.updateBatchById(list3);
                    }
                    //设置area2的childNum
                    areaCopy2.setChildNum(list3.size());
                }

            }
            if (CollectionUtil.isNotEmpty(list2)) {
                //更新list2
                this.updateBatchById(list2);
            }
            //设置area的childNum
            areaCopy.setChildNum(list2.size());
        }
        this.updateBatchById(list);
    }
    private void shi() {
        QueryWrapper<AreaCopy> queryWrapper = new QueryWrapper<>();
        queryWrapper.likeLeft("adcode", "0000").likeLeft("name", "市");
        List<AreaCopy> list = this.list(queryWrapper);
        //2.遍历list
        for (AreaCopy area : list) {
            //设置level
            area.setLevel("1");

            Integer id = area.getId();
            Integer adcode = area.getAdcode();
            String adcode2 = adcode + "";
            adcode2 = adcode2.substring(0, 3);
            //根据adcode获取北京市下的区, select * from area where adcode like '11%' and adcode!=110000
            QueryWrapper<AreaCopy> queryWrapper2 = new QueryWrapper<>();
            queryWrapper2.likeRight("adcode", adcode2).ne("adcode", adcode);
            List<AreaCopy> list2 = this.list(queryWrapper2);
            for (AreaCopy tmp : list2) {
                //设置pid
                tmp.setPid(id);
                tmp.setLevel("2");
            }
            //批量更新
            this.updateBatchById(list2);

            //设置childNum
            area.setChildNum(list2.size());
        }

        //3.批量更新
        this.updateBatchById(list);
    }
}
