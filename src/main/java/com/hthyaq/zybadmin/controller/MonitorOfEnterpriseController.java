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
import com.hthyaq.zybadmin.model.excelModel.TreeSelcetDataTouchPersonOfEnterprise;
import com.hthyaq.zybadmin.model.vo.MonitorOfEnterpriseView;
import com.hthyaq.zybadmin.model.vo.TouchPersonOfEnterpriseView;
import com.hthyaq.zybadmin.service.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 日常监测信息 前端控制器
 * </p>
 *
 * @author zhangqiang
 * @since 2019-08-30
 */
@RestController
@RequestMapping("/monitorOfEnterprise")
public class MonitorOfEnterpriseController {
    @Autowired
    MonitorOfEnterpriseService monitorOfEnterpriseService;
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
    public boolean add(@RequestBody MonitorOfEnterpriseView monitorOfEnterpriseView, HttpSession httpSession) {
        boolean flag = false;
        System.out.println(monitorOfEnterpriseView);
        MonitorOfEnterprise monitorOfEnterprise = new MonitorOfEnterprise();

        //other
        BeanUtils.copyProperties(monitorOfEnterpriseView, monitorOfEnterprise);

        //enterpriseId
        SysUser sysUser = (SysUser) httpSession.getAttribute(GlobalConstants.LOGIN_NAME);
        QueryWrapper<Enterprise> queryWrapper1=new QueryWrapper();
        queryWrapper1.eq("name",sysUser.getCompanyName());
        List<Enterprise> list1 = enterpriseService.list(queryWrapper1);
        for (Enterprise enterprise : list1) {
            monitorOfEnterprise.setEnterpriseId(enterprise.getId());
        }

        //postDangerId
        monitorOfEnterprise.setPostDangerId(Long.parseLong(monitorOfEnterpriseView.getTreeSelect()));

        //通过postDangerId查询出workplaceId,postId
        PostDangerOfEnterprise postDangerOfEnterprise = postDangerOfEnterpriseService.getById(monitorOfEnterpriseView.getTreeSelect());
        monitorOfEnterprise.setWorkplaceId(postDangerOfEnterprise.getWorkplaceId());
        monitorOfEnterprise.setPostId(postDangerOfEnterprise.getPostId());

        System.out.println(postDangerOfEnterprise);
        flag = monitorOfEnterpriseService.save(monitorOfEnterprise);

        return flag;
    }

    @GetMapping("/delete")
    public boolean delete(String id) {
        return monitorOfEnterpriseService.removeById(id);
    }

    @GetMapping("/getById")
    public MonitorOfEnterprise getById(Integer id) {
        MonitorOfEnterpriseView monitorOfEnterpriseView=new MonitorOfEnterpriseView();
        MonitorOfEnterprise monitorOfEnterprise = monitorOfEnterpriseService.getById(id);
        BeanUtils.copyProperties(monitorOfEnterprise,monitorOfEnterpriseView);
        PostDangerOfEnterprise postDangerOfEnterprise = postDangerOfEnterpriseService.getById(monitorOfEnterprise.getPostDangerId());
        PostOfEnterprise postOfEnterprise = postOfEnterpriseService.getById(monitorOfEnterprise.getPostId());
        WorkplaceOfEnterprise workplaceOfEnterprise= workplaceOfEnterpriseService.getById(monitorOfEnterprise.getWorkplaceId());
        monitorOfEnterpriseView.setTreeSelect(String.valueOf(workplaceOfEnterprise.getName()+"--"+postOfEnterprise.getPostBigName()+"--"+postDangerOfEnterprise.getDangerBigName()));

        System.out.println(monitorOfEnterpriseView);
        //将demoCourse的数据设置到demoData
        return monitorOfEnterpriseView;
    }

    @PostMapping("/edit")
    public boolean edit(@RequestBody MonitorOfEnterprise monitorOfEnterprise) {
        return monitorOfEnterpriseService.updateById(monitorOfEnterprise);
    }

