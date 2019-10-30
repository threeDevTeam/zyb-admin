package com.hthyaq.zybadmin.controller.dataVisual.nation.vo;

import lombok.Data;

import java.util.List;

@Data
public class StrList {
    //区域、行业、注册类型
    private List<String> flagList;
    private List<Integer> zero;
    private List<Integer> one;
    private List<Integer> two;
    private List<Integer> three;
}
