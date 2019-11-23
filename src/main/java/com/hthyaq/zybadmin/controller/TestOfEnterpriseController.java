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
import com.hthyaq.zybadmin.model.excelModel.TestOfEnterpriseModel;
import com.hthyaq.zybadmin.model.excelModel.TreeSelcetDataPersonProtectOfEnterprise;
import com.hthyaq.zybadmin.model.excelModel.TreeSelcetDataTestOfEnterprise;
import com.hthyaq.zybadmin.model.vo.PersonProtectOfEnterpriseView;
import com.hthyaq.zybadmin.model.vo.TestOfEnterpriseView;
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
    @Autowired
    SysUserService sysUserService;
    @Autowired
    SysRoleUserService sysRoleUserService;

    @PostMapping("/add")
    public boolean add(@RequestBody TestOfEnterpriseView testOfEnterpriseView, HttpSession httpSession) {
        boolean flag = false;
        System.out.println(testOfEnterpriseView);
        TestOfEnterprise testOfEnterprise = new TestOfEnterprise();

        //other
        BeanUtils.copyProperties(testOfEnterpriseView, testOfEnterprise);

        //enterpriseId
        SysUser sysUser = (SysUser) httpSession.getAttribute(GlobalConstants.LOGIN_NAME);
        QueryWrapper<Enterprise> queryWrapper1=new QueryWrapper();
        queryWrapper1.eq("name",sysUser.getCompanyName());
        List<Enterprise> list1 = enterpriseService.list(queryWrapper1);
        for (Enterprise enterprise : list1) {
            testOfEnterprise.setEnterpriseId(enterprise.getId());
        }

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
        testOfEnterpriseView.setTreeSelect(String.valueOf(workplaceOfEnterprise.getName()+"--"+postOfEnterprise.getPostSmallName()+"--"+postDangerOfEnterprise.getDangerSmallName()));

        System.out.println(testOfEnterpriseView);
        //将demoCourse的数据设置到demoData
        return testOfEnterpriseView;
    }

    @PostMapping("/edit")
    public boolean edit(@RequestBody TestOfEnterprise testOfEnterprise) {
        return testOfEnterpriseService.updateById(testOfEnterprise);
    }

    @GetMapping("/list")
    public IPage<TestOfEnterprise> list(String json, HttpSession httpSession) {
        SysUser sysUser = (SysUser) httpSession.getAttribute(GlobalConstants.LOGIN_NAME);
        List list1 = new ArrayList();
        //字符串解析成java对象
        JSONObject jsonObject = JSON.parseObject(json);
        //从对象中获取值
        Integer currentPage = jsonObject.getInteger("currentPage");
        Integer pageSize = jsonObject.getInteger("pageSize");
        String name = jsonObject.getString("name");
        QueryWrapper<SysRoleUser> qw = new QueryWrapper<>();
        qw.eq("userId", sysUser.getId());
        SysRoleUser sysRoleUser = sysRoleUserService.getOne(qw);
        if (sysRoleUser.getRoleId() == 1) {

            QueryWrapper<TestOfEnterprise> queryWrapper = new QueryWrapper<>();
            if (!Strings.isNullOrEmpty(name)) {
                queryWrapper.like("name", name);
            }
            queryWrapper.orderByDesc("id");
            IPage<TestOfEnterprise> page = testOfEnterpriseService.page(new Page<>(currentPage, pageSize), queryWrapper);

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
            QueryWrapper<TestOfEnterprise> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("enterpriseId", list1.get(0));
            if (!Strings.isNullOrEmpty(name)) {
                queryWrapper.like("name", name);
            }
            queryWrapper.orderByDesc("id");
            IPage<TestOfEnterprise> page = testOfEnterpriseService.page(new Page<>(currentPage, pageSize), queryWrapper);

            return page;
        }
    }
    @GetMapping("/TreeSelcetData")
    public List<TreeSelcetDataTestOfEnterprise> TreeSelcetData(HttpSession httpSession) {

        SysUser sysUser = (SysUser) httpSession.getAttribute(GlobalConstants.LOGIN_NAME);
        QueryWrapper<SysUser> qws=new QueryWrapper<>();
        qws.eq("loginName",sysUser.getLoginName());
        SysUser one = sysUserService.getOne(qws);

        QueryWrapper<Enterprise> qwe=new QueryWrapper<>();
        qwe.eq("name",one.getCompanyName());
        Enterprise one1 = enterpriseService.getOne(qwe);


        List<TreeSelcetDataTestOfEnterprise> treeSelcetDatalist = new ArrayList();
        QueryWrapper<WorkplaceOfEnterprise> qww=new QueryWrapper<>();
        qww.eq("enterpriseId",one1.getId());
        List<WorkplaceOfEnterprise> list = workplaceOfEnterpriseService.list(qww);
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
                treeSelcetDataTestOfEnterprise.setTitle(postOfEnterprise.getPostSmallName());
                treeSelcetDataTestOfEnterprise.setValue(String.valueOf(postOfEnterprise.getId()));
                treeSelcetDataTestOfEnterprise.setKey(String.valueOf(postOfEnterprise.getId()));
                chilren.add(treeSelcetDataTestOfEnterprise);
                List<TreeSelcetDataTestOfEnterprise> chilren2 = Lists.newArrayList();
                QueryWrapper<PostDangerOfEnterprise> queryWrapper2 = new QueryWrapper<>();
                queryWrapper2.eq("postId", postOfEnterprise.getId());
                List<PostDangerOfEnterprise> list2 = postDangerOfEnterpriseService.list(queryWrapper2);

                for (PostDangerOfEnterprise postDangerOfEnterprise : list2) {
                    TreeSelcetDataTestOfEnterprise treeSelcetDataTestOfEnterprise2 = new TreeSelcetDataTestOfEnterprise();
                    treeSelcetDataTestOfEnterprise2.setTitle(postDangerOfEnterprise.getDangerSmallName());
                    treeSelcetDataTestOfEnterprise2.setValue(String.valueOf(postDangerOfEnterprise.getId()));
                    treeSelcetDataTestOfEnterprise2.setKey(String.valueOf(postDangerOfEnterprise.getId()));
                    chilren2.add(treeSelcetDataTestOfEnterprise2);
                }
                treeSelcetDataTestOfEnterprise.setChildren(chilren2);
            }
        }
        return treeSelcetDatalist;
    }
    @PostMapping("/exceladd")
    public boolean list(String from, MultipartFile[] files, HttpSession httpSession) {
        boolean flag = true;
        //excel->model
        Class<? extends BaseRowModel>[] modelClassArr = new Class[1];
        modelClassArr[0]= TestOfEnterpriseModel.class;
        Map<String, List<Object>> modelMap = MyExcelUtil.readMoreSheetExcel(files,modelClassArr);
        //model->entity
        for (Map.Entry<String, List<Object>> entry : modelMap.entrySet()) {
            String type = entry.getKey();
            List<Object> modelList = entry.getValue();
            List<TestOfEnterprise> dataList = getDataList(modelList, type,httpSession);
            flag = testOfEnterpriseService.saveBatch(dataList);
        }
        return flag;
    }
    private List<TestOfEnterprise> getDataList(List<Object> modelList, String type, HttpSession httpSession) {
        List<TestOfEnterprise> dataList = org.apache.commons.compress.utils.Lists.newArrayList();
        for (Object object : modelList) {
            TestOfEnterpriseModel testOfEnterpriseModel = (TestOfEnterpriseModel) object;
            //业务处理

            TestOfEnterprise testOfEnterprise = new TestOfEnterprise();
            //enterpriseId
            SysUser sysUser = (SysUser) httpSession.getAttribute(GlobalConstants.LOGIN_NAME);
            QueryWrapper<Enterprise> queryWrapper1=new QueryWrapper();
            queryWrapper1.eq("name",sysUser.getCompanyName());
            List<Enterprise> list1 = enterpriseService.list(queryWrapper1);
            for (Enterprise enterprise : list1) {
                testOfEnterprise.setEnterpriseId(enterprise.getId());
            }
            //workplaceId
            QueryWrapper<WorkplaceOfEnterprise> qww=new QueryWrapper();
            qww.eq("name",testOfEnterpriseModel.getWorkplaceId()).eq("enterpriseId",testOfEnterprise.getEnterpriseId());
            WorkplaceOfEnterprise one1 = workplaceOfEnterpriseService.getOne(qww);
            testOfEnterprise.setWorkplaceId(one1.getId());
            //postId
            QueryWrapper<PostOfEnterprise> qwp=new QueryWrapper();
            qwp.eq("postSmallName",testOfEnterpriseModel.getPostId()).eq("enterpriseId",testOfEnterprise.getEnterpriseId()).eq("workplaceId", testOfEnterprise.getWorkplaceId());
            PostOfEnterprise one = postOfEnterpriseService.getOne(qwp);
            testOfEnterprise.setPostId(one.getId());
            //postDangerId
            QueryWrapper<PostDangerOfEnterprise> qwD=new QueryWrapper();
            qwD.eq("dangerSmallName",testOfEnterpriseModel.getPostDangerId()).eq("enterpriseId",testOfEnterprise.getEnterpriseId()).eq("workplaceId", testOfEnterprise.getWorkplaceId()).eq("postId", testOfEnterprise.getPostId());
            PostDangerOfEnterprise one2 = postDangerOfEnterpriseService.getOne(qwD);
            testOfEnterprise.setPostDangerId(one2.getId());

            BeanUtils.copyProperties(testOfEnterpriseModel, testOfEnterprise);
            if(testOfEnterpriseModel.getType().equals("离岗时") || testOfEnterpriseModel.getType().equals("在岗期间") ||testOfEnterpriseModel.getType().equals("上岗前")){
                testOfEnterprise.setType(testOfEnterpriseModel.getType());
            }else{
                return null;
            }
            if(testOfEnterpriseModel.getResult().equals("正常") || testOfEnterpriseModel.getResult().equals("异常") ||testOfEnterpriseModel.getResult().equals("职业禁忌证")||testOfEnterpriseModel.getResult().equals("疑似职业病")){
                testOfEnterprise.setResult(testOfEnterpriseModel.getResult());
            }else{
                return null;
            }
            dataList.add(testOfEnterprise);
        }
        return dataList;
    }
}
