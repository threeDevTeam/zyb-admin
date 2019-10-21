package com.hthyaq.zybadmin.model.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.ArrayList;

@Data
@Accessors(chain = true)
public class SysRoleUserView {
    private ArrayList checkbox;
}
