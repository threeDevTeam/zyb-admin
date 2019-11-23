package com.hthyaq.zybadmin.controller;


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
import com.hthyaq.zybadmin.model.excelModel.*;
import com.hthyaq.zybadmin.model.vo.MonitorOfEnterpriseView;
import com.hthyaq.zybadmin.model.vo.PersonProtectOfEnterpriseView;
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
 * 个体防护信息 前端控制器
 * </p>
 *
 * @author zhangqiang
 * @since 2019-08-30
 */
@RestController
@RequestMapping("/personProtectOfEnterprise")
public class PersonProtectOfEnterpriseController {
    @Autowired
    PersonProtectOfEnterpriseService personProtectOfEnterpriseService;
    @Autowired
    EnterpriseOfRegisterService enterpriseOfRegisterService;
    @Autowired
    EnterpriseService enterpriseService;
    @Autowired
    WorkplaceOfEnterpriseService workplaceOfEnterpriseService;
    @Autowired
    PostOfEnterpriseService postOfEnterpriseService;
    @Autowired
    PostDangerOfEnterpriseService postDangerOfEnterpriseService;
    @Autowired
    SysUserService sysUserService;
    @Autowired
    SysRoleUserService sysRoleUserService;
    @PostMapping("/add")
    public boolean add(@RequestBody PersonProtectOfEnterpriseView personProtectOfEnterpriseView, HttpSession httpSession) {
        boolean flag = false;
        System.out.println(personProtectOfEnterpriseView);
        PersonProtectOfEnterprise personProtectOfEnterprise = new PersonProtectOfEnterprise();

        //other
        BeanUtils.copyProperties(personProtectOfEnterpriseView, personProtectOfEnterprise);

        //enterpriseId
        SysUser sysUser = (SysUser) httpSession.getAttribute(GlobalConstants.LOGIN_NAME);
        QueryWrapper<Enterprise> queryWrapper1=new QueryWrapper();
        queryWrapper1.eq("name",sysUser.getCompanyName());
        List<Enterprise> list1 = enterpriseService.list(queryWrapper1);
        for (Enterprise enterprise : list1) {
            personProtectOfEnterprise.setEnterpriseId(enterprise.getId());
        }

        //postDangerId
        personProtectOfEnterprise.setPostDangerId(Long.parseLong(personProtectOfEnterpriseView.getTreeSelect()));

        //通过postDangerId查询出workplaceId,postId
        PostDangerOfEnterprise postDangerOfEnterprise = postDangerOfEnterpriseService.getById(personProtectOfEnterpriseView.getTreeSelect());
        personProtectOfEnterprise.setWorkplaceId(postDangerOfEnterprise.getWorkplaceId());
        personProtectOfEnterprise.setPostId(postDangerOfEnterprise.getPostId());

        System.out.println(postDangerOfEnterprise);
        flag = personProtectOfEnterpriseService.save(personProtectOfEnterprise);

        return flag;
    }

    @GetMapping("/delete")
    public boolean delete(String id) {
        return personProtectOfEnterpriseService.removeById(id);
    }

    @GetMapping("/getById")
    public PersonProtectOfEnterprise getById(Integer id) {
        PersonProtectOfEnterpriseView personProtectOfEnterpriseView=new PersonProtectOfEnterpriseView();
        PersonProtectOfEnterprise personProtectOfEnterprise = personProtectOfEnterpriseService.getById(id);
        BeanUtils.copyProperties(personProtectOfEnterprise,personProtectOfEnterpriseView);
        PostDangerOfEnterprise postDangerOfEnterprise = postDangerOfEnterpriseService.getById(personProtectOfEnterprise.getPostDangerId());
        PostOfEnterprise postOfEnterprise = postOfEnterpriseService.getById(personProtectOfEnterprise.getPostId());
        WorkplaceOfEnterprise workplaceOfEnterprise= workplaceOfEnterpriseService.getById(personProtectOfEnterprise.getWorkplaceId());
        personProtectOfEnterpriseView.setTreeSelect(String.valueOf(workplaceOfEnterprise.getName()+"--"+postOfEnterprise.getPostSmallName()+"--"+postDangerOfEnterprise.getDangerSmallName()));

        System.out.println(personProtectOfEnterpriseView);
        //将demoCourse的数据设置到demoData
        return personProtectOfEnterpriseView;
    }

    @PostMapping("/edit")
    public boolean edit(@RequestBody PersonProtectOfEnterprise personProtectOfEnterprise) {
        return personProtectOfEnterpriseService.updateById(personProtectOfEnterprise);
    }

