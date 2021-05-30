package com.example.my;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;
import cn.afterturn.easypoi.excel.entity.result.ExcelImportResult;
import com.example.my.excel.EasyPoiUtil;
import com.example.my.excel.vo.AssetDesignationRatio;
import com.example.my.excel.vo.CreditDebt;
import com.example.my.excel.vo.ExcelVo;
import com.example.my.excel.vo.IndustryInfo;
import com.example.my.file.FileTypeUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @author sunhw
 * @date 2021/5/28 使用 ExcelPoi 进行 Excel 的导出
 */
@RestController
@RequestMapping("/excel")
public class ExcelController {

    /**
     * 多 sheet 导出
     */
    @GetMapping("/download")
    public void download(HttpServletResponse response) {

        Map<String, Object> creditSheet = EasyPoiUtil.buildSheet("信用债",
                CreditDebt.class, Collections.EMPTY_LIST);

        Map<String, Object> industrySheet = EasyPoiUtil.buildSheet("行业",
                IndustryInfo.class, Collections.EMPTY_LIST);

        Map<String, Object> ratioSheet = EasyPoiUtil.buildSheet("资产指定比例",
                AssetDesignationRatio.class, Collections.EMPTY_LIST);

        List<Map<String, Object>> sheetsList = new ArrayList<>();
        sheetsList.add(creditSheet);
        sheetsList.add(industrySheet);
        sheetsList.add(ratioSheet);

        Workbook workBook = ExcelExportUtil.exportExcel(sheetsList, ExcelType.XSSF);

        EasyPoiUtil.downLoadExcel("资产类型", response, workBook);

    }


    /**
     * 解析 excel 实体类
     *
     * @param file 文件
     */
    @GetMapping("/parse")
    public String parse(@RequestParam("file") MultipartFile file) throws IOException {

        String fileType = FileTypeUtil.getFileType(file);

        if (!FileTypeUtil.isAllowedTypes(fileType)) {
            return "文件格式不支持";
        }

        List<CreditDebt> creditDebtList = EasyPoiUtil.importExcel(file,
                0, 0, 1, CreditDebt.class);
        List<IndustryInfo> industryInfoList = EasyPoiUtil.importExcel(file,
                1, 0, 1, IndustryInfo.class);
        List<AssetDesignationRatio> ratioList = EasyPoiUtil.importExcel(file,
                2, 0, 1, AssetDesignationRatio.class);

        ExcelVo excelVo = ExcelVo.builder()
                .creditDebtList(creditDebtList)
                .industryInfoList(industryInfoList)
                .rationList(ratioList)
                .build();

        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(excelVo);
    }

    /**
     * 解析 excel Map结构
     *
     * @param file 文件
     */
    @GetMapping("/parseMap")
    public String parseMap(@RequestParam("file") MultipartFile file) throws IOException {

        String fileType = FileTypeUtil.getFileType(file);

        if (!FileTypeUtil.isAllowedTypes(fileType)) {
            return "文件格式不支持";
        }

        List<List<Object>> excelInfoList = new ArrayList<>();

        Workbook workbook = EasyPoiUtil.getWorkBook(file);

        int sheetNum = workbook.getNumberOfSheets();

        for (int i = 0; i < sheetNum; i++) {
            excelInfoList.add(EasyPoiUtil.parseExcelMap(file, i, 0, 1));
        }

        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(excelInfoList);
    }

    /**
     * 校验 excel
     *
     * @param file 文件
     * @return {@link String}
     */
    @GetMapping("/check")
    public String check(@RequestParam("file") MultipartFile file) throws Exception {
        String fileType = FileTypeUtil.getFileType(file);

        if (!FileTypeUtil.isAllowedTypes(fileType)) {
            return "文件格式不支持";
        }

        ExcelImportResult<CreditDebt> result = EasyPoiUtil.checkExcel(file, 0, 0, 1, CreditDebt.class);

        ExcelImportResult<IndustryInfo> result2= EasyPoiUtil.checkExcel(file, 1, 0, 1, IndustryInfo.class);

        ObjectMapper objectMapper = new ObjectMapper();

        System.out.println("是否校验失败: " + result.isVerfiyFail());
        System.out.println("校验失败的集合:" + objectMapper.writeValueAsString(result.getFailList()));
        System.out.println("校验通过的集合:" + objectMapper.writeValueAsString(result.getList()));

        System.out.println("是否校验失败: " + result2.isVerfiyFail());
        System.out.println("校验失败的集合:" + objectMapper.writeValueAsString(result2.getFailList()));
        System.out.println("校验通过的集合:" + objectMapper.writeValueAsString(result2.getList()));

        for (CreditDebt entity : result.getFailList()) {
            String msg = "第" + entity.getRowNum() + "行的错误是：" + entity.getErrorMsg();
            System.out.println(msg);
        }

        for (IndustryInfo entity : result2.getFailList()) {
            String msg = "第" + entity.getRowNum() + "行的错误是：" + entity.getErrorMsg();
            System.out.println(msg);
        }

        return "false";
    }


}
