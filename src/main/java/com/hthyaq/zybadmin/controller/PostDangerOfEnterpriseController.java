package com.hthyaq.zybadmin.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.hthyaq.zybadmin.common.constants.GlobalConstants;
import com.hthyaq.zybadmin.model.entity.*;
import com.hthyaq.zybadmin.model.vo.PostDangerOfEnterpriseView;
import com.hthyaq.zybadmin.model.vo.PostOfEnterpriseView;
import com.hthyaq.zybadmin.service.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 岗位危害信息 前端控制器
 * </p>
 *
 * @author zhangqiang
 * @since 2019-08-30
 */
@RestController
@RequestMapping("/postDangerOfEnterprise")
public class PostDangerOfEnterpriseController {
    @Autowired
    PostDangerOfEnterpriseService postDangerOfEnterpriseService;
    @Autowired
    EnterpriseOfRegisterService enterpriseOfRegisterService;
    @Autowired
    EnterpriseService enterpriseService;
    @Autowired
    WorkplaceOfEnterpriseService workplaceOfEnterpriseService;
    @Autowired
    PostOfEnterpriseService postOfEnterpriseService;

    @PostMapping("/add")
    public boolean add(@RequestBody PostDangerOfEnterpriseView postDangerOfEnterpriseView, HttpSession httpSession) {
        boolean flag = false;
        System.out.println(postDangerOfEnterpriseView);
        PostDangerOfEnterprise postDangerOfEnterprise = new PostDangerOfEnterprise();
        //other
        BeanUtils.copyProperties(postDangerOfEnterpriseView, postDangerOfEnterprise);

        //enterpriseId
        SysUser sysUser = (SysUser) httpSession.getAttribute(GlobalConstants.LOGIN_NAME);
        QueryWrapper<Enterprise> queryWrapper1=new QueryWrapper();
        queryWrapper1.eq("name",sysUser.getCompanyName());
        List<Enterprise> list1 = enterpriseService.list(queryWrapper1);
        for (Enterprise enterprise : list1) {
            postDangerOfEnterprise.setEnterpriseId(enterprise.getId());
        }

        //postId
        postDangerOfEnterprise.setPostId(Long.parseLong(postDangerOfEnterpriseView.getTreeSelect()));

        //通过postId查询出workplaceId
        PostOfEnterprise postOfEnterprise = postOfEnterpriseService.getById(postDangerOfEnterpriseView.getTreeSelect());
        postDangerOfEnterprise.setWorkplaceId(postOfEnterprise.getWorkplaceId());

        //保存
        System.out.println(postDangerOfEnterprise);
        flag = postDangerOfEnterpriseService.save(postDangerOfEnterprise);


        return flag;
    }

    @GetMapping("/delete")
    public boolean delete(String id) {
        return postDangerOfEnterpriseService.removeById(id);
    }

    @GetMapping("/getById")
    public PostDangerOfEnterprise getById(Integer id) {
        PostDangerOfEnterpriseView postDangerOfEnterpriseView=new PostDangerOfEnterpriseView();
        PostDangerOfEnterprise postDangerOfEnterprise = postDangerOfEnterpriseService.getById(id);
        BeanUtils.copyProperties(postDangerOfEnterprise, postDangerOfEnterpriseView);
        PostOfEnterprise postOfEnterprise = postOfEnterpriseService.getById(postDangerOfEnterprise.getPostId());
        WorkplaceOfEnterprise workplaceOfEnterprise= workplaceOfEnterpriseService.getById(postDangerOfEnterprise.getWorkplaceId());
        postDangerOfEnterpriseView.setTreeSelect(String.valueOf(workplaceOfEnterprise.getName()+"--"+postOfEnterprise.getPostBigName()));
        return postDangerOfEnterpriseView;
    }

    @PostMapping("/edit")
    public boolean edit(@RequestBody PostDangerOfEnterprise postDangerOfEnterprise) {
        return postDangerOfEnterpriseService.updateById(postDangerOfEnterprise);
    }

    @GetMapping("/list")
    public IPage<PostDangerOfEnterprise> list(String json, HttpSession httpSession) {
        SysUser sysUser = (SysUser) httpSession.getAttribute(GlobalConstants.LOGIN_NAME);
        List list1 = new ArrayList();
        //字符串解析成java对象
        JSONObject jsonObject = JSON.parseObject(json);
        //从对象中获取值
        Integer currentPage = jsonObject.getInteger("currentPage");
        Integer pageSize = jsonObject.getInteger("pageSize");
        String upDate = jsonObject.getString("upDate");

        QueryWrapper<Enterprise> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.eq("name", sysUser.getCompanyName());
        List<Enterprise> list = enterpriseService.list(queryWrapper1);
        for (Enterprise enterprise : list) {
            list1.clear();
            Long id = enterprise.getId();
            list1.add(id);
        }
        QueryWrapper<PostDangerOfEnterprise> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("enterpriseId",list1.get(0));
        if (!Strings.isNullOrEmpty(upDate)) {
            queryWrapper.eq("upDate", upDate);
        }
        IPage<PostDangerOfEnterprise> page = postDangerOfEnterpriseService.page(new Page<>(currentPage, pageSize), queryWrapper);

        return page;
    }

    @GetMapping("/TreeSelcetData")
    public List<TreeSelcetDataPersonOfEnterprise> TreeSelcetData() {
        List<TreeSelcetDataPersonOfEnterprise> treeSelcetDatalist = new ArrayList();
        List<WorkplaceOfEnterprise> list = workplaceOfEnterpriseService.list();
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
                treeSelcetDataPersonOfEnterprise.setTitle(postOfEnterprise.getPostBigName());
                treeSelcetDataPersonOfEnterprise.setValue(String.valueOf(postOfEnterprise.getId()));
                treeSelcetDataPersonOfEnterprise.setKey(String.valueOf(postOfEnterprise.getId()));
                chilren.add(treeSelcetDataPersonOfEnterprise);
            }

        }
        return treeSelcetDatalist;
    }
}
