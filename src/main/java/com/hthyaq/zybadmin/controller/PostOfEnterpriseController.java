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
import com.hthyaq.zybadmin.model.excelModel.PostOfEnterpriseModel;
import com.hthyaq.zybadmin.model.vo.PostOfEnterpriseView;
import com.hthyaq.zybadmin.service.*;
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
 * 岗位 前端控制器
 * </p>
 *
 * @author zhangqiang
 * @since 2019-08-30
 */
@RestController
@RequestMapping("/postOfEnterprise")
public class PostOfEnterpriseController {
    @Autowired
    PostOfEnterpriseService postOfEnterpriseService;
    @Autowired
    EnterpriseOfRegisterService enterpriseOfRegisterService;
    @Autowired
    EnterpriseService enterpriseService;
    @Autowired
    WorkplaceOfEnterpriseService workplaceOfEnterpriseService;
    @Autowired
    GangweiService gangweiService;
    @Autowired
    SysUserService sysUserService;
    @Autowired
    SysRoleUserService sysRoleUserService;

    @PostMapping("/add")
    public boolean add(@RequestBody PostOfEnterpriseView postOfEnterpriseView, HttpSession httpSession) {
        boolean flag = false;

        PostOfEnterprise postOfEnterprise = new PostOfEnterprise();
        //other
        BeanUtils.copyProperties(postOfEnterpriseView, postOfEnterprise);
        //岗位名称
        QueryWrapper<Gangwei> qw3 = new QueryWrapper<>();
        qw3.eq("id", postOfEnterpriseView.getCascaded1().get(0));
        List<Gangwei> list3 = gangweiService.list(qw3);
        for (Gangwei gangwei : list3) {
            postOfEnterprise.setPostBigName(gangwei.getName());
        }
        if(postOfEnterpriseView.getCascaded1().size()==2){
            QueryWrapper<Gangwei> qw1= new QueryWrapper<>();
            qw1.eq("id",postOfEnterpriseView.getCascaded1().get(1));
            List<Gangwei> list5= gangweiService.list(qw1);
            for (Gangwei gangwei : list5) {
                postOfEnterprise.setPostSmallName(gangwei.getName());
            }
        }else{
            qw3.eq("id", postOfEnterpriseView.getCascaded1().get(0));
            List<Gangwei> list2 = gangweiService.list(qw3);
            for (Gangwei gangwei : list2) {
                postOfEnterprise.setPostBigName(gangwei.getName());
            }
        }
        //enterpriseId
        SysUser sysUser = (SysUser) httpSession.getAttribute(GlobalConstants.LOGIN_NAME);
        QueryWrapper<Enterprise> queryWrapper1=new QueryWrapper();
        queryWrapper1.eq("name",sysUser.getCompanyName());
        List<Enterprise> list1 = enterpriseService.list(queryWrapper1);
        for (Enterprise enterprise : list1) {
            postOfEnterprise.setEnterpriseId(enterprise.getId());
        }

        postOfEnterprise.setWorkplaceId(Long.parseLong(postOfEnterpriseView.getTreeSelect()));
        //保存
        flag = postOfEnterpriseService.save(postOfEnterprise);

        return flag;


    }

    @GetMapping("/delete")
    public boolean delete(String id) {
        return postOfEnterpriseService.removeById(id);
    }

    @GetMapping("/getById")
    public PostOfEnterpriseView getById(Integer id) {
        List listc1 = new ArrayList();
        PostOfEnterpriseView postOfEnterpriseView=new PostOfEnterpriseView();
        PostOfEnterprise postOfEnterprise = postOfEnterpriseService.getById(id);
        BeanUtils.copyProperties(postOfEnterprise, postOfEnterpriseView);

        QueryWrapper<WorkplaceOfEnterprise> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", postOfEnterprise.getWorkplaceId());
        List<WorkplaceOfEnterprise> List = workplaceOfEnterpriseService.list(queryWrapper);
        for (WorkplaceOfEnterprise workplaceOfEnterprise : List) {
            postOfEnterpriseView.setTreeSelect(String.valueOf(workplaceOfEnterprise.getName()));
        }
        //岗位名称
        QueryWrapper<Gangwei> gw1 = new QueryWrapper<>();
        gw1.eq("name", postOfEnterprise.getPostBigName());
        gw1.eq("level", 1);
        List<Gangwei> gw = gangweiService.list(gw1);
        for (Gangwei gangwei : gw) {
            listc1.add(gangwei.getId());
        }
        QueryWrapper<Gangwei> gw5 = new QueryWrapper<>();
        gw5.eq("name", postOfEnterprise.getPostSmallName());
        gw5.eq("pid", listc1.get(0));
        List<Gangwei> gw3 = gangweiService.list(gw5);
        for (Gangwei gangwei : gw3) {
            listc1.add(gangwei.getId());
        }
        postOfEnterpriseView.setCascaded1((ArrayList) listc1);

        return postOfEnterpriseView;
    }

