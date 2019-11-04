package com.hthyaq.zybadmin.model.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.hthyaq.zybadmin.model.entity.PostDangerOfEnterprise;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.ArrayList;
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
public class PostDangerOfEnterpriseView  extends PostDangerOfEnterprise {
    private String treeSelect;
    private ArrayList cascaded1;
    private ArrayList cascaded2;
    private String upDateeStr;
}
