package com.hthyaq.zybadmin.controller;


import ch.qos.logback.classic.turbo.TurboFilter;
import com.alibaba.excel.metadata.BaseRowModel;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.hthyaq.zybadmin.common.constants.GlobalConstants;
import com.hthyaq.zybadmin.common.excle.MyExcelUtil;
import com.hthyaq.zybadmin.model.entity.*;
import com.hthyaq.zybadmin.model.excelModel.EnterpriseModel;
import com.hthyaq.zybadmin.model.excelModel.PersonOfEnterpriseModel;
import com.hthyaq.zybadmin.model.excelModel.PostOfEnterpriseModel;
import com.hthyaq.zybadmin.model.vo.PersonOfEnterpriseView;
import com.hthyaq.zybadmin.service.*;
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
 * 人员信息 前端控制器
 * </p>
 *
 * @author zhangqiang
 * @since 2019-08-30
 */
@RestController
@RequestMapping("/personOfEnterprise")
public class PersonOfEnterpriseController {
    @Autowired
    PersonOfEnterpriseService personOfEnterpriseService;
    @Autowired
    EnterpriseOfRegisterService enterpriseOfRegisterService;
    @Autowired
    EnterpriseService enterpriseService;
    @Autowired
    PostOfEnterpriseService postOfEnterpriseService;
    @Autowired
    WorkplaceOfEnterpriseService workplaceOfEnterpriseService;
    @Autowired
    SysUserService sysUserService;
    @PostMapping("/add")
    public boolean add(@RequestBody PersonOfEnterpriseView personOfEnterpriseView, HttpSession httpSession) {
        boolean flag = false;

        PersonOfEnterprise personOfEnterprise = new PersonOfEnterprise();
        //other
        BeanUtils.copyProperties(personOfEnterpriseView, personOfEnterprise);

        //enterpriseId
        SysUser sysUser = (SysUser) httpSession.getAttribute(GlobalConstants.LOGIN_NAME);
        QueryWrapper<Enterprise> queryWrapper1=new QueryWrapper();
        queryWrapper1.eq("name",sysUser.getCompanyName());
        List<Enterprise> list1 = enterpriseService.list(queryWrapper1);
        for (Enterprise enterprise : list1) {
            personOfEnterprise.setEnterpriseId(enterprise.getId());
        }

        //postId
        personOfEnterprise.setPostId(Long.parseLong(personOfEnterpriseView.getTreeSelect()));

        //通过postId查询出workplaceId
        PostOfEnterprise postOfEnterprise = postOfEnterpriseService.getById(personOfEnterpriseView.getTreeSelect());
        personOfEnterprise.setWorkplaceId(postOfEnterprise.getWorkplaceId());

        //保存
        System.out.println(personOfEnterprise);
        flag = personOfEnterpriseService.save(personOfEnterprise);

        return flag;
    }

    @GetMapping("/delete")
    public boolean delete(String id) {
        return personOfEnterpriseService.removeById(id);
    }

    @GetMapping("/getById")
    public PersonOfEnterpriseView getById(Integer id) {
        PersonOfEnterpriseView personOfEnterpriseView=new PersonOfEnterpriseView();
        PersonOfEnterprise personOfEnterprise = personOfEnterpriseService.getById(id);
        BeanUtils.copyProperties(personOfEnterprise,personOfEnterpriseView);
        PostOfEnterprise postOfEnterprise = postOfEnterpriseService.getById(personOfEnterprise.getPostId());
        WorkplaceOfEnterprise workplaceOfEnterprise= workplaceOfEnterpriseService.getById(personOfEnterprise.getWorkplaceId());
        personOfEnterpriseView.setTreeSelect(String.valueOf(workplaceOfEnterprise.getName()+"--"+postOfEnterprise.getPostSmallName()));

        System.out.println(personOfEnterpriseView);
        //将demoCourse的数据设置到demoData
        return personOfEnterpriseView;
    }

    @PostMapping("/edit")
    public boolean edit(@RequestBody PersonOfEnterprise personOfEnterprise) {
        return personOfEnterpriseService.updateById(personOfEnterprise);
    }

    @GetMapping("/list")
    public IPage<PersonOfEnterprise> list(String json, HttpSession httpSession) {
        SysUser sysUser = (SysUser) httpSession.getAttribute(GlobalConstants.LOGIN_NAME);
        List list1 = new ArrayList();
        //字符串解析成java对象
        JSONObject jsonObject = JSON.parseObject(json);
        //从对象中获取值
        Integer currentPage = jsonObject.getInteger("currentPage");
        Integer pageSize = jsonObject.getInteger("pageSize");
        String name = jsonObject.getString("name");
        QueryWrapper<Enterprise> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.eq("name", sysUser.getCompanyName());
        List<Enterprise> list = enterpriseService.list(queryWrapper1);
        for (Enterprise enterprise : list) {
            list1.clear();
            Long id = enterprise.getId();
            list1.add(id);
        }
        QueryWrapper<PersonOfEnterprise> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("enterpriseId",list1.get(0));
        if (!Strings.isNullOrEmpty(name)) {
            queryWrapper.eq("name", name);
        }
        IPage<PersonOfEnterprise> page = personOfEnterpriseService.page(new Page<>(currentPage, pageSize), queryWrapper);

        return page;
    }

