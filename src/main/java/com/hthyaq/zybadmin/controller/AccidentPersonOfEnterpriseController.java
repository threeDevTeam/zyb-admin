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
import com.hthyaq.zybadmin.common.utils.AntdDateUtil;
import com.hthyaq.zybadmin.model.entity.*;
import com.hthyaq.zybadmin.model.excelModel.AccidentPersonOfEnterpriseModel;
import com.hthyaq.zybadmin.model.excelModel.EnterpriseModel;
import com.hthyaq.zybadmin.model.excelModel.TreeSelcetDataAccidentPersonOfEnterprise;
import com.hthyaq.zybadmin.model.vo.AccidentPersonOfEnterpriseView;
import com.hthyaq.zybadmin.model.vo.PostDangerOfEnterpriseView;
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
    @Autowired
    SysUserService sysUserService;
    @Autowired
    SysRoleUserService sysRoleUserService;

    @PostMapping("/add")
    public boolean add(@RequestBody AccidentPersonOfEnterpriseView accidentPersonOfEnterpriseView ,HttpSession httpSession) {
        boolean flag = false;
        System.out.println(accidentPersonOfEnterpriseView);
        AccidentPersonOfEnterprise accidentPersonOfEnterprise = new AccidentPersonOfEnterprise();
        //other
        BeanUtils.copyProperties(accidentPersonOfEnterpriseView, accidentPersonOfEnterprise);
        accidentPersonOfEnterprise.setDieDate(AntdDateUtil.getInteger(accidentPersonOfEnterpriseView.getDieDateStr()));

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

        accidentPersonOfEnterpriseView.setDieDateStr( AntdDateUtil.getString(accidentPersonOfEnterprise.getDieDate()));

        PostOfEnterprise postOfEnterprise = postOfEnterpriseService.getById(accidentPersonOfEnterprise.getPostId());
        WorkplaceOfEnterprise workplaceOfEnterprise= workplaceOfEnterpriseService.getById(accidentPersonOfEnterprise.getWorkplaceId());
        accidentPersonOfEnterpriseView.setTreeSelect(String.valueOf(workplaceOfEnterprise.getName()+"--"+postOfEnterprise.getPostSmallName()));
        return accidentPersonOfEnterpriseView;
    }

    @PostMapping("/edit")
    public boolean edit(@RequestBody AccidentPersonOfEnterpriseView accidentPersonOfEnterpriseView) {
        AccidentPersonOfEnterprise accidentPersonOfEnterprise=new AccidentPersonOfEnterprise();
        BeanUtils.copyProperties(accidentPersonOfEnterpriseView, accidentPersonOfEnterprise);
        accidentPersonOfEnterprise.setDieDate(AntdDateUtil.getInteger(accidentPersonOfEnterpriseView.getDieDateStr()));

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
        QueryWrapper<SysRoleUser> qw = new QueryWrapper<>();
        qw.eq("userId", sysUser.getId());
        SysRoleUser sysRoleUser = sysRoleUserService.getOne(qw);
        if (sysRoleUser.getRoleId() == 1) {
            QueryWrapper<AccidentPersonOfEnterprise> queryWrapper = new QueryWrapper<>();
            if (!Strings.isNullOrEmpty(org)) {
                queryWrapper.like("org", org);
            }
            queryWrapper.orderByDesc("id");
            IPage<AccidentPersonOfEnterprise> page = accidentPersonOfEnterpriseService.page(new Page<>(currentPage, pageSize), queryWrapper);

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
            QueryWrapper<AccidentPersonOfEnterprise> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("enterpriseId", list1.get(0));
            if (!Strings.isNullOrEmpty(org)) {
                queryWrapper.like("org", org);
            }
            queryWrapper.orderByDesc("id");
            IPage<AccidentPersonOfEnterprise> page = accidentPersonOfEnterpriseService.page(new Page<>(currentPage, pageSize), queryWrapper);

            return page;
        }
    }
    @GetMapping("/TreeSelcetData")
    public List<TreeSelcetDataAccidentPersonOfEnterprise> TreeSelcetData(HttpSession httpSession) {
        SysUser sysUser = (SysUser) httpSession.getAttribute(GlobalConstants.LOGIN_NAME);
        QueryWrapper<SysUser> qws=new QueryWrapper<>();
        qws.eq("loginName",sysUser.getLoginName());
        SysUser one = sysUserService.getOne(qws);

        QueryWrapper<Enterprise> qwe=new QueryWrapper<>();
        qwe.eq("name",one.getCompanyName());
        Enterprise one1 = enterpriseService.getOne(qwe);

        List<TreeSelcetDataAccidentPersonOfEnterprise> treeSelcetDatalist = new ArrayList();
        QueryWrapper<WorkplaceOfEnterprise> qww=new QueryWrapper<>();
        qww.eq("enterpriseId",one1.getId());
        List<WorkplaceOfEnterprise> list = workplaceOfEnterpriseService.list(qww);
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
                treeSelcetDataAccidentPersonOfEnterprise.setTitle(postOfEnterprise.getPostSmallName());
                treeSelcetDataAccidentPersonOfEnterprise.setValue(String.valueOf(postOfEnterprise.getId()));
                treeSelcetDataAccidentPersonOfEnterprise.setKey(String.valueOf(postOfEnterprise.getId()));
                chilren.add(treeSelcetDataAccidentPersonOfEnterprise);
            }

        }
        return treeSelcetDatalist;
    }
    @PostMapping("/exceladd")
    public boolean list(String from, MultipartFile[] files,HttpSession httpSession) {
        boolean flag = true;
        //excel->model
        Class<? extends BaseRowModel>[] modelClassArr = new Class[1];
        modelClassArr[0]= AccidentPersonOfEnterpriseModel.class;
        Map<String, List<Object>> modelMap = MyExcelUtil.readMoreSheetExcel(files,modelClassArr);
        //model->entity
        for (Map.Entry<String, List<Object>> entry : modelMap.entrySet()) {
            String type = entry.getKey();
            List<Object> modelList = entry.getValue();
            List<AccidentPersonOfEnterprise> dataList = getDataList(modelList, type,httpSession);
            flag = accidentPersonOfEnterpriseService.saveBatch(dataList);
        }
        return flag;
    }
    private List<AccidentPersonOfEnterprise> getDataList(List<Object> modelList, String type,HttpSession httpSession) {
        List<AccidentPersonOfEnterprise> dataList = org.apache.commons.compress.utils.Lists.newArrayList();
        for (Object object : modelList) {
            AccidentPersonOfEnterpriseModel accidentPersonOfEnterpriseModel = (AccidentPersonOfEnterpriseModel) object;
            //业务处理
            AccidentPersonOfEnterprise accidentPersonOfEnterprise = new AccidentPersonOfEnterprise();
            //enterpriseId
            SysUser sysUser = (SysUser) httpSession.getAttribute(GlobalConstants.LOGIN_NAME);
            QueryWrapper<Enterprise> queryWrapper1=new QueryWrapper();
            queryWrapper1.eq("name",sysUser.getCompanyName());
            List<Enterprise> list1 = enterpriseService.list(queryWrapper1);
            for (Enterprise enterprise : list1) {
                accidentPersonOfEnterprise.setEnterpriseId(enterprise.getId());
            }
            //关联-工作场所
            QueryWrapper<WorkplaceOfEnterprise> qww=new QueryWrapper();
            qww.eq("name",accidentPersonOfEnterpriseModel.getWorkplaceId()).eq("enterpriseId",accidentPersonOfEnterprise.getEnterpriseId());
            WorkplaceOfEnterprise one1 = workplaceOfEnterpriseService.getOne(qww);
            accidentPersonOfEnterprise.setWorkplaceId(one1.getId());

            //岗位
            QueryWrapper<PostOfEnterprise> qwp=new QueryWrapper();
            qwp.eq("postSmallName",accidentPersonOfEnterpriseModel.getPostId()).eq("enterpriseId",accidentPersonOfEnterprise.getEnterpriseId()).eq("workplaceId", accidentPersonOfEnterprise.getWorkplaceId());
            PostOfEnterprise one = postOfEnterpriseService.getOne(qwp);
            accidentPersonOfEnterprise.setPostId(one.getId());
            BeanUtils.copyProperties(accidentPersonOfEnterpriseModel, accidentPersonOfEnterprise);
            dataList.add(accidentPersonOfEnterprise);
        }
        return dataList;
    }
}
