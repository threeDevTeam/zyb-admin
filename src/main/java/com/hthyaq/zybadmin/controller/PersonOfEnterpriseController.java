package com.hthyaq.zybadmin.controller;


import ch.qos.logback.classic.turbo.TurboFilter;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.hthyaq.zybadmin.common.constants.GlobalConstants;
import com.hthyaq.zybadmin.model.entity.*;
import com.hthyaq.zybadmin.model.vo.PersonOfEnterpriseView;
import com.hthyaq.zybadmin.service.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

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

    @PostMapping("/add")
    public boolean add(@RequestBody PersonOfEnterpriseView personOfEnterpriseView, HttpSession httpSession) {
        boolean flag = false;

        PersonOfEnterprise personOfEnterprise = new PersonOfEnterprise();
        //other
        BeanUtils.copyProperties(personOfEnterpriseView, personOfEnterprise);

        //enterpriseId
        SysUser sysUser = (SysUser) httpSession.getAttribute(GlobalConstants.LOGIN_NAME);
        personOfEnterprise.setEnterpriseId(sysUser.getCompanyId());

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
        personOfEnterpriseView.setTreeSelect(String.valueOf(workplaceOfEnterprise.getName()+"--"+postOfEnterprise.getPostBigName()));

        System.out.println(personOfEnterpriseView);
        //将demoCourse的数据设置到demoData
        return personOfEnterpriseView;
    }

    @PostMapping("/edit")
    public boolean edit(@RequestBody PersonOfEnterprise personOfEnterprise) {
        return personOfEnterpriseService.updateById(personOfEnterprise);
    }

    @GetMapping("/list")
    public IPage<PersonOfEnterprise> list(String json) {
        //字符串解析成java对象
        JSONObject jsonObject = JSON.parseObject(json);
        //从对象中获取值
        Integer currentPage = jsonObject.getInteger("currentPage");
        Integer pageSize = jsonObject.getInteger("pageSize");
        String name = jsonObject.getString("name");
        QueryWrapper<PersonOfEnterprise> queryWrapper = new QueryWrapper<>();
        if (!Strings.isNullOrEmpty(name)) {
            queryWrapper.eq("name", name);
        }
        IPage<PersonOfEnterprise> page = personOfEnterpriseService.page(new Page<>(currentPage, pageSize), queryWrapper);

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
