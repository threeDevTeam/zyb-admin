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
import com.hthyaq.zybadmin.model.excelModel.PostDangerOfEnterpriseModel;
import com.hthyaq.zybadmin.model.vo.PostDangerOfEnterpriseView;
import com.hthyaq.zybadmin.model.vo.PostOfEnterpriseView;
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
 * 岗位危害信息 前端控制器
 * </p>
 *
 * @author zhangqiang
 * @since 2019-08-30
 */
@RestController
@RequestMapping("/postDangerOfEnterprise")
public class PostDangerOfEnterpriseController {
    @Autowired
    PostDangerOfEnterpriseService postDangerOfEnterpriseService;
    @Autowired
    EnterpriseOfRegisterService enterpriseOfRegisterService;
    @Autowired
    EnterpriseService enterpriseService;
    @Autowired
    WorkplaceOfEnterpriseService workplaceOfEnterpriseService;
    @Autowired
    PostOfEnterpriseService postOfEnterpriseService;
    @Autowired
    HazardousfactorsService hazardousfactorsService;
    @Autowired
    ZybnameService zybnameService;
    @Autowired
    SysUserService sysUserService;
    @PostMapping("/add")
    public boolean add(@RequestBody PostDangerOfEnterpriseView postDangerOfEnterpriseView, HttpSession httpSession) {
        boolean flag = false;
        System.out.println(postDangerOfEnterpriseView);
        PostDangerOfEnterprise postDangerOfEnterprise = new PostDangerOfEnterprise();
        //other
        BeanUtils.copyProperties(postDangerOfEnterpriseView, postDangerOfEnterprise);
        //职业病危害因素名称
        QueryWrapper<Hazardousfactors> qw4 = new QueryWrapper<>();
        qw4.eq("id", postDangerOfEnterpriseView.getCascaded1().get(0));
        List<Hazardousfactors> list5 = hazardousfactorsService.list(qw4);
        for (Hazardousfactors hazardousfactors : list5) {
            postDangerOfEnterprise.setDangerBigName(hazardousfactors.getName());
        }
        if(postDangerOfEnterpriseView.getCascaded1().size()==2){
            QueryWrapper<Hazardousfactors> qw1= new QueryWrapper<>();
            qw1.eq("id",postDangerOfEnterpriseView.getCascaded1().get(1));
            List<Hazardousfactors> list6=hazardousfactorsService.list(qw1);
            for (Hazardousfactors hazardousfactors : list6) {
                postDangerOfEnterprise.setDangerSmallName(hazardousfactors.getName());
            }
        }else{
            postDangerOfEnterprise.setDangerSmallName("无");
        }
        //职业病名称
        QueryWrapper<Zybname> qwZ = new QueryWrapper<>();
        qwZ.eq("id", postDangerOfEnterpriseView.getCascaded2().get(0));
        List<Zybname> listZ = zybnameService.list(qwZ);
        for (Zybname zybname : listZ) {
            postDangerOfEnterprise.setSickBigName(zybname.getName());
        }
        if (postDangerOfEnterpriseView.getCascaded2().size() == 2) {
            QueryWrapper<Zybname> qw1 = new QueryWrapper<>();
            qw1.eq("id", postDangerOfEnterpriseView.getCascaded2().get(1));
            List<Zybname> list6 = zybnameService.list(qw1);
            for (Zybname zybname : list6) {
                postDangerOfEnterprise.setSickSmallName(zybname.getName());
            }
        } else {
            postDangerOfEnterprise.setDangerSmallName("无");
        }
        //enterpriseId
        SysUser sysUser = (SysUser) httpSession.getAttribute(GlobalConstants.LOGIN_NAME);
        QueryWrapper<Enterprise> queryWrapper1=new QueryWrapper();
        queryWrapper1.eq("name",sysUser.getCompanyName());
        List<Enterprise> list1 = enterpriseService.list(queryWrapper1);
        for (Enterprise enterprise : list1) {
            postDangerOfEnterprise.setEnterpriseId(enterprise.getId());
        }

        //postId
        postDangerOfEnterprise.setPostId(Long.parseLong(postDangerOfEnterpriseView.getTreeSelect()));

        //通过postId查询出workplaceId
        PostOfEnterprise postOfEnterprise = postOfEnterpriseService.getById(postDangerOfEnterpriseView.getTreeSelect());
        postDangerOfEnterprise.setWorkplaceId(postOfEnterprise.getWorkplaceId());

        //保存
        System.out.println(postDangerOfEnterprise);
        flag = postDangerOfEnterpriseService.save(postDangerOfEnterprise);


        return flag;
    }

