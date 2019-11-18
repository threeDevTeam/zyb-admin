package com.hthyaq.zybadmin.common.utils.cache;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.base.Strings;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.Lists;
import com.hthyaq.zybadmin.common.utils.SpringUtil;
import com.hthyaq.zybadmin.model.entity.AreaOfDic;
import com.hthyaq.zybadmin.model.entity.BaseOfDic;
import com.hthyaq.zybadmin.model.entity.IndustryOfDic;
import com.hthyaq.zybadmin.service.AreaOfDicService;
import com.hthyaq.zybadmin.service.BaseOfDicService;
import com.hthyaq.zybadmin.service.IndustryOfDicService;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

//将地区、注册类型、所属行业缓存起来
public class DataVisualCacheUtil {
    private static LoadingCache<String, Object> cache = CacheBuilder.newBuilder()
            .maximumSize(1000)// 设置缓存个数
            .build(new CacheLoader<String, Object>() {
                @Override
                //当本地缓存命没有中时，调用load方法获取结果并将结果缓存
                public Object load(String key) {
                    return get(key);
                }

                // 数据库进行查询
                private Object get(String key) {
                    AreaOfDicService areaOfDicService = SpringUtil.getBean(AreaOfDicService.class);
                    IndustryOfDicService industryOfDicService = SpringUtil.getBean(IndustryOfDicService.class);
                    BaseOfDicService baseOfDicService = SpringUtil.getBean(BaseOfDicService.class);
                    Object obj = null;
                    if (key.contains("AreaSelf")) {
                        //key=安徽AreaSelf/安徽,合肥市,庐江县AreaSelf
                        String[] strArr = key.replaceAll("AreaSelf", "").split(",");
                        String name1 = null, name2 = null, name3 = null;
                        for (int i = 0; i < strArr.length; i++) {
                            if (i == 0) name1 = strArr[i];
                            if (i == 1) name2 = strArr[i];
                            if (i == 2) name3 = strArr[i];
                        }
                        //查询
                        if (!Strings.isNullOrEmpty(name1) && Strings.isNullOrEmpty(name2) && Strings.isNullOrEmpty(name3)) {
                            //name1
                            AreaOfDic n1 = areaOfDicService.getOne(new QueryWrapper<AreaOfDic>().eq("level", 1).eq("name", name1));
                            obj = n1;
                        } else if (!Strings.isNullOrEmpty(name1) && !Strings.isNullOrEmpty(name2) && Strings.isNullOrEmpty(name3)) {
                            //name1、name2
                            AreaOfDic n1 = areaOfDicService.getOne(new QueryWrapper<AreaOfDic>().eq("level", 1).eq("name", name1));
                            AreaOfDic n2 = areaOfDicService.getOne(new QueryWrapper<AreaOfDic>().eq("pid", n1.getId()).eq("name", name2));
                            obj = n2;
                        } else if (!Strings.isNullOrEmpty(name1) && !Strings.isNullOrEmpty(name2) && !Strings.isNullOrEmpty(name3)) {
                            //name1、name2、name3
                            AreaOfDic n1 = areaOfDicService.getOne(new QueryWrapper<AreaOfDic>().eq("level", 1).eq("name", name1));
                            AreaOfDic n2 = areaOfDicService.getOne(new QueryWrapper<AreaOfDic>().eq("pid", n1.getId()).eq("name", name2));
                            AreaOfDic n3 = areaOfDicService.getOne(new QueryWrapper<AreaOfDic>().eq("pid", n2.getId()).eq("name", name3));
                            obj = n3;
                        }
                    } else if (key.contains("AreaChildren")) {
                        if (key.equals("国家AreaChildren")) {
                            //key=国家AreaChildren
                            List<AreaOfDic> areaList = areaOfDicService.list(new QueryWrapper<AreaOfDic>().eq("level", 1).notIn("name", "澳门", "台湾"));
                            obj = areaList;
                        } else {
                            //key=安徽AreaChildren/安徽,合肥市,庐江县AreaChildren
                            String[] strArr = key.replaceAll("AreaChildren", "").split(",");
                            String name1 = null, name2 = null, name3 = null;
                            for (int i = 0; i < strArr.length; i++) {
                                if (i == 0) name1 = strArr[i];
                                if (i == 1) name2 = strArr[i];
                                if (i == 2) name3 = strArr[i];
                            }
                            //查询
                            if (!Strings.isNullOrEmpty(name1) && Strings.isNullOrEmpty(name2) && Strings.isNullOrEmpty(name3)) {
                                //name1
                                AreaOfDic n1 = areaOfDicService.getOne(new QueryWrapper<AreaOfDic>().eq("level", 1).eq("name", name1));
                                List<AreaOfDic> areaList = areaOfDicService.list(new QueryWrapper<AreaOfDic>().eq("pid", n1.getId()).orderByAsc("id"));
                                obj = areaList;
                            } else if (!Strings.isNullOrEmpty(name1) && !Strings.isNullOrEmpty(name2) && Strings.isNullOrEmpty(name3)) {
                                //name1、name2
                                AreaOfDic n1 = areaOfDicService.getOne(new QueryWrapper<AreaOfDic>().eq("level", 1).eq("name", name1));
                                AreaOfDic n2 = areaOfDicService.getOne(new QueryWrapper<AreaOfDic>().eq("pid", n1.getId()).eq("name", name2));
                                List<AreaOfDic> areaList = areaOfDicService.list(new QueryWrapper<AreaOfDic>().eq("pid", n2.getId()).orderByAsc("id"));
                                obj = areaList;
                            } else if (!Strings.isNullOrEmpty(name1) && !Strings.isNullOrEmpty(name2) && !Strings.isNullOrEmpty(name3)) {
                                //name1、name2、name3
                                AreaOfDic n1 = areaOfDicService.getOne(new QueryWrapper<AreaOfDic>().eq("level", 1).eq("name", name1));
                                AreaOfDic n2 = areaOfDicService.getOne(new QueryWrapper<AreaOfDic>().eq("pid", n1.getId()).eq("name", name2));
                                AreaOfDic n3 = areaOfDicService.getOne(new QueryWrapper<AreaOfDic>().eq("pid", n2.getId()).eq("name", name3));
                                List<AreaOfDic> areaList = areaOfDicService.list(new QueryWrapper<AreaOfDic>().eq("pid", n3.getId()).orderByAsc("id"));
                                obj = areaList;
                            }
                        }
                    } else if (key.equals("industry")) {
                        //所属行业
                        List<IndustryOfDic> industryList = industryOfDicService.list(new QueryWrapper<IndustryOfDic>().eq("topid", -1));
                        obj = industryList;
                    } else if (key.equals("registerType")) {
                        //登记注册类型
                        List<com.hthyaq.zybadmin.model.entity.BaseOfDic> registerList = baseOfDicService.list(new QueryWrapper<com.hthyaq.zybadmin.model.entity.BaseOfDic>().eq("flag", "登记注册类型").eq("level",1));
                        obj = registerList;
                    } else if (key.equals("danger")) {
                        //危害因素
                        obj = com.google.common.collect.Lists.newArrayList("粉尘", "化学因素", "物理因素", "放射性因素", "生物因素");
                    } else if (key.equals("enterpriseSize")) {
                        //企业规模
                        obj = com.google.common.collect.Lists.newArrayList("微型", "小型", "中型", "大型");
                    } else {
                        throw new RuntimeException("key找不到了！");
                    }
                    return obj;
                }
            });

