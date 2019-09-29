package com.hthyaq.zybadmin.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hthyaq.zybadmin.common.constants.GlobalConstants;
import com.hthyaq.zybadmin.model.entity.*;
import com.hthyaq.zybadmin.mapper.JianceDetailOfServiceMapper;
import com.hthyaq.zybadmin.model.vo.JianceDetailOfServiceView;
import com.hthyaq.zybadmin.service.*;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * <p>
 * 检测机构的具体报告

检测机构的具体报告






















 服务实现类
 * </p>
 *
 * @author zhangqiang
 * @since 2019-08-30
 */
@Service
public class JianceDetailOfServiceServiceImpl extends ServiceImpl<JianceDetailOfServiceMapper, JianceDetailOfService> implements JianceDetailOfServiceService {
    @Autowired
    JianceDetailResultOfServiceService jianceDetailResultOfServiceService;
    @Autowired
    ServiceOfRegisterService serviceOfRegisterService;
    @Autowired
    JianceBasicOfServiceService jianceBasicOfServiceService;
    @Autowired
    AreaOfDicService areaOfDicService;
    @Override
    public boolean saveData(JianceDetailOfServiceView jianceDetailOfServiceView, HttpSession httpSession) {
        boolean flag2 = true;
        SysUser sysUser = (SysUser) httpSession.getAttribute(GlobalConstants.LOGIN_NAME);
        //demo
        JianceDetailOfService jianceDetailOfService = new JianceDetailOfService();
        BeanUtils.copyProperties(jianceDetailOfServiceView, jianceDetailOfService);
        jianceDetailOfService.setProvinceName((String) jianceDetailOfServiceView.getCascader().get(0));

        QueryWrapper<AreaOfDic> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name", (String) jianceDetailOfServiceView.getCascader().get(0));
        List<AreaOfDic> list = areaOfDicService.list(queryWrapper);
        for (AreaOfDic areaOfDic : list) {
            jianceDetailOfService.setProvinceCode(String.valueOf(areaOfDic.getCode()));
        }

        jianceDetailOfService.setCityName((String) jianceDetailOfServiceView.getCascader().get(1));

        QueryWrapper<AreaOfDic> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.eq("name", (String) jianceDetailOfServiceView.getCascader().get(1));
        List<AreaOfDic> list1 = areaOfDicService.list(queryWrapper1);
        for (AreaOfDic areaOfDic : list1) {
            jianceDetailOfService.setCityCode(String.valueOf(areaOfDic.getCode()));
        }

        if (jianceDetailOfServiceView.getCascader().size() !=3) {
            jianceDetailOfService.setDistrictName((String) jianceDetailOfServiceView.getCascader().get(1));
            QueryWrapper<AreaOfDic> queryWrapper3= new QueryWrapper<>();
            queryWrapper3.eq("name", (String) jianceDetailOfServiceView.getCascader().get(1));
            List<AreaOfDic> list3 = areaOfDicService.list(queryWrapper3);
            for (AreaOfDic areaOfDic : list3) {
                jianceDetailOfService.setDistrictCode(String.valueOf(areaOfDic.getCode()));
            }
        } else {
            jianceDetailOfService.setDistrictName((String) jianceDetailOfServiceView.getCascader().get(2));
            QueryWrapper<AreaOfDic> queryWrapper2 = new QueryWrapper<>();
            queryWrapper2.eq("name", (String) jianceDetailOfServiceView.getCascader().get(2));
            List<AreaOfDic> list2 = areaOfDicService.list(queryWrapper2);
            for (AreaOfDic areaOfDic : list2) {
                jianceDetailOfService.setDistrictCode(String.valueOf(areaOfDic.getCode()));
            }
        }
                jianceDetailOfService.setJianceBasicId(sysUser.getCompanyId());
              this.save(jianceDetailOfService);


            //demoCourse
            List<JianceDetailResultOfService> dataSource = jianceDetailOfServiceView.getCourse().getDataSource();

            if (ObjectUtil.length(dataSource) > 0) {
                //设置demoCourse的demo_id
                long demoBId = jianceDetailOfService.getJianceBasicId();
                long demoId = jianceDetailOfService.getId();
                dataSource.forEach(demoCourse -> demoCourse.setJianceDetailId(demoId));
                dataSource.forEach(demoCourse -> demoCourse.setJianceBasicId(demoBId));
                //保存
               flag2 = jianceDetailResultOfServiceService.saveBatch(dataSource);
            }

        return flag2;
    }
    @Override
    public boolean deleteData(String id) {
        boolean flag1, flag2 = true;

        //demo
        flag1 = this.removeById(id);

        //demoCourse
        QueryWrapper<JianceDetailResultOfService> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("jianceDetailId", id);
        flag2 = jianceDetailResultOfServiceService.remove(queryWrapper);

        return flag1 && flag2;
    }

