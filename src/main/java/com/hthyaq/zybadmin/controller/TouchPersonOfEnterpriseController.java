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
import com.hthyaq.zybadmin.model.excelModel.EnterpriseModel;
import com.hthyaq.zybadmin.model.excelModel.TouchPersonOfEnterpriseModel;
import com.hthyaq.zybadmin.model.excelModel.TreeSelcetDataTouchPersonOfEnterprise;
import com.hthyaq.zybadmin.model.vo.PersonOfEnterpriseView;
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
 * 接害人员信息 前端控制器
 * </p>
 *
 * @author zhangqiang
 * @since 2019-09-02
 */
@RestController
@RequestMapping("/touchPersonOfEnterprise")
public class TouchPersonOfEnterpriseController {
    @Autowired
    TouchPersonOfEnterpriseService touchPersonOfEnterpriseService;
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
    public boolean add(@RequestBody TouchPersonOfEnterpriseView touchPersonOfEnterpriseView, HttpSession httpSession) {
        boolean flag = false;
        System.out.println(touchPersonOfEnterpriseView);
        TouchPersonOfEnterprise touchPersonOfEnterprise = new TouchPersonOfEnterprise();

        //other
        BeanUtils.copyProperties(touchPersonOfEnterpriseView, touchPersonOfEnterprise);

        //enterpriseId
        SysUser sysUser = (SysUser) httpSession.getAttribute(GlobalConstants.LOGIN_NAME);
        QueryWrapper<Enterprise> queryWrapper1 = new QueryWrapper();
        queryWrapper1.eq("name", sysUser.getCompanyName());
        List<Enterprise> list1 = enterpriseService.list(queryWrapper1);
        for (Enterprise enterprise : list1) {
            touchPersonOfEnterprise.setEnterpriseId(enterprise.getId());
        }

        //postDangerId
        touchPersonOfEnterprise.setPostDangerId(Long.parseLong(touchPersonOfEnterpriseView.getTreeSelect()));

        //通过postDangerId查询出workplaceId,postId
        PostDangerOfEnterprise postDangerOfEnterprise = postDangerOfEnterpriseService.getById(touchPersonOfEnterpriseView.getTreeSelect());
        touchPersonOfEnterprise.setWorkplaceId(postDangerOfEnterprise.getWorkplaceId());
        touchPersonOfEnterprise.setPostId(postDangerOfEnterprise.getPostId());

        touchPersonOfEnterprise.setBirth(AntdDateUtil.getInteger(touchPersonOfEnterpriseView.getBirthStr()));
        touchPersonOfEnterprise.setStartDate(AntdDateUtil.getInteger(touchPersonOfEnterpriseView.getStartDateStr()));
        touchPersonOfEnterprise.setLeaveDate(AntdDateUtil.getInteger(touchPersonOfEnterpriseView.getLeaveDateStr()));
        System.out.println(postDangerOfEnterprise);
        flag = touchPersonOfEnterpriseService.save(touchPersonOfEnterprise);

        return flag;
    }

    @GetMapping("/delete")
    public boolean delete(String id) {
        return touchPersonOfEnterpriseService.removeById(id);
    }

    @GetMapping("/getById")
    public TouchPersonOfEnterprise getById(Integer id) {
        TouchPersonOfEnterpriseView touchPersonOfEnterpriseView = new TouchPersonOfEnterpriseView();
        TouchPersonOfEnterprise touchPersonOfEnterprise = touchPersonOfEnterpriseService.getById(id);
        BeanUtils.copyProperties(touchPersonOfEnterprise, touchPersonOfEnterpriseView);
        PostDangerOfEnterprise postDangerOfEnterprise = postDangerOfEnterpriseService.getById(touchPersonOfEnterprise.getPostDangerId());
        PostOfEnterprise postOfEnterprise = postOfEnterpriseService.getById(touchPersonOfEnterprise.getPostId());
        WorkplaceOfEnterprise workplaceOfEnterprise = workplaceOfEnterpriseService.getById(touchPersonOfEnterprise.getWorkplaceId());
        touchPersonOfEnterpriseView.setTreeSelect(String.valueOf(workplaceOfEnterprise.getName() + "--" + postOfEnterprise.getPostSmallName() + "--" + postDangerOfEnterprise.getDangerSmallName()));
        touchPersonOfEnterpriseView.setBirthStr(AntdDateUtil.getString(touchPersonOfEnterprise.getBirth()));
        touchPersonOfEnterpriseView.setStartDateStr(AntdDateUtil.getString(touchPersonOfEnterprise.getStartDate()));
        touchPersonOfEnterpriseView.setLeaveDateStr(AntdDateUtil.getString(touchPersonOfEnterprise.getLeaveDate()));
        System.out.println(touchPersonOfEnterpriseView);
        //将demoCourse的数据设置到demoData
        return touchPersonOfEnterpriseView;
    }

