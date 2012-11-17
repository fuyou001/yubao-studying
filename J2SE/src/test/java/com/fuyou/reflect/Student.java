/*
 * Copyright (c) 2012 Qunar.com. All Rights Reserved.
 */
package com.fuyou.reflect;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author yubaofu created on  12-11-9 上午10:05
 * @version $Id$
 */
public class Student {
    private int age;
    private String name2;
    private double score;
    private boolean isChinese;
    @DiffField(name = "金钱")
    private BigDecimal bigDecimal;
    private int[] arrays;
    @DiffField(name = "注册日期")
    private Date date;

    public Student() {
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName2() {
        return name2;
    }

    public void setName2(String name2) {
        this.name2 = name2;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public boolean isChinese() {
        return isChinese;
    }

    public void setChinese(boolean chinese) {
        isChinese = chinese;
    }

    public BigDecimal getBigDecimal() {
        return bigDecimal;
    }

    public void setBigDecimal(BigDecimal bigDecimal) {
        this.bigDecimal = bigDecimal;
    }

    public int[] getArrays() {
        return arrays;
    }

    public void setArrays(int[] arrays) {
        this.arrays = arrays;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