    @GetMapping("/delete")
    public boolean delete(String id) {
        return postDangerOfEnterpriseService.removeById(id);
    }

    @GetMapping("/getById")
    public PostDangerOfEnterprise getById(Integer id) {
        List listc1 = new ArrayList();
        List listc2 = new ArrayList();
        PostDangerOfEnterpriseView postDangerOfEnterpriseView=new PostDangerOfEnterpriseView();
        PostDangerOfEnterprise postDangerOfEnterprise = postDangerOfEnterpriseService.getById(id);
        BeanUtils.copyProperties(postDangerOfEnterprise, postDangerOfEnterpriseView);
        PostOfEnterprise postOfEnterprise = postOfEnterpriseService.getById(postDangerOfEnterprise.getPostId());
        WorkplaceOfEnterprise workplaceOfEnterprise= workplaceOfEnterpriseService.getById(postDangerOfEnterprise.getWorkplaceId());
        postDangerOfEnterpriseView.setTreeSelect(String.valueOf(workplaceOfEnterprise.getName()+"--"+postOfEnterprise.getPostSmallName()));

        //职业病危害因素名称
        QueryWrapper<Hazardousfactors> qw6 = new QueryWrapper<>();
        qw6.eq("name", postDangerOfEnterpriseView.getDangerBigName());
        List<Hazardousfactors> hd = hazardousfactorsService.list(qw6);
        for (Hazardousfactors hazardousfactors : hd) {
            listc1.add(hazardousfactors.getId());
        }
        QueryWrapper<Hazardousfactors> qw7 = new QueryWrapper<>();
        qw7.eq("name", postDangerOfEnterpriseView.getDangerSmallName());
        List<Hazardousfactors> hd2 = hazardousfactorsService.list(qw7);
        for (Hazardousfactors hazardousfactors : hd2) {
            listc1.add(hazardousfactors.getId());
        }
        postDangerOfEnterpriseView.setCascaded1((ArrayList) listc1);
        //职业病名称
        QueryWrapper<Zybname> qwZ = new QueryWrapper<>();
        qwZ.eq("name", postDangerOfEnterpriseView.getSickBigName());
        List<Zybname> hdZ1 = zybnameService.list(qwZ);
        for (Zybname zybname : hdZ1) {
            listc2.add(zybname.getId());
        }
        QueryWrapper<Zybname> qwZ2 = new QueryWrapper<>();
        qwZ2.eq("name", postDangerOfEnterpriseView.getSickSmallName());
        List<Zybname> hdZ = zybnameService.list(qwZ2);
        for (Zybname zybname : hdZ) {
            listc2.add(zybname.getId());
        }
        postDangerOfEnterpriseView.setCascaded2((ArrayList) listc2);
        return postDangerOfEnterpriseView;
    }

    @PostMapping("/edit")
    public boolean edit(@RequestBody PostDangerOfEnterpriseView postDangerOfEnterpriseView) {
        PostDangerOfEnterprise postDangerOfEnterprise = new PostDangerOfEnterprise();

        BeanUtils.copyProperties(postDangerOfEnterpriseView, postDangerOfEnterprise);

        //职业病危害因素名称
        QueryWrapper<Hazardousfactors> qw4 = new QueryWrapper<>();
        qw4.eq("id", postDangerOfEnterpriseView.getCascaded1().get(0));
        List<Hazardousfactors> list5 = hazardousfactorsService.list(qw4);
        for (Hazardousfactors hazardousfactors : list5) {
            postDangerOfEnterprise.setDangerBigName(hazardousfactors.getName());
        }
        if(postDangerOfEnterpriseView.getCascaded1().size()==2){
            QueryWrapper<Hazardousfactors> qw1= new QueryWrapper<>();
            qw1.eq("id",postDangerOfEnterpriseView.getCascaded1().get(1));
            List<Hazardousfactors> list6=hazardousfactorsService.list(qw1);
            for (Hazardousfactors hazardousfactors : list6) {
                postDangerOfEnterprise.setDangerSmallName(hazardousfactors.getName());
            }
        }else{
            postDangerOfEnterprise.setDangerSmallName("无");
        }
        //职业病名称
        QueryWrapper<Zybname> qwZ = new QueryWrapper<>();
        qwZ.eq("id", postDangerOfEnterpriseView.getCascaded2().get(0));
        List<Zybname> listZ = zybnameService.list(qwZ);
        for (Zybname zybname : listZ) {
            postDangerOfEnterprise.setSickBigName(zybname.getName());
        }
        if (postDangerOfEnterpriseView.getCascaded2().size() == 2) {
            QueryWrapper<Zybname> qw1 = new QueryWrapper<>();
            qw1.eq("id", postDangerOfEnterpriseView.getCascaded2().get(1));
            List<Zybname> list6 = zybnameService.list(qw1);
            for (Zybname zybname : list6) {
                postDangerOfEnterprise.setSickSmallName(zybname.getName());
            }
        } else {
            postDangerOfEnterprise.setSickSmallName("无");
        }

        return postDangerOfEnterpriseService.updateById(postDangerOfEnterprise);
    }

