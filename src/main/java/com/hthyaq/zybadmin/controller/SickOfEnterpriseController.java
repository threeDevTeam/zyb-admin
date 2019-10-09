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
import com.hthyaq.zybadmin.model.excelModel.TreeSelcetDataSickOfEnterprise;
import com.hthyaq.zybadmin.model.excelModel.TreeSelcetDataTestOfEnterprise;
import com.hthyaq.zybadmin.model.vo.SickOfEnterpriseView;
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
 * 职业病病人信息 前端控制器
 * </p>
 *
 * @author zhangqiang
 * @since 2019-08-30
 */
@RestController
@RequestMapping("/sickOfEnterprise")
public class SickOfEnterpriseController {
    @Autowired
    SickOfEnterpriseService sickOfEnterpriseService;
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
    @Autowired
    ZybnameService zybnameService;
    @PostMapping("/add")
    public boolean add(@RequestBody SickOfEnterpriseView sickOfEnterpriseView, HttpSession httpSession) {
        boolean flag = false;
        System.out.println(sickOfEnterpriseView);
        SickOfEnterprise sickOfEnterprise = new SickOfEnterprise();

        //other
        BeanUtils.copyProperties(sickOfEnterpriseView, sickOfEnterprise);
        //职业病名称
        QueryWrapper<Zybname> qw4 = new QueryWrapper<>();
        qw4.eq("id", sickOfEnterpriseView.getCascaded1().get(0));
        List<Zybname> list5 = zybnameService.list(qw4);
        for (Zybname zybname : list5) {
            sickOfEnterprise.setSickBigName(zybname.getName());
        }
        if (sickOfEnterpriseView.getCascaded1().size() == 2) {
            QueryWrapper<Zybname> qw1 = new QueryWrapper<>();
            qw1.eq("id", sickOfEnterpriseView.getCascaded1().get(1));
            List<Zybname> list6 = zybnameService.list(qw1);
            for (Zybname zybname : list6) {
                sickOfEnterprise.setSickSmallName(zybname.getName());
            }
        } else {
            sickOfEnterprise.setSickSmallName("无");
        }

        //enterpriseId
        SysUser sysUser = (SysUser) httpSession.getAttribute(GlobalConstants.LOGIN_NAME);
        QueryWrapper<Enterprise> queryWrapper1=new QueryWrapper();
        queryWrapper1.eq("name",sysUser.getCompanyName());
        List<Enterprise> list1 = enterpriseService.list(queryWrapper1);
        for (Enterprise enterprise : list1) {
            sickOfEnterprise.setEnterpriseId(enterprise.getId());
        }

        //postDangerId
        sickOfEnterprise.setPostDangerId(Long.parseLong(sickOfEnterpriseView.getTreeSelect()));

        //通过postDangerId查询出workplaceId,postId
        PostDangerOfEnterprise postDangerOfEnterprise = postDangerOfEnterpriseService.getById(sickOfEnterpriseView.getTreeSelect());
        sickOfEnterprise.setWorkplaceId(postDangerOfEnterprise.getWorkplaceId());
        sickOfEnterprise.setPostId(postDangerOfEnterprise.getPostId());

        System.out.println(postDangerOfEnterprise);
        flag = sickOfEnterpriseService.save(sickOfEnterprise);
        return flag;
    }

    @GetMapping("/delete")
    public boolean delete(String id) {
        return sickOfEnterpriseService.removeById(id);
    }

    @GetMapping("/getById")
    public SickOfEnterprise getById(Integer id) {
        List listc1 = new ArrayList();
        SickOfEnterpriseView sickOfEnterpriseView=new SickOfEnterpriseView();
        SickOfEnterprise sickOfEnterprise = sickOfEnterpriseService.getById(id);
        BeanUtils.copyProperties(sickOfEnterprise,sickOfEnterpriseView);
        PostDangerOfEnterprise postDangerOfEnterprise = postDangerOfEnterpriseService.getById(sickOfEnterprise.getPostDangerId());
        PostOfEnterprise postOfEnterprise = postOfEnterpriseService.getById(sickOfEnterprise.getPostId());
        WorkplaceOfEnterprise workplaceOfEnterprise= workplaceOfEnterpriseService.getById(sickOfEnterprise.getWorkplaceId());
        sickOfEnterpriseView.setTreeSelect(String.valueOf(workplaceOfEnterprise.getName()+"--"+postOfEnterprise.getPostBigName()+"--"+postDangerOfEnterprise.getDangerBigName()));

        System.out.println(sickOfEnterpriseView);
        //将demoCourse的数据设置到demoData
        //职业病名称
        QueryWrapper<Zybname> qwZ = new QueryWrapper<>();
        qwZ.eq("name", sickOfEnterpriseView.getSickBigName());
        List<Zybname> hdZ1 = zybnameService.list(qwZ);
        for (Zybname zybname : hdZ1) {
            listc1.add(zybname.getId());
        }
        QueryWrapper<Zybname> qwZ2 = new QueryWrapper<>();
        qwZ2.eq("name", sickOfEnterpriseView.getSickSmallName());
        List<Zybname> hdZ = zybnameService.list(qwZ2);
        for (Zybname zybname : hdZ) {
            listc1.add(zybname.getId());
        }
        sickOfEnterpriseView.setCascaded1((ArrayList) listc1);
        return sickOfEnterpriseView;
    }

