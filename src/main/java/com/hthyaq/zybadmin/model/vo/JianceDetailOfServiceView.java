package com.hthyaq.zybadmin.model.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.hthyaq.zybadmin.model.bean.Child2;
import com.hthyaq.zybadmin.model.entity.JianceDetailResultOfService;
import com.hthyaq.zybadmin.model.entity.JianceDetailOfService;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

//demo的页面数据
@Data
@Accessors(chain = true)
public class JianceDetailOfServiceView extends JianceDetailOfService {
    private Child2<JianceDetailResultOfService> course;
    private ArrayList cascader;
    private ArrayList cascaded1;
    private ArrayList cascaded2;
    private ArrayList cascaded3;
    private ArrayList cascaded4;
}
