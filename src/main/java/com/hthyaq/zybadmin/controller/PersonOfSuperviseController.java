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
import com.hthyaq.zybadmin.model.excelModel.LawOfSuperviseModel;
import com.hthyaq.zybadmin.model.excelModel.PersonOfSuperviseModel;
import com.hthyaq.zybadmin.model.excelModel.SuperviseModel;
import com.hthyaq.zybadmin.service.PersonOfSuperviseService;
import com.hthyaq.zybadmin.service.SuperviseOfRegisterService;
import com.hthyaq.zybadmin.service.SuperviseService;
import com.hthyaq.zybadmin.service.SysRoleUserService;
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
 * 监管人员信息
 * <p>
 * <p>
 * <p>
 * <p>
 * <p>
 * <p>
 * <p>
 * <p>
 * 前端控制器
 * </p>
 *
 * @author zhangqiang
 * @since 2019-08-30
 */
@RestController
@RequestMapping("/personOfSupervise")
public class PersonOfSuperviseController {
    @Autowired
    SuperviseOfRegisterService superviseOfRegisterService;
    @Autowired
    PersonOfSuperviseService personOfSuperviseService;
    @Autowired
    SuperviseService superviseService;
    @Autowired
    SysRoleUserService sysRoleUserService;

    @PostMapping("/add")
    public boolean add(@RequestBody PersonOfSupervise personOfSupervise, HttpSession httpSession) {
        boolean flag = false;
        SysUser sysUser = (SysUser) httpSession.getAttribute(GlobalConstants.LOGIN_NAME);
        QueryWrapper<SuperviseOfRegister> queryWrapper = new QueryWrapper();
        queryWrapper.eq("id", sysUser.getCompanyId());
        List<SuperviseOfRegister> list = superviseOfRegisterService.list(queryWrapper);
        for (SuperviseOfRegister superviseOfRegister : list) {
            QueryWrapper<Supervise> queryWrapper1 = new QueryWrapper();
            queryWrapper1.eq("name", superviseOfRegister.getName());
            List<Supervise> list1 = superviseService.list(queryWrapper1);
            for (Supervise supervise : list1) {
                personOfSupervise.setSuperviseId(supervise.getId());
                flag = personOfSuperviseService.save(personOfSupervise);
            }
        }
        return flag;
    }

    @GetMapping("/delete")
    public boolean delete(String id) {
        return personOfSuperviseService.removeById(id);
    }

    @GetMapping("/getById")
    public PersonOfSupervise getById(Integer id) {

        PersonOfSupervise personOfSupervise = personOfSuperviseService.getById(id);
        //demoCourse
        QueryWrapper<PersonOfSupervise> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", id);
        List<PersonOfSupervise> demoCourseList = personOfSuperviseService.list(queryWrapper);
        //将demoCourse的数据设置到demoData
        return personOfSupervise;
    }

    @PostMapping("/edit")
    public boolean edit(@RequestBody PersonOfSupervise personOfSupervise) {
        return personOfSuperviseService.updateById(personOfSupervise);
    }

    @GetMapping("/list")
    public IPage<PersonOfSupervise> list(String json, HttpSession httpSession) {
        SysUser sysUser = (SysUser) httpSession.getAttribute(GlobalConstants.LOGIN_NAME);
        List list1 = new ArrayList();
        //字符串解析成java对象
        JSONObject jsonObject = JSON.parseObject(json);
        //从对象中获取值
        Integer currentPage = jsonObject.getInteger("currentPage");
        Integer pageSize = jsonObject.getInteger("pageSize");
        String name = jsonObject.getString("name");
        String idNum = jsonObject.getString("idNum");
        QueryWrapper<SysRoleUser> qw = new QueryWrapper<>();
        qw.eq("userId", sysUser.getId());
        SysRoleUser sysRoleUser = sysRoleUserService.getOne(qw);
        if (sysRoleUser.getRoleId() == 1) {
            QueryWrapper<PersonOfSupervise> queryWrapper = new QueryWrapper<>();
            if (!Strings.isNullOrEmpty(name)) {
                queryWrapper.eq("name", name);
            }
            if (!Strings.isNullOrEmpty(idNum)) {
                queryWrapper.eq("idNum", idNum);
            }

            IPage<PersonOfSupervise> page = personOfSuperviseService.page(new Page<>(currentPage, pageSize), queryWrapper);

            return page;
        } else {
            QueryWrapper<Supervise> queryWrapper1 = new QueryWrapper<>();
            queryWrapper1.eq("name", sysUser.getCompanyName());
            List<Supervise> list = superviseService.list(queryWrapper1);
            for (Supervise supervise : list) {
                list1.clear();
                Long id = supervise.getId();
                list1.add(id);
            }
            QueryWrapper<PersonOfSupervise> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("superviseId", list1.get(0));
            if (!Strings.isNullOrEmpty(name)) {
                queryWrapper.eq("name", name);
            }
            if (!Strings.isNullOrEmpty(idNum)) {
                queryWrapper.eq("idNum", idNum);
            }

            IPage<PersonOfSupervise> page = personOfSuperviseService.page(new Page<>(currentPage, pageSize), queryWrapper);

            return page;
        }
    }
    @PostMapping("/exceladd")
    public boolean list(String from, MultipartFile[] files, HttpSession httpSession) {
        boolean flag = true;
        //excel->model
        Class<? extends BaseRowModel>[] modelClassArr = new Class[1];
        modelClassArr[0]= PersonOfSuperviseModel.class;
        Map<String, List<Object>> modelMap = MyExcelUtil.readMoreSheetExcel(files,modelClassArr);
        //model->entity
        for (Map.Entry<String, List<Object>> entry : modelMap.entrySet()) {
            String type = entry.getKey();
            List<Object> modelList = entry.getValue();
            List<PersonOfSupervise> dataList = getDataList(modelList, type, httpSession);
            flag = personOfSuperviseService.saveBatch(dataList);
        }
        return flag;
    }

    private List<PersonOfSupervise> getDataList(List<Object> modelList, String type, HttpSession httpSession) {
        List<PersonOfSupervise> dataList = Lists.newArrayList();
        for (Object object : modelList) {
            PersonOfSuperviseModel personOfSuperviseModel = (PersonOfSuperviseModel) object;
            //业务处理
            PersonOfSupervise personOfSupervise = new PersonOfSupervise();
            SysUser sysUser = (SysUser) httpSession.getAttribute(GlobalConstants.LOGIN_NAME);
            QueryWrapper<SuperviseOfRegister> queryWrapper = new QueryWrapper();
            queryWrapper.eq("id", sysUser.getCompanyId());
            List<SuperviseOfRegister> list = superviseOfRegisterService.list(queryWrapper);
            for (SuperviseOfRegister superviseOfRegister : list) {
                QueryWrapper<Supervise> queryWrapper1 = new QueryWrapper();
                queryWrapper1.eq("name", superviseOfRegister.getName());
                List<Supervise> list1 = superviseService.list(queryWrapper1);
                for (Supervise supervise : list1) {
                    personOfSupervise.setSuperviseId(supervise.getId());
                }
            }
            BeanUtils.copyProperties(personOfSuperviseModel, personOfSupervise);
            dataList.add(personOfSupervise);
        }
        return dataList;
    }
}