    @PostMapping("/edit")
    public boolean edit(@RequestBody PostOfEnterpriseView postOfEnterpriseView) {
        boolean flag=false;
        postOfEnterpriseService.removeById(postOfEnterpriseView.getId());
        //demo
        PostOfEnterprise postOfEnterprise = new PostOfEnterprise();

        BeanUtils.copyProperties(postOfEnterpriseView, postOfEnterprise);

        //岗位名称
        QueryWrapper<Gangwei> qw3 = new QueryWrapper<>();
        qw3.eq("id", postOfEnterpriseView.getCascaded1().get(0));
        List<Gangwei> list3 = gangweiService.list(qw3);
        for (Gangwei gangwei : list3) {
            postOfEnterprise.setPostBigName(gangwei.getName());
        }
        if(postOfEnterpriseView.getCascaded1().size()==2){
            QueryWrapper<Gangwei> qw1= new QueryWrapper<>();
            qw1.eq("id",postOfEnterpriseView.getCascaded1().get(1));
            List<Gangwei> list5= gangweiService.list(qw1);
            for (Gangwei gangwei : list5) {
                postOfEnterprise.setPostSmallName(gangwei.getName());
            }
        }else{
            postOfEnterprise.setPostSmallName("无");
        }
        flag=postOfEnterpriseService.save(postOfEnterprise);

        return flag;
    }