    @GetMapping("/list")
    public IPage<PostDangerOfEnterprise> list(String json, HttpSession httpSession) {
        SysUser sysUser = (SysUser) httpSession.getAttribute(GlobalConstants.LOGIN_NAME);
        List list1 = new ArrayList();
        //字符串解析成java对象
        JSONObject jsonObject = JSON.parseObject(json);
        //从对象中获取值
        Integer currentPage = jsonObject.getInteger("currentPage");
        Integer pageSize = jsonObject.getInteger("pageSize");
        String upDate = jsonObject.getString("upDate");

        QueryWrapper<Enterprise> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.eq("name", sysUser.getCompanyName());
        List<Enterprise> list = enterpriseService.list(queryWrapper1);
        for (Enterprise enterprise : list) {
            list1.clear();
            Long id = enterprise.getId();
            list1.add(id);
        }
        QueryWrapper<PostDangerOfEnterprise> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("enterpriseId",list1.get(0));
        if (!Strings.isNullOrEmpty(upDate)) {
            queryWrapper.eq("upDate", upDate);
        }
        IPage<PostDangerOfEnterprise> page = postDangerOfEnterpriseService.page(new Page<>(currentPage, pageSize), queryWrapper);

        return page;
    }

    @GetMapping("/TreeSelcetData")
    public List<TreeSelcetDataPersonOfEnterprise> TreeSelcetData(HttpSession httpSession) {
        SysUser sysUser = (SysUser) httpSession.getAttribute(GlobalConstants.LOGIN_NAME);
        QueryWrapper<SysUser> qws=new QueryWrapper<>();
        qws.eq("loginName",sysUser.getLoginName());
        SysUser one = sysUserService.getOne(qws);

        QueryWrapper<Enterprise> qwe=new QueryWrapper<>();
        qwe.eq("name",one.getCompanyName());
        Enterprise one1 = enterpriseService.getOne(qwe);

        List<TreeSelcetDataPersonOfEnterprise> treeSelcetDatalist = new ArrayList();
        QueryWrapper<WorkplaceOfEnterprise> qww=new QueryWrapper<>();
        qww.eq("enterpriseId",one1.getId());
        List<WorkplaceOfEnterprise> list = workplaceOfEnterpriseService.list(qww);


        for (WorkplaceOfEnterprise workplaceOfEnterprise : list) {
            List<TreeSelcetDataPersonOfEnterprise> chilren = Lists.newArrayList();
            TreeSelcetDataPersonOfEnterprise treeSelcetDataPersonOfEnterprise1 = new TreeSelcetDataPersonOfEnterprise();
            treeSelcetDataPersonOfEnterprise1.setTitle(workplaceOfEnterprise.getName());
            treeSelcetDataPersonOfEnterprise1.setValue(String.valueOf(workplaceOfEnterprise.getId()));
            treeSelcetDataPersonOfEnterprise1.setKey(String.valueOf(workplaceOfEnterprise.getId()));
            treeSelcetDataPersonOfEnterprise1.setChildren(chilren);
            treeSelcetDatalist.add(treeSelcetDataPersonOfEnterprise1);
            QueryWrapper<PostOfEnterprise> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("workplaceId", workplaceOfEnterprise.getId());
            List<PostOfEnterprise> list1 = postOfEnterpriseService.list(queryWrapper);
            for (PostOfEnterprise postOfEnterprise : list1) {
                TreeSelcetDataPersonOfEnterprise treeSelcetDataPersonOfEnterprise = new TreeSelcetDataPersonOfEnterprise();
                treeSelcetDataPersonOfEnterprise.setTitle(postOfEnterprise.getPostSmallName());
                treeSelcetDataPersonOfEnterprise.setValue(String.valueOf(postOfEnterprise.getId()));
                treeSelcetDataPersonOfEnterprise.setKey(String.valueOf(postOfEnterprise.getId()));
                chilren.add(treeSelcetDataPersonOfEnterprise);
            }

        }
        return treeSelcetDatalist;
    }
    @PostMapping("/exceladd")
    public boolean list(String from, MultipartFile[] files, HttpSession httpSession) {
        boolean flag = true;
        //excel->model
        Class<? extends BaseRowModel>[] modelClassArr = new Class[1];
        modelClassArr[0]= PostDangerOfEnterpriseModel.class;
        Map<String, List<Object>> modelMap = MyExcelUtil.readMoreSheetExcel(files,modelClassArr);
        //model->entity
        for (Map.Entry<String, List<Object>> entry : modelMap.entrySet()) {
            String type = entry.getKey();
            List<Object> modelList = entry.getValue();
            List<PostDangerOfEnterprise> dataList = getDataList(modelList, type,httpSession);
            flag = postDangerOfEnterpriseService.saveBatch(dataList);
        }
        return flag;
    }
    private List<PostDangerOfEnterprise> getDataList(List<Object> modelList, String type, HttpSession httpSession) {
        List<PostDangerOfEnterprise> dataList = org.apache.commons.compress.utils.Lists.newArrayList();
        for (Object object : modelList) {
            PostDangerOfEnterpriseModel postDangerOfEnterpriseModel = (PostDangerOfEnterpriseModel) object;
            //业务处理
            PostDangerOfEnterprise postDangerOfEnterprise = new PostDangerOfEnterprise();
            //enterpriseId
            SysUser sysUser = (SysUser) httpSession.getAttribute(GlobalConstants.LOGIN_NAME);
            QueryWrapper<Enterprise> queryWrapper1=new QueryWrapper();
            queryWrapper1.eq("name",sysUser.getCompanyName());
            List<Enterprise> list1 = enterpriseService.list(queryWrapper1);
            for (Enterprise enterprise : list1) {
                postDangerOfEnterprise.setEnterpriseId(enterprise.getId());
            }
            //workplaceId
            QueryWrapper<WorkplaceOfEnterprise> qww=new QueryWrapper();
            qww.eq("name",postDangerOfEnterpriseModel.getWorkplaceId()).eq("enterpriseId",postDangerOfEnterprise.getEnterpriseId());
            WorkplaceOfEnterprise one1 = workplaceOfEnterpriseService.getOne(qww);
            postDangerOfEnterprise.setWorkplaceId(one1.getId());
            //postId
            QueryWrapper<PostOfEnterprise> qwp=new QueryWrapper();
            qwp.eq("postSmallName",postDangerOfEnterpriseModel.getPostId()).eq("enterpriseId",postDangerOfEnterprise.getEnterpriseId()).eq("workplaceId", postDangerOfEnterprise.getWorkplaceId());
            PostOfEnterprise one = postOfEnterpriseService.getOne(qwp);
            postDangerOfEnterprise.setPostId(one.getId());
            BeanUtils.copyProperties(postDangerOfEnterpriseModel, postDangerOfEnterprise);
            if(postDangerOfEnterpriseModel.getTouchMode().equals("定点作业") || postDangerOfEnterpriseModel.getTouchMode().equals("巡检作业") ||postDangerOfEnterpriseModel.getTouchMode().equals("手工作业")||postDangerOfEnterpriseModel.getTouchMode().equals("自动控制")){
                postDangerOfEnterprise.setTouchMode(postDangerOfEnterpriseModel.getTouchMode());
            }else{
                return null;
            }
            dataList.add(postDangerOfEnterprise);
        }
        return dataList;
    }
}
