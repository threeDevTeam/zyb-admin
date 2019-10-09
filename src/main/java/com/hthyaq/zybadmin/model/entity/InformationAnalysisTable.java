package com.hthyaq.zybadmin.model.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class InformationAnalysisTable {
    //用人单位数
    private int idNum;
    //从业人数
    private int engagedNum;
    //接触职业病危害人数
    private int  hazardsNum;
    //接害率
    private double Damagerate1;
    //接触粉尘危害人数
    private int hazardsNumf;
    //接尘率
    private double Damagerate2;
    //接触化学因素危害人数
    private int hazardsNumh;
    //接毒率
    private double Damagerate3;
    //接触物理因素危害人数
    private int hazardsNumw;
    //接触物理因素危害率
    private double Damagerate4;
    //接触放射性因素危害人数
    private int hazardsNumS;
    //接触放射性因素危害率
    private double Damagerate5;
    //接触生物因素危害人数
    private int hazardsNumsw;
    //接触生物因素危害率
    private double Damagerate6;
}
