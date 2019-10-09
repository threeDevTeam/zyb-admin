package com.hthyaq.zybadmin.model.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.hthyaq.zybadmin.model.entity.PostOfEnterprise;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.ArrayList;

/**
 * @author Administrator
 */
@Data
@Accessors(chain = true)
public class PostOfEnterpriseView extends  PostOfEnterprise {
    private String treeSelect;
    private ArrayList cascaded1;

}
