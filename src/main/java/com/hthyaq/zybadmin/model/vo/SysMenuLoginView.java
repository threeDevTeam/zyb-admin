package com.hthyaq.zybadmin.model.vo;

import com.hthyaq.zybadmin.model.bean.Child3;
import com.hthyaq.zybadmin.model.entity.FixCheckResultOfEnterprise;
import com.hthyaq.zybadmin.model.entity.SysMenu;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.ArrayList;

@Data
@Accessors(chain = true)
public class SysMenuLoginView  extends SysMenu {
    private ArrayList children;
}
