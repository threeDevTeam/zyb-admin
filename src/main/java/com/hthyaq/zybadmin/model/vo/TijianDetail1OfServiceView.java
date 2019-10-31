package com.hthyaq.zybadmin.model.vo;

import com.hthyaq.zybadmin.model.entity.TijianDetail1OfService;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

@Data
@Accessors(chain = true)
public class TijianDetail1OfServiceView extends TijianDetail1OfService {
    private ArrayList cascader;
    private ArrayList cascaded1;
    private ArrayList cascaded2;
    private ArrayList cascaded3;
    private String checkDateStr;
}
