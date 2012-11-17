/*
 * Copyright (c) 2012 Qunar.com. All Rights Reserved.
 */
package com.fuyou.reflect;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author yubaofu created on  12-11-9 上午10:18
 * @version $Id$
 */
public class DiffTest {

    public static void main(String[] args) throws Exception {
        Student oldSt = new Student();
        oldSt.setAge(10);
        oldSt.setBigDecimal(new BigDecimal("120.00"));
        oldSt.setChinese(true);
        oldSt.setName2("fuyou");
        oldSt.setScore(30f);
        oldSt.setDate(new Date());
        oldSt.setArrays(new int[]{10,20});

        Student newSt = new Student();
        newSt.setAge(11);
        newSt.setBigDecimal(new BigDecimal("1200.000"));
        newSt.setChinese(false);
        newSt.setName2("fuyou25");
        newSt.setScore(30f);
        newSt.setDate(new Date());
        System.out.println(DiffUtil.diff(oldSt, newSt));


    }
}
