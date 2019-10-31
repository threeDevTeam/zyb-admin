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
import com.hthyaq.zybadmin.common.utils.AntdDateUtil;
import com.hthyaq.zybadmin.model.entity.*;
import com.hthyaq.zybadmin.model.excelModel.AlikeSickOfEnterpriseModel;
import com.hthyaq.zybadmin.model.excelModel.EnterpriseModel;
import com.hthyaq.zybadmin.model.excelModel.TreeSelcetDataAlikeSickOfEnterprise;
import com.hthyaq.zybadmin.model.excelModel.TreeSelcetDataSickOfEnterprise;
import com.hthyaq.zybadmin.model.vo.AlikeSickOfEnterpriseView;
import com.hthyaq.zybadmin.model.vo.SickOfEnterpriseView;
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
 * 疑似职业病病人信息 前端控制器
 * </p>
 *
 * @author zhangqiang
 * @since 2019-08-30
 */
@RestController
@RequestMapping("/alikeSickOfEnterprise")
public class AlikeSickOfEnterpriseController {
    @Autowired
    AlikeSickOfEnterpriseService alikeSickOfEnterpriseService;
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
    public boolean add(@RequestBody AlikeSickOfEnterpriseView alikeSickOfEnterpriseView, HttpSession httpSession) {
        boolean flag = false;
        System.out.println(alikeSickOfEnterpriseView);
        AlikeSickOfEnterprise alikeSickOfEnterprise = new  AlikeSickOfEnterprise();

        //other
        BeanUtils.copyProperties(alikeSickOfEnterpriseView,  alikeSickOfEnterprise);
        alikeSickOfEnterprise.setCheckDate(AntdDateUtil.getInteger(alikeSickOfEnterpriseView.getCheckDateStr()));

        //enterpriseId
        SysUser sysUser = (SysUser) httpSession.getAttribute(GlobalConstants.LOGIN_NAME);
        QueryWrapper<Enterprise> queryWrapper1=new QueryWrapper();
        queryWrapper1.eq("name",sysUser.getCompanyName());
        List<Enterprise> list1 = enterpriseService.list(queryWrapper1);
        for (Enterprise enterprise : list1) {
            alikeSickOfEnterprise.setEnterpriseId(enterprise.getId());
        }

        //postDangerId
        alikeSickOfEnterprise.setPostDangerId(Long.parseLong(alikeSickOfEnterpriseView.getTreeSelect()));

        //通过postDangerId查询出workplaceId,postId
        PostDangerOfEnterprise postDangerOfEnterprise = postDangerOfEnterpriseService.getById(alikeSickOfEnterpriseView.getTreeSelect());
        alikeSickOfEnterprise.setWorkplaceId(postDangerOfEnterprise.getWorkplaceId());
        alikeSickOfEnterprise.setPostId(postDangerOfEnterprise.getPostId());

        System.out.println(postDangerOfEnterprise);
        flag = alikeSickOfEnterpriseService.save(alikeSickOfEnterprise);
        return flag;
    }

    @GetMapping("/delete")
    public boolean delete(String id) {
        return alikeSickOfEnterpriseService.removeById(id);
    }

    @GetMapping("/getById")
    public AlikeSickOfEnterprise getById(Integer id) {
        AlikeSickOfEnterpriseView alikeSickOfEnterpriseView=new AlikeSickOfEnterpriseView();
        AlikeSickOfEnterprise alikeSickOfEnterprise = alikeSickOfEnterpriseService.getById(id);
        BeanUtils.copyProperties(alikeSickOfEnterprise,alikeSickOfEnterpriseView);

        alikeSickOfEnterpriseView.setCheckDateStr( AntdDateUtil.getString(alikeSickOfEnterprise.getCheckDate()));

        PostDangerOfEnterprise postDangerOfEnterprise = postDangerOfEnterpriseService.getById(alikeSickOfEnterprise.getPostDangerId());
        PostOfEnterprise postOfEnterprise = postOfEnterpriseService.getById(alikeSickOfEnterprise.getPostId());
        WorkplaceOfEnterprise workplaceOfEnterprise= workplaceOfEnterpriseService.getById(alikeSickOfEnterprise.getWorkplaceId());
        alikeSickOfEnterpriseView.setTreeSelect(String.valueOf(workplaceOfEnterprise.getName()+"--"+postOfEnterprise.getPostSmallName()+"--"+postDangerOfEnterprise.getDangerSmallName()));

        System.out.println(alikeSickOfEnterpriseView);
        //将demoCourse的数据设置到demoData
        return alikeSickOfEnterpriseView;
    }

