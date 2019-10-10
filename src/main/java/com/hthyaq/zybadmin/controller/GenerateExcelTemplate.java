package com.hthyaq.zybadmin.controller;

import com.hthyaq.zybadmin.common.constants.GlobalConstants;
import com.hthyaq.zybadmin.common.excle.MyExcelUtil;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.WordUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.net.URLEncoder;

//生成excel模板
@Controller
@RequestMapping("/excelTemplate")
public class GenerateExcelTemplate {
    @GetMapping("/download/{flag}")
    public void download(@PathVariable String flag, HttpServletResponse response) throws Exception {
        //截取
        int index = flag.lastIndexOf("/");
        String englishTableName = flag.substring(index + 1);
        //准备excel需要的数据
        String chineseName = getChineseName(englishTableName);
        String file = GlobalConstants.EXCEL_PATH + "/" + chineseName + ".xls";
        File tmp = new File(file);
        if (tmp.exists()) {
            tmp.delete();
        }
        //生成模板
        MyExcelUtil.writeOneSheetExcel(file, getClazz(englishTableName));

/*        String[] sheetNameArr=new String[2];
        sheetNameArr[0]="监管部门";
        sheetNameArr[1]="企业基本信息";

        Class[] classArr = new Class[2];
        classArr[0] = SuperviseModel.class;
        classArr[1] = EnterpriseModel.class;

        MyExcelUtil.writeMoreSheetExcel(file, sheetNameArr, classArr);*/
        //返回模板
        returnTemplate(file, response);
    }

    private void returnTemplate(String file, HttpServletResponse response) throws Exception {
        response.setCharacterEncoding("UTF-8");
        response.setHeader("content-type", "application/octet-stream");
        response.setContentType("application/octet-stream");
        String fileName = FilenameUtils.getName(file);
        // 下载文件能正常显示中文
        response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
        //实现下载
        byte[] buffer = new byte[2048];
        FileInputStream fis = null;
        BufferedInputStream bis = null;
        try {
            fis = new FileInputStream(file);
            bis = new BufferedInputStream(fis);
            OutputStream os = response.getOutputStream();
            int i = bis.read(buffer);
            while (i != -1) {
                os.write(buffer, 0, i);
                i = bis.read(buffer);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(bis);
            IOUtils.closeQuietly(fis);
        }
    }

    //根据flag获取中文名称
    private String getChineseName(String englishTableName) {
        //select distinct chineseTableName,englishTableName from TableMapInfo
        return "监管部门信息";
    }

    //根据类名的字符串获取class
    private Class getClazz(String classNameStr) throws ClassNotFoundException {
        //首字母大写
        String tmp = WordUtils.capitalize(classNameStr);
        //加上model
        tmp = "com.hthyaq.zybadmin.model.excelModel." + tmp + "Model";
        //根据字符串获取Class类型
        Class clazz = Class.forName(tmp);
        return clazz;
    }

}