    @GetMapping("/list")
    public IPage<MonitorOfEnterprise> list(String json, HttpSession httpSession) {
        SysUser sysUser = (SysUser) httpSession.getAttribute(GlobalConstants.LOGIN_NAME);
        List list1 = new ArrayList();
        //字符串解析成java对象
        JSONObject jsonObject = JSON.parseObject(json);
        //从对象中获取值
        Integer currentPage = jsonObject.getInteger("currentPage");
        Integer pageSize = jsonObject.getInteger("pageSize");
        String monitorTime = jsonObject.getString("monitorTime");
        QueryWrapper<Enterprise> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.eq("name", sysUser.getCompanyName());
        List<Enterprise> list = enterpriseService.list(queryWrapper1);
        for (Enterprise enterprise : list) {
            list1.clear();
            Long id = enterprise.getId();
            list1.add(id);
        }
        QueryWrapper<MonitorOfEnterprise> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("enterpriseId",list1.get(0));
        if (!Strings.isNullOrEmpty(monitorTime)) {
            queryWrapper.eq("monitorTime", monitorTime);
        }
        IPage<MonitorOfEnterprise> page = monitorOfEnterpriseService.page(new Page<>(currentPage, pageSize), queryWrapper);

        return page;
    }
    @GetMapping("/TreeSelcetData")
    public List<TreeSelcetDataMonitorOfEnterprise> TreeSelcetData() {
        List<TreeSelcetDataMonitorOfEnterprise> treeSelcetDatalist = new ArrayList();
        List<WorkplaceOfEnterprise> list = workplaceOfEnterpriseService.list();
        for (WorkplaceOfEnterprise workplaceOfEnterprise : list) {
            List<TreeSelcetDataMonitorOfEnterprise> chilren = Lists.newArrayList();

            TreeSelcetDataMonitorOfEnterprise treeSelcetDataMonitorOfEnterprise1 = new TreeSelcetDataMonitorOfEnterprise();
            treeSelcetDataMonitorOfEnterprise1.setTitle(workplaceOfEnterprise.getName());
            treeSelcetDataMonitorOfEnterprise1.setValue(String.valueOf(workplaceOfEnterprise.getId()));
            treeSelcetDataMonitorOfEnterprise1.setKey(String.valueOf(workplaceOfEnterprise.getId()));
            treeSelcetDataMonitorOfEnterprise1.setChildren(chilren);
            treeSelcetDatalist.add(treeSelcetDataMonitorOfEnterprise1);


            QueryWrapper<PostOfEnterprise> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("workplaceId", workplaceOfEnterprise.getId());
            List<PostOfEnterprise> list1 = postOfEnterpriseService.list(queryWrapper);
            for (PostOfEnterprise postOfEnterprise : list1) {

                TreeSelcetDataMonitorOfEnterprise treeSelcetDataMonitorOfEnterprise = new TreeSelcetDataMonitorOfEnterprise();
                treeSelcetDataMonitorOfEnterprise.setTitle(postOfEnterprise.getPostBigName());
                treeSelcetDataMonitorOfEnterprise.setValue(String.valueOf(postOfEnterprise.getId()));
                treeSelcetDataMonitorOfEnterprise.setKey(String.valueOf(postOfEnterprise.getId()));
                chilren.add(treeSelcetDataMonitorOfEnterprise);
                List<TreeSelcetDataMonitorOfEnterprise> chilren2 = Lists.newArrayList();
                QueryWrapper<PostDangerOfEnterprise> queryWrapper2 = new QueryWrapper<>();
                queryWrapper2.eq("postId", postOfEnterprise.getId());
                List<PostDangerOfEnterprise> list2 = postDangerOfEnterpriseService.list(queryWrapper2);

                for (PostDangerOfEnterprise postDangerOfEnterprise : list2) {
                    TreeSelcetDataMonitorOfEnterprise treeSelcetDataMonitorOfEnterprise2 = new TreeSelcetDataMonitorOfEnterprise();
                    treeSelcetDataMonitorOfEnterprise2.setTitle(postDangerOfEnterprise.getDangerBigName());
                    treeSelcetDataMonitorOfEnterprise2.setValue(String.valueOf(postDangerOfEnterprise.getId()));
                    treeSelcetDataMonitorOfEnterprise2.setKey(String.valueOf(postDangerOfEnterprise.getId()));
                    chilren2.add(treeSelcetDataMonitorOfEnterprise2);
                }
                treeSelcetDataMonitorOfEnterprise.setChildren(chilren2);
            }
        }
        return treeSelcetDatalist;
    }
}
