package com.hthyaq.zybadmin.controller;

import com.google.common.collect.Lists;
import com.hthyaq.zybadmin.common.constants.GlobalConstants;
import com.hthyaq.zybadmin.common.excle.MyExcelUtil;
import com.hthyaq.zybadmin.model.excelModel.SuperviseModel;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.List;

//生成excel模板
@Controller
@RequestMapping("/excelTemplate")
public class GenerateExcelTemplate {
    @GetMapping("/download")
    public void download(HttpServletResponse response) throws Exception {
        System.out.println("aa");
        List<SuperviseModel> dataList = Lists.newArrayList();
        String file = GlobalConstants.EXCEL_PATH + "/" + "Supervise.xls";
        File tmp = new File(file);
        if (!tmp.exists()) {
            //生成模板
            MyExcelUtil.writeOneSheetExcel(file, dataList, SuperviseModel.class);
        }
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
}
