package com.hthyaq.zybadmin.model.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.hthyaq.zybadmin.model.entity.PostDangerOfEnterprise;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class PostDangerOfEnterpriseView  extends PostDangerOfEnterprise {
    private String treeSelect;
}