    @Override
    public boolean editData(JianceDetailOfServiceView jianceDetailOfServiceView) {
        boolean flag1, flag2 = true;

        //demo
        JianceDetailOfService jianceDetailOfService = new JianceDetailOfService();

        BeanUtils.copyProperties(jianceDetailOfServiceView, jianceDetailOfService);

        jianceDetailOfService.setProvinceName((String) jianceDetailOfServiceView.getCascader().get(0));

        QueryWrapper<AreaOfDic> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name", (String) jianceDetailOfServiceView.getCascader().get(0));
        List<AreaOfDic> list = areaOfDicService.list(queryWrapper);
        for (AreaOfDic areaOfDic : list) {
            jianceDetailOfService.setProvinceCode(String.valueOf(areaOfDic.getCode()));
        }

        jianceDetailOfService.setCityName((String) jianceDetailOfServiceView.getCascader().get(1));

        QueryWrapper<AreaOfDic> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.eq("name", (String) jianceDetailOfServiceView.getCascader().get(1));
        List<AreaOfDic> list1 = areaOfDicService.list(queryWrapper1);
        for (AreaOfDic areaOfDic : list1) {
            jianceDetailOfService.setCityCode(String.valueOf(areaOfDic.getCode()));
        }

        if (jianceDetailOfServiceView.getCascader().size() !=3) {
            jianceDetailOfService.setDistrictName((String) jianceDetailOfServiceView.getCascader().get(1));
            QueryWrapper<AreaOfDic> queryWrapper3= new QueryWrapper<>();
            queryWrapper3.eq("name", (String) jianceDetailOfServiceView.getCascader().get(1));
            List<AreaOfDic> list3 = areaOfDicService.list(queryWrapper3);
            for (AreaOfDic areaOfDic : list3) {
                jianceDetailOfService.setDistrictCode(String.valueOf(areaOfDic.getCode()));
            }
        } else {
            jianceDetailOfService.setDistrictName((String) jianceDetailOfServiceView.getCascader().get(2));
            QueryWrapper<AreaOfDic> queryWrapper2 = new QueryWrapper<>();
            queryWrapper2.eq("name", (String) jianceDetailOfServiceView.getCascader().get(2));
            List<AreaOfDic> list2 = areaOfDicService.list(queryWrapper2);
            for (AreaOfDic areaOfDic : list2) {
                jianceDetailOfService.setDistrictCode(String.valueOf(areaOfDic.getCode()));
            }
        }


        flag1 = this.updateById(jianceDetailOfService);

        //demoCourse，先删除，后插入
        QueryWrapper<JianceDetailResultOfService> queryWrapper2 = new QueryWrapper<>();
        queryWrapper2.eq("jianceDetailId", jianceDetailOfServiceView.getId());
        flag2 = jianceDetailResultOfServiceService.remove(queryWrapper2);

        List<JianceDetailResultOfService> dataSource = jianceDetailOfServiceView.getCourse().getDataSource();
        if (ObjectUtil.length(dataSource) > 0) {
            //设置demoCourse的demo_id
            dataSource.forEach(demoCourse -> demoCourse.setJianceDetailId(jianceDetailOfService.getId()));
            //保存
            flag2 = jianceDetailResultOfServiceService.saveBatch(dataSource);
        }

        return flag1 && flag2;
    }
}
