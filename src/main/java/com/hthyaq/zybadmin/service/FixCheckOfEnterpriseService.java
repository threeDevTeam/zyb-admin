package com.hthyaq.zybadmin.service;

import com.hthyaq.zybadmin.model.entity.FixCheckOfEnterprise;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hthyaq.zybadmin.model.vo.FixCheckOfView;

import javax.servlet.http.HttpSession;

/**
 * <p>
 * 定期检测信息 服务类
 * </p>
 *
 * @author zhangqiang
 * @since 2019-09-02
 */
public interface FixCheckOfEnterpriseService extends IService<FixCheckOfEnterprise> {

    boolean editData(FixCheckOfView fixCheckOfView);

    boolean deleteData(String id);

    boolean saveData(FixCheckOfView fixCheckOfView, HttpSession httpSession);
}
