package com.hthyaq.zybadmin.model.vo;

import com.hthyaq.zybadmin.model.entity.JianceBasicOfService;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
public class JianceBasicOfView extends JianceBasicOfService {
    private ArrayList cascader;
    private ArrayList cascaded1;
}
