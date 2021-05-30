package com.example.my.excel.vo;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.handler.inter.IExcelDataModel;
import cn.afterturn.easypoi.handler.inter.IExcelModel;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 信用债券
 *
 * @author sunhw
 * @date 2021/5/28
 */
@Data
public class CreditDebt implements Serializable, IExcelDataModel, IExcelModel {
    private static final long serialVersionUID = 1L;

    @Excel(name = "债券代码")
    @NotBlank(message = "债券代码不可为空")
    private String bondCode;

    /**
     * 行号
     */
    private int rowNum;

    /**
     * 错误消息
     */
    private String errorMsg;

}
