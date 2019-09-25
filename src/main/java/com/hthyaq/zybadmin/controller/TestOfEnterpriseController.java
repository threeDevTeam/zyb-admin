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
import com.hthyaq.zybadmin.model.excelModel.TreeSelcetDataPersonProtectOfEnterprise;
import com.hthyaq.zybadmin.model.excelModel.TreeSelcetDataTestOfEnterprise;
import com.hthyaq.zybadmin.model.vo.PersonProtectOfEnterpriseView;
import com.hthyaq.zybadmin.model.vo.TestOfEnterpriseView;
import com.hthyaq.zybadmin.service.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 岗位职业健康检查信息 前端控制器
 * </p>
 *
 * @author zhangqiang
 * @since 2019-08-30
 */
@RestController
@RequestMapping("/testOfEnterprise")
public class TestOfEnterpriseController {
    @Autowired
    TestOfEnterpriseService testOfEnterpriseService;
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
    public boolean add(@RequestBody TestOfEnterpriseView testOfEnterpriseView, HttpSession httpSession) {
        boolean flag = false;
        System.out.println(testOfEnterpriseView);
        TestOfEnterprise testOfEnterprise = new TestOfEnterprise();

        //other
        BeanUtils.copyProperties(testOfEnterpriseView, testOfEnterprise);

        //enterpriseId
        SysUser sysUser = (SysUser) httpSession.getAttribute(GlobalConstants.LOGIN_NAME);
        testOfEnterprise.setEnterpriseId(sysUser.getCompanyId());

        //postDangerId
        testOfEnterprise.setPostDangerId(Long.parseLong(testOfEnterpriseView.getTreeSelect()));

        //通过postDangerId查询出workplaceId,postId
        PostDangerOfEnterprise postDangerOfEnterprise = postDangerOfEnterpriseService.getById(testOfEnterpriseView.getTreeSelect());
        testOfEnterprise.setWorkplaceId(postDangerOfEnterprise.getWorkplaceId());
        testOfEnterprise.setPostId(postDangerOfEnterprise.getPostId());

        System.out.println(postDangerOfEnterprise);
        flag = testOfEnterpriseService.save(testOfEnterprise);
        return flag;
    }

    @GetMapping("/delete")
    public boolean delete(String id) {
        return testOfEnterpriseService.removeById(id);
    }

    @GetMapping("/getById")
    public TestOfEnterprise getById(Integer id) {
        TestOfEnterpriseView testOfEnterpriseView=new TestOfEnterpriseView();
        TestOfEnterprise testOfEnterprise = testOfEnterpriseService.getById(id);
        BeanUtils.copyProperties(testOfEnterprise,testOfEnterpriseView);
        PostDangerOfEnterprise postDangerOfEnterprise = postDangerOfEnterpriseService.getById(testOfEnterprise.getPostDangerId());
        PostOfEnterprise postOfEnterprise = postOfEnterpriseService.getById(testOfEnterprise.getPostId());
        WorkplaceOfEnterprise workplaceOfEnterprise= workplaceOfEnterpriseService.getById(testOfEnterprise.getWorkplaceId());
        testOfEnterpriseView.setTreeSelect(String.valueOf(workplaceOfEnterprise.getName()+"--"+postOfEnterprise.getPostBigName()+"--"+postDangerOfEnterprise.getDangerBigName()));

        System.out.println(testOfEnterpriseView);
        //将demoCourse的数据设置到demoData
        return testOfEnterpriseView;
    }

    @PostMapping("/edit")
    public boolean edit(@RequestBody TestOfEnterprise testOfEnterprise) {
        return testOfEnterpriseService.updateById(testOfEnterprise);
    }

    @GetMapping("/list")
    public IPage<TestOfEnterprise> list(String json) {
        //字符串解析成java对象
        JSONObject jsonObject = JSON.parseObject(json);
        //从对象中获取值
        Integer currentPage = jsonObject.getInteger("currentPage");
        Integer pageSize = jsonObject.getInteger("pageSize");
        String name = jsonObject.getString("name");
        QueryWrapper<TestOfEnterprise> queryWrapper = new QueryWrapper<>();
        if (!Strings.isNullOrEmpty(name)) {
            queryWrapper.eq("name", name);
        }
        IPage<TestOfEnterprise> page = testOfEnterpriseService.page(new Page<>(currentPage, pageSize), queryWrapper);

        return page;
    }
    @GetMapping("/TreeSelcetData")
    public List<TreeSelcetDataTestOfEnterprise> TreeSelcetData() {
        List<TreeSelcetDataTestOfEnterprise> treeSelcetDatalist = new ArrayList();
        List<WorkplaceOfEnterprise> list = workplaceOfEnterpriseService.list();
        for (WorkplaceOfEnterprise workplaceOfEnterprise : list) {
            List<TreeSelcetDataTestOfEnterprise> chilren = Lists.newArrayList();

            TreeSelcetDataTestOfEnterprise treeSelcetDataTestOfEnterprise1 = new TreeSelcetDataTestOfEnterprise();
            treeSelcetDataTestOfEnterprise1.setTitle(workplaceOfEnterprise.getName());
            treeSelcetDataTestOfEnterprise1.setValue(String.valueOf(workplaceOfEnterprise.getId()));
            treeSelcetDataTestOfEnterprise1.setKey(String.valueOf(workplaceOfEnterprise.getId()));
            treeSelcetDataTestOfEnterprise1.setChildren(chilren);
            treeSelcetDatalist.add(treeSelcetDataTestOfEnterprise1);


            QueryWrapper<PostOfEnterprise> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("workplaceId", workplaceOfEnterprise.getId());
            List<PostOfEnterprise> list1 = postOfEnterpriseService.list(queryWrapper);
            for (PostOfEnterprise postOfEnterprise : list1) {

                TreeSelcetDataTestOfEnterprise treeSelcetDataTestOfEnterprise = new TreeSelcetDataTestOfEnterprise();
                treeSelcetDataTestOfEnterprise.setTitle(postOfEnterprise.getPostBigName());
                treeSelcetDataTestOfEnterprise.setValue(String.valueOf(postOfEnterprise.getId()));
                treeSelcetDataTestOfEnterprise.setKey(String.valueOf(postOfEnterprise.getId()));
                chilren.add(treeSelcetDataTestOfEnterprise);
                List<TreeSelcetDataTestOfEnterprise> chilren2 = Lists.newArrayList();
                QueryWrapper<PostDangerOfEnterprise> queryWrapper2 = new QueryWrapper<>();
                queryWrapper2.eq("postId", postOfEnterprise.getId());
                List<PostDangerOfEnterprise> list2 = postDangerOfEnterpriseService.list(queryWrapper2);

                for (PostDangerOfEnterprise postDangerOfEnterprise : list2) {
                    TreeSelcetDataTestOfEnterprise treeSelcetDataTestOfEnterprise2 = new TreeSelcetDataTestOfEnterprise();
                    treeSelcetDataTestOfEnterprise2.setTitle(postDangerOfEnterprise.getDangerBigName());
                    treeSelcetDataTestOfEnterprise2.setValue(String.valueOf(postDangerOfEnterprise.getId()));
                    treeSelcetDataTestOfEnterprise2.setKey(String.valueOf(postDangerOfEnterprise.getId()));
                    chilren2.add(treeSelcetDataTestOfEnterprise2);
                }
                treeSelcetDataTestOfEnterprise.setChildren(chilren2);
            }
        }
        return treeSelcetDatalist;
    }
}
