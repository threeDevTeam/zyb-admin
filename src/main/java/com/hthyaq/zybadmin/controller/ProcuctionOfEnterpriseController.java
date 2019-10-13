package com.hthyaq.zybadmin.controller;


import com.alibaba.excel.metadata.BaseRowModel;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.base.Strings;
import com.hthyaq.zybadmin.common.constants.GlobalConstants;
import com.hthyaq.zybadmin.common.excle.MyExcelUtil;
import com.hthyaq.zybadmin.model.entity.*;
import com.hthyaq.zybadmin.model.excelModel.EnterpriseModel;
import com.hthyaq.zybadmin.model.excelModel.ProcuctionOfEnterpriseModel;
import com.hthyaq.zybadmin.service.EnterpriseOfRegisterService;
import com.hthyaq.zybadmin.service.EnterpriseService;
import com.hthyaq.zybadmin.service.PersonOfEnterpriseService;
import com.hthyaq.zybadmin.service.ProcuctionOfEnterpriseService;
import org.apache.commons.compress.utils.Lists;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 生产工艺信息 前端控制器
 * </p>
 *
 * @author zhangqiang
 * @since 2019-08-30
 */
@RestController
@RequestMapping("/procuctionOfEnterprise")
public class ProcuctionOfEnterpriseController {
    @Autowired
    ProcuctionOfEnterpriseService procuctionOfEnterpriseService;
    @Autowired
    EnterpriseOfRegisterService enterpriseOfRegisterService;
    @Autowired
    EnterpriseService enterpriseService;

    @PostMapping("/add")
    public boolean add(@RequestBody ProcuctionOfEnterprise procuctionOfEnterprise, HttpSession httpSession) {
        System.out.println(procuctionOfEnterprise);
        SysUser sysUser = (SysUser) httpSession.getAttribute(GlobalConstants.LOGIN_NAME);
        QueryWrapper<EnterpriseOfRegister> queryWrapper = new QueryWrapper();
        queryWrapper.eq("id", sysUser.getCompanyId());
        List<EnterpriseOfRegister> list = enterpriseOfRegisterService.list(queryWrapper);
        for (EnterpriseOfRegister enterpriseOfRegister : list) {
            QueryWrapper<Enterprise> queryWrapper1 = new QueryWrapper();
            queryWrapper1.eq("name", enterpriseOfRegister.getName());
            List<Enterprise> list1 = enterpriseService.list(queryWrapper1);
            for (Enterprise enterprise : list1) {
                procuctionOfEnterprise.setEnterpriseId(enterprise.getId());
                procuctionOfEnterpriseService.save(procuctionOfEnterprise);
            }
        }
        return true;
    }

    @GetMapping("/delete")
    public boolean delete(String id) {
        return procuctionOfEnterpriseService.removeById(id);
    }

    @GetMapping("/getById")
    public ProcuctionOfEnterprise getById(Integer id) {

        ProcuctionOfEnterprise procuctionOfEnterprise = procuctionOfEnterpriseService.getById(id);
        //demoCourse
        QueryWrapper<ProcuctionOfEnterprise> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", id);
        List<ProcuctionOfEnterprise> demoCourseList = procuctionOfEnterpriseService.list(queryWrapper);
        //将demoCourse的数据设置到demoData
        return procuctionOfEnterprise;
    }

    @PostMapping("/edit")
    public boolean edit(@RequestBody ProcuctionOfEnterprise procuctionOfEnterprise) {
        return procuctionOfEnterpriseService.updateById(procuctionOfEnterprise);
    }

    @GetMapping("/list")
    public IPage<ProcuctionOfEnterprise> list(String json, HttpSession httpSession) {
        SysUser sysUser = (SysUser) httpSession.getAttribute(GlobalConstants.LOGIN_NAME);
        List list1 = new ArrayList();
        //字符串解析成java对象
        JSONObject jsonObject = JSON.parseObject(json);
        //从对象中获取值
        Integer currentPage = jsonObject.getInteger("currentPage");
        Integer pageSize = jsonObject.getInteger("pageSize");
        String name = jsonObject.getString("name");
        String productionType = jsonObject.getString("productionType");
        QueryWrapper<Enterprise> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.eq("name", sysUser.getCompanyName());
        List<Enterprise> list = enterpriseService.list(queryWrapper1);
        for (Enterprise enterprise : list) {
            list1.clear();
            Long id = enterprise.getId();
            list1.add(id);
        }
        QueryWrapper<ProcuctionOfEnterprise> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("enterpriseId", list1.get(0));
        if (!Strings.isNullOrEmpty(name)) {
            queryWrapper.eq("name", name);
        }
        if (!Strings.isNullOrEmpty(productionType)) {
            queryWrapper.eq("productionType", productionType);
        }
        IPage<ProcuctionOfEnterprise> page = procuctionOfEnterpriseService.page(new Page<>(currentPage, pageSize), queryWrapper);

        return page;
    }

    @PostMapping("/exceladd")
    public boolean list(String from, MultipartFile[] files, HttpSession httpSession) {
        boolean flag = true;
        //excel->model
        Class<? extends BaseRowModel>[] modelClassArr = new Class[1];
        modelClassArr[0] = ProcuctionOfEnterpriseModel.class;
        Map<String, List<Object>> modelMap = MyExcelUtil.readMoreSheetExcel(files, modelClassArr);
        //model->entity
        for (Map.Entry<String, List<Object>> entry : modelMap.entrySet()) {
            String type = entry.getKey();
            List<Object> modelList = entry.getValue();
            List<ProcuctionOfEnterprise> dataList = getDataList(modelList, type,httpSession);
            flag = procuctionOfEnterpriseService.saveBatch(dataList);
        }
        return flag;
    }

    private List<ProcuctionOfEnterprise> getDataList(List<Object> modelList, String type, HttpSession httpSession) {
        List<ProcuctionOfEnterprise> dataList = Lists.newArrayList();
        for (Object object : modelList) {
            ProcuctionOfEnterpriseModel procuctionOfEnterpriseModel = (ProcuctionOfEnterpriseModel) object;
            //业务处理
            ProcuctionOfEnterprise procuctionOfEnterprise = new ProcuctionOfEnterprise();
            SysUser sysUser = (SysUser) httpSession.getAttribute(GlobalConstants.LOGIN_NAME);
            QueryWrapper<EnterpriseOfRegister> queryWrapper = new QueryWrapper();
            queryWrapper.eq("id", sysUser.getCompanyId());
            List<EnterpriseOfRegister> list = enterpriseOfRegisterService.list(queryWrapper);
            for (EnterpriseOfRegister enterpriseOfRegister : list) {
                QueryWrapper<Enterprise> queryWrapper1 = new QueryWrapper();
                queryWrapper1.eq("name", enterpriseOfRegister.getName());
                List<Enterprise> list1 = enterpriseService.list(queryWrapper1);
                for (Enterprise enterprise : list1) {
                    procuctionOfEnterprise.setEnterpriseId(enterprise.getId());
                }
                BeanUtils.copyProperties(procuctionOfEnterpriseModel, procuctionOfEnterprise);
                dataList.add(procuctionOfEnterprise);
            }
        }
        return dataList;
    }
}