    @GetMapping("/TreeSelcetData")
    public List<TreeSelcetDataPersonOfEnterprise> TreeSelcetData(HttpSession httpSession) {
        SysUser sysUser = (SysUser) httpSession.getAttribute(GlobalConstants.LOGIN_NAME);
        QueryWrapper<SysUser> qws=new QueryWrapper<>();
        qws.eq("loginName",sysUser.getLoginName());
        SysUser one = sysUserService.getOne(qws);

        QueryWrapper<Enterprise> qwe=new QueryWrapper<>();
        qwe.eq("name",one.getCompanyName());
        Enterprise one1 = enterpriseService.getOne(qwe);

        List<TreeSelcetDataPersonOfEnterprise> treeSelcetDatalist = new ArrayList();
        QueryWrapper<WorkplaceOfEnterprise> qww=new QueryWrapper<>();
        qww.eq("enterpriseId",one1.getId());
        List<WorkplaceOfEnterprise> list = workplaceOfEnterpriseService.list(qww);


        for (WorkplaceOfEnterprise workplaceOfEnterprise : list) {
            List<TreeSelcetDataPersonOfEnterprise> chilren = Lists.newArrayList();
            TreeSelcetDataPersonOfEnterprise treeSelcetDataPersonOfEnterprise1 = new TreeSelcetDataPersonOfEnterprise();
            treeSelcetDataPersonOfEnterprise1.setTitle(workplaceOfEnterprise.getName());
            treeSelcetDataPersonOfEnterprise1.setValue(String.valueOf(workplaceOfEnterprise.getId()));
            treeSelcetDataPersonOfEnterprise1.setKey(String.valueOf(workplaceOfEnterprise.getId()));
            treeSelcetDataPersonOfEnterprise1.setChildren(chilren);
            treeSelcetDatalist.add(treeSelcetDataPersonOfEnterprise1);
            QueryWrapper<PostOfEnterprise> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("workplaceId", workplaceOfEnterprise.getId());
            List<PostOfEnterprise> list1 = postOfEnterpriseService.list(queryWrapper);
            for (PostOfEnterprise postOfEnterprise : list1) {
                TreeSelcetDataPersonOfEnterprise treeSelcetDataPersonOfEnterprise = new TreeSelcetDataPersonOfEnterprise();
                treeSelcetDataPersonOfEnterprise.setTitle(postOfEnterprise.getPostSmallName());
                treeSelcetDataPersonOfEnterprise.setValue(String.valueOf(postOfEnterprise.getId()));
                treeSelcetDataPersonOfEnterprise.setKey(String.valueOf(postOfEnterprise.getId()));
                chilren.add(treeSelcetDataPersonOfEnterprise);
            }

    }
        return treeSelcetDatalist;
}
    @PostMapping("/exceladd")
    public boolean list(String from, MultipartFile[] files, HttpSession httpSession) {
        boolean flag = true;
        //excel->model
        Class<? extends BaseRowModel>[] modelClassArr = new Class[1];
        modelClassArr[0]= PersonOfEnterpriseModel.class;
        Map<String, List<Object>> modelMap = MyExcelUtil.readMoreSheetExcel(files,modelClassArr);
        //model->entity
        for (Map.Entry<String, List<Object>> entry : modelMap.entrySet()) {
            String type = entry.getKey();
            List<Object> modelList = entry.getValue();
            List<PersonOfEnterprise> dataList = getDataList(modelList, type,httpSession);
            flag = personOfEnterpriseService.saveBatch(dataList);
        }
        return flag;
    }
    private List<PersonOfEnterprise> getDataList(List<Object> modelList, String type, HttpSession httpSession) {
        List<PersonOfEnterprise> dataList = org.apache.commons.compress.utils.Lists.newArrayList();
        for (Object object : modelList) {
            PersonOfEnterpriseModel personOfEnterpriseModel = (PersonOfEnterpriseModel) object;
            //业务处理
            PersonOfEnterprise personOfEnterprise = new PersonOfEnterprise();
            //enterpriseId
            SysUser sysUser = (SysUser) httpSession.getAttribute(GlobalConstants.LOGIN_NAME);
            QueryWrapper<Enterprise> queryWrapper1=new QueryWrapper();
            queryWrapper1.eq("name",sysUser.getCompanyName());
            List<Enterprise> list1 = enterpriseService.list(queryWrapper1);
            for (Enterprise enterprise : list1) {
                personOfEnterprise.setEnterpriseId(enterprise.getId());
            }
            //关联-工作场所
            QueryWrapper<WorkplaceOfEnterprise> qww=new QueryWrapper();
            qww.eq("name",personOfEnterpriseModel.getWorkplaceId()).eq("enterpriseId",personOfEnterprise.getEnterpriseId());
            WorkplaceOfEnterprise one1 = workplaceOfEnterpriseService.getOne(qww);
            personOfEnterprise.setWorkplaceId(one1.getId());

            //岗位
            QueryWrapper<PostOfEnterprise> qwp=new QueryWrapper();
            qwp.eq("postSmallName",personOfEnterpriseModel.getPostId()).eq("enterpriseId",personOfEnterprise.getEnterpriseId()).eq("workplaceId", personOfEnterprise.getWorkplaceId());
            PostOfEnterprise one = postOfEnterpriseService.getOne(qwp);
            personOfEnterprise.setPostId(one.getId());
            BeanUtils.copyProperties(personOfEnterpriseModel, personOfEnterprise);
            dataList.add(personOfEnterprise);
        }
        return dataList;
    }
}
