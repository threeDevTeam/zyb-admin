package com.hthyaq.zybadmin.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.base.Strings;
import com.hthyaq.zybadmin.common.constants.GlobalConstants;
import com.hthyaq.zybadmin.model.entity.*;
import com.hthyaq.zybadmin.model.vo.DemoView;
import com.hthyaq.zybadmin.model.vo.SuperviseView;
import com.hthyaq.zybadmin.service.AreaOfDicService;
import com.hthyaq.zybadmin.service.SuperviseOfRegisterService;
import com.hthyaq.zybadmin.service.SuperviseService;
import org.apache.commons.compress.utils.Lists;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 监管部门信息


 前端控制器
 * </p>
 *
 * @author zhangqiang
 * @since 2019-08-30
 */
@RestController
@RequestMapping("/supervise")

public class SuperviseController {
    @Autowired
    SuperviseService superviseService;
    @Autowired
    AreaOfDicService areaOfDicService;
    @Autowired
    SuperviseOfRegisterService superviseOfRegisterService;
    @PostMapping("/add")
    public boolean add(@RequestBody Supervise supervise, HttpSession httpSession) {
        boolean flag=false;
        SysUser sysUser = (SysUser) httpSession.getAttribute(GlobalConstants.LOGIN_NAME);
        QueryWrapper<SuperviseOfRegister> queryWrapper=new QueryWrapper();
        queryWrapper.eq("id",sysUser.getCompanyId());
        List<SuperviseOfRegister> list = superviseOfRegisterService.list(queryWrapper);
        for (SuperviseOfRegister superviseOfRegister : list) {
            supervise.setProvinceName(superviseOfRegister.getProvinceName());
            supervise.setProvinceCode(superviseOfRegister.getProvinceCode());
            supervise.setCityName(superviseOfRegister.getCityName());
            supervise.setCityCode(superviseOfRegister.getCityCode());
            supervise.setDistrictName(superviseOfRegister.getDistrictName());
            supervise.setDistrictCode(superviseOfRegister.getDistrictCode());
            supervise.setRegisterAddress(superviseOfRegister.getRegisterAddress());
            supervise.setName(superviseOfRegister.getName());
            flag=superviseService.saveData(supervise);
        }
        return flag;
    }
    @GetMapping("/delete")
    public boolean delete(String id) {
        return superviseService.deleteData(id);
    }
    @GetMapping("/getById")
    public Supervise getById(Integer id) {
        List list=new ArrayList();
        SuperviseView superviseView=new SuperviseView();
        Supervise supervise = superviseService.getById(id);
        BeanUtils.copyProperties(supervise, superviseView);
        String provinceName = supervise.getProvinceName();
        String cityName = supervise.getCityName();
        String districtName = supervise.getDistrictName();
        list.add(provinceName);
        if(cityName.equals(districtName)){
            list.add(cityName);
        }else {
            list.add(cityName);
            list.add(districtName);
        }


        superviseView.setCascader(list);
        return superviseView;
    }
    @PostMapping("/edit")
    public boolean edit(@RequestBody Supervise supervise) {
        return superviseService.editData(supervise);
    }

    @GetMapping("/list")
    public IPage<Supervise> list(String json, HttpSession httpSession) {
        SysUser sysUser = (SysUser) httpSession.getAttribute(GlobalConstants.LOGIN_NAME);

        //字符串解析成java对象
        JSONObject jsonObject = JSON.parseObject(json);
        //从对象中获取值
        Integer currentPage = jsonObject.getInteger("currentPage");
        Integer pageSize = jsonObject.getInteger("pageSize");
        String year = jsonObject.getString("year");
        String name = jsonObject.getString("name");
        QueryWrapper<Supervise> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id",sysUser.getCompanyId());
        if (!Strings.isNullOrEmpty(year)) {
            queryWrapper.eq("year", year);
        }
        if (!Strings.isNullOrEmpty(name)) {
            queryWrapper.eq("name", name);
        }

        IPage<Supervise> page = superviseService.page(new Page<>(currentPage, pageSize), queryWrapper);

        return page;
    }
    @GetMapping("/TreeSelcetData")
    public List<TreeSelcetData> TreeSelcetData() {
        List<TreeSelcetData> treeSelcetDatalist = new ArrayList();

        QueryWrapper<AreaOfDic> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("pid", "-1");
        List<AreaOfDic> list = areaOfDicService.list(queryWrapper);
        for (AreaOfDic areaCopy : list) {
            List<TreeSelcetData> chilren = Lists.newArrayList();
            TreeSelcetData treeSelcetData = new TreeSelcetData();
            treeSelcetData.setLabel(areaCopy.getName());
            treeSelcetData.setValue(areaCopy.getName());
            treeSelcetData.setChildren(chilren);
            treeSelcetDatalist.add(treeSelcetData);

            QueryWrapper<AreaOfDic> queryWrapper1 = new QueryWrapper<>();
            queryWrapper1.eq("pid", areaCopy.getId());
            List<AreaOfDic> list1 = areaOfDicService.list(queryWrapper1);

            for (AreaOfDic areaCopy1 : list1) {
                TreeSelcetData treeSelcetData1 = new TreeSelcetData();
                treeSelcetData1.setLabel(areaCopy1.getName());
                treeSelcetData1.setValue(areaCopy1.getName());
                chilren.add(treeSelcetData1);
                List<TreeSelcetData> chilren2= Lists.newArrayList();
                QueryWrapper<AreaOfDic> queryWrapper2=new QueryWrapper<>();
                queryWrapper2.eq("pid",areaCopy1.getId());
                List<AreaOfDic> list2 = areaOfDicService.list(queryWrapper2);

                for (AreaOfDic areaCopy2 : list2) {
                    TreeSelcetData treeSelcetData2=new TreeSelcetData();
                    treeSelcetData2.setLabel(areaCopy2.getName());
                    treeSelcetData2.setValue(areaCopy2.getName());
                    chilren2.add(treeSelcetData2);
                }

                treeSelcetData1.setChildren(chilren2);
            }
        }
        return treeSelcetDatalist;
    }
}
