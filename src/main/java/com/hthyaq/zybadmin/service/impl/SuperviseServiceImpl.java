package com.hthyaq.zybadmin.service.impl;

import com.hthyaq.zybadmin.model.entity.Supervise;
import com.hthyaq.zybadmin.mapper.SuperviseMapper;
import com.hthyaq.zybadmin.service.SuperviseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 监管部门信息












 服务实现类
 * </p>
 *
 * @author zhangqiang
 * @since 2019-08-30
 */
@Service
public class SuperviseServiceImpl extends ServiceImpl<SuperviseMapper, Supervise> implements SuperviseService {

    @Override
    public boolean editData(Supervise supervise) {
        this.updateById(supervise);
        return true;
    }

    @Override
    public boolean saveData(Supervise supervise) {
        this.save(supervise);
        return true;
    }

    @Override
    public boolean deleteData(String id) {
        this.removeById(id);
        return true;
    }
}
