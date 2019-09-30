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
import com.hthyaq.zybadmin.model.excelModel.TreeSelcetDataAccidentPersonOfEnterprise;
import com.hthyaq.zybadmin.model.vo.AccidentPersonOfEnterpriseView;
import com.hthyaq.zybadmin.model.vo.PostDangerOfEnterpriseView;
import com.hthyaq.zybadmin.service.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 事故伤亡人员信息 前端控制器
 * </p>
 *
 * @author zhangqiang
 * @since 2019-08-30
 */
@RestController
@RequestMapping("/accidentPersonOfEnterprise")
public class AccidentPersonOfEnterpriseController {
    @Autowired
    AccidentPersonOfEnterpriseService accidentPersonOfEnterpriseService;
    @Autowired
    EnterpriseOfRegisterService enterpriseOfRegisterService;
    @Autowired
    EnterpriseService enterpriseService;
    @Autowired
    WorkplaceOfEnterpriseService workplaceOfEnterpriseService;
    @Autowired
    PostOfEnterpriseService postOfEnterpriseService;
    @PostMapping("/add")
    public boolean add(@RequestBody AccidentPersonOfEnterpriseView accidentPersonOfEnterpriseView ,HttpSession httpSession) {
        boolean flag = false;
        System.out.println(accidentPersonOfEnterpriseView);
        AccidentPersonOfEnterprise accidentPersonOfEnterprise = new AccidentPersonOfEnterprise();
        //other
        BeanUtils.copyProperties(accidentPersonOfEnterpriseView, accidentPersonOfEnterprise);

        //enterpriseId
        SysUser sysUser = (SysUser) httpSession.getAttribute(GlobalConstants.LOGIN_NAME);
        QueryWrapper<Enterprise> queryWrapper1=new QueryWrapper();
        queryWrapper1.eq("name",sysUser.getCompanyName());
        List<Enterprise> list1 = enterpriseService.list(queryWrapper1);
        for (Enterprise enterprise : list1) {
            accidentPersonOfEnterprise.setEnterpriseId(enterprise.getId());
        }

        //postId
        accidentPersonOfEnterprise.setPostId(Long.parseLong(accidentPersonOfEnterpriseView.getTreeSelect()));

        //通过postId查询出workplaceId
        PostOfEnterprise postOfEnterprise = postOfEnterpriseService.getById(accidentPersonOfEnterpriseView.getTreeSelect());
        accidentPersonOfEnterprise.setWorkplaceId(postOfEnterprise.getWorkplaceId());

        //保存
        System.out.println(accidentPersonOfEnterprise);
        flag = accidentPersonOfEnterpriseService.save(accidentPersonOfEnterprise);

        return flag;
    }

    @GetMapping("/delete")
    public boolean delete(String id) {
        return accidentPersonOfEnterpriseService.removeById(id);
    }

    @GetMapping("/getById")
    public AccidentPersonOfEnterprise getById(Integer id) {
        AccidentPersonOfEnterpriseView accidentPersonOfEnterpriseView=new AccidentPersonOfEnterpriseView();
        AccidentPersonOfEnterprise accidentPersonOfEnterprise = accidentPersonOfEnterpriseService.getById(id);
        BeanUtils.copyProperties(accidentPersonOfEnterprise, accidentPersonOfEnterpriseView);
        PostOfEnterprise postOfEnterprise = postOfEnterpriseService.getById(accidentPersonOfEnterprise.getPostId());
        WorkplaceOfEnterprise workplaceOfEnterprise= workplaceOfEnterpriseService.getById(accidentPersonOfEnterprise.getWorkplaceId());
        accidentPersonOfEnterpriseView.setTreeSelect(String.valueOf(workplaceOfEnterprise.getName()+"--"+postOfEnterprise.getPostBigName()));
        return accidentPersonOfEnterpriseView;
    }

    @PostMapping("/edit")
    public boolean edit(@RequestBody AccidentPersonOfEnterprise accidentPersonOfEnterprise) {
        return accidentPersonOfEnterpriseService.updateById(accidentPersonOfEnterprise);
    }

    @GetMapping("/list")
    public IPage<AccidentPersonOfEnterprise> list(String json, HttpSession httpSession) {
        SysUser sysUser = (SysUser) httpSession.getAttribute(GlobalConstants.LOGIN_NAME);
        List list1 = new ArrayList();
        //字符串解析成java对象
        JSONObject jsonObject = JSON.parseObject(json);
        //从对象中获取值
        Integer currentPage = jsonObject.getInteger("currentPage");
        Integer pageSize = jsonObject.getInteger("pageSize");
        String org = jsonObject.getString("org");
        QueryWrapper<Enterprise> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.eq("name", sysUser.getCompanyName());
        List<Enterprise> list = enterpriseService.list(queryWrapper1);
        for (Enterprise enterprise : list) {
            list1.clear();
            Long id = enterprise.getId();
            list1.add(id);
        }
        QueryWrapper<AccidentPersonOfEnterprise> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("enterpriseId",list1.get(0));
        if (!Strings.isNullOrEmpty(org)) {
            queryWrapper.eq("org", org);
        }
        IPage<AccidentPersonOfEnterprise> page = accidentPersonOfEnterpriseService.page(new Page<>(currentPage, pageSize), queryWrapper);

        return page;
    }
    @GetMapping("/TreeSelcetData")
    public List<TreeSelcetDataAccidentPersonOfEnterprise> TreeSelcetData() {
        List<TreeSelcetDataAccidentPersonOfEnterprise> treeSelcetDatalist = new ArrayList();
        List<WorkplaceOfEnterprise> list = workplaceOfEnterpriseService.list();
        for (WorkplaceOfEnterprise workplaceOfEnterprise : list) {
            List<TreeSelcetDataAccidentPersonOfEnterprise> chilren = Lists.newArrayList();
            TreeSelcetDataAccidentPersonOfEnterprise treeSelcetDataAccidentPersonOfEnterprise1 = new TreeSelcetDataAccidentPersonOfEnterprise();
            treeSelcetDataAccidentPersonOfEnterprise1.setTitle(workplaceOfEnterprise.getName());
            treeSelcetDataAccidentPersonOfEnterprise1.setValue(String.valueOf(workplaceOfEnterprise.getId()));
            treeSelcetDataAccidentPersonOfEnterprise1.setKey(String.valueOf(workplaceOfEnterprise.getId()));
            treeSelcetDataAccidentPersonOfEnterprise1.setChildren(chilren);
            treeSelcetDatalist.add(treeSelcetDataAccidentPersonOfEnterprise1);
            QueryWrapper<PostOfEnterprise> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("workplaceId", workplaceOfEnterprise.getId());
            List<PostOfEnterprise> list1 = postOfEnterpriseService.list(queryWrapper);
            for (PostOfEnterprise postOfEnterprise : list1) {
                TreeSelcetDataAccidentPersonOfEnterprise treeSelcetDataAccidentPersonOfEnterprise = new TreeSelcetDataAccidentPersonOfEnterprise();
                treeSelcetDataAccidentPersonOfEnterprise.setTitle(postOfEnterprise.getPostBigName());
                treeSelcetDataAccidentPersonOfEnterprise.setValue(String.valueOf(postOfEnterprise.getId()));
                treeSelcetDataAccidentPersonOfEnterprise.setKey(String.valueOf(postOfEnterprise.getId()));
                chilren.add(treeSelcetDataAccidentPersonOfEnterprise);
            }

        }
        return treeSelcetDatalist;
    }
}
