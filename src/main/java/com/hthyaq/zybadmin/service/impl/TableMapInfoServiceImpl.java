package com.hthyaq.zybadmin.service.impl;

import com.hthyaq.zybadmin.model.entity.TableMapInfo;
import com.hthyaq.zybadmin.mapper.TableMapInfoMapper;
import com.hthyaq.zybadmin.service.TableMapInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 中英文表名、字段名的映射信息 服务实现类
 * </p>
 *
 * @author zhangqiang
 * @since 2019-09-01
 */
@Service
public class TableMapInfoServiceImpl extends ServiceImpl<TableMapInfoMapper, TableMapInfo> implements TableMapInfoService {

}
