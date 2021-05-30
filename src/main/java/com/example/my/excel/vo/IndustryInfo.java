package com.example.my.excel.vo;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.handler.inter.IExcelDataModel;
import cn.afterturn.easypoi.handler.inter.IExcelModel;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

/**
 * 行业信息
 *
 * @author sunhw
 * @date 2021/5/28
 */
@Data
public class IndustryInfo implements Serializable , IExcelDataModel, IExcelModel {
	private static final long serialVersionUID = 1L;

	@Excel(name = "行业")
	@NotBlank(message = "行业名称不可为空")
	private String industry;

	@Excel(name = "行业整体评级")
	@NotBlank(message = "行业整体评级不可为空")
	@Pattern(regexp = "^看好|看差$")
	private String industryGood;

	/**
	 * 行号
	 */
	private int rowNum;

	/**
	 * 错误消息
	 */
	private String errorMsg;

}