    @PostMapping("/edit")
    public boolean edit(@RequestBody TouchPersonOfEnterpriseView touchPersonOfEnterpriseView) {
        TouchPersonOfEnterprise touchPersonOfEnterprise=new TouchPersonOfEnterpriseView();
        BeanUtils.copyProperties(touchPersonOfEnterpriseView, touchPersonOfEnterprise);
        touchPersonOfEnterprise.setBirth(AntdDateUtil.getInteger(touchPersonOfEnterpriseView.getBirthStr()));
        touchPersonOfEnterprise.setStartDate(AntdDateUtil.getInteger(touchPersonOfEnterpriseView.getStartDateStr()));
        touchPersonOfEnterprise.setLeaveDate(AntdDateUtil.getInteger(touchPersonOfEnterpriseView.getLeaveDateStr()));

        touchPersonOfEnterprise.setBirth(AntdDateUtil.getInteger(touchPersonOfEnterpriseView.getBirthStr()));
        touchPersonOfEnterprise.setStartDate(AntdDateUtil.getInteger(touchPersonOfEnterpriseView.getStartDateStr()));
        touchPersonOfEnterprise.setLeaveDate(AntdDateUtil.getInteger(touchPersonOfEnterpriseView.getLeaveDateStr()));

        return touchPersonOfEnterpriseService.updateById(touchPersonOfEnterprise);
    }

