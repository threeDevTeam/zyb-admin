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
import com.hthyaq.zybadmin.service.EnterpriseOfRegisterService;
import com.hthyaq.zybadmin.service.EnterpriseService;
import com.hthyaq.zybadmin.service.PostOfEnterpriseService;
import com.hthyaq.zybadmin.service.WorkplaceOfEnterpriseService;
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

    @PostMapping("/add")
    public boolean add(@RequestBody PostOfEnterpriseView postOfEnterpriseView, HttpSession httpSession) {
        boolean flag = false;

        PostOfEnterprise postOfEnterprise = new PostOfEnterprise();
        //other
        BeanUtils.copyProperties(postOfEnterpriseView, postOfEnterprise);

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
        PostOfEnterpriseView postOfEnterpriseView=new PostOfEnterpriseView();
        PostOfEnterprise postOfEnterprise = postOfEnterpriseService.getById(id);
        BeanUtils.copyProperties(postOfEnterprise, postOfEnterpriseView);

        QueryWrapper<WorkplaceOfEnterprise> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", postOfEnterprise.getWorkplaceId());
        List<WorkplaceOfEnterprise> List = workplaceOfEnterpriseService.list(queryWrapper);
        for (WorkplaceOfEnterprise workplaceOfEnterprise : List) {
            postOfEnterpriseView.setTreeSelect(String.valueOf(workplaceOfEnterprise.getName()));
        }
       return postOfEnterpriseView;
    }

    @PostMapping("/edit")
    public boolean edit(@RequestBody PostOfEnterprise postOfEnterprise) {
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
