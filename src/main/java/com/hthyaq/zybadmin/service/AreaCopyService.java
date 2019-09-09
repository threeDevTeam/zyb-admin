package com.hthyaq.zybadmin.service;

import com.hthyaq.zybadmin.model.entity.AreaCopy;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 省市县三级的行政区域数据 服务类
 * </p>
 *
 * @author zhangqiang
 * @since 2019-09-04
 */
public interface AreaCopyService extends IService<AreaCopy> {

    boolean seletshen();

}