    @GetMapping("/list")
    public IPage<PostOfEnterprise> list(String json, HttpSession httpSession) {
        SysUser sysUser = (SysUser) httpSession.getAttribute(GlobalConstants.LOGIN_NAME);
        List list1 = new ArrayList();
        //字符串解析成java对象
        JSONObject jsonObject = JSON.parseObject(json);
        //从对象中获取值
        Integer currentPage = jsonObject.getInteger("currentPage");
        Integer pageSize = jsonObject.getInteger("pageSize");
        String postSmallName = jsonObject.getString("postSmallName");
        QueryWrapper<SysRoleUser> qw = new QueryWrapper<>();
        qw.eq("userId", sysUser.getId());
        SysRoleUser sysRoleUser = sysRoleUserService.getOne(qw);
        if (sysRoleUser.getRoleId() == 1) {
            QueryWrapper<PostOfEnterprise> queryWrapper = new QueryWrapper<>();
            if (!Strings.isNullOrEmpty(postSmallName)) {
                queryWrapper.like("postSmallName", postSmallName);
            }
            queryWrapper.orderByDesc("id");
            IPage<PostOfEnterprise> page = postOfEnterpriseService.page(new Page<>(currentPage, pageSize), queryWrapper);

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
            QueryWrapper<PostOfEnterprise> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("enterpriseId", list1.get(0));
            if (!Strings.isNullOrEmpty(postSmallName)) {
                queryWrapper.like("postSmallName", postSmallName);
            }
            queryWrapper.orderByDesc("id");
            IPage<PostOfEnterprise> page = postOfEnterpriseService.page(new Page<>(currentPage, pageSize), queryWrapper);

            return page;
        }
    }
    @GetMapping("/TreeSelcetData")
    public List<TreeSelcetDataPostofenterprise> TreeSelcetData(HttpSession httpSession) {
        List<TreeSelcetDataPostofenterprise> treeSelcetDatalist = new ArrayList();
        SysUser sysUser = (SysUser) httpSession.getAttribute(GlobalConstants.LOGIN_NAME);
        QueryWrapper<SysUser> qws=new QueryWrapper<>();
        qws.eq("loginName",sysUser.getLoginName());
        SysUser one = sysUserService.getOne(qws);
        QueryWrapper<Enterprise> qwe=new QueryWrapper<>();
        qwe.eq("name",one.getCompanyName());
        Enterprise one1 = enterpriseService.getOne(qwe);

        QueryWrapper<WorkplaceOfEnterprise> qww=new QueryWrapper<>();
        qww.eq("enterpriseId",one1.getId());
        List<WorkplaceOfEnterprise> list = workplaceOfEnterpriseService.list(qww);

      for (WorkplaceOfEnterprise workplaceOfEnterprise : list) {
            TreeSelcetDataPostofenterprise treeSelcetDataPostofenterprise = new TreeSelcetDataPostofenterprise();
            treeSelcetDataPostofenterprise.setTitle(workplaceOfEnterprise.getName());
            treeSelcetDataPostofenterprise.setValue(String.valueOf(workplaceOfEnterprise.getId()));
            treeSelcetDataPostofenterprise.setKey(String.valueOf(workplaceOfEnterprise.getId()));
            treeSelcetDatalist.add(treeSelcetDataPostofenterprise);
        }
        return treeSelcetDatalist;
    }
    @PostMapping("/exceladd")
    public boolean list(String from, MultipartFile[] files, HttpSession httpSession) {
        boolean flag = true;
        //excel->model
        Class<? extends BaseRowModel>[] modelClassArr = new Class[1];
        modelClassArr[0]= PostOfEnterpriseModel.class;
        Map<String, List<Object>> modelMap = MyExcelUtil.readMoreSheetExcel(files,modelClassArr);
        //model->entity
        for (Map.Entry<String, List<Object>> entry : modelMap.entrySet()) {
            String type = entry.getKey();
            List<Object> modelList = entry.getValue();
            List<PostOfEnterprise> dataList = getDataList(modelList, type,httpSession);
            flag = postOfEnterpriseService.saveBatch(dataList);
        }
        return flag;
    }
    private List<PostOfEnterprise> getDataList(List<Object> modelList, String type, HttpSession httpSession) {
        List<PostOfEnterprise> dataList = Lists.newArrayList();
        for (Object object : modelList) {
            PostOfEnterpriseModel postOfEnterpriseModel = (PostOfEnterpriseModel) object;
            //业务处理
            PostOfEnterprise postOfEnterprise = new PostOfEnterprise();
            SysUser sysUser = (SysUser) httpSession.getAttribute(GlobalConstants.LOGIN_NAME);
            QueryWrapper<Enterprise> queryWrapper1=new QueryWrapper();
            queryWrapper1.eq("name",sysUser.getCompanyName());
            List<Enterprise> list1 = enterpriseService.list(queryWrapper1);
            for (Enterprise enterprise : list1) {
                postOfEnterprise.setEnterpriseId(enterprise.getId());
            }
            //场所
            QueryWrapper<WorkplaceOfEnterprise> queryWrapper=new QueryWrapper<>();
            queryWrapper.eq("name", postOfEnterpriseModel.getName());
            WorkplaceOfEnterprise one = workplaceOfEnterpriseService.getOne(queryWrapper);
            try {
                postOfEnterprise.setWorkplaceId(one.getId());
            } catch (Exception e) {
                throw new RuntimeException("表格内容不正确");
            }
            //大类名岗位
            QueryWrapper<Gangwei> qwg1=new QueryWrapper<>();
            qwg1.eq("name",postOfEnterpriseModel.getPostSmallName());
            Gangwei gangwei = gangweiService.getOne(qwg1);
            QueryWrapper<Gangwei> qwg2=new QueryWrapper<>();
            qwg2.eq("id",gangwei.getPid());
            Gangwei gangwei1 = gangweiService.getOne(qwg2);
            postOfEnterprise.setPostBigName(gangwei1.getName());
            BeanUtils.copyProperties(postOfEnterpriseModel, postOfEnterprise);
            dataList.add(postOfEnterprise);
        }
        return dataList;
    }
}
