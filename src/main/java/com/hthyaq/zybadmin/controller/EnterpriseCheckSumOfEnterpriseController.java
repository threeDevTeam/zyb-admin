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
import com.hthyaq.zybadmin.model.excelModel.EnterpriseCheckSumOfEnterpriseModel;
import com.hthyaq.zybadmin.model.excelModel.EnterpriseModel;
import com.hthyaq.zybadmin.model.excelModel.TreeSelcetDataEnterpriseCheckSumOfEnterprise;
import com.hthyaq.zybadmin.model.excelModel.TreeSelcetDataTouchPersonOfEnterprise;
import com.hthyaq.zybadmin.model.vo.EnterpriseCheckSumOfEnterpriseView;
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
 * 职业病危害因素检测信息 前端控制器
 * </p>
 *
 * @author zhangqiang
 * @since 2019-09-02
 */
@RestController
@RequestMapping("/enterpriseCheckSumOfEnterprise")
public class EnterpriseCheckSumOfEnterpriseController {
    @Autowired
    EnterpriseCheckSumOfEnterpriseService enterpriseCheckSumOfEnterpriseService;
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
    public boolean add(@RequestBody EnterpriseCheckSumOfEnterpriseView enterpriseCheckSumOfEnterpriseView, HttpSession httpSession) {
        boolean flag = false;
        System.out.println(enterpriseCheckSumOfEnterpriseView);
        EnterpriseCheckSumOfEnterprise enterpriseCheckSumOfEnterprise = new EnterpriseCheckSumOfEnterprise();

        //other
        BeanUtils.copyProperties(enterpriseCheckSumOfEnterpriseView, enterpriseCheckSumOfEnterprise);

        //enterpriseId
        SysUser sysUser = (SysUser) httpSession.getAttribute(GlobalConstants.LOGIN_NAME);
        QueryWrapper<Enterprise> queryWrapper1=new QueryWrapper();
        queryWrapper1.eq("name",sysUser.getCompanyName());
        List<Enterprise> list1 = enterpriseService.list(queryWrapper1);
        for (Enterprise enterprise : list1) {
            enterpriseCheckSumOfEnterprise.setEnterpriseId(enterprise.getId());
        }

        //postDangerId
        enterpriseCheckSumOfEnterprise.setPostDangerId(Long.parseLong(enterpriseCheckSumOfEnterpriseView.getTreeSelect()));

        //通过postDangerId查询出workplaceId,postId
        PostDangerOfEnterprise postDangerOfEnterprise = postDangerOfEnterpriseService.getById(enterpriseCheckSumOfEnterpriseView.getTreeSelect());
        enterpriseCheckSumOfEnterprise.setWorkplaceId(postDangerOfEnterprise.getWorkplaceId());
        enterpriseCheckSumOfEnterprise.setPostId(postDangerOfEnterprise.getPostId());

        System.out.println(postDangerOfEnterprise);
        flag = enterpriseCheckSumOfEnterpriseService.save(enterpriseCheckSumOfEnterprise);

        return flag;
    }

    @GetMapping("/delete")
    public boolean delete(String id) {
        return enterpriseCheckSumOfEnterpriseService.removeById(id);
    }

    @GetMapping("/getById")
    public EnterpriseCheckSumOfEnterprise getById(Integer id) {
        EnterpriseCheckSumOfEnterpriseView enterpriseCheckSumOfEnterpriseView=new EnterpriseCheckSumOfEnterpriseView();
        EnterpriseCheckSumOfEnterprise enterpriseCheckSumOfEnterprise =   enterpriseCheckSumOfEnterpriseService.getById(id);
        BeanUtils.copyProperties(enterpriseCheckSumOfEnterprise,enterpriseCheckSumOfEnterpriseView);
        PostDangerOfEnterprise postDangerOfEnterprise = postDangerOfEnterpriseService.getById(enterpriseCheckSumOfEnterprise.getPostDangerId());
        PostOfEnterprise postOfEnterprise = postOfEnterpriseService.getById(enterpriseCheckSumOfEnterprise.getPostId());
        WorkplaceOfEnterprise workplaceOfEnterprise= workplaceOfEnterpriseService.getById(enterpriseCheckSumOfEnterprise.getWorkplaceId());
        enterpriseCheckSumOfEnterpriseView.setTreeSelect(String.valueOf(workplaceOfEnterprise.getName()+"--"+postOfEnterprise.getPostSmallName()+"--"+postDangerOfEnterprise.getDangerSmallName()));

        System.out.println(enterpriseCheckSumOfEnterprise);
        //将demoCourse的数据设置到demoData
        return enterpriseCheckSumOfEnterpriseView;
    }

    @PostMapping("/edit")
    public boolean edit(@RequestBody EnterpriseCheckSumOfEnterprise enterpriseCheckSumOfEnterprise) {
        return enterpriseCheckSumOfEnterpriseService.updateById(enterpriseCheckSumOfEnterprise);
    }

