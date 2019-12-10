package com.hthyaq.zybadmin.controller;


import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.hthyaq.zybadmin.common.constants.GlobalConstants;
import com.hthyaq.zybadmin.model.entity.*;
import com.hthyaq.zybadmin.model.vo.PublicOpinionView;
import com.hthyaq.zybadmin.model.vo.SysRoleTree;
import com.hthyaq.zybadmin.service.OpinionNameService;
import com.hthyaq.zybadmin.service.PublicOpinionService;
import com.hthyaq.zybadmin.service.SysRoleService;
import org.apache.commons.compress.utils.Lists;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author zhangqiang
 * @since 2019-12-10
 */
@RestController
@RequestMapping("/publicOpinion")
public class PublicOpinionController {
    @Autowired
    PublicOpinionService publicOpinionService;
    @Autowired
    OpinionNameService opinionNameService;

    @PostMapping("/add")
    public boolean add(@RequestBody PublicOpinionView publicOpinionView) {
        boolean flag = false;

        //先删除
        publicOpinionService.remove(new QueryWrapper<PublicOpinion>().eq("userid", "-1"));
        //后插入
        Set<Integer> menuIdSet = Sets.newHashSet();
        if (ObjectUtil.length(publicOpinionView.getTypename()) > 0) {
            menuIdSet.addAll(publicOpinionView.getTypename());
        }
        PublicOpinion publicOpinion = new PublicOpinion();
        BeanUtils.copyProperties(publicOpinionView, publicOpinion);
        List<PublicOpinion> data = Lists.newArrayList();
        for (Integer menuId : menuIdSet) {
            PublicOpinion tmp = new PublicOpinion();
            tmp.setType(menuId);
            data.add(tmp);
        }

        flag = publicOpinionService.saveBatch(data);
        ;
        return flag;
    }

    @GetMapping("/list")
    public Map<String, List<Integer>> getById() {
        Map<String, List<Integer>> map = Maps.newHashMap();
        List<PublicOpinion> list = new ArrayList<>();
        List<OpinionName> list1 = opinionNameService.list();
        for (OpinionName opinionName : list1) {
            QueryWrapper<PublicOpinion> queryWrapper = new QueryWrapper<>();
            queryWrapper.in("type", opinionName.getId());
            PublicOpinion menuId = publicOpinionService.getOne(queryWrapper);
            if (menuId != null) {
                list.add(menuId);
            }
        }
        if (ObjectUtil.length(list) > 0) {

            map.put("typename", list.stream().map(PublicOpinion::getType).collect(Collectors.toList()));
        }
        return map;
    }

    @GetMapping("/sysRoleTree")
    public List<PublicOpinion> cascadeData() {
        List list1 = new ArrayList();
        List<OpinionName> list = opinionNameService.list();
        for (OpinionName opinionName : list) {
            SysRoleTree sysRoleTree = new SysRoleTree();
            sysRoleTree.setLabel(opinionName.getTypename());
            sysRoleTree.setValue(opinionName.getId());
            list1.add(sysRoleTree);
        }
        return list1;
    }
}
