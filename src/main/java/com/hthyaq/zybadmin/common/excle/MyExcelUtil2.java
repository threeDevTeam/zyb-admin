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
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.utils.Lists;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;

import java.io.*;
import java.util.List;
import java.util.Map;

@Slf4j
public class MyExcelUtil2 {
    //多少个sheet
    public static int sheetLen(File file, Class<? extends BaseRowModel> modelClass) {
        int len = 0;
        List<Object> dataList = Lists.newArrayList();
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(file);
            String filename = file.getName();
            // 解析每行结果在listener中处理
            ExcelReader excelReader = new ExcelReader(inputStream, getExcelTypeEnum(filename), null, new ExcelListener(dataList));
            //headLineMun 从第二行开始读数据
            excelReader.read(new Sheet(1, 1, modelClass));
            len = excelReader.getSheets().size();
        } catch (Exception e) {
            log.error("读取Excel失败了！");
            log.error(Throwables.getStackTraceAsString(e));
        } finally {
            IOUtils.closeQuietly(inputStream);
        }
        return len;
    }

    //读取单个sheet
    public static List<Object> readOneSheetExcel(File file, Class<? extends BaseRowModel> modelClass) {
        List<Object> dataList = readOneSheetExcel(file, modelClass, 1, new SheetNamee());
        return dataList;
    }

    //读取单个sheet,sheetNo从1开始
    public static List<Object> readOneSheetExcel(File file, Class<? extends BaseRowModel> modelClass, int sheetNo, SheetNamee sheetNamee) {
        List<Object> dataList = Lists.newArrayList();
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(file);
            String filename = file.getName();
            // 解析每行结果在listener中处理
            ExcelReader excelReader = new ExcelReader(inputStream, getExcelTypeEnum(filename), null, new MyExcelUtil2.ExcelListener(dataList));
            //headLineMun 从第二行开始读数据
            excelReader.read(new Sheet(sheetNo, 1, modelClass));
            sheetNamee.setName(excelReader.getSheets().get(sheetNo - 1).getSheetName());
        } catch (Exception e) {
            log.error("读取Excel失败了！");
            log.error(Throwables.getStackTraceAsString(e));
        } finally {
            IOUtils.closeQuietly(inputStream);
        }
        return dataList;
    }

    //读取多个sheet
    public static Map<String, List<Object>> readMoreSheetExcel(File[] files, Class<? extends BaseRowModel> modelClass) {
        Map<String, List<Object>> dataMap = Maps.newHashMap();
        for (File file : files) {
            //获取文件的sheet的个数
            int len = sheetLen(file, modelClass);
            for (int i = 0; i < len; i++) {
                SheetNamee sheetNamee = new SheetNamee();
                List<Object> dataList = readOneSheetExcel(file, modelClass, i + 1, sheetNamee);
                dataMap.put(file.getName() + "$$##&&" + sheetNamee.getName(), dataList);
            }
        }
        return dataMap;
    }

    /*
    生成excel,只有一个sheet
    file=路径+文件名
 */
    public static void writeOneSheetExcel(String file, Class<? extends BaseRowModel> modelClass) {
        String pathStr = FilenameUtils.getFullPath(file);
        File path = new File(pathStr);
        if (!path.exists()) {
            path.mkdirs();
        }
        OutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(file);
            ExcelWriter writer = new ExcelWriter(outputStream, getExcelTypeEnum(file));
            List<BaseRowModel> list = Lists.newArrayList();
            writer.write(list, new Sheet(1, 0, modelClass, FilenameUtils.getBaseName(file), null)).finish();
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
    public static void writeMoreSheetExcel(String file, String[] sheetNameArr, Class<? extends BaseRowModel>[] modelClassArr) {
        OutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(file);
            ExcelWriter writer = new ExcelWriter(outputStream, getExcelTypeEnum(file), true);
            int sheetNo = 1;
            for (int i = 0; i < sheetNameArr.length; i++) {
                List<BaseRowModel> list = Lists.newArrayList();
                writer.write(list, new Sheet(sheetNo, 0, modelClassArr[i], sheetNameArr[i], null));
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
    @EqualsAndHashCode(callSuper = true)
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