    @GetMapping("/list")
    public IPage<EnterpriseCheckSumOfEnterprise> list(String json, HttpSession httpSession) {
        SysUser sysUser = (SysUser) httpSession.getAttribute(GlobalConstants.LOGIN_NAME);
        List list1 = new ArrayList();
        //字符串解析成java对象
        JSONObject jsonObject = JSON.parseObject(json);
        //从对象中获取值
        Integer currentPage = jsonObject.getInteger("currentPage");
        Integer pageSize = jsonObject.getInteger("pageSize");
        String upDate = jsonObject.getString("upDate");
        QueryWrapper<SysRoleUser> qw = new QueryWrapper<>();
        qw.eq("userId", sysUser.getId());
        SysRoleUser sysRoleUser = sysRoleUserService.getOne(qw);
        if (sysRoleUser.getRoleId() == 1) {
            QueryWrapper<EnterpriseCheckSumOfEnterprise> queryWrapper = new QueryWrapper<>();
            if (!Strings.isNullOrEmpty(upDate)) {
                queryWrapper.like("upDate", upDate);
            }
            queryWrapper.orderByDesc("id");
            IPage<EnterpriseCheckSumOfEnterprise> page = enterpriseCheckSumOfEnterpriseService.page(new Page<>(currentPage, pageSize), queryWrapper);

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
            QueryWrapper<EnterpriseCheckSumOfEnterprise> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("enterpriseId", list1.get(0));
            if (!Strings.isNullOrEmpty(upDate)) {
                queryWrapper.like("upDate", upDate);
            }
            queryWrapper.orderByDesc("id");
            IPage<EnterpriseCheckSumOfEnterprise> page = enterpriseCheckSumOfEnterpriseService.page(new Page<>(currentPage, pageSize), queryWrapper);

            return page;
        }
    }
    @GetMapping("/TreeSelcetData")
    public List<TreeSelcetDataEnterpriseCheckSumOfEnterprise> TreeSelcetData(HttpSession httpSession) {

        SysUser sysUser = (SysUser) httpSession.getAttribute(GlobalConstants.LOGIN_NAME);
        QueryWrapper<SysUser> qws=new QueryWrapper<>();
        qws.eq("loginName",sysUser.getLoginName());
        SysUser one = sysUserService.getOne(qws);

        QueryWrapper<Enterprise> qwe=new QueryWrapper<>();
        qwe.eq("name",one.getCompanyName());
        Enterprise one1 = enterpriseService.getOne(qwe);



        List<TreeSelcetDataEnterpriseCheckSumOfEnterprise> treeSelcetDatalist = new ArrayList();
        QueryWrapper<WorkplaceOfEnterprise> qww=new QueryWrapper<>();
        qww.eq("enterpriseId",one1.getId());
        List<WorkplaceOfEnterprise> list = workplaceOfEnterpriseService.list(qww);

        for (WorkplaceOfEnterprise workplaceOfEnterprise : list) {
            List<TreeSelcetDataEnterpriseCheckSumOfEnterprise> chilren = Lists.newArrayList();

            TreeSelcetDataEnterpriseCheckSumOfEnterprise TreeSelcetDataEnterpriseCheckSumOfEnterprise1 = new TreeSelcetDataEnterpriseCheckSumOfEnterprise();
            TreeSelcetDataEnterpriseCheckSumOfEnterprise1.setTitle(workplaceOfEnterprise.getName());
            TreeSelcetDataEnterpriseCheckSumOfEnterprise1.setValue(String.valueOf(workplaceOfEnterprise.getId()));
            TreeSelcetDataEnterpriseCheckSumOfEnterprise1.setKey(String.valueOf(workplaceOfEnterprise.getId()));
            TreeSelcetDataEnterpriseCheckSumOfEnterprise1.setChildren(chilren);
            treeSelcetDatalist.add(TreeSelcetDataEnterpriseCheckSumOfEnterprise1);


            QueryWrapper<PostOfEnterprise> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("workplaceId", workplaceOfEnterprise.getId());
            List<PostOfEnterprise> list1 = postOfEnterpriseService.list(queryWrapper);
            for (PostOfEnterprise postOfEnterprise : list1) {

                TreeSelcetDataEnterpriseCheckSumOfEnterprise treeSelcetDataEnterpriseCheckSumOfEnterprise = new TreeSelcetDataEnterpriseCheckSumOfEnterprise();
                treeSelcetDataEnterpriseCheckSumOfEnterprise.setTitle(postOfEnterprise.getPostSmallName());
                treeSelcetDataEnterpriseCheckSumOfEnterprise.setValue(String.valueOf(postOfEnterprise.getId()));
                treeSelcetDataEnterpriseCheckSumOfEnterprise.setKey(String.valueOf(postOfEnterprise.getId()));
                chilren.add(treeSelcetDataEnterpriseCheckSumOfEnterprise);
                List<TreeSelcetDataEnterpriseCheckSumOfEnterprise> chilren2 = Lists.newArrayList();
                QueryWrapper<PostDangerOfEnterprise> queryWrapper2 = new QueryWrapper<>();
                queryWrapper2.eq("postId", postOfEnterprise.getId());
                List<PostDangerOfEnterprise> list2 = postDangerOfEnterpriseService.list(queryWrapper2);

                for (PostDangerOfEnterprise postDangerOfEnterprise : list2) {
                    TreeSelcetDataEnterpriseCheckSumOfEnterprise treeSelcetDataEnterpriseCheckSumOfEnterprise2 = new TreeSelcetDataEnterpriseCheckSumOfEnterprise();
                    treeSelcetDataEnterpriseCheckSumOfEnterprise2.setTitle(postDangerOfEnterprise.getDangerSmallName());
                    treeSelcetDataEnterpriseCheckSumOfEnterprise2.setValue(String.valueOf(postDangerOfEnterprise.getId()));
                    treeSelcetDataEnterpriseCheckSumOfEnterprise2.setKey(String.valueOf(postDangerOfEnterprise.getId()));
                    chilren2.add(treeSelcetDataEnterpriseCheckSumOfEnterprise2);
                }
                treeSelcetDataEnterpriseCheckSumOfEnterprise.setChildren(chilren2);
            }
        }
        return treeSelcetDatalist;
    }
    @PostMapping("/exceladd")
    public boolean list(String from, MultipartFile[] files, HttpSession httpSession) {
        boolean flag = true;
        //excel->model
        Class<? extends BaseRowModel>[] modelClassArr = new Class[1];
        modelClassArr[0]= EnterpriseCheckSumOfEnterpriseModel.class;
        Map<String, List<Object>> modelMap = MyExcelUtil.readMoreSheetExcel(files,modelClassArr);
        //model->entity
        for (Map.Entry<String, List<Object>> entry : modelMap.entrySet()) {
            String type = entry.getKey();
            List<Object> modelList = entry.getValue();
            List<EnterpriseCheckSumOfEnterprise> dataList = getDataList(modelList, type,httpSession);
            flag = enterpriseCheckSumOfEnterpriseService.saveBatch(dataList);
        }
        return flag;
    }
    private List<EnterpriseCheckSumOfEnterprise> getDataList(List<Object> modelList, String type, HttpSession httpSession) {
        List<EnterpriseCheckSumOfEnterprise> dataList = org.apache.commons.compress.utils.Lists.newArrayList();
        for (Object object : modelList) {
            EnterpriseCheckSumOfEnterpriseModel enterpriseCheckSumOfEnterpriseModel = (EnterpriseCheckSumOfEnterpriseModel) object;
            //业务处理
            EnterpriseCheckSumOfEnterprise enterpriseCheckSumOfEnterprise = new EnterpriseCheckSumOfEnterprise();
            //enterpriseId
            SysUser sysUser = (SysUser) httpSession.getAttribute(GlobalConstants.LOGIN_NAME);
            QueryWrapper<Enterprise> queryWrapper1=new QueryWrapper();
            queryWrapper1.eq("name",sysUser.getCompanyName());
            List<Enterprise> list1 = enterpriseService.list(queryWrapper1);
            for (Enterprise enterprise : list1) {
                enterpriseCheckSumOfEnterprise.setEnterpriseId(enterprise.getId());
            }
            //workplaceId
            QueryWrapper<WorkplaceOfEnterprise> qww=new QueryWrapper();
            qww.eq("name",enterpriseCheckSumOfEnterpriseModel.getWorkplaceId()).eq("enterpriseId",enterpriseCheckSumOfEnterprise.getEnterpriseId());
            WorkplaceOfEnterprise one1 = workplaceOfEnterpriseService.getOne(qww);
            enterpriseCheckSumOfEnterprise.setWorkplaceId(one1.getId());
            //postId
            QueryWrapper<PostOfEnterprise> qwp=new QueryWrapper();
            qwp.eq("postSmallName",enterpriseCheckSumOfEnterpriseModel.getPostId()).eq("enterpriseId",enterpriseCheckSumOfEnterprise.getEnterpriseId()).eq("workplaceId", enterpriseCheckSumOfEnterprise.getWorkplaceId());
            PostOfEnterprise one = postOfEnterpriseService.getOne(qwp);
            enterpriseCheckSumOfEnterprise.setPostId(one.getId());
            //postDangerId
            QueryWrapper<PostDangerOfEnterprise> qwD=new QueryWrapper();
            qwD.eq("dangerSmallName",enterpriseCheckSumOfEnterpriseModel.getPostDangerId()).eq("enterpriseId",enterpriseCheckSumOfEnterprise.getEnterpriseId()).eq("workplaceId", enterpriseCheckSumOfEnterprise.getWorkplaceId()).eq("postId", enterpriseCheckSumOfEnterprise.getPostId());
            PostDangerOfEnterprise one2 = postDangerOfEnterpriseService.getOne(qwD);
            enterpriseCheckSumOfEnterprise.setPostDangerId(one2.getId());

            BeanUtils.copyProperties(enterpriseCheckSumOfEnterpriseModel, enterpriseCheckSumOfEnterprise);
            dataList.add(enterpriseCheckSumOfEnterprise);
        }
        return dataList;
    }
}
