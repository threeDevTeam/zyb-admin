package com.hthyaq.zybadmin.model.vo;

import com.hthyaq.zybadmin.model.entity.SysMenu;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.ArrayList;
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
public class SysMenuView extends SysMenu {
    private ArrayList cascader;

}
