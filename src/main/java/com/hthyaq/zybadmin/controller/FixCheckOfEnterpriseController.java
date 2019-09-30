package com.hthyaq.zybadmin.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.hthyaq.zybadmin.common.constants.GlobalConstants;
import com.hthyaq.zybadmin.model.bean.Child;
import com.hthyaq.zybadmin.model.bean.Child3;
import com.hthyaq.zybadmin.model.entity.*;
import com.hthyaq.zybadmin.model.excelModel.TreeSelcetDataEnterpriseCheckSumOfEnterprise;
import com.hthyaq.zybadmin.model.excelModel.TreeSelcetDataFixCheckOfEnterprise;
import com.hthyaq.zybadmin.model.vo.DemoView;
import com.hthyaq.zybadmin.model.vo.EnterpriseCheckSumOfEnterpriseView;
import com.hthyaq.zybadmin.model.vo.FixCheckOfView;
import com.hthyaq.zybadmin.service.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 定期检测信息 前端控制器
 * </p>
 *
 * @author zhangqiang
 * @since 2019-09-02
 */
@RestController
@RequestMapping("/fixCheckOfEnterprise")
public class FixCheckOfEnterpriseController {
    @Autowired
    FixCheckOfEnterpriseService fixCheckOfEnterpriseService;
    @Autowired
    FixCheckResultOfEnterpriseService fixCheckResultOfEnterpriseService;
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
    public boolean add(@RequestBody FixCheckOfView fixCheckOfView, HttpSession httpSession) {
        return fixCheckOfEnterpriseService.saveData(fixCheckOfView,httpSession);
    }

    @GetMapping("/delete")
    public boolean delete(String id) {
        return fixCheckOfEnterpriseService.deleteData(id);
    }

    @GetMapping("/getById")
    public FixCheckOfView getById(Integer id) {
        FixCheckOfView fixCheckOfView = new FixCheckOfView();

        //demo
        FixCheckOfEnterprise fixCheckOfEnterprise = fixCheckOfEnterpriseService.getById(id);
        //将demo的数据设置到demoData
        BeanUtils.copyProperties(fixCheckOfEnterprise, fixCheckOfView);

        PostDangerOfEnterprise postDangerOfEnterprise = postDangerOfEnterpriseService.getById(fixCheckOfEnterprise.getPostDangerId());
        PostOfEnterprise postOfEnterprise = postOfEnterpriseService.getById(fixCheckOfEnterprise.getPostId());
        WorkplaceOfEnterprise workplaceOfEnterprise= workplaceOfEnterpriseService.getById(fixCheckOfEnterprise.getWorkplaceId());
        fixCheckOfView.setTreeSelect(String.valueOf(workplaceOfEnterprise.getName()+"--"+postOfEnterprise.getPostBigName()+"--"+postDangerOfEnterprise.getDangerBigName()));

        //demoCourse
        QueryWrapper<FixCheckResultOfEnterprise> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("fixCheckId", id);
        List<FixCheckResultOfEnterprise> demoCourseList = fixCheckResultOfEnterpriseService.list(queryWrapper);

        //将demoCourse的数据设置到demoData
        fixCheckOfView.setCourse(new Child3<>(demoCourseList));
        return fixCheckOfView;
    }

    @PostMapping("/edit")
    public boolean edit(@RequestBody FixCheckOfView fixCheckOfView) {
        return fixCheckOfEnterpriseService.editData(fixCheckOfView);
    }


