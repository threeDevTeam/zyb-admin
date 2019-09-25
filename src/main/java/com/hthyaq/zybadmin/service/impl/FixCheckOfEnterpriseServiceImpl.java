package com.hthyaq.zybadmin.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hthyaq.zybadmin.model.entity.Demo;
import com.hthyaq.zybadmin.model.entity.DemoCourse;
import com.hthyaq.zybadmin.model.entity.FixCheckOfEnterprise;
import com.hthyaq.zybadmin.mapper.FixCheckOfEnterpriseMapper;
import com.hthyaq.zybadmin.model.entity.FixCheckResultOfEnterprise;
import com.hthyaq.zybadmin.model.vo.FixCheckOfView;
import com.hthyaq.zybadmin.service.FixCheckOfEnterpriseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hthyaq.zybadmin.service.FixCheckResultOfEnterpriseService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    @Override
    public boolean editData(FixCheckOfView fixCheckOfView) {
        boolean flag1, flag2 = true;

        FixCheckOfEnterprise fixCheckOfEnterprise=new FixCheckOfEnterprise();

        BeanUtils.copyProperties(fixCheckOfView, fixCheckOfEnterprise);

        flag1 = this.save(fixCheckOfEnterprise);

        List<FixCheckResultOfEnterprise> dataSource = fixCheckOfView.getCourse().getDataSource();

        if (ObjectUtil.length(dataSource) > 0) {
            //设置demoCourse的demo_id
            long demoId = fixCheckOfEnterprise.getId();
            dataSource.forEach(demoCourse -> demoCourse.setFixCheckId(demoId));
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
    public boolean saveData(FixCheckOfView fixCheckOfView) {
        boolean flag1, flag2 = true;

        //demo
        FixCheckOfEnterprise fixCheckOfEnterprise=new FixCheckOfEnterprise();
        BeanUtils.copyProperties(fixCheckOfView, fixCheckOfEnterprise);
        flag1 = this.updateById(fixCheckOfEnterprise);

        //demoCourse，先删除，后插入
        QueryWrapper<FixCheckResultOfEnterprise> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("fixCheckId", fixCheckOfView.getId());
        flag2 = fixCheckResultOfEnterpriseService.remove(queryWrapper);

        List<FixCheckResultOfEnterprise> dataSource = fixCheckOfView.getCourse().getDataSource();
        if (ObjectUtil.length(dataSource) > 0) {
            //设置demoCourse的demo_id
            dataSource.forEach(demoCourse -> demoCourse.setFixCheckId(fixCheckOfEnterprise.getId()));
            //保存
            flag2 = fixCheckResultOfEnterpriseService.saveBatch(dataSource);
        }

        return flag1 && flag2;
    }
}
