package com.hthyaq.zybadmin.model.vo;

import com.hthyaq.zybadmin.model.bean.Child;
import com.hthyaq.zybadmin.model.entity.Demo;
import com.hthyaq.zybadmin.model.entity.DemoCourse;
import lombok.Data;
import lombok.experimental.Accessors;

//demo的页面数据
@Data
@Accessors(chain = true)
public class DemoView extends Demo {
    private Child<DemoCourse> course;
}
