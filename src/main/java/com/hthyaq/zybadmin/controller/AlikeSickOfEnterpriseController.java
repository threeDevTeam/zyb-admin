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
import com.hthyaq.zybadmin.model.excelModel.TreeSelcetDataAlikeSickOfEnterprise;
import com.hthyaq.zybadmin.model.excelModel.TreeSelcetDataSickOfEnterprise;
import com.hthyaq.zybadmin.model.vo.AlikeSickOfEnterpriseView;
import com.hthyaq.zybadmin.model.vo.SickOfEnterpriseView;
import com.hthyaq.zybadmin.service.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

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
    @PostMapping("/add")
    public boolean add(@RequestBody AlikeSickOfEnterpriseView alikeSickOfEnterpriseView, HttpSession httpSession) {
        boolean flag = false;
        System.out.println(alikeSickOfEnterpriseView);
        AlikeSickOfEnterprise alikeSickOfEnterprise = new  AlikeSickOfEnterprise();

        //other
        BeanUtils.copyProperties(alikeSickOfEnterpriseView,  alikeSickOfEnterprise);

        //enterpriseId
        SysUser sysUser = (SysUser) httpSession.getAttribute(GlobalConstants.LOGIN_NAME);
        alikeSickOfEnterprise.setEnterpriseId(sysUser.getCompanyId());

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
        PostDangerOfEnterprise postDangerOfEnterprise = postDangerOfEnterpriseService.getById(alikeSickOfEnterprise.getPostDangerId());
        PostOfEnterprise postOfEnterprise = postOfEnterpriseService.getById(alikeSickOfEnterprise.getPostId());
        WorkplaceOfEnterprise workplaceOfEnterprise= workplaceOfEnterpriseService.getById(alikeSickOfEnterprise.getWorkplaceId());
        alikeSickOfEnterpriseView.setTreeSelect(String.valueOf(workplaceOfEnterprise.getName()+"--"+postOfEnterprise.getPostBigName()+"--"+postDangerOfEnterprise.getDangerBigName()));

        System.out.println(alikeSickOfEnterpriseView);
        //将demoCourse的数据设置到demoData
        return alikeSickOfEnterpriseView;
    }

    @PostMapping("/edit")
    public boolean edit(@RequestBody AlikeSickOfEnterprise alikeSickOfEnterprise) {
        return alikeSickOfEnterpriseService.updateById(alikeSickOfEnterprise);
    }

    @GetMapping("/list")
    public IPage<AlikeSickOfEnterprise> list(String json) {
        //字符串解析成java对象
        JSONObject jsonObject = JSON.parseObject(json);
        //从对象中获取值
        Integer currentPage = jsonObject.getInteger("currentPage");
        Integer pageSize = jsonObject.getInteger("pageSize");
        String name = jsonObject.getString("name");
        QueryWrapper<AlikeSickOfEnterprise> queryWrapper = new QueryWrapper<>();
        if (!Strings.isNullOrEmpty(name)) {
            queryWrapper.eq("name", name);
        }
        IPage<AlikeSickOfEnterprise> page = alikeSickOfEnterpriseService.page(new Page<>(currentPage, pageSize), queryWrapper);

        return page;
    }
    @GetMapping("/TreeSelcetData")
    public List<TreeSelcetDataAlikeSickOfEnterprise> TreeSelcetData() {
        List<TreeSelcetDataAlikeSickOfEnterprise> treeSelcetDatalist = new ArrayList();
        List<WorkplaceOfEnterprise> list = workplaceOfEnterpriseService.list();
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
                treeSelcetDataAlikeSickOfEnterprise.setTitle(postOfEnterprise.getPostBigName());
                treeSelcetDataAlikeSickOfEnterprise.setValue(String.valueOf(postOfEnterprise.getId()));
                treeSelcetDataAlikeSickOfEnterprise.setKey(String.valueOf(postOfEnterprise.getId()));
                chilren.add(treeSelcetDataAlikeSickOfEnterprise);
                List<TreeSelcetDataAlikeSickOfEnterprise> chilren2 = Lists.newArrayList();
                QueryWrapper<PostDangerOfEnterprise> queryWrapper2 = new QueryWrapper<>();
                queryWrapper2.eq("postId", postOfEnterprise.getId());
                List<PostDangerOfEnterprise> list2 = postDangerOfEnterpriseService.list(queryWrapper2);

                for (PostDangerOfEnterprise postDangerOfEnterprise : list2) {
                    TreeSelcetDataAlikeSickOfEnterprise treeSelcetDataAlikeSickOfEnterprise2 = new TreeSelcetDataAlikeSickOfEnterprise();
                    treeSelcetDataAlikeSickOfEnterprise2.setTitle(postDangerOfEnterprise.getDangerBigName());
                    treeSelcetDataAlikeSickOfEnterprise2.setValue(String.valueOf(postDangerOfEnterprise.getId()));
                    treeSelcetDataAlikeSickOfEnterprise2.setKey(String.valueOf(postDangerOfEnterprise.getId()));
                    chilren2.add(treeSelcetDataAlikeSickOfEnterprise2);
                }
                treeSelcetDataAlikeSickOfEnterprise.setChildren(chilren2);
            }
        }
        return treeSelcetDatalist;
    }
}

