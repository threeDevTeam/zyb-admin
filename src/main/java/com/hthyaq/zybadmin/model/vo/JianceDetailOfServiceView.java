package com.hthyaq.zybadmin.model.vo;

import com.hthyaq.zybadmin.model.bean.Child2;
import com.hthyaq.zybadmin.model.entity.JianceDetailResultOfService;
import com.hthyaq.zybadmin.model.entity.JianceDetailOfService;
import lombok.Data;
import lombok.experimental.Accessors;

//demo的页面数据
@Data
@Accessors(chain = true)
public class JianceDetailOfServiceView extends JianceDetailOfService {
    private Child2<JianceDetailResultOfService> course;
}
