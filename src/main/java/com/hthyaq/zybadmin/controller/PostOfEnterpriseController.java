package com.hthyaq.zybadmin.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.base.Strings;
import com.hthyaq.zybadmin.common.constants.GlobalConstants;
import com.hthyaq.zybadmin.model.entity.*;
import com.hthyaq.zybadmin.model.vo.PostOfEnterpriseView;
import com.hthyaq.zybadmin.service.*;
import org.apache.commons.compress.utils.Lists;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

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
            postOfEnterprise.setPostSmallName("无");
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
        System.out.println(postOfEnterprise);
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
        List<Gangwei> gw = gangweiService.list(gw1);
        for (Gangwei gangwei : gw) {
            listc1.add(gangwei.getId());
        }
        QueryWrapper<Gangwei> gw5 = new QueryWrapper<>();
        gw5.eq("name", postOfEnterprise.getPostSmallName());
        List<Gangwei> gw3 = gangweiService.list(gw5);
        for (Gangwei gangwei : gw3) {
            listc1.add(gangwei.getId());
        }
        postOfEnterpriseView.setCascaded1((ArrayList) listc1);

        return postOfEnterpriseView;
    }

    @PostMapping("/edit")
    public boolean edit(@RequestBody PostOfEnterpriseView postOfEnterpriseView) {
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
        return postOfEnterpriseService.updateById(postOfEnterprise);
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

        QueryWrapper<Enterprise> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.eq("name", sysUser.getCompanyName());
        List<Enterprise> list = enterpriseService.list(queryWrapper1);
        for (Enterprise enterprise : list) {
            list1.clear();
            Long id = enterprise.getId();
            list1.add(id);
        }
        QueryWrapper<PostOfEnterprise> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("enterpriseId",list1.get(0));
        if (!Strings.isNullOrEmpty(postSmallName)) {
            queryWrapper.eq("name", postSmallName);
        }
        IPage<PostOfEnterprise> page = postOfEnterpriseService.page(new Page<>(currentPage, pageSize), queryWrapper);

        return page;
    }

    @GetMapping("/TreeSelcetData")
    public List<TreeSelcetDataPostofenterprise> TreeSelcetData() {
        List<TreeSelcetDataPostofenterprise> treeSelcetDatalist = new ArrayList();
        List<WorkplaceOfEnterprise> list = workplaceOfEnterpriseService.list();
        for (WorkplaceOfEnterprise workplaceOfEnterprise : list) {
            TreeSelcetDataPostofenterprise treeSelcetDataPostofenterprise = new TreeSelcetDataPostofenterprise();
            treeSelcetDataPostofenterprise.setTitle(workplaceOfEnterprise.getName());
            treeSelcetDataPostofenterprise.setValue(String.valueOf(workplaceOfEnterprise.getId()));
            treeSelcetDataPostofenterprise.setKey(String.valueOf(workplaceOfEnterprise.getId()));
            treeSelcetDatalist.add(treeSelcetDataPostofenterprise);
        }
        return treeSelcetDatalist;
    }
}