    @PostMapping("/edit")
    public boolean edit(@RequestBody SickOfEnterpriseView sickOfEnterpriseView) {
        SickOfEnterprise sickOfEnterprise = new SickOfEnterprise();

        BeanUtils.copyProperties(sickOfEnterpriseView, sickOfEnterprise);
        //职业病名称
        QueryWrapper<Zybname> qwZ = new QueryWrapper<>();
        qwZ.eq("id", sickOfEnterpriseView.getCascaded1().get(0));
        List<Zybname> listZ = zybnameService.list(qwZ);
        for (Zybname zybname : listZ) {
            sickOfEnterprise.setSickBigName(zybname.getName());
        }
        if (sickOfEnterpriseView.getCascaded1().size() == 2) {
            QueryWrapper<Zybname> qw1 = new QueryWrapper<>();
            qw1.eq("id", sickOfEnterpriseView.getCascaded1().get(1));
            List<Zybname> list6 = zybnameService.list(qw1);
            for (Zybname zybname : list6) {
                sickOfEnterprise.setSickSmallName(zybname.getName());
            }
        } else {
            sickOfEnterprise.setSickSmallName("无");
        }
        return sickOfEnterpriseService.updateById(sickOfEnterprise);
    }

    @GetMapping("/list")
    public IPage<SickOfEnterprise> list(String json, HttpSession httpSession) {
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
        QueryWrapper<SickOfEnterprise> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("enterpriseId",list1.get(0));
        if (!Strings.isNullOrEmpty(name)) {
            queryWrapper.eq("name", name);
        }
        IPage<SickOfEnterprise> page = sickOfEnterpriseService.page(new Page<>(currentPage, pageSize), queryWrapper);

        return page;
    }
    @GetMapping("/TreeSelcetData")
    public List<TreeSelcetDataSickOfEnterprise> TreeSelcetData() {
        List<TreeSelcetDataSickOfEnterprise> treeSelcetDatalist = new ArrayList();
        List<WorkplaceOfEnterprise> list = workplaceOfEnterpriseService.list();
        for (WorkplaceOfEnterprise workplaceOfEnterprise : list) {
            List<TreeSelcetDataSickOfEnterprise> chilren = Lists.newArrayList();

            TreeSelcetDataSickOfEnterprise treeSelcetDataSickOfEnterprise1 = new TreeSelcetDataSickOfEnterprise();
            treeSelcetDataSickOfEnterprise1.setTitle(workplaceOfEnterprise.getName());
            treeSelcetDataSickOfEnterprise1.setValue(String.valueOf(workplaceOfEnterprise.getId()));
            treeSelcetDataSickOfEnterprise1.setKey(String.valueOf(workplaceOfEnterprise.getId()));
            treeSelcetDataSickOfEnterprise1.setChildren(chilren);
            treeSelcetDatalist.add(treeSelcetDataSickOfEnterprise1);


            QueryWrapper<PostOfEnterprise> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("workplaceId", workplaceOfEnterprise.getId());
            List<PostOfEnterprise> list1 = postOfEnterpriseService.list(queryWrapper);
            for (PostOfEnterprise postOfEnterprise : list1) {

                TreeSelcetDataSickOfEnterprise treeSelcetDataSickOfEnterprise = new TreeSelcetDataSickOfEnterprise();
                treeSelcetDataSickOfEnterprise.setTitle(postOfEnterprise.getPostBigName());
                treeSelcetDataSickOfEnterprise.setValue(String.valueOf(postOfEnterprise.getId()));
                treeSelcetDataSickOfEnterprise.setKey(String.valueOf(postOfEnterprise.getId()));
                chilren.add(treeSelcetDataSickOfEnterprise);
                List<TreeSelcetDataSickOfEnterprise> chilren2 = Lists.newArrayList();
                QueryWrapper<PostDangerOfEnterprise> queryWrapper2 = new QueryWrapper<>();
                queryWrapper2.eq("postId", postOfEnterprise.getId());
                List<PostDangerOfEnterprise> list2 = postDangerOfEnterpriseService.list(queryWrapper2);

                for (PostDangerOfEnterprise postDangerOfEnterprise : list2) {
                    TreeSelcetDataSickOfEnterprise treeSelcetDataSickOfEnterprise2 = new TreeSelcetDataSickOfEnterprise();
                    treeSelcetDataSickOfEnterprise2.setTitle(postDangerOfEnterprise.getDangerBigName()+"---"+postDangerOfEnterprise.getDangerSmallName());
                    treeSelcetDataSickOfEnterprise2.setValue(String.valueOf(postDangerOfEnterprise.getId()));
                    treeSelcetDataSickOfEnterprise2.setKey(String.valueOf(postDangerOfEnterprise.getId()));
                    chilren2.add(treeSelcetDataSickOfEnterprise2);
                }
                treeSelcetDataSickOfEnterprise.setChildren(chilren2);
            }
        }
        return treeSelcetDatalist;
    }
}