    @PostMapping("/edit")
    public boolean edit(@RequestBody AlikeSickOfEnterprise alikeSickOfEnterprise) {
        return alikeSickOfEnterpriseService.updateById(alikeSickOfEnterprise);
    }

    @GetMapping("/list")
    public IPage<AlikeSickOfEnterprise> list(String json, HttpSession httpSession) {
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

            QueryWrapper<AlikeSickOfEnterprise> queryWrapper = new QueryWrapper<>();
            if (!Strings.isNullOrEmpty(name)) {
                queryWrapper.eq("name", name);
            }
            queryWrapper.orderByDesc("id");
            IPage<AlikeSickOfEnterprise> page = alikeSickOfEnterpriseService.page(new Page<>(currentPage, pageSize), queryWrapper);

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
            QueryWrapper<AlikeSickOfEnterprise> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("enterpriseId", list1.get(0));
            if (!Strings.isNullOrEmpty(name)) {
                queryWrapper.eq("name", name);
            }
            queryWrapper.orderByDesc("id");
            IPage<AlikeSickOfEnterprise> page = alikeSickOfEnterpriseService.page(new Page<>(currentPage, pageSize), queryWrapper);

            return page;
        }
    }
    @GetMapping("/TreeSelcetData")
    public List<TreeSelcetDataAlikeSickOfEnterprise> TreeSelcetData(HttpSession httpSession) {

        SysUser sysUser = (SysUser) httpSession.getAttribute(GlobalConstants.LOGIN_NAME);
        QueryWrapper<SysUser> qws=new QueryWrapper<>();
        qws.eq("loginName",sysUser.getLoginName());
        SysUser one = sysUserService.getOne(qws);

        QueryWrapper<Enterprise> qwe=new QueryWrapper<>();
        qwe.eq("name",one.getCompanyName());
        Enterprise one1 = enterpriseService.getOne(qwe);


        List<TreeSelcetDataAlikeSickOfEnterprise> treeSelcetDatalist = new ArrayList();
        QueryWrapper<WorkplaceOfEnterprise> qww=new QueryWrapper<>();
        qww.eq("enterpriseId",one1.getId());
        List<WorkplaceOfEnterprise> list = workplaceOfEnterpriseService.list(qww);
        for (WorkplaceOfEnterprise workplaceOfEnterprise : list) {
            List<TreeSelcetDataAlikeSickOfEnterprise> chilren = Lists.newArrayList();

            TreeSelcetDataAlikeSickOfEnterprise treeSelcetDataAlikeSickOfEnterprise1 = new TreeSelcetDataAlikeSickOfEnterprise();
            treeSelcetDataAlikeSickOfEnterprise1.setTitle(workplaceOfEnterprise.getName());
            treeSelcetDataAlikeSickOfEnterprise1.setValue(String.valueOf(workplaceOfEnterprise.getId()));
            treeSelcetDataAlikeSickOfEnterprise1.setKey(String.valueOf(workplaceOfEnterprise.getId()));
            treeSelcetDataAlikeSickOfEnterprise1.setChildren(chilren);
            treeSelcetDatalist.add(treeSelcetDataAlikeSickOfEnterprise1);


            QueryWrapper<PostOfEnterprise> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("workplaceId", workplaceOfEnterprise.getId());
            List<PostOfEnterprise> list1 = postOfEnterpriseService.list(queryWrapper);
            for (PostOfEnterprise postOfEnterprise : list1) {

                TreeSelcetDataAlikeSickOfEnterprise treeSelcetDataAlikeSickOfEnterprise = new TreeSelcetDataAlikeSickOfEnterprise();
                treeSelcetDataAlikeSickOfEnterprise.setTitle(postOfEnterprise.getPostSmallName());
                treeSelcetDataAlikeSickOfEnterprise.setValue(String.valueOf(postOfEnterprise.getId()));
                treeSelcetDataAlikeSickOfEnterprise.setKey(String.valueOf(postOfEnterprise.getId()));
                chilren.add(treeSelcetDataAlikeSickOfEnterprise);
                List<TreeSelcetDataAlikeSickOfEnterprise> chilren2 = Lists.newArrayList();
                QueryWrapper<PostDangerOfEnterprise> queryWrapper2 = new QueryWrapper<>();
                queryWrapper2.eq("postId", postOfEnterprise.getId());
                List<PostDangerOfEnterprise> list2 = postDangerOfEnterpriseService.list(queryWrapper2);

                for (PostDangerOfEnterprise postDangerOfEnterprise : list2) {
                    TreeSelcetDataAlikeSickOfEnterprise treeSelcetDataAlikeSickOfEnterprise2 = new TreeSelcetDataAlikeSickOfEnterprise();
                    treeSelcetDataAlikeSickOfEnterprise2.setTitle(postDangerOfEnterprise.getDangerSmallName());
                    treeSelcetDataAlikeSickOfEnterprise2.setValue(String.valueOf(postDangerOfEnterprise.getId()));
                    treeSelcetDataAlikeSickOfEnterprise2.setKey(String.valueOf(postDangerOfEnterprise.getId()));
                    chilren2.add(treeSelcetDataAlikeSickOfEnterprise2);
                }
                treeSelcetDataAlikeSickOfEnterprise.setChildren(chilren2);
            }
        }
        return treeSelcetDatalist;
    }
    @PostMapping("/exceladd")
    public boolean list(String from, MultipartFile[] files, HttpSession httpSession) {
        boolean flag = true;
        //excel->model
        Class<? extends BaseRowModel>[] modelClassArr = new Class[1];
        modelClassArr[0]= AlikeSickOfEnterpriseModel.class;
        Map<String, List<Object>> modelMap = MyExcelUtil.readMoreSheetExcel(files,modelClassArr);
        //model->entity
        for (Map.Entry<String, List<Object>> entry : modelMap.entrySet()) {
            String type = entry.getKey();
            List<Object> modelList = entry.getValue();
            List<AlikeSickOfEnterprise> dataList = getDataList(modelList, type,httpSession);
            flag = alikeSickOfEnterpriseService.saveBatch(dataList);
        }
        return flag;
    }
    private List<AlikeSickOfEnterprise> getDataList(List<Object> modelList, String type, HttpSession httpSession) {
        List<AlikeSickOfEnterprise> dataList = org.apache.commons.compress.utils.Lists.newArrayList();
        for (Object object : modelList) {
            AlikeSickOfEnterpriseModel alikeSickOfEnterpriseModel = (AlikeSickOfEnterpriseModel) object;
            //业务处理
            AlikeSickOfEnterprise alikeSickOfEnterprise = new AlikeSickOfEnterprise();
            //enterpriseId
            SysUser sysUser = (SysUser) httpSession.getAttribute(GlobalConstants.LOGIN_NAME);
            QueryWrapper<Enterprise> queryWrapper1=new QueryWrapper();
            queryWrapper1.eq("name",sysUser.getCompanyName());
            List<Enterprise> list1 = enterpriseService.list(queryWrapper1);
            for (Enterprise enterprise : list1) {
                alikeSickOfEnterprise.setEnterpriseId(enterprise.getId());
            }
            //workplaceId
            QueryWrapper<WorkplaceOfEnterprise> qww=new QueryWrapper();
            qww.eq("name",alikeSickOfEnterpriseModel.getWorkplaceId()).eq("enterpriseId",alikeSickOfEnterprise.getEnterpriseId());
            WorkplaceOfEnterprise one1 = workplaceOfEnterpriseService.getOne(qww);
            alikeSickOfEnterprise.setWorkplaceId(one1.getId());
            //postId
            QueryWrapper<PostOfEnterprise> qwp=new QueryWrapper();
            qwp.eq("postSmallName",alikeSickOfEnterpriseModel.getPostId()).eq("enterpriseId",alikeSickOfEnterprise.getEnterpriseId()).eq("workplaceId", alikeSickOfEnterprise.getWorkplaceId());
            PostOfEnterprise one = postOfEnterpriseService.getOne(qwp);
            alikeSickOfEnterprise.setPostId(one.getId());
            //postDangerId
            QueryWrapper<PostDangerOfEnterprise> qwD=new QueryWrapper();
            qwD.eq("dangerSmallName",alikeSickOfEnterpriseModel.getPostDangerId()).eq("enterpriseId",alikeSickOfEnterprise.getEnterpriseId()).eq("workplaceId", alikeSickOfEnterprise.getWorkplaceId()).eq("postId", alikeSickOfEnterprise.getPostId());
            PostDangerOfEnterprise one2 = postDangerOfEnterpriseService.getOne(qwD);
            alikeSickOfEnterprise.setPostDangerId(one2.getId());

            BeanUtils.copyProperties(alikeSickOfEnterpriseModel, alikeSickOfEnterprise);
            dataList.add(alikeSickOfEnterprise);
        }
        return dataList;
    }
}

