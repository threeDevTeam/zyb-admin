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
import com.hthyaq.zybadmin.model.excelModel.TreeSelcetDataProtectOfEnterprise;
import com.hthyaq.zybadmin.model.vo.MonitorOfEnterpriseView;
import com.hthyaq.zybadmin.model.vo.ProtectOfEnterpriseView;
import com.hthyaq.zybadmin.service.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 防护设施信息 前端控制器
 * </p>
 *
 * @author zhangqiang
 * @since 2019-09-02
 */
@RestController
@RequestMapping("/protectOfEnterprise")
public class ProtectOfEnterpriseController {
    @Autowired
    ProtectOfEnterpriseService protectOfEnterpriseService;
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
    public boolean add(@RequestBody ProtectOfEnterpriseView protectOfEnterpriseView, HttpSession httpSession) {
        boolean flag = false;
        System.out.println(protectOfEnterpriseView);
        ProtectOfEnterprise protectOfEnterprise = new  ProtectOfEnterprise();

        //other
        BeanUtils.copyProperties(protectOfEnterpriseView, protectOfEnterprise);

        //enterpriseId
        SysUser sysUser = (SysUser) httpSession.getAttribute(GlobalConstants.LOGIN_NAME);
        protectOfEnterprise.setEnterpriseId(sysUser.getCompanyId());

        //postDangerId
        protectOfEnterprise.setPostDangerId(Long.parseLong(protectOfEnterpriseView.getTreeSelect()));

        //通过postDangerId查询出workplaceId,postId
        PostDangerOfEnterprise postDangerOfEnterprise = postDangerOfEnterpriseService.getById(protectOfEnterpriseView.getTreeSelect());
        protectOfEnterprise.setWorkplaceId(postDangerOfEnterprise.getWorkplaceId());
        protectOfEnterprise.setPostId(postDangerOfEnterprise.getPostId());

        System.out.println(postDangerOfEnterprise);
        flag = protectOfEnterpriseService.save(protectOfEnterprise);

        return flag;
    }

    @GetMapping("/delete")
    public boolean delete(String id) {
        return protectOfEnterpriseService.removeById(id);
    }

    @GetMapping("/getById")
    public ProtectOfEnterprise getById(Integer id) {
        ProtectOfEnterpriseView protectOfEnterpriseView =new ProtectOfEnterpriseView();
        ProtectOfEnterprise protectOfEnterprise = protectOfEnterpriseService.getById(id);
        BeanUtils.copyProperties(protectOfEnterprise,protectOfEnterpriseView);
        PostDangerOfEnterprise postDangerOfEnterprise = postDangerOfEnterpriseService.getById(protectOfEnterprise.getPostDangerId());
        PostOfEnterprise postOfEnterprise = postOfEnterpriseService.getById(protectOfEnterprise.getPostId());
        WorkplaceOfEnterprise workplaceOfEnterprise= workplaceOfEnterpriseService.getById(protectOfEnterprise.getWorkplaceId());
        protectOfEnterpriseView.setTreeSelect(String.valueOf(workplaceOfEnterprise.getName()+"--"+postOfEnterprise.getPostBigName()+"--"+postDangerOfEnterprise.getDangerBigName()));

        System.out.println(protectOfEnterpriseView);
        //将demoCourse的数据设置到demoData
        return protectOfEnterpriseView;
    }

    @PostMapping("/edit")
    public boolean edit(@RequestBody ProtectOfEnterprise protectOfEnterprise) {
        return protectOfEnterpriseService.updateById(protectOfEnterprise);
    }

    @GetMapping("/list")
    public IPage<ProtectOfEnterprise> list(String json) {
        //字符串解析成java对象
        JSONObject jsonObject = JSON.parseObject(json);
        //从对象中获取值
        Integer currentPage = jsonObject.getInteger("currentPage");
        Integer pageSize = jsonObject.getInteger("pageSize");
        String name = jsonObject.getString("name");
        String type = jsonObject.getString("type");
        QueryWrapper<ProtectOfEnterprise> queryWrapper = new QueryWrapper<>();
        if (!Strings.isNullOrEmpty(name)) {
            queryWrapper.eq("name", name);
        }
        if (!Strings.isNullOrEmpty(type)) {
            queryWrapper.eq("type", type);
        }
        IPage<ProtectOfEnterprise> page = protectOfEnterpriseService.page(new Page<>(currentPage, pageSize), queryWrapper);

        return page;
    }
    @GetMapping("/TreeSelcetData")
    public List<TreeSelcetDataProtectOfEnterprise> TreeSelcetData() {
        List<TreeSelcetDataProtectOfEnterprise> treeSelcetDatalist = new ArrayList();
        List<WorkplaceOfEnterprise> list = workplaceOfEnterpriseService.list();
        for (WorkplaceOfEnterprise workplaceOfEnterprise : list) {
            List<TreeSelcetDataProtectOfEnterprise> chilren = Lists.newArrayList();

            TreeSelcetDataProtectOfEnterprise treeSelcetDataProtectOfEnterprise1 = new TreeSelcetDataProtectOfEnterprise();
            treeSelcetDataProtectOfEnterprise1.setTitle(workplaceOfEnterprise.getName());
            treeSelcetDataProtectOfEnterprise1.setValue(String.valueOf(workplaceOfEnterprise.getId()));
            treeSelcetDataProtectOfEnterprise1.setKey(String.valueOf(workplaceOfEnterprise.getId()));
            treeSelcetDataProtectOfEnterprise1.setChildren(chilren);
            treeSelcetDatalist.add(treeSelcetDataProtectOfEnterprise1);


            QueryWrapper<PostOfEnterprise> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("workplaceId", workplaceOfEnterprise.getId());
            List<PostOfEnterprise> list1 = postOfEnterpriseService.list(queryWrapper);
            for (PostOfEnterprise postOfEnterprise : list1) {

                TreeSelcetDataProtectOfEnterprise treeSelcetDataProtectOfEnterprise = new TreeSelcetDataProtectOfEnterprise();
                treeSelcetDataProtectOfEnterprise.setTitle(postOfEnterprise.getPostBigName());
                treeSelcetDataProtectOfEnterprise.setValue(String.valueOf(postOfEnterprise.getId()));
                treeSelcetDataProtectOfEnterprise.setKey(String.valueOf(postOfEnterprise.getId()));
                chilren.add(treeSelcetDataProtectOfEnterprise);
                List<TreeSelcetDataProtectOfEnterprise> chilren2 = Lists.newArrayList();
                QueryWrapper<PostDangerOfEnterprise> queryWrapper2 = new QueryWrapper<>();
                queryWrapper2.eq("postId", postOfEnterprise.getId());
                List<PostDangerOfEnterprise> list2 = postDangerOfEnterpriseService.list(queryWrapper2);

                for (PostDangerOfEnterprise postDangerOfEnterprise : list2) {
                    TreeSelcetDataProtectOfEnterprise treeSelcetDataProtectOfEnterprise2 = new TreeSelcetDataProtectOfEnterprise();
                    treeSelcetDataProtectOfEnterprise2.setTitle(postDangerOfEnterprise.getDangerBigName());
                    treeSelcetDataProtectOfEnterprise2.setValue(String.valueOf(postDangerOfEnterprise.getId()));
                    treeSelcetDataProtectOfEnterprise2.setKey(String.valueOf(postDangerOfEnterprise.getId()));
                    chilren2.add(treeSelcetDataProtectOfEnterprise2);
                }
                treeSelcetDataProtectOfEnterprise.setChildren(chilren2);
            }
        }
        return treeSelcetDatalist;
    }
}
