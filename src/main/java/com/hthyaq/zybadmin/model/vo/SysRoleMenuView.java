package com.hthyaq.zybadmin.model.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.HashSet;

@Data
@Accessors(chain = true)
public class SysRoleMenuView {
    private HashSet<Integer> checkbox;
    private HashSet<Integer> checkbox2;
    private HashSet<Integer> checkbox3;
    private HashSet<Integer> checkbox4;
}
