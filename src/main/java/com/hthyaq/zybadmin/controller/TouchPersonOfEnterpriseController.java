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
import com.hthyaq.zybadmin.model.excelModel.TreeSelcetDataTouchPersonOfEnterprise;
import com.hthyaq.zybadmin.model.vo.PersonOfEnterpriseView;
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

    @PostMapping("/add")
    public boolean add(@RequestBody TouchPersonOfEnterpriseView touchPersonOfEnterpriseView, HttpSession httpSession) {
        boolean flag = false;
        System.out.println(touchPersonOfEnterpriseView);
        TouchPersonOfEnterprise touchPersonOfEnterprise = new TouchPersonOfEnterprise();

        //other
        BeanUtils.copyProperties(touchPersonOfEnterpriseView, touchPersonOfEnterprise);

        //enterpriseId
        SysUser sysUser = (SysUser) httpSession.getAttribute(GlobalConstants.LOGIN_NAME);
        touchPersonOfEnterprise.setEnterpriseId(sysUser.getCompanyId());

        //postDangerId
        touchPersonOfEnterprise.setPostDangerId(Long.parseLong(touchPersonOfEnterpriseView.getTreeSelect()));

        //通过postDangerId查询出workplaceId,postId
       PostDangerOfEnterprise postDangerOfEnterprise = postDangerOfEnterpriseService.getById(touchPersonOfEnterpriseView.getTreeSelect());
        touchPersonOfEnterprise.setWorkplaceId(postDangerOfEnterprise.getWorkplaceId());
        touchPersonOfEnterprise.setPostId(postDangerOfEnterprise.getPostId());

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
        TouchPersonOfEnterpriseView touchPersonOfEnterpriseView=new TouchPersonOfEnterpriseView();
        TouchPersonOfEnterprise touchPersonOfEnterprise = touchPersonOfEnterpriseService.getById(id);
        BeanUtils.copyProperties(touchPersonOfEnterprise,touchPersonOfEnterpriseView);
        PostDangerOfEnterprise postDangerOfEnterprise = postDangerOfEnterpriseService.getById(touchPersonOfEnterprise.getPostDangerId());
        PostOfEnterprise postOfEnterprise = postOfEnterpriseService.getById(touchPersonOfEnterprise.getPostId());
        WorkplaceOfEnterprise workplaceOfEnterprise= workplaceOfEnterpriseService.getById(touchPersonOfEnterprise.getWorkplaceId());
        touchPersonOfEnterpriseView.setTreeSelect(String.valueOf(workplaceOfEnterprise.getName()+"--"+postOfEnterprise.getPostBigName()+"--"+postDangerOfEnterprise.getDangerBigName()));

        System.out.println(touchPersonOfEnterpriseView);
        //将demoCourse的数据设置到demoData
        return touchPersonOfEnterpriseView;
    }

    @PostMapping("/edit")
    public boolean edit(@RequestBody TouchPersonOfEnterprise touchPersonOfEnterprise) {
        return touchPersonOfEnterpriseService.updateById(touchPersonOfEnterprise);
    }

    @GetMapping("/list")
    public IPage<TouchPersonOfEnterprise> list(String json) {
        //字符串解析成java对象
        JSONObject jsonObject = JSON.parseObject(json);
        //从对象中获取值
        Integer currentPage = jsonObject.getInteger("currentPage");
        Integer pageSize = jsonObject.getInteger("pageSize");
        String upDate = jsonObject.getString("upDate");
        QueryWrapper<TouchPersonOfEnterprise> queryWrapper = new QueryWrapper<>();
        if (!Strings.isNullOrEmpty(upDate)) {
            queryWrapper.eq("upDate", upDate);
        }
        IPage<TouchPersonOfEnterprise> page = touchPersonOfEnterpriseService.page(new Page<>(currentPage, pageSize), queryWrapper);

        return page;
    }

    @GetMapping("/TreeSelcetData")
    public List<TreeSelcetDataTouchPersonOfEnterprise> TreeSelcetData() {
        List<TreeSelcetDataTouchPersonOfEnterprise> treeSelcetDatalist = new ArrayList();
        List<WorkplaceOfEnterprise> list = workplaceOfEnterpriseService.list();
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
                treeSelcetDataTouchPersonOfEnterprise.setTitle(postOfEnterprise.getPostBigName());
                treeSelcetDataTouchPersonOfEnterprise.setValue(String.valueOf(postOfEnterprise.getId()));
                treeSelcetDataTouchPersonOfEnterprise.setKey(String.valueOf(postOfEnterprise.getId()));
                chilren.add(treeSelcetDataTouchPersonOfEnterprise);
                List<TreeSelcetDataTouchPersonOfEnterprise> chilren2 = Lists.newArrayList();
                QueryWrapper<PostDangerOfEnterprise> queryWrapper2 = new QueryWrapper<>();
                queryWrapper2.eq("postId", postOfEnterprise.getId());
                List<PostDangerOfEnterprise> list2 = postDangerOfEnterpriseService.list(queryWrapper2);

                for (PostDangerOfEnterprise postDangerOfEnterprise : list2) {
                    TreeSelcetDataTouchPersonOfEnterprise treeSelcetDataTouchPersonOfEnterprise2 = new TreeSelcetDataTouchPersonOfEnterprise();
                    treeSelcetDataTouchPersonOfEnterprise2.setTitle(postDangerOfEnterprise.getDangerBigName());
                    treeSelcetDataTouchPersonOfEnterprise2.setValue(String.valueOf(postDangerOfEnterprise.getId()));
                    treeSelcetDataTouchPersonOfEnterprise2.setKey(String.valueOf(postDangerOfEnterprise.getId()));
                    chilren2.add(treeSelcetDataTouchPersonOfEnterprise2);
                }
                treeSelcetDataTouchPersonOfEnterprise.setChildren(chilren2);
            }
        }
        return treeSelcetDatalist;
    }
}
