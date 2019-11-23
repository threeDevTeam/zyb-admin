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
import com.hthyaq.zybadmin.model.excelModel.*;
import com.hthyaq.zybadmin.model.vo.MonitorOfEnterpriseView;
import com.hthyaq.zybadmin.model.vo.ProtectOfEnterpriseView;
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
    @Autowired
    SysUserService sysUserService;
    @Autowired
    SysRoleUserService sysRoleUserService;

    @PostMapping("/add")
    public boolean add(@RequestBody ProtectOfEnterpriseView protectOfEnterpriseView, HttpSession httpSession) {
        boolean flag = false;
        System.out.println(protectOfEnterpriseView);
        ProtectOfEnterprise protectOfEnterprise = new  ProtectOfEnterprise();

        //other
        BeanUtils.copyProperties(protectOfEnterpriseView, protectOfEnterprise);

        //enterpriseId
        SysUser sysUser = (SysUser) httpSession.getAttribute(GlobalConstants.LOGIN_NAME);
        QueryWrapper<Enterprise> queryWrapper1=new QueryWrapper();
        queryWrapper1.eq("name",sysUser.getCompanyName());
        List<Enterprise> list1 = enterpriseService.list(queryWrapper1);
        for (Enterprise enterprise : list1) {
            protectOfEnterprise.setEnterpriseId(enterprise.getId());
        }

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
        protectOfEnterpriseView.setTreeSelect(String.valueOf(workplaceOfEnterprise.getName()+"--"+postOfEnterprise.getPostSmallName()+"--"+postDangerOfEnterprise.getDangerSmallName()));

        System.out.println(protectOfEnterpriseView);
        //将demoCourse的数据设置到demoData
        return protectOfEnterpriseView;
    }

    @PostMapping("/edit")
    public boolean edit(@RequestBody ProtectOfEnterprise protectOfEnterprise) {
        return protectOfEnterpriseService.updateById(protectOfEnterprise);
    }

    @GetMapping("/list")
    public IPage<ProtectOfEnterprise> list(String json, HttpSession httpSession) {
        SysUser sysUser = (SysUser) httpSession.getAttribute(GlobalConstants.LOGIN_NAME);
        List list1 = new ArrayList();
        //字符串解析成java对象
        JSONObject jsonObject = JSON.parseObject(json);
        //从对象中获取值
        Integer currentPage = jsonObject.getInteger("currentPage");
        Integer pageSize = jsonObject.getInteger("pageSize");
        String name = jsonObject.getString("name");
        String type = jsonObject.getString("type");
        QueryWrapper<SysRoleUser> qw = new QueryWrapper<>();
        qw.eq("userId", sysUser.getId());
        SysRoleUser sysRoleUser = sysRoleUserService.getOne(qw);
        if (sysRoleUser.getRoleId() == 1) {

            QueryWrapper<ProtectOfEnterprise> queryWrapper = new QueryWrapper<>();
            if (!Strings.isNullOrEmpty(name)) {
                queryWrapper.like("name", name);
            }
            if (!Strings.isNullOrEmpty(type)) {
                queryWrapper.like("type", type);
            }
            queryWrapper.orderByDesc("id");
            IPage<ProtectOfEnterprise> page = protectOfEnterpriseService.page(new Page<>(currentPage, pageSize), queryWrapper);

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
            QueryWrapper<ProtectOfEnterprise> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("enterpriseId", list1.get(0));
            if (!Strings.isNullOrEmpty(name)) {
                queryWrapper.like("name", name);
            }
            if (!Strings.isNullOrEmpty(type)) {
                queryWrapper.like("type", type);
            }
            queryWrapper.orderByDesc("id");
            IPage<ProtectOfEnterprise> page = protectOfEnterpriseService.page(new Page<>(currentPage, pageSize), queryWrapper);

            return page;
        }
    }
    @GetMapping("/TreeSelcetData")
    public List<TreeSelcetDataProtectOfEnterprise> TreeSelcetData(HttpSession httpSession) {

        SysUser sysUser = (SysUser) httpSession.getAttribute(GlobalConstants.LOGIN_NAME);
        QueryWrapper<SysUser> qws=new QueryWrapper<>();
        qws.eq("loginName",sysUser.getLoginName());
        SysUser one = sysUserService.getOne(qws);

        QueryWrapper<Enterprise> qwe=new QueryWrapper<>();
        qwe.eq("name",one.getCompanyName());
        Enterprise one1 = enterpriseService.getOne(qwe);


        List<TreeSelcetDataProtectOfEnterprise> treeSelcetDatalist = new ArrayList();
        QueryWrapper<WorkplaceOfEnterprise> qww=new QueryWrapper<>();
        qww.eq("enterpriseId",one1.getId());
        List<WorkplaceOfEnterprise> list = workplaceOfEnterpriseService.list(qww);
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
                treeSelcetDataProtectOfEnterprise.setTitle(postOfEnterprise.getPostSmallName());
                treeSelcetDataProtectOfEnterprise.setValue(String.valueOf(postOfEnterprise.getId()));
                treeSelcetDataProtectOfEnterprise.setKey(String.valueOf(postOfEnterprise.getId()));
                chilren.add(treeSelcetDataProtectOfEnterprise);
                List<TreeSelcetDataProtectOfEnterprise> chilren2 = Lists.newArrayList();
                QueryWrapper<PostDangerOfEnterprise> queryWrapper2 = new QueryWrapper<>();
                queryWrapper2.eq("postId", postOfEnterprise.getId());
                List<PostDangerOfEnterprise> list2 = postDangerOfEnterpriseService.list(queryWrapper2);

                for (PostDangerOfEnterprise postDangerOfEnterprise : list2) {
                    TreeSelcetDataProtectOfEnterprise treeSelcetDataProtectOfEnterprise2 = new TreeSelcetDataProtectOfEnterprise();
                    treeSelcetDataProtectOfEnterprise2.setTitle(postDangerOfEnterprise.getDangerSmallName());
                    treeSelcetDataProtectOfEnterprise2.setValue(String.valueOf(postDangerOfEnterprise.getId()));
                    treeSelcetDataProtectOfEnterprise2.setKey(String.valueOf(postDangerOfEnterprise.getId()));
                    chilren2.add(treeSelcetDataProtectOfEnterprise2);
                }
                treeSelcetDataProtectOfEnterprise.setChildren(chilren2);
            }
        }
        return treeSelcetDatalist;
    }
    @PostMapping("/exceladd")
    public boolean list(String from, MultipartFile[] files, HttpSession httpSession) {
        boolean flag = true;
        //excel->model
        Class<? extends BaseRowModel>[] modelClassArr = new Class[1];
        modelClassArr[0]= ProtectOfEnterpriseModel.class;
        Map<String, List<Object>> modelMap = MyExcelUtil.readMoreSheetExcel(files,modelClassArr);
        //model->entity
        for (Map.Entry<String, List<Object>> entry : modelMap.entrySet()) {
            String type = entry.getKey();
            List<Object> modelList = entry.getValue();
            List<ProtectOfEnterprise> dataList = getDataList(modelList, type,httpSession);
            flag = protectOfEnterpriseService.saveBatch(dataList);
        }
        return flag;
    }
    private List<ProtectOfEnterprise> getDataList(List<Object> modelList, String type, HttpSession httpSession) {
        List<ProtectOfEnterprise> dataList = org.apache.commons.compress.utils.Lists.newArrayList();
        for (Object object : modelList) {
            ProtectOfEnterpriseModel protectOfEnterpriseModel = (ProtectOfEnterpriseModel) object;
            //业务处理

            ProtectOfEnterprise protectOfEnterprise = new ProtectOfEnterprise();
            //enterpriseId
            SysUser sysUser = (SysUser) httpSession.getAttribute(GlobalConstants.LOGIN_NAME);
            QueryWrapper<Enterprise> queryWrapper1=new QueryWrapper();
            queryWrapper1.eq("name",sysUser.getCompanyName());
            List<Enterprise> list1 = enterpriseService.list(queryWrapper1);
            for (Enterprise enterprise : list1) {
                protectOfEnterprise.setEnterpriseId(enterprise.getId());
            }
            //workplaceId
            QueryWrapper<WorkplaceOfEnterprise> qww=new QueryWrapper();
            qww.eq("name",protectOfEnterpriseModel.getWorkplaceId()).eq("enterpriseId",protectOfEnterprise.getEnterpriseId());
            WorkplaceOfEnterprise one1 = workplaceOfEnterpriseService.getOne(qww);
            protectOfEnterprise.setWorkplaceId(one1.getId());
            //postId
            QueryWrapper<PostOfEnterprise> qwp=new QueryWrapper();
            qwp.eq("postSmallName",protectOfEnterpriseModel.getPostId()).eq("enterpriseId",protectOfEnterprise.getEnterpriseId()).eq("workplaceId", protectOfEnterprise.getWorkplaceId());
            PostOfEnterprise one = postOfEnterpriseService.getOne(qwp);
            protectOfEnterprise.setPostId(one.getId());
            //postDangerId
            QueryWrapper<PostDangerOfEnterprise> qwD=new QueryWrapper();
            qwD.eq("dangerSmallName",protectOfEnterpriseModel.getPostDangerId()).eq("enterpriseId",protectOfEnterprise.getEnterpriseId()).eq("workplaceId", protectOfEnterprise.getWorkplaceId()).eq("postId", protectOfEnterprise.getPostId());
            PostDangerOfEnterprise one2 = postDangerOfEnterpriseService.getOne(qwD);
            protectOfEnterprise.setPostDangerId(one2.getId());

            BeanUtils.copyProperties(protectOfEnterpriseModel, protectOfEnterpriseModel);
            if(protectOfEnterpriseModel.getType().equals("防尘设施") || protectOfEnterpriseModel.getType().equals("防毒设施") ||protectOfEnterpriseModel.getType().equals("防噪声设施")||protectOfEnterpriseModel.getType().equals("防高温设施")
                    ||protectOfEnterpriseModel.getType().equals("防辐射设施")||protectOfEnterpriseModel.getType().equals("其他")){
                protectOfEnterpriseModel.setType(protectOfEnterpriseModel.getType());
            }else{
                return null;
            }
            if(protectOfEnterpriseModel.getStatus().equals("正常") || protectOfEnterpriseModel.getStatus().equals("故障") ||protectOfEnterpriseModel.getStatus().equals("报废")||protectOfEnterpriseModel.getStatus().equals("其他")
                    ||protectOfEnterpriseModel.getStatus().equals("维修")||protectOfEnterpriseModel.getStatus().equals("停用")){
                protectOfEnterpriseModel.setType(protectOfEnterpriseModel.getType());
            }else{
                return null;
            }
            if(protectOfEnterpriseModel.getProtectEffect().equals("差") || protectOfEnterpriseModel.getProtectEffect().equals("一般") ||protectOfEnterpriseModel.getProtectEffect().equals("良好")){
                protectOfEnterpriseModel.setProtectEffect(protectOfEnterpriseModel.getProtectEffect());
            }else{
                return null;
            }

            dataList.add(protectOfEnterprise);
        }
        return dataList;
    }
}
