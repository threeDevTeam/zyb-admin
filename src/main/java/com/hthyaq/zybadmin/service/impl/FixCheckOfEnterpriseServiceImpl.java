package com.hthyaq.zybadmin.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hthyaq.zybadmin.common.constants.GlobalConstants;
import com.hthyaq.zybadmin.common.utils.AntdDateUtil;
import com.hthyaq.zybadmin.model.entity.*;
import com.hthyaq.zybadmin.mapper.FixCheckOfEnterpriseMapper;
import com.hthyaq.zybadmin.model.vo.FixCheckOfView;
import com.hthyaq.zybadmin.service.EnterpriseService;
import com.hthyaq.zybadmin.service.FixCheckOfEnterpriseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hthyaq.zybadmin.service.FixCheckResultOfEnterpriseService;
import com.hthyaq.zybadmin.service.PostDangerOfEnterpriseService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * <p>
 * 定期检测信息 服务实现类
 * </p>
 *
 * @author zhangqiang
 * @since 2019-09-02
 */
@Service
public class FixCheckOfEnterpriseServiceImpl extends ServiceImpl<FixCheckOfEnterpriseMapper, FixCheckOfEnterprise> implements FixCheckOfEnterpriseService {
    @Autowired
    FixCheckResultOfEnterpriseService fixCheckResultOfEnterpriseService;
    @Autowired
    PostDangerOfEnterpriseService postDangerOfEnterpriseService;
    @Autowired
    EnterpriseService enterpriseService;
    @Override
    public boolean saveData(FixCheckOfView fixCheckOfView, HttpSession httpSession) {
        boolean flag1, flag2 = true;

        FixCheckOfEnterprise fixCheckOfEnterprise=new FixCheckOfEnterprise();

        BeanUtils.copyProperties(fixCheckOfView, fixCheckOfEnterprise);
        fixCheckOfEnterprise.setCheckDate(AntdDateUtil.getInteger(fixCheckOfView.getCheckDateStr()));

        //enterpriseId
        SysUser sysUser = (SysUser) httpSession.getAttribute(GlobalConstants.LOGIN_NAME);
        QueryWrapper<Enterprise> queryWrapper1=new QueryWrapper();
        queryWrapper1.eq("name",sysUser.getCompanyName());
        List<Enterprise> list1 = enterpriseService.list(queryWrapper1);
        for (Enterprise enterprise : list1) {
            fixCheckOfEnterprise.setEnterpriseId(enterprise.getId());
        }
        //postDangerId
        fixCheckOfEnterprise.setPostDangerId(Long.parseLong(fixCheckOfView.getTreeSelect()));

        PostDangerOfEnterprise postDangerOfEnterprise = postDangerOfEnterpriseService.getById(fixCheckOfView.getTreeSelect());
        fixCheckOfEnterprise.setWorkplaceId(postDangerOfEnterprise.getWorkplaceId());
        fixCheckOfEnterprise.setPostId(postDangerOfEnterprise.getPostId());

        flag1 = this.save(fixCheckOfEnterprise);

        List<FixCheckResultOfEnterprise> dataSource = fixCheckOfView.getCourse().getDataSource();

        if (ObjectUtil.length(dataSource) > 0) {
            //设置demoCourse的demo_id
            long fixCheckId = fixCheckOfEnterprise.getId();
            long EnterpriseId = fixCheckOfEnterprise.getEnterpriseId();
            dataSource.forEach(demoCourse -> demoCourse.setEnterpriseId(EnterpriseId));
            dataSource.forEach(demoCourse -> demoCourse.setFixCheckId(fixCheckId));
            dataSource.forEach(demoCourse -> demoCourse.setPostId(postDangerOfEnterprise.getPostId()));
            dataSource.forEach(demoCourse -> demoCourse.setPostDangerId(Long.parseLong(fixCheckOfView.getTreeSelect())));
            dataSource.forEach(demoCourse -> demoCourse.setWorkplaceId(postDangerOfEnterprise.getWorkplaceId()));
            //保存
            flag2 = fixCheckResultOfEnterpriseService.saveBatch(dataSource);
        }

        return flag1 && flag2;
    }

    @Override
    public boolean deleteData(String id) {
        boolean flag1, flag2 = true;

        //demo
        flag1 = this.removeById(id);

        //demoCourse
        QueryWrapper<FixCheckResultOfEnterprise> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("fixCheckId", id);
        flag2 = fixCheckResultOfEnterpriseService.remove(queryWrapper);

        return flag1 && flag2;
    }

    @Override
    public boolean editData(FixCheckOfView fixCheckOfView) {
        boolean flag1, flag2 = true;

        //demo
        FixCheckOfEnterprise fixCheckOfEnterprise=new FixCheckOfEnterprise();
        BeanUtils.copyProperties(fixCheckOfView, fixCheckOfEnterprise);
        fixCheckOfEnterprise.setCheckDate(AntdDateUtil.getInteger(fixCheckOfView.getCheckDateStr()));

        flag1 = this.updateById(fixCheckOfEnterprise);

        //demoCourse，先删除，后插入
        QueryWrapper<FixCheckResultOfEnterprise> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("fixCheckId", fixCheckOfView.getId());
        flag2 = fixCheckResultOfEnterpriseService.remove(queryWrapper);

        List<FixCheckResultOfEnterprise> dataSource = fixCheckOfView.getCourse().getDataSource();
        if (ObjectUtil.length(dataSource) > 0) {
            //设置demoCourse的demo_id
            long enterpriseId = fixCheckOfEnterprise.getId();
            long fixCheckId = fixCheckOfEnterprise.getId();
            long postDangerId = fixCheckOfEnterprise.getId();
            long postId = fixCheckOfEnterprise.getId();
            long workplaceId = fixCheckOfEnterprise.getId();
            dataSource.forEach(demoCourse -> demoCourse.setFixCheckId(enterpriseId));
            dataSource.forEach(demoCourse -> demoCourse.setFixCheckId(fixCheckId));
            dataSource.forEach(demoCourse -> demoCourse.setFixCheckId(postDangerId));
            dataSource.forEach(demoCourse -> demoCourse.setFixCheckId(postId));
            dataSource.forEach(demoCourse -> demoCourse.setFixCheckId(workplaceId));
            //保存
            flag2 = fixCheckResultOfEnterpriseService.saveBatch(dataSource);
        }

        return flag1 && flag2;
    }
}
