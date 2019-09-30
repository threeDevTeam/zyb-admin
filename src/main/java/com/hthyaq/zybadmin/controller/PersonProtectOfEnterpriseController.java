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
import com.hthyaq.zybadmin.model.excelModel.TreeSelcetDataMonitorOfEnterprise;
import com.hthyaq.zybadmin.model.excelModel.TreeSelcetDataPersonProtectOfEnterprise;
import com.hthyaq.zybadmin.model.vo.MonitorOfEnterpriseView;
import com.hthyaq.zybadmin.model.vo.PersonProtectOfEnterpriseView;
import com.hthyaq.zybadmin.service.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

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
        personProtectOfEnterpriseView.setTreeSelect(String.valueOf(workplaceOfEnterprise.getName()+"--"+postOfEnterprise.getPostBigName()+"--"+postDangerOfEnterprise.getDangerBigName()));

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
        QueryWrapper<Enterprise> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.eq("name", sysUser.getCompanyName());
        List<Enterprise> list = enterpriseService.list(queryWrapper1);
        for (Enterprise enterprise : list) {
            list1.clear();
            Long id = enterprise.getId();
            list1.add(id);
        }
        QueryWrapper<PersonProtectOfEnterprise> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("enterpriseId",list1.get(0));
        if (!Strings.isNullOrEmpty(name)) {
            queryWrapper.eq("name", name);
        }
        IPage<PersonProtectOfEnterprise> page = personProtectOfEnterpriseService.page(new Page<>(currentPage, pageSize), queryWrapper);

        return page;
    }
    @GetMapping("/TreeSelcetData")
    public List<TreeSelcetDataPersonProtectOfEnterprise> TreeSelcetData() {
        List<TreeSelcetDataPersonProtectOfEnterprise> treeSelcetDatalist = new ArrayList();
        List<WorkplaceOfEnterprise> list = workplaceOfEnterpriseService.list();
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
                treeSelcetDataPersonProtectOfEnterprise.setTitle(postOfEnterprise.getPostBigName());
                treeSelcetDataPersonProtectOfEnterprise.setValue(String.valueOf(postOfEnterprise.getId()));
                treeSelcetDataPersonProtectOfEnterprise.setKey(String.valueOf(postOfEnterprise.getId()));
                chilren.add(treeSelcetDataPersonProtectOfEnterprise);
                List<TreeSelcetDataPersonProtectOfEnterprise> chilren2 = Lists.newArrayList();
                QueryWrapper<PostDangerOfEnterprise> queryWrapper2 = new QueryWrapper<>();
                queryWrapper2.eq("postId", postOfEnterprise.getId());
                List<PostDangerOfEnterprise> list2 = postDangerOfEnterpriseService.list(queryWrapper2);

                for (PostDangerOfEnterprise postDangerOfEnterprise : list2) {
                    TreeSelcetDataPersonProtectOfEnterprise treeSelcetDataPersonProtectOfEnterprise2 = new TreeSelcetDataPersonProtectOfEnterprise();
                    treeSelcetDataPersonProtectOfEnterprise2.setTitle(postDangerOfEnterprise.getDangerBigName());
                    treeSelcetDataPersonProtectOfEnterprise2.setValue(String.valueOf(postDangerOfEnterprise.getId()));
                    treeSelcetDataPersonProtectOfEnterprise2.setKey(String.valueOf(postDangerOfEnterprise.getId()));
                    chilren2.add(treeSelcetDataPersonProtectOfEnterprise2);
                }
                treeSelcetDataPersonProtectOfEnterprise.setChildren(chilren2);
            }
        }
        return treeSelcetDatalist;
    }
}
