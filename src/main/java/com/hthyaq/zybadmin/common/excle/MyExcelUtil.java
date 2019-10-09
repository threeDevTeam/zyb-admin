package com.hthyaq.zybadmin.common.excle;

import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.metadata.BaseRowModel;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.google.common.base.Throwables;
import com.google.common.collect.Maps;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.utils.Lists;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
public class MyExcelUtil {
    //读取单个sheet
    public static List<Object> readOneSheetExcel(MultipartFile[] files, Class<? extends BaseRowModel> modelClass) {
        List<Object> dataList = Lists.newArrayList();
        InputStream inputStream = null;
        try {
            inputStream = files[0].getInputStream();
            String filename = files[0].getOriginalFilename();
            // 解析每行结果在listener中处理
            ExcelReader excelReader = new ExcelReader(inputStream, getExcelTypeEnum(filename), null, new ExcelListener(dataList));
            //headLineMun 从第二行开始读数据
            excelReader.read(new Sheet(1, 1, modelClass));
        } catch (Exception e) {
            log.error("读取Excel失败了！");
            log.error(Throwables.getStackTraceAsString(e));
        } finally {
            IOUtils.closeQuietly(inputStream);
        }
        return dataList;
    }

    //读取多个sheet
    public static Map<String, List<Object>> readMoreSheetExcel(MultipartFile[] files, Class<? extends BaseRowModel> modelClass) {
        Map<String, List<Object>> dataMap = Maps.newTreeMap();
        List<Object> dataList = Lists.newArrayList();
        InputStream inputStream = null;
        try {
            inputStream = files[0].getInputStream();
            String filename = files[0].getOriginalFilename();
            // 解析每行结果在listener中处理
            ExcelReader excelReader = new ExcelReader(inputStream, getExcelTypeEnum(filename), null, new ExcelListener(dataList));
            List<Sheet> sheets = excelReader.getSheets();
            for (int i = 0; i < sheets.size(); i++) {
                Sheet sheet = sheets.get(i);
                sheet.setClazz(modelClass);
                sheet.setHeadLineMun(1);
                excelReader.read(sheet);
                //
                dataMap.put(sheet.getSheetName(), new ArrayList<>(dataList));
                //清空dataList
                dataList.clear();
            }
        } catch (Exception e) {
            log.error("读取Excel失败了！");
            log.error(Throwables.getStackTraceAsString(e));
        } finally {
            IOUtils.closeQuietly(inputStream);
        }
        return dataMap;
    }

    /*
    生成excel,只有一个sheet
    file=路径+文件名
 */
    public static void writeOneSheetExcel(String file, List<? extends BaseRowModel> dataList, Class<? extends BaseRowModel> modelClass) {
        String pathStr = FilenameUtils.getFullPath(file);
        File path = new File(pathStr);
        if (!path.exists()) {
            path.mkdirs();
        }
        OutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(file);
            ExcelWriter writer = new ExcelWriter(outputStream, getExcelTypeEnum(file));
            writer.write(dataList, new Sheet(1, 0, modelClass, FilenameUtils.getBaseName(file), null)).finish();
        } catch (Exception e) {
            log.error("生成Excel失败了！");
            log.error(Throwables.getStackTraceAsString(e));
        } finally {
            IOUtils.closeQuietly(outputStream);
        }
    }

    /*
    生成excel,多个sheet
    file=路径+文件名
    map
        key-sheet的名字
        value-数据
    */
    public static void writeMoreSheetExcel(String file, Map<String, List<? extends BaseRowModel>> map, Class modelClass) {
        OutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(file);
            ExcelWriter writer = new ExcelWriter(outputStream, getExcelTypeEnum(file), true);
            int sheetNo = 1;
            for (String sheetName : map.keySet()) {
                writer.write(map.get(sheetName), new Sheet(sheetNo, 0, modelClass, sheetName, null));
                sheetNo++;
            }
            writer.finish();
        } catch (Exception e) {
            log.error("生成Excel失败了！");
            log.error(Throwables.getStackTraceAsString(e));
        }
    }

    //根据文件获取excel的后缀
    private static ExcelTypeEnum getExcelTypeEnum(String filename) {
        String suffix = FilenameUtils.getExtension(filename);
        if ("xlsx".equals(suffix)) {
            return ExcelTypeEnum.XLSX;
        }
        return ExcelTypeEnum.XLS;
    }


    // 模型 解析监听器
    @Data
    private static class ExcelListener extends AnalysisEventListener {
        private List<Object> dataList;

        ExcelListener(List<Object> dataList) {
            this.dataList = dataList;
        }

        @Override
        public void invoke(Object object, AnalysisContext context) {
            dataList.add(object);
        }

        @Override
        public void doAfterAllAnalysed(AnalysisContext context) {
        }

    }

}
