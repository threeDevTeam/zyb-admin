package com.hthyaq.zybadmin.model.vo;

import com.hthyaq.zybadmin.model.entity.TijianDetail2OfService;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

@Data
@Accessors(chain = true)
public class TijianDetail2OfServiceView extends TijianDetail2OfService {
    private ArrayList cascader;
    private ArrayList cascaded1;
    private ArrayList cascaded2;
    private ArrayList cascaded3;
    private ArrayList cascaded4;
    private ArrayList cascaded5;
}
