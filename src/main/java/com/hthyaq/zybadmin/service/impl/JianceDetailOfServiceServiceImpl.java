package com.hthyaq.zybadmin.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hthyaq.zybadmin.model.entity.*;
import com.hthyaq.zybadmin.mapper.JianceDetailOfServiceMapper;
import com.hthyaq.zybadmin.model.vo.JianceDetailOfServiceView;
import com.hthyaq.zybadmin.service.DemoCourseService;
import com.hthyaq.zybadmin.service.JianceDetailOfServiceService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hthyaq.zybadmin.service.JianceDetailResultOfServiceService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    @Override
    public boolean saveData(JianceDetailOfServiceView jianceDetailOfServiceView) {
        boolean flag1, flag2 = true;

        //demo
        JianceDetailOfService jianceDetailOfService = new JianceDetailOfService();

        BeanUtils.copyProperties(jianceDetailOfServiceView, jianceDetailOfService);
        //save之后，就有id
        flag1 = this.save(jianceDetailOfService);

        //demoCourse
        List<JianceDetailResultOfService> dataSource = jianceDetailOfServiceView.getCourse().getDataSource();

        if (ObjectUtil.length(dataSource) > 0) {
            //设置demoCourse的demo_id
            long demoId =jianceDetailOfService.getId();
            dataSource.forEach(demoCourse -> demoCourse.setJianceDetailId(demoId));
            //保存
            flag2 = jianceDetailResultOfServiceService.saveBatch(dataSource);
        }


        return flag1 && flag2;
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
        flag1 = this.updateById(jianceDetailOfService);

        //demoCourse，先删除，后插入
        QueryWrapper<JianceDetailResultOfService> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("jianceDetailId", jianceDetailOfServiceView.getId());
        flag2 = jianceDetailResultOfServiceService.remove(queryWrapper);

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
