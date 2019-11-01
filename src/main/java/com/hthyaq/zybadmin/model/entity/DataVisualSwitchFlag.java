package com.hthyaq.zybadmin.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 数据可视化的真假数据的开关
 * </p>
 *
 * @author zhangqiang
 * @since 2019-11-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("dataVisualSwitchFlag")
public class DataVisualSwitchFlag implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 国家，yes、no
     */
    @TableField("nationSwitch")
    private String nationSwitch;

    /**
     * 安徽省、北京市
     */
    @TableField("provinceOrCitySwitch")
    private String provinceOrCitySwitch;

    /**
     * 安徽省->合肥市
     */
    @TableField("citySwitch")
    private String citySwitch;

    /**
     * 安徽省->合肥市->庐江县、北京市->海淀区
     */
    @TableField("countryOrDistrictSwitch")
    private String countryOrDistrictSwitch;

    /**
     * 企业
     */
    @TableField("enterpriceSwitch")
    private String enterpriceSwitch;

    /**
     * 检测机构的开关
     */
    @TableField("jianceSwitch")
    private String jianceSwitch;

    /**
     * 体检机构的开关
     */
    @TableField("tijianSwitch")
    private String tijianSwitch;

    /**
     * 诊断机构的开关
     */
    @TableField("zhenduanSwitch")
    private String zhenduanSwitch;

    /**
     * 政府开关
     */
    @TableField("zhengfuSwitch")
    private String zhengfuSwitch;


}
