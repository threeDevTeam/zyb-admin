package com.hthyaq.zybadmin.controller;


import com.alibaba.excel.metadata.BaseRowModel;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.hthyaq.zybadmin.common.constants.GlobalConstants;
import com.hthyaq.zybadmin.common.excle.MyExcelUtil;
import com.hthyaq.zybadmin.model.entity.*;
import com.hthyaq.zybadmin.model.excelModel.EnterpriseModel;
import com.hthyaq.zybadmin.model.excelModel.MonitorOfEnterpriseModel;
import com.hthyaq.zybadmin.model.excelModel.TreeSelcetDataMonitorOfEnterprise;
import com.hthyaq.zybadmin.model.excelModel.TreeSelcetDataTouchPersonOfEnterprise;
import com.hthyaq.zybadmin.model.vo.MonitorOfEnterpriseView;
import com.hthyaq.zybadmin.model.vo.TouchPersonOfEnterpriseView;
import com.hthyaq.zybadmin.service.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
    @Autowired
    SysUserService sysUserService;
    @Autowired
    SysRoleUserService sysRoleUserService;

    @PostMapping("/add")
    public boolean add(@RequestBody MonitorOfEnterpriseView monitorOfEnterpriseView, HttpSession httpSession) {
        boolean flag = false;
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
        monitorOfEnterpriseView.setTreeSelect(String.valueOf(workplaceOfEnterprise.getName()+"--"+postOfEnterprise.getPostSmallName()+"--"+postDangerOfEnterprise.getDangerSmallName()));

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
        QueryWrapper<SysRoleUser> qw = new QueryWrapper<>();
        qw.eq("userId", sysUser.getId());
        SysRoleUser sysRoleUser = sysRoleUserService.getOne(qw);
        if (sysRoleUser.getRoleId() == 1) {
            QueryWrapper<MonitorOfEnterprise> queryWrapper = new QueryWrapper<>();
            if (!Strings.isNullOrEmpty(monitorTime)) {
                queryWrapper.like("monitorTime", monitorTime);
            }
            queryWrapper.orderByDesc("id");
            IPage<MonitorOfEnterprise> page = monitorOfEnterpriseService.page(new Page<>(currentPage, pageSize), queryWrapper);

            return page;
        } else {
            QueryWrapper<Enterprise> queryWrapper1 = new QueryWrapper<>();
            queryWrapper1.eq("name", sysUser.getCompanyName());
            List<Enterprise> list = enterpriseService.list(queryWrapper1);
            for (Enterprise enterprise : list) {
                list1.clear();
                Long id = enterprise.getId();
                list1.add(id);
            }
            QueryWrapper<MonitorOfEnterprise> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("enterpriseId", list1.get(0));
            if (!Strings.isNullOrEmpty(monitorTime)) {
                queryWrapper.like("monitorTime", monitorTime);
            }
            queryWrapper.orderByDesc("id");
            IPage<MonitorOfEnterprise> page = monitorOfEnterpriseService.page(new Page<>(currentPage, pageSize), queryWrapper);

            return page;
        }
    }
    @GetMapping("/TreeSelcetData")
    public List<TreeSelcetDataMonitorOfEnterprise> TreeSelcetData(HttpSession httpSession) {

        SysUser sysUser = (SysUser) httpSession.getAttribute(GlobalConstants.LOGIN_NAME);
        QueryWrapper<SysUser> qws=new QueryWrapper<>();
        qws.eq("loginName",sysUser.getLoginName());
        SysUser one = sysUserService.getOne(qws);

        QueryWrapper<Enterprise> qwe=new QueryWrapper<>();
        qwe.eq("name",one.getCompanyName());
        Enterprise one1 = enterpriseService.getOne(qwe);


        List<TreeSelcetDataMonitorOfEnterprise> treeSelcetDatalist = new ArrayList();
        QueryWrapper<WorkplaceOfEnterprise> qww=new QueryWrapper<>();
        qww.eq("enterpriseId",one1.getId());
        List<WorkplaceOfEnterprise> list = workplaceOfEnterpriseService.list(qww);
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
                treeSelcetDataMonitorOfEnterprise.setTitle(postOfEnterprise.getPostSmallName());
                treeSelcetDataMonitorOfEnterprise.setValue(String.valueOf(postOfEnterprise.getId()));
                treeSelcetDataMonitorOfEnterprise.setKey(String.valueOf(postOfEnterprise.getId()));
                chilren.add(treeSelcetDataMonitorOfEnterprise);
                List<TreeSelcetDataMonitorOfEnterprise> chilren2 = Lists.newArrayList();
                QueryWrapper<PostDangerOfEnterprise> queryWrapper2 = new QueryWrapper<>();
                queryWrapper2.eq("postId", postOfEnterprise.getId());
                List<PostDangerOfEnterprise> list2 = postDangerOfEnterpriseService.list(queryWrapper2);

                for (PostDangerOfEnterprise postDangerOfEnterprise : list2) {
                    TreeSelcetDataMonitorOfEnterprise treeSelcetDataMonitorOfEnterprise2 = new TreeSelcetDataMonitorOfEnterprise();
                    treeSelcetDataMonitorOfEnterprise2.setTitle(postDangerOfEnterprise.getDangerSmallName());
                    treeSelcetDataMonitorOfEnterprise2.setValue(String.valueOf(postDangerOfEnterprise.getId()));
                    treeSelcetDataMonitorOfEnterprise2.setKey(String.valueOf(postDangerOfEnterprise.getId()));
                    chilren2.add(treeSelcetDataMonitorOfEnterprise2);
                }
                treeSelcetDataMonitorOfEnterprise.setChildren(chilren2);
            }
        }
        return treeSelcetDatalist;
    }
    @PostMapping("/exceladd")
    public boolean list(String from, MultipartFile[] files, HttpSession httpSession) {
        boolean flag = true;
        //excel->model
        Class<? extends BaseRowModel>[] modelClassArr = new Class[1];
        modelClassArr[0]= MonitorOfEnterpriseModel.class;
        Map<String, List<Object>> modelMap = MyExcelUtil.readMoreSheetExcel(files,modelClassArr);
        //model->entity
        for (Map.Entry<String, List<Object>> entry : modelMap.entrySet()) {
            String type = entry.getKey();
            List<Object> modelList = entry.getValue();
            List<MonitorOfEnterprise> dataList = getDataList(modelList, type,httpSession);
            flag = monitorOfEnterpriseService.saveBatch(dataList);
        }
        return flag;
    }
    private List<MonitorOfEnterprise> getDataList(List<Object> modelList, String type, HttpSession httpSession) {
        List<MonitorOfEnterprise> dataList = org.apache.commons.compress.utils.Lists.newArrayList();
        for (Object object : modelList) {
            MonitorOfEnterpriseModel monitorOfEnterpriseModel = (MonitorOfEnterpriseModel) object;
            //业务处理
            MonitorOfEnterprise monitorOfEnterprise = new MonitorOfEnterprise();
            //enterpriseId
            SysUser sysUser = (SysUser) httpSession.getAttribute(GlobalConstants.LOGIN_NAME);
            QueryWrapper<Enterprise> queryWrapper1=new QueryWrapper();
            queryWrapper1.eq("name",sysUser.getCompanyName());
            List<Enterprise> list1 = enterpriseService.list(queryWrapper1);
            for (Enterprise enterprise : list1) {
                monitorOfEnterprise.setEnterpriseId(enterprise.getId());
            }
            //workplaceId
            QueryWrapper<WorkplaceOfEnterprise> qww=new QueryWrapper();
            qww.eq("name",monitorOfEnterpriseModel.getWorkplaceId()).eq("enterpriseId",monitorOfEnterprise.getEnterpriseId());
            WorkplaceOfEnterprise one1 = workplaceOfEnterpriseService.getOne(qww);
            monitorOfEnterprise.setWorkplaceId(one1.getId());
            //postId
            QueryWrapper<PostOfEnterprise> qwp=new QueryWrapper();
            qwp.eq("postSmallName",monitorOfEnterpriseModel.getPostId()).eq("enterpriseId",monitorOfEnterprise.getEnterpriseId()).eq("workplaceId", monitorOfEnterprise.getWorkplaceId());
            PostOfEnterprise one = postOfEnterpriseService.getOne(qwp);
            monitorOfEnterprise.setPostId(one.getId());
            //postDangerId
            QueryWrapper<PostDangerOfEnterprise> qwD=new QueryWrapper();
            qwD.eq("dangerSmallName",monitorOfEnterpriseModel.getPostDangerId()).eq("enterpriseId",monitorOfEnterprise.getEnterpriseId()).eq("workplaceId", monitorOfEnterprise.getWorkplaceId()).eq("postId", monitorOfEnterprise.getPostId());
            PostDangerOfEnterprise one2 = postDangerOfEnterpriseService.getOne(qwD);
            monitorOfEnterprise.setPostDangerId(one2.getId());

            BeanUtils.copyProperties(monitorOfEnterpriseModel, monitorOfEnterprise);
            dataList.add(monitorOfEnterprise);
        }
        return dataList;
    }
}