    @GetMapping("/list")
    public IPage< FixCheckOfEnterprise> list(String json, HttpSession httpSession) {
        SysUser sysUser = (SysUser) httpSession.getAttribute(GlobalConstants.LOGIN_NAME);
        List list1 = new ArrayList();
        //字符串解析成java对象
        JSONObject jsonObject = JSON.parseObject(json);jsonObject.getObject("zbry",String.class);
        //从对象中获取值
        Integer currentPage = jsonObject.getInteger("currentPage");
        Integer pageSize = jsonObject.getInteger("pageSize");
        String decideResult = jsonObject.getString("decideResult");
        QueryWrapper<Enterprise> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.eq("name", sysUser.getCompanyName());
        List<Enterprise> list = enterpriseService.list(queryWrapper1);
        for (Enterprise enterprise : list) {
            list1.clear();
            Long id = enterprise.getId();
            list1.add(id);
        }
        QueryWrapper<FixCheckOfEnterprise> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("enterpriseId",list1.get(0));
        if (!Strings.isNullOrEmpty(decideResult)) {
            queryWrapper.eq("decideResult", decideResult);
        }

        IPage< FixCheckOfEnterprise> page = fixCheckOfEnterpriseService.page(new Page<>(currentPage, pageSize), queryWrapper);

        return page;
    }
    @GetMapping("/TreeSelcetData")
    public List<TreeSelcetDataFixCheckOfEnterprise> TreeSelcetData() {
        List<TreeSelcetDataFixCheckOfEnterprise> treeSelcetDatalist = new ArrayList();
        List<WorkplaceOfEnterprise> list = workplaceOfEnterpriseService.list();
        for (WorkplaceOfEnterprise workplaceOfEnterprise : list) {
            List<TreeSelcetDataFixCheckOfEnterprise> chilren = Lists.newArrayList();

            TreeSelcetDataFixCheckOfEnterprise treeSelcetDataFixCheckOfEnterprise1 = new TreeSelcetDataFixCheckOfEnterprise();
            treeSelcetDataFixCheckOfEnterprise1.setTitle(workplaceOfEnterprise.getName());
            treeSelcetDataFixCheckOfEnterprise1.setValue(String.valueOf(workplaceOfEnterprise.getId()));
            treeSelcetDataFixCheckOfEnterprise1.setKey(String.valueOf(workplaceOfEnterprise.getId()));
            treeSelcetDataFixCheckOfEnterprise1.setChildren(chilren);
            treeSelcetDatalist.add(treeSelcetDataFixCheckOfEnterprise1);


            QueryWrapper<PostOfEnterprise> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("workplaceId", workplaceOfEnterprise.getId());
            List<PostOfEnterprise> list1 = postOfEnterpriseService.list(queryWrapper);
            for (PostOfEnterprise postOfEnterprise : list1) {

                TreeSelcetDataFixCheckOfEnterprise treeSelcetDataFixCheckOfEnterprise = new TreeSelcetDataFixCheckOfEnterprise();
                treeSelcetDataFixCheckOfEnterprise.setTitle(postOfEnterprise.getPostBigName());
                treeSelcetDataFixCheckOfEnterprise.setValue(String.valueOf(postOfEnterprise.getId()));
                treeSelcetDataFixCheckOfEnterprise.setKey(String.valueOf(postOfEnterprise.getId()));
                chilren.add(treeSelcetDataFixCheckOfEnterprise);
                List<TreeSelcetDataFixCheckOfEnterprise> chilren2 = Lists.newArrayList();
                QueryWrapper<PostDangerOfEnterprise> queryWrapper2 = new QueryWrapper<>();
                queryWrapper2.eq("postId", postOfEnterprise.getId());
                List<PostDangerOfEnterprise> list2 = postDangerOfEnterpriseService.list(queryWrapper2);

                for (PostDangerOfEnterprise postDangerOfEnterprise : list2) {
                    TreeSelcetDataFixCheckOfEnterprise treeSelcetDataFixCheckOfEnterprise2 = new TreeSelcetDataFixCheckOfEnterprise();
                    treeSelcetDataFixCheckOfEnterprise2.setTitle(postDangerOfEnterprise.getDangerBigName());
                    treeSelcetDataFixCheckOfEnterprise2.setValue(String.valueOf(postDangerOfEnterprise.getId()));
                    treeSelcetDataFixCheckOfEnterprise2.setKey(String.valueOf(postDangerOfEnterprise.getId()));
                    chilren2.add(treeSelcetDataFixCheckOfEnterprise2);
                }
                treeSelcetDataFixCheckOfEnterprise.setChildren(chilren2);
            }
        }
        return treeSelcetDatalist;
    }
}
