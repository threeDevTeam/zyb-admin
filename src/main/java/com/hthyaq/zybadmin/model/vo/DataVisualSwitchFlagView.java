package com.hthyaq.zybadmin.model.vo;

import com.baomidou.mybatisplus.annotation.TableField;

public class DataVisualSwitchFlagView {
    /**
     * 国家，yes、no
     */
    @TableField("nationSwitch")
    private boolean nationSwitch;

    /**
     * 安徽省、北京市
     */
    @TableField("provinceOrCitySwitch")
    private boolean provinceOrCitySwitch;

    /**
     * 安徽省->合肥市
     */
    @TableField("citySwitch")
    private boolean citySwitch;

    /**
     * 安徽省->合肥市->庐江县、北京市->海淀区
     */
    @TableField("countryOrDistrictSwitch")
    private boolean countryOrDistrictSwitch;

    /**
     * 企业
     */
    @TableField("enterpriceSwitch")
    private boolean enterpriceSwitch;

    /**
     * 检测机构的开关
     */
    @TableField("jianceSwitch")
    private boolean jianceSwitch;

    /**
     * 体检机构的开关
     */
    @TableField("tijianSwitch")
    private boolean tijianSwitch;

    /**
     * 诊断机构的开关
     */
    @TableField("zhenduanSwitch")
    private boolean zhenduanSwitch;

    /**
     * 政府开关
     */
    @TableField("zhengfuSwitch")
    private boolean zhengfuSwitch;

}
