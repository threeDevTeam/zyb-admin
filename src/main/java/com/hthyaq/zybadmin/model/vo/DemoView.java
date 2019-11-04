package com.hthyaq.zybadmin.model.vo;

import com.hthyaq.zybadmin.model.bean.Child;
import com.hthyaq.zybadmin.model.entity.Demo;
import com.hthyaq.zybadmin.model.entity.DemoCourse;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
@EqualsAndHashCode(callSuper = true)
//demo的页面数据
@Data
@Accessors(chain = false)
public class DemoView extends Demo {
    private Child<DemoCourse> course;
}