    @GetMapping("/list")
    public IPage<TouchPersonOfEnterprise> list(String json, HttpSession httpSession) {
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
            QueryWrapper<TouchPersonOfEnterprise> queryWrapper = new QueryWrapper<>();
            if (!Strings.isNullOrEmpty(upDate)) {
                queryWrapper.eq("upDate", upDate);
            }
            queryWrapper.orderByDesc("id");
            IPage<TouchPersonOfEnterprise> page = touchPersonOfEnterpriseService.page(new Page<>(currentPage, pageSize), queryWrapper);

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
            QueryWrapper<TouchPersonOfEnterprise> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("enterpriseId", list1.get(0));
            if (!Strings.isNullOrEmpty(upDate)) {
                queryWrapper.eq("upDate", upDate);
            }
            queryWrapper.orderByDesc("id");
            IPage<TouchPersonOfEnterprise> page = touchPersonOfEnterpriseService.page(new Page<>(currentPage, pageSize), queryWrapper);

            return page;
        }
    }

    @GetMapping("/TreeSelcetData")
    public List<TreeSelcetDataTouchPersonOfEnterprise> TreeSelcetData(HttpSession httpSession) {

        SysUser sysUser = (SysUser) httpSession.getAttribute(GlobalConstants.LOGIN_NAME);
        QueryWrapper<SysUser> qws = new QueryWrapper<>();
        qws.eq("loginName", sysUser.getLoginName());
        SysUser one = sysUserService.getOne(qws);

        QueryWrapper<Enterprise> qwe = new QueryWrapper<>();
        qwe.eq("name", one.getCompanyName());
        Enterprise one1 = enterpriseService.getOne(qwe);


        List<TreeSelcetDataTouchPersonOfEnterprise> treeSelcetDatalist = new ArrayList();
        QueryWrapper<WorkplaceOfEnterprise> qww = new QueryWrapper<>();
        qww.eq("enterpriseId", one1.getId());
        List<WorkplaceOfEnterprise> list = workplaceOfEnterpriseService.list(qww);
        for (WorkplaceOfEnterprise workplaceOfEnterprise : list) {
            List<TreeSelcetDataTouchPersonOfEnterprise> chilren = Lists.newArrayList();

            TreeSelcetDataTouchPersonOfEnterprise treeSelcetDataTouchPersonOfEnterprise1 = new TreeSelcetDataTouchPersonOfEnterprise();
            treeSelcetDataTouchPersonOfEnterprise1.setTitle(workplaceOfEnterprise.getName());
            treeSelcetDataTouchPersonOfEnterprise1.setValue(String.valueOf(workplaceOfEnterprise.getId()));
            treeSelcetDataTouchPersonOfEnterprise1.setKey(String.valueOf(workplaceOfEnterprise.getId()));
            treeSelcetDataTouchPersonOfEnterprise1.setChildren(chilren);
            treeSelcetDatalist.add(treeSelcetDataTouchPersonOfEnterprise1);


            QueryWrapper<PostOfEnterprise> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("workplaceId", workplaceOfEnterprise.getId());
            List<PostOfEnterprise> list1 = postOfEnterpriseService.list(queryWrapper);
            for (PostOfEnterprise postOfEnterprise : list1) {

                TreeSelcetDataTouchPersonOfEnterprise treeSelcetDataTouchPersonOfEnterprise = new TreeSelcetDataTouchPersonOfEnterprise();
                treeSelcetDataTouchPersonOfEnterprise.setTitle(postOfEnterprise.getPostSmallName());
                treeSelcetDataTouchPersonOfEnterprise.setValue(String.valueOf(postOfEnterprise.getId()));
                treeSelcetDataTouchPersonOfEnterprise.setKey(String.valueOf(postOfEnterprise.getId()));
                chilren.add(treeSelcetDataTouchPersonOfEnterprise);
                List<TreeSelcetDataTouchPersonOfEnterprise> chilren2 = Lists.newArrayList();
                QueryWrapper<PostDangerOfEnterprise> queryWrapper2 = new QueryWrapper<>();
                queryWrapper2.eq("postId", postOfEnterprise.getId());
                List<PostDangerOfEnterprise> list2 = postDangerOfEnterpriseService.list(queryWrapper2);

                for (PostDangerOfEnterprise postDangerOfEnterprise : list2) {
                    TreeSelcetDataTouchPersonOfEnterprise treeSelcetDataTouchPersonOfEnterprise2 = new TreeSelcetDataTouchPersonOfEnterprise();
                    treeSelcetDataTouchPersonOfEnterprise2.setTitle(postDangerOfEnterprise.getDangerSmallName());
                    treeSelcetDataTouchPersonOfEnterprise2.setValue(String.valueOf(postDangerOfEnterprise.getId()));
                    treeSelcetDataTouchPersonOfEnterprise2.setKey(String.valueOf(postDangerOfEnterprise.getId()));
                    chilren2.add(treeSelcetDataTouchPersonOfEnterprise2);
                }
                treeSelcetDataTouchPersonOfEnterprise.setChildren(chilren2);
            }
        }
        return treeSelcetDatalist;
    }

    @PostMapping("/exceladd")
    public boolean list(String from, MultipartFile[] files, HttpSession httpSession) {
        boolean flag = true;
        //excel->model
        Class<? extends BaseRowModel>[] modelClassArr = new Class[1];
        modelClassArr[0] = TouchPersonOfEnterpriseModel.class;
        Map<String, List<Object>> modelMap = MyExcelUtil.readMoreSheetExcel(files, modelClassArr);
        //model->entity
        for (Map.Entry<String, List<Object>> entry : modelMap.entrySet()) {
            String type = entry.getKey();
            List<Object> modelList = entry.getValue();
            List<TouchPersonOfEnterprise> dataList = getDataList(modelList, type, httpSession);
            flag = touchPersonOfEnterpriseService.saveBatch(dataList);
        }
        return flag;
    }

    private List<TouchPersonOfEnterprise> getDataList(List<Object> modelList, String type, HttpSession httpSession) {
        List<TouchPersonOfEnterprise> dataList = org.apache.commons.compress.utils.Lists.newArrayList();
        for (Object object : modelList) {
            TouchPersonOfEnterpriseModel touchPersonOfEnterpriseModel = (TouchPersonOfEnterpriseModel) object;
            //业务处理
            TouchPersonOfEnterprise touchPersonOfEnterprise = new TouchPersonOfEnterprise();
            //enterpriseId
            SysUser sysUser = (SysUser) httpSession.getAttribute(GlobalConstants.LOGIN_NAME);
            QueryWrapper<Enterprise> queryWrapper1 = new QueryWrapper();
            queryWrapper1.eq("name", sysUser.getCompanyName());
            List<Enterprise> list1 = enterpriseService.list(queryWrapper1);
            for (Enterprise enterprise : list1) {
                touchPersonOfEnterprise.setEnterpriseId(enterprise.getId());
            }
            //workplaceId
            QueryWrapper<WorkplaceOfEnterprise> qww = new QueryWrapper();
            qww.eq("name", touchPersonOfEnterpriseModel.getWorkplaceId()).eq("enterpriseId", touchPersonOfEnterprise.getEnterpriseId());
            WorkplaceOfEnterprise one1 = workplaceOfEnterpriseService.getOne(qww);
            touchPersonOfEnterprise.setWorkplaceId(one1.getId());
            //postId
            QueryWrapper<PostOfEnterprise> qwp = new QueryWrapper();
            qwp.eq("postSmallName", touchPersonOfEnterpriseModel.getPostId()).eq("enterpriseId", touchPersonOfEnterprise.getEnterpriseId()).eq("workplaceId", touchPersonOfEnterprise.getWorkplaceId());
            PostOfEnterprise one = postOfEnterpriseService.getOne(qwp);
            touchPersonOfEnterprise.setPostId(one.getId());
            //postDangerId
            QueryWrapper<PostDangerOfEnterprise> qwD = new QueryWrapper();
            qwD.eq("dangerSmallName", touchPersonOfEnterpriseModel.getPostDangerId()).eq("enterpriseId", touchPersonOfEnterprise.getEnterpriseId()).eq("workplaceId", touchPersonOfEnterprise.getWorkplaceId()).eq("postId", touchPersonOfEnterprise.getPostId());
            PostDangerOfEnterprise one2 = postDangerOfEnterpriseService.getOne(qwD);
            touchPersonOfEnterprise.setPostDangerId(one2.getId());

            BeanUtils.copyProperties(touchPersonOfEnterpriseModel, touchPersonOfEnterprise);
            dataList.add(touchPersonOfEnterprise);
        }
        return dataList;
    }

}
