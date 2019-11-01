package com.hthyaq.zybadmin.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hthyaq.zybadmin.model.entity.CheckOfEnterprise;
import com.hthyaq.zybadmin.model.entity.DataVisualSwitchFlag;
import com.hthyaq.zybadmin.model.entity.SysUser;
import com.hthyaq.zybadmin.model.vo.DataVisualSwitchFlagView;
import com.hthyaq.zybadmin.service.DataVisualSwitchFlagService;
import com.hthyaq.zybadmin.service.SysRoleUserService;
import com.sun.mail.imap.protocol.ID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

/**
 * <p>
 * 数据可视化的真假数据的开关 前端控制器
 * </p>
 *
 * @author zhangqiang
 * @since 2019-11-01
 */
@RestController
@RequestMapping("/dataVisualSwitchFlag")
public class DataVisualSwitchFlagController {
    @Autowired
    DataVisualSwitchFlagService dataVisualSwitchFlagService;
    @PostMapping("/up")
    public boolean up( @RequestBody DataVisualSwitchFlag dataVisualSwitchFlag) {
        boolean flag=false;
        System.out.println(dataVisualSwitchFlag);
        if(dataVisualSwitchFlag.getNationSwitch().equals("开")){
            dataVisualSwitchFlag.setNationSwitch("yes");
        }else {
            dataVisualSwitchFlag.setNationSwitch("no");
        }
        if(dataVisualSwitchFlag.getProvinceOrCitySwitch().equals("开")){
            dataVisualSwitchFlag.setProvinceOrCitySwitch("yes");
        }else {
            dataVisualSwitchFlag.setProvinceOrCitySwitch("no");
        }
        if(dataVisualSwitchFlag.getCitySwitch().equals("开")){
            dataVisualSwitchFlag.setCitySwitch("yes");
        }else {
            dataVisualSwitchFlag.setCitySwitch("no");
        }
        if(dataVisualSwitchFlag.getCountryOrDistrictSwitch().equals("开")){
            dataVisualSwitchFlag.setCountryOrDistrictSwitch("yes");
        }else {
            dataVisualSwitchFlag.setCountryOrDistrictSwitch("no");
        }
        if(dataVisualSwitchFlag.getEnterpriceSwitch().equals("开")){
            dataVisualSwitchFlag.setEnterpriceSwitch("yes");
        }else {
            dataVisualSwitchFlag.setEnterpriceSwitch("no");
        }if(dataVisualSwitchFlag.getJianceSwitch().equals("开")){
            dataVisualSwitchFlag.setJianceSwitch("yes");
        }else {
            dataVisualSwitchFlag.setJianceSwitch("no");
        }
        if(dataVisualSwitchFlag.getTijianSwitch().equals("开")){
            dataVisualSwitchFlag.setTijianSwitch("yes");
        }else {
            dataVisualSwitchFlag.setTijianSwitch("no");
        }
        if(dataVisualSwitchFlag.getZhenduanSwitch().equals("开")){
            dataVisualSwitchFlag.setZhenduanSwitch("yes");
        }else {
            dataVisualSwitchFlag.setZhenduanSwitch("no");
        }
        if(dataVisualSwitchFlag.getZhengfuSwitch().equals("开")){
            dataVisualSwitchFlag.setZhengfuSwitch("yes");
        }else {
            dataVisualSwitchFlag.setZhengfuSwitch("no");
        }
        dataVisualSwitchFlag.setId(1);
        flag=dataVisualSwitchFlagService.updateById(dataVisualSwitchFlag);
        return flag;
    }
    @GetMapping("/list")
    public DataVisualSwitchFlag list() {
        QueryWrapper<DataVisualSwitchFlag> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("id",1);
        DataVisualSwitchFlag dataVisualSwitchFlag = dataVisualSwitchFlagService.getOne(queryWrapper);
        if(dataVisualSwitchFlag.getNationSwitch().equals("yes")){
            dataVisualSwitchFlag.setNationSwitch("开");
        }else {
            dataVisualSwitchFlag.setNationSwitch("关");
        }
        if(dataVisualSwitchFlag.getProvinceOrCitySwitch().equals("yes")){
            dataVisualSwitchFlag.setProvinceOrCitySwitch("开");
        }else {
            dataVisualSwitchFlag.setProvinceOrCitySwitch("关");
        }
        if(dataVisualSwitchFlag.getCitySwitch().equals("yes")){
            dataVisualSwitchFlag.setCitySwitch("开");
        }else {
            dataVisualSwitchFlag.setCitySwitch("关");
        }
        if(dataVisualSwitchFlag.getCountryOrDistrictSwitch().equals("yes")){
            dataVisualSwitchFlag.setCountryOrDistrictSwitch("开");
        }else {
            dataVisualSwitchFlag.setCountryOrDistrictSwitch("关");
        }
        if(dataVisualSwitchFlag.getEnterpriceSwitch().equals("yes")){
            dataVisualSwitchFlag.setEnterpriceSwitch("开");
        }else {
            dataVisualSwitchFlag.setEnterpriceSwitch("关");
        }if(dataVisualSwitchFlag.getJianceSwitch().equals("yes")){
            dataVisualSwitchFlag.setJianceSwitch("开");
        }else {
            dataVisualSwitchFlag.setJianceSwitch("关");
        }
        if(dataVisualSwitchFlag.getTijianSwitch().equals("yes")){
            dataVisualSwitchFlag.setTijianSwitch("开");
        }else {
            dataVisualSwitchFlag.setTijianSwitch("关");
        }
        if(dataVisualSwitchFlag.getZhenduanSwitch().equals("yes")){
            dataVisualSwitchFlag.setZhenduanSwitch("开");
        }else {
            dataVisualSwitchFlag.setZhenduanSwitch("关");
        }
        if(dataVisualSwitchFlag.getZhengfuSwitch().equals("yes")){
            dataVisualSwitchFlag.setZhengfuSwitch("开");
        }else {
            dataVisualSwitchFlag.setZhengfuSwitch("关");
        }
        return dataVisualSwitchFlag;
    }
}
