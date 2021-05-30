package com.example.my.excel.vo;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

/**
 * @author sunhw
 * @date 2021/5/29
 */
@Data
public class Hello {

    @Excel(name = "hello")
    private String hello;

    @Excel(name = "hi")
    private String hi;

}