    public static void set(String key, Object list) {
        cache.put(key, list);
    }

    /*
    key=安徽AreaSelf/安徽,合肥市,庐江县AreaSelf
     */
    public static AreaOfDic getAreaSelf(String name1, String name2, String name3) {
        String key = null;
        //组装key
        StringBuffer sb = new StringBuffer();
        if (!Strings.isNullOrEmpty(name1)) sb.append(name1);
        if (!Strings.isNullOrEmpty(name2)) sb.append("," + name2);
        if (!Strings.isNullOrEmpty(name3)) sb.append("," + name3);
        sb.append("AreaSelf");
        key = sb.toString();

        AreaOfDic areaOfDic = null;
        try {
            Object obj = cache.get(key);
            areaOfDic = (AreaOfDic) obj;
        } catch (ExecutionException e) {
            throw new RuntimeException("获取缓存中的地区、注册类型等时，出现了并发问题....");
        }
        return areaOfDic;
    }


    /*
        key=国家AreaChildren
        key=安徽AreaChildren/安徽,合肥市,庐江县AreaChildren
    */
    public static List<AreaOfDic> getAreaChildren(String name1, String name2, String name3) {
        String key = null;
        if (name1.equals("国家")) {
            key = "国家AreaChildren";
        } else {
            StringBuffer sb = new StringBuffer();
            if (!Strings.isNullOrEmpty(name1)) sb.append(name1);
            if (!Strings.isNullOrEmpty(name2)) sb.append("," + name2);
            if (!Strings.isNullOrEmpty(name3)) sb.append("," + name3);
            sb.append("AreaChildren");
            key = sb.toString();
        }

        List<AreaOfDic> list = null;
        try {
            Object obj = cache.get(key);
            list = (List<AreaOfDic>) obj;
        } catch (ExecutionException e) {
            throw new RuntimeException("获取缓存中的地区、注册类型等时，出现了并发问题....");
        }
        return list;
    }

