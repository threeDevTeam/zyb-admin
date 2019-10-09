package com.hthyaq.zybadmin.model.entity;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class ResourceStatistics  implements Serializable {
    private int id;
    private int workerNumber;
    private int idNum;
    private int idNum2;
    private int amount;
    private int amount2;

}
