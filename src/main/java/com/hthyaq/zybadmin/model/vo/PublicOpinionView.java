package com.hthyaq.zybadmin.model.vo;


import com.hthyaq.zybadmin.model.entity.PublicOpinion;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.HashSet;

@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
public class PublicOpinionView extends PublicOpinion {

    private HashSet<Integer> typename;
}
