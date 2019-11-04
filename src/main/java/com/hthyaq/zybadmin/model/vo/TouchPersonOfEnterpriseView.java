package com.hthyaq.zybadmin.model.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.hthyaq.zybadmin.model.entity.TouchPersonOfEnterprise;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
public class TouchPersonOfEnterpriseView extends TouchPersonOfEnterprise {
    private String treeSelect;
    /**
     * 出生日期，例如20190101
     */
    private String birthStr;

    /**
     * 上岗时间，例如20190101
     */
    @TableField("startDate")
    private String startDateStr;

    /**
     * 离岗时间，例如20190101
     */
    @TableField("leaveDate")
    private String leaveDateStr;

}