    @GetMapping("/list")
    public IPage<PersonProtectOfEnterprise> list(String json, HttpSession httpSession) {
        SysUser sysUser = (SysUser) httpSession.getAttribute(GlobalConstants.LOGIN_NAME);
        List list1 = new ArrayList();
        //字符串解析成java对象
        JSONObject jsonObject = JSON.parseObject(json);
        //从对象中获取值
        Integer currentPage = jsonObject.getInteger("currentPage");
        Integer pageSize = jsonObject.getInteger("pageSize");
        String name = jsonObject.getString("name");
        QueryWrapper<SysRoleUser> qw = new QueryWrapper<>();
        qw.eq("userId", sysUser.getId());
        SysRoleUser sysRoleUser = sysRoleUserService.getOne(qw);
        if (sysRoleUser.getRoleId() == 1) {
            QueryWrapper<PersonProtectOfEnterprise> queryWrapper = new QueryWrapper<>();
            if (!Strings.isNullOrEmpty(name)) {
                queryWrapper.like("name", name);
            }
            queryWrapper.orderByDesc("id");
            IPage<PersonProtectOfEnterprise> page = personProtectOfEnterpriseService.page(new Page<>(currentPage, pageSize), queryWrapper);

            return page;
        } else {
            QueryWrapper<Enterprise> queryWrapper1 = new QueryWrapper<>();
            queryWrapper1.eq("name", sysUser.getCompanyName());
            List<Enterprise> list = enterpriseService.list(queryWrapper1);
            for (Enterprise enterprise : list) {
                list1.clear();
                Long id = enterprise.getId();
                list1.add(id);
            }
            QueryWrapper<PersonProtectOfEnterprise> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("enterpriseId", list1.get(0));
            if (!Strings.isNullOrEmpty(name)) {
                queryWrapper.like("name", name);
            }
            queryWrapper.orderByDesc("id");
            IPage<PersonProtectOfEnterprise> page = personProtectOfEnterpriseService.page(new Page<>(currentPage, pageSize), queryWrapper);

            return page;
        }
    }
    @GetMapping("/TreeSelcetData")
    public List<TreeSelcetDataPersonProtectOfEnterprise> TreeSelcetData(HttpSession httpSession) {

        SysUser sysUser = (SysUser) httpSession.getAttribute(GlobalConstants.LOGIN_NAME);
        QueryWrapper<SysUser> qws=new QueryWrapper<>();
        qws.eq("loginName",sysUser.getLoginName());
        SysUser one = sysUserService.getOne(qws);

        QueryWrapper<Enterprise> qwe=new QueryWrapper<>();
        qwe.eq("name",one.getCompanyName());
        Enterprise one1 = enterpriseService.getOne(qwe);


        List<TreeSelcetDataPersonProtectOfEnterprise> treeSelcetDatalist = new ArrayList();
        QueryWrapper<WorkplaceOfEnterprise> qww=new QueryWrapper<>();
        qww.eq("enterpriseId",one1.getId());
        List<WorkplaceOfEnterprise> list = workplaceOfEnterpriseService.list(qww);
        for (WorkplaceOfEnterprise workplaceOfEnterprise : list) {
            List<TreeSelcetDataPersonProtectOfEnterprise> chilren = Lists.newArrayList();

            TreeSelcetDataPersonProtectOfEnterprise treeSelcetDataPersonProtectOfEnterprise1 = new TreeSelcetDataPersonProtectOfEnterprise();
            treeSelcetDataPersonProtectOfEnterprise1.setTitle(workplaceOfEnterprise.getName());
            treeSelcetDataPersonProtectOfEnterprise1.setValue(String.valueOf(workplaceOfEnterprise.getId()));
            treeSelcetDataPersonProtectOfEnterprise1.setKey(String.valueOf(workplaceOfEnterprise.getId()));
            treeSelcetDataPersonProtectOfEnterprise1.setChildren(chilren);
            treeSelcetDatalist.add(treeSelcetDataPersonProtectOfEnterprise1);


            QueryWrapper<PostOfEnterprise> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("workplaceId", workplaceOfEnterprise.getId());
            List<PostOfEnterprise> list1 = postOfEnterpriseService.list(queryWrapper);
            for (PostOfEnterprise postOfEnterprise : list1) {

                TreeSelcetDataPersonProtectOfEnterprise treeSelcetDataPersonProtectOfEnterprise = new TreeSelcetDataPersonProtectOfEnterprise();
                treeSelcetDataPersonProtectOfEnterprise.setTitle(postOfEnterprise.getPostSmallName());
                treeSelcetDataPersonProtectOfEnterprise.setValue(String.valueOf(postOfEnterprise.getId()));
                treeSelcetDataPersonProtectOfEnterprise.setKey(String.valueOf(postOfEnterprise.getId()));
                chilren.add(treeSelcetDataPersonProtectOfEnterprise);
                List<TreeSelcetDataPersonProtectOfEnterprise> chilren2 = Lists.newArrayList();
                QueryWrapper<PostDangerOfEnterprise> queryWrapper2 = new QueryWrapper<>();
                queryWrapper2.eq("postId", postOfEnterprise.getId());
                List<PostDangerOfEnterprise> list2 = postDangerOfEnterpriseService.list(queryWrapper2);

                for (PostDangerOfEnterprise postDangerOfEnterprise : list2) {
                    TreeSelcetDataPersonProtectOfEnterprise treeSelcetDataPersonProtectOfEnterprise2 = new TreeSelcetDataPersonProtectOfEnterprise();
                    treeSelcetDataPersonProtectOfEnterprise2.setTitle(postDangerOfEnterprise.getDangerSmallName());
                    treeSelcetDataPersonProtectOfEnterprise2.setValue(String.valueOf(postDangerOfEnterprise.getId()));
                    treeSelcetDataPersonProtectOfEnterprise2.setKey(String.valueOf(postDangerOfEnterprise.getId()));
                    chilren2.add(treeSelcetDataPersonProtectOfEnterprise2);
                }
                treeSelcetDataPersonProtectOfEnterprise.setChildren(chilren2);
            }
        }
        return treeSelcetDatalist;
    }
    @PostMapping("/exceladd")
    public boolean list(String from, MultipartFile[] files, HttpSession httpSession) {
        boolean flag = true;
        //excel->model
        Class<? extends BaseRowModel>[] modelClassArr = new Class[1];
        modelClassArr[0]= PersonProtectOfEnterpriseModel.class;
        Map<String, List<Object>> modelMap = MyExcelUtil.readMoreSheetExcel(files,modelClassArr);
        //model->entity
        for (Map.Entry<String, List<Object>> entry : modelMap.entrySet()) {
            String type = entry.getKey();
            List<Object> modelList = entry.getValue();
            List<PersonProtectOfEnterprise> dataList = getDataList(modelList, type,httpSession);
            flag = personProtectOfEnterpriseService.saveBatch(dataList);
        }
        return flag;
    }
    private List<PersonProtectOfEnterprise> getDataList(List<Object> modelList, String type, HttpSession httpSession) {
        List<PersonProtectOfEnterprise> dataList = org.apache.commons.compress.utils.Lists.newArrayList();
        for (Object object : modelList) {
            PersonProtectOfEnterpriseModel personProtectOfEnterpriseModel = (PersonProtectOfEnterpriseModel) object;
            //业务处理

            PersonProtectOfEnterprise personProtectOfEnterprise = new PersonProtectOfEnterprise();
            //enterpriseId
            SysUser sysUser = (SysUser) httpSession.getAttribute(GlobalConstants.LOGIN_NAME);
            QueryWrapper<Enterprise> queryWrapper1=new QueryWrapper();
            queryWrapper1.eq("name",sysUser.getCompanyName());
            List<Enterprise> list1 = enterpriseService.list(queryWrapper1);
            for (Enterprise enterprise : list1) {
                personProtectOfEnterprise.setEnterpriseId(enterprise.getId());
            }
            //workplaceId
            QueryWrapper<WorkplaceOfEnterprise> qww=new QueryWrapper();
            qww.eq("name",personProtectOfEnterpriseModel.getWorkplaceId()).eq("enterpriseId",personProtectOfEnterprise.getEnterpriseId());
            WorkplaceOfEnterprise one1 = workplaceOfEnterpriseService.getOne(qww);
            personProtectOfEnterprise.setWorkplaceId(one1.getId());
            //postId
            QueryWrapper<PostOfEnterprise> qwp=new QueryWrapper();
            qwp.eq("postSmallName",personProtectOfEnterpriseModel.getPostId()).eq("enterpriseId",personProtectOfEnterprise.getEnterpriseId()).eq("workplaceId", personProtectOfEnterprise.getWorkplaceId());
            PostOfEnterprise one = postOfEnterpriseService.getOne(qwp);
            personProtectOfEnterprise.setPostId(one.getId());
            //postDangerId
            QueryWrapper<PostDangerOfEnterprise> qwD=new QueryWrapper();
            qwD.eq("dangerSmallName",personProtectOfEnterpriseModel.getPostDangerId()).eq("enterpriseId",personProtectOfEnterprise.getEnterpriseId()).eq("workplaceId", personProtectOfEnterprise.getWorkplaceId()).eq("postId", personProtectOfEnterprise.getPostId());
            PostDangerOfEnterprise one2 = postDangerOfEnterpriseService.getOne(qwD);
            personProtectOfEnterprise.setPostDangerId(one2.getId());

            BeanUtils.copyProperties(personProtectOfEnterpriseModel, personProtectOfEnterprise);
            dataList.add(personProtectOfEnterprise);
        }
        return dataList;
    }
}
