package com.hthyaq.zybadmin.model.vo;

import com.hthyaq.zybadmin.model.entity.Supervise;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
public class SuperviseView extends Supervise {
    private ArrayList cascader;
}
