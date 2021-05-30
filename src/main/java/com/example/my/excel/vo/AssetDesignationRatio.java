package com.example.my.excel.vo;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

/**
 * 资产指定比率
 *
 * @author sunhw
 * @date 2021/5/28
 */
@Data
public class AssetDesignationRatio {

	@Excel(name = "资产类别")
	private String assetType;

	@Excel(name = "资产代码")
	private String assetCode;

	@Excel(name = "指定比例%")
	private String designationRatio;

}