    /*
        key=国家AreaChildren
        key=安徽AreaChildren/安徽,合肥市,庐江县AreaChildren
     */
    public static List<String> getAreaStrChildren(String name1, String name2, String name3) {
        List<AreaOfDic> tmp = getAreaChildren(name1, name2, name3);
        List<String> list = tmp.stream().map(AreaOfDic::getName).collect(Collectors.toList());
        return list;
    }

    /*
    key=国家AreaChildren
    key=安徽AreaChildren/安徽,合肥市,庐江县AreaChildren
 */
    public static List<String> getAreaReverseStrChildren(String name1, String name2, String name3) {
        List<String> list = Lists.newArrayList();
        List<AreaOfDic> tmp = getAreaChildren(name1, name2, name3);
        int len = tmp.size() - 1;
        for (int i = len; i > -1; i--) {
            list.add(tmp.get(i).getName());
        }
        return list;
    }

    //登记注册类型
    public static List<BaseOfDic> getRegisterTypeList() {
        List<BaseOfDic> list = null;
        try {
            Object obj = cache.get("registerType");
            list = (List<BaseOfDic>) obj;
        } catch (ExecutionException e) {
            throw new RuntimeException("获取缓存中的地区、注册类型等时，出现了并发问题....");
        }
        return list;
    }

    public static List<String> getRegisterTypeStrList() {
        List<BaseOfDic> tmp = getRegisterTypeList();
        List<String> list = tmp.stream().map(BaseOfDic::getBigName).collect(Collectors.toList());
        return list;
    }

    public static List<String> getRegisterTypeReverseStrList() {
        List<String> list = Lists.newArrayList();
        List<BaseOfDic> tmp = getRegisterTypeList();
        int len = tmp.size() - 1;
        for (int i = len; i > -1; i--) {
            list.add(tmp.get(i).getBigName());
        }
        return list;
    }

    //所属行业
    public static List<IndustryOfDic> getIndustryList() {
        List<IndustryOfDic> list = null;
        try {
            Object obj = cache.get("industry");
            list = (List<IndustryOfDic>) obj;
        } catch (ExecutionException e) {
            throw new RuntimeException("获取缓存中的地区、注册类型等时，出现了并发问题....");
        }
        return list;
    }

    public static List<String> getIndustryStrList() {
        List<IndustryOfDic> tmp = getIndustryList();
        List<String> list = tmp.stream().map(IndustryOfDic::getName).collect(Collectors.toList());
        return list;
    }

    public static List<String> getIndustryReverseStrList() {
        List<String> list = Lists.newArrayList();
        List<IndustryOfDic> tmp = getIndustryList();
        int len = tmp.size() - 1;
        for (int i = len; i > -1; i--) {
            list.add(tmp.get(i).getName());
        }
        return list;
    }

    //危害因素
    public static List<String> getDangerList() {
        List<String> list = null;
        try {
            Object obj = cache.get("danger");
            list = (List<String>) obj;
        } catch (ExecutionException e) {
            throw new RuntimeException("获取缓存中的地区、注册类型等时，出现了并发问题....");
        }
        return list;
    }

    //企业规模
    public static List<String> getEnterpriseSize() {
        List<String> list = null;
        try {
            Object obj = cache.get("enterpriseSize");
            list = (List<String>) obj;
        } catch (ExecutionException e) {
            throw new RuntimeException("获取缓存中的地区、注册类型等时，出现了并发问题....");
        }
        return list;
    }
}
