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
    @Override
    public boolean saveData(JianceDetailOfServiceView jianceDetailOfServiceView, HttpSession httpSession) {
        boolean flag2 = true;
        SysUser sysUser = (SysUser) httpSession.getAttribute(GlobalConstants.LOGIN_NAME);
        //demo
        JianceDetailOfService jianceDetailOfService = new JianceDetailOfService();

        BeanUtils.copyProperties(jianceDetailOfServiceView, jianceDetailOfService);
        //save之后，就有id
        QueryWrapper<ServiceOfRegister> queryWrapper = new QueryWrapper();
        queryWrapper.eq("id", sysUser.getCompanyId());
        List<ServiceOfRegister> list = serviceOfRegisterService.list(queryWrapper);
        for (ServiceOfRegister serviceOfRegister : list) {
            jianceDetailOfService.setEnterpriseName(serviceOfRegister.getName());
            jianceDetailOfService.setEnterpriseCode(serviceOfRegister.getCode());
            jianceDetailOfService.setProvinceName(serviceOfRegister.getProvinceName());
            jianceDetailOfService.setProvinceCode(serviceOfRegister.getProvinceCode());
            jianceDetailOfService.setCityName(serviceOfRegister.getCityName());
            jianceDetailOfService.setCityCode(serviceOfRegister.getCityCode());
            jianceDetailOfService.setDistrictName(serviceOfRegister.getDistrictName());
            jianceDetailOfService.setDistrictCode(serviceOfRegister.getDistrictCode());
            jianceDetailOfService.setRegisterAddress(serviceOfRegister.getRegisterAddress());
            QueryWrapper<JianceBasicOfService> queryWrapper1 = new QueryWrapper();
            queryWrapper1.eq("name", serviceOfRegister.getName());
            List<JianceBasicOfService> list1 = jianceBasicOfServiceService.list(queryWrapper1);
            for (JianceBasicOfService jianceBasicOfService : list1) {
                jianceDetailOfService.setJianceBasicId(jianceBasicOfService.getId());
                this.save(jianceDetailOfService);
            }


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
