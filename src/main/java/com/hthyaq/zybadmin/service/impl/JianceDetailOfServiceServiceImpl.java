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
        QueryWrapper<ServiceOfRegister> qw=new QueryWrapper();
        qw.eq("name",sysUser.getCompanyName());
        List<ServiceOfRegister> list4 = serviceOfRegisterService.list(qw);
        for (ServiceOfRegister serviceOfRegister : list4) {
            if(serviceOfRegister.getType().equals("检测机构")) {
                //demo
                JianceDetailOfService jianceDetailOfService = new JianceDetailOfService();
                BeanUtils.copyProperties(jianceDetailOfServiceView, jianceDetailOfService);
                QueryWrapper<AreaOfDic> queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("id", jianceDetailOfServiceView.getCascader().get(0));
                List<AreaOfDic> list = areaOfDicService.list(queryWrapper);
                for (AreaOfDic areaOfDic : list) {
                    jianceDetailOfService.setProvinceName(String.valueOf(areaOfDic.getName()));
                    jianceDetailOfService.setProvinceCode(String.valueOf(areaOfDic.getCode()));
                }

                QueryWrapper<AreaOfDic> queryWrapper1 = new QueryWrapper<>();
                queryWrapper1.eq("id",jianceDetailOfServiceView.getCascader().get(1));
                List<AreaOfDic> list1 = areaOfDicService.list(queryWrapper1);
                for (AreaOfDic areaOfDic : list1) {
                    jianceDetailOfService.setCityName(String.valueOf(areaOfDic.getName()));
                    jianceDetailOfService.setCityCode(String.valueOf(areaOfDic.getCode()));
                }

                if (jianceDetailOfServiceView.getCascader().size() !=3) {
                    QueryWrapper<AreaOfDic> queryWrapper3= new QueryWrapper<>();
                    queryWrapper3.eq("id",jianceDetailOfServiceView.getCascader().get(1));
                    List<AreaOfDic> list3 = areaOfDicService.list(queryWrapper3);
                    for (AreaOfDic areaOfDic : list3) {
                        jianceDetailOfService.setDistrictName(String.valueOf(areaOfDic.getName()));
                        jianceDetailOfService.setDistrictCode(String.valueOf(areaOfDic.getCode()));
                    }
                } else {
                    QueryWrapper<AreaOfDic> queryWrapper2 = new QueryWrapper<>();
                    queryWrapper2.eq("id", jianceDetailOfServiceView.getCascader().get(2));
                    List<AreaOfDic> list2 = areaOfDicService.list(queryWrapper2);
                    for (AreaOfDic areaOfDic : list2) {
                        jianceDetailOfService.setDistrictName(String.valueOf(areaOfDic.getName()));
                        jianceDetailOfService.setDistrictCode(String.valueOf(areaOfDic.getCode()));
                    }
                }
                QueryWrapper<JianceBasicOfService> qw1= new QueryWrapper();
                qw1.eq("name", serviceOfRegister.getName());
                List<JianceBasicOfService> list5 = jianceBasicOfServiceService.list(qw1);
                for (JianceBasicOfService jianceBasicOfService : list5) {
                    jianceDetailOfService.setJianceBasicId(jianceBasicOfService.getId());
                }
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

        QueryWrapper<AreaOfDic> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id",jianceDetailOfServiceView.getCascader().get(0));
        List<AreaOfDic> list = areaOfDicService.list(queryWrapper);
        for (AreaOfDic areaOfDic : list) {
            jianceDetailOfService.setProvinceName(String.valueOf(areaOfDic.getName()));
            jianceDetailOfService.setProvinceCode(String.valueOf(areaOfDic.getCode()));
        }
        QueryWrapper<AreaOfDic> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.eq("id", jianceDetailOfServiceView.getCascader().get(1));
        List<AreaOfDic> list1 = areaOfDicService.list(queryWrapper1);
        for (AreaOfDic areaOfDic : list1) {
            jianceDetailOfService.setCityName(String.valueOf(areaOfDic.getName()));

            jianceDetailOfService.setCityCode(String.valueOf(areaOfDic.getCode()));
        }

        if (jianceDetailOfServiceView.getCascader().size() !=3) {
            QueryWrapper<AreaOfDic> queryWrapper3= new QueryWrapper<>();
            queryWrapper3.eq("id", jianceDetailOfServiceView.getCascader().get(1));
            List<AreaOfDic> list3 = areaOfDicService.list(queryWrapper3);
            for (AreaOfDic areaOfDic : list3) {
                jianceDetailOfService.setDistrictName(String.valueOf(areaOfDic.getName()));
                jianceDetailOfService.setDistrictCode(String.valueOf(areaOfDic.getCode()));
            }
        } else {
            QueryWrapper<AreaOfDic> queryWrapper2 = new QueryWrapper<>();
            queryWrapper2.eq("id", jianceDetailOfServiceView.getCascader().get(2));
            List<AreaOfDic> list2 = areaOfDicService.list(queryWrapper2);
            for (AreaOfDic areaOfDic : list2) {
                jianceDetailOfService.setDistrictName(String.valueOf(areaOfDic.getName()));
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
