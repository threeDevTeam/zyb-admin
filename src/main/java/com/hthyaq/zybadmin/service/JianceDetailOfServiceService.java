package com.hthyaq.zybadmin.service;

import com.hthyaq.zybadmin.model.entity.JianceDetailOfService;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hthyaq.zybadmin.model.vo.JianceDetailOfServiceView;

import javax.servlet.http.HttpSession;

/**
 * <p>
 * 检测机构的具体报告

检测机构的具体报告






















 服务类
 * </p>
 *
 * @author zhangqiang
 * @since 2019-08-30
 */
public interface JianceDetailOfServiceService extends IService<JianceDetailOfService> {

    boolean saveData(JianceDetailOfServiceView jianceDetailOfServiceView, HttpSession httpSession);

    boolean deleteData(String id);

    boolean editData(JianceDetailOfServiceView jianceDetailOfServiceView);
}
