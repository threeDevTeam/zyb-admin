package com.hthyaq.zybadmin.model.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.hthyaq.zybadmin.model.entity.PersonOfEnterprise;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @author Administrator
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
public class PersonOfEnterpriseView extends PersonOfEnterprise {
    private String treeSelect;
}
