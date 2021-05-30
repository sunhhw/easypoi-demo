package com.example.my.excel.vo;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * @author sunhw
 * @date 2021/5/29
 */
@Data
@Builder
public class ExcelVo {

    private List<CreditDebt> creditDebtList;

    private List<IndustryInfo> industryInfoList;

    private List<AssetDesignationRatio> rationList;

    private List<Hello> heldList;

}
