package com.hthyaq.zybadmin.model.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.hthyaq.zybadmin.model.entity.TouchPersonOfEnterprise;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class TouchPersonOfEnterpriseView extends TouchPersonOfEnterprise {
    private String treeSelect;
    /**
     * 出生日期，例如20190101
     */
    private String birth;

    /**
     * 上岗时间，例如20190101
     */
    @TableField("startDate")
    private String startDate;

    /**
     * 离岗时间，例如20190101
     */
    @TableField("leaveDate")
    private String leaveDate;

}
