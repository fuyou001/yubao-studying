/*
 * Copyright (c) 2012 Qunar.com. All Rights Reserved.
 */
package com.fuyou.reflect;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;

/**
 * @author yubaofu created on  12-11-9 上午10:11
 * @version $Id$
 */
public class DiffUtil {
    private static final Log logger = LogFactory.getLog(DiffUtil.class);
    private static final String separator = " ";

    public static String diff(Object oldObj, Object newObj) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, ParseException {
        StringBuilder sb = new StringBuilder();
        if (oldObj == null || newObj == null)
            return sb.toString();
        java.lang.reflect.Field[] oldFields = oldObj.getClass().getDeclaredFields();
        Method[] oldMethods = oldObj.getClass().getDeclaredMethods();
        Method[] newMethods = oldObj.getClass().getDeclaredMethods();
        for (java.lang.reflect.Field field : oldFields) {
            Disable disable = field.getAnnotation(Disable.class);
            if (disable != null) continue;
            String filedName = field.getName();
            Class filedClass = field.getType();

            Method oldCurrentMethod = getMethodByName(oldMethods, filedName);
            Method newCurrentMethod = getMethodByName(newMethods, filedName);
            if (oldCurrentMethod == null || newCurrentMethod == null)
                continue;
            oldCurrentMethod.setAccessible(true);
            newCurrentMethod.setAccessible(true);

            Object oldValue = oldCurrentMethod.invoke(oldObj, null);
            Object newValue = newCurrentMethod.invoke(newObj, null);

            if (logger.isDebugEnabled()) {
                logger.debug("filedName:" + filedName + " filedClass:" + filedClass + " method:" + oldCurrentMethod.getName()
                        + "valueClass:" + (oldValue == null ? null : oldValue.getClass().getName())
                        + "oldValue:" + oldValue + " newValue:" + newValue);
            }

            StringBuilder diffValueLog = diffValueLog(oldValue, newValue, field);
            if (StringUtils.isNotEmpty(diffValueLog)) {
                if (sb.length() > 1) sb.append(System.getProperty("line.separator"));
                sb.append(diffValueLog);
            }

        }



        return sb.toString();
    }

    private static StringBuilder diffValueLog(Object oldValue, Object newValue, Field field) throws ParseException {
        StringBuilder sb = new StringBuilder();
        if (oldValue == newValue) {
            return sb;
        }
        if (oldValue != null && oldValue.getClass().getName().contains("[")) { //数组先忽略
            return sb;
        }
        String filedName = field.getName();
        DiffField displayDiffField = field.getAnnotation(DiffField.class);
        String name = displayDiffField == null ? filedName : displayDiffField.name();
        if (oldValue == null || newValue == null) {
            sb.append(name + ":" + format(oldValue, field) + "改为" + format(newValue, field));
        }else if (!equals(oldValue, newValue, field)) {
            sb.append(name + ":" + format(oldValue, field) + "改为" + format(newValue, field));
        }
        return sb;
    }

    private static String format(Object value, Field field) {
        if(value==null) return separator;
        if(value instanceof  Date){
            String pattern = field.getAnnotation(DiffField.class).datePatter();
            return DateFormatUtils.format((Date) value, pattern);
        }
        return value.toString();
    }

    private static boolean equals(Object oldValue, Object newValue, Field field) throws ParseException {
        if (oldValue instanceof BigDecimal && newValue instanceof BigDecimal) {
            BigDecimal oldBigDecimal = (BigDecimal) oldValue;
            BigDecimal newBigDecimal = (BigDecimal) oldValue;
            oldBigDecimal.setScale(4).equals(newBigDecimal.setScale(4));
        }

        if (oldValue instanceof Date && newValue instanceof Date) {
            String pattern = field.getAnnotation(DiffField.class).datePatter();
            Date oldDate = DateUtils.parseDate(DateFormatUtils.format((Date) oldValue, pattern), pattern);
            Date newDate = DateUtils.parseDate(DateFormatUtils.format((Date) newValue, pattern), pattern);
            return oldDate.equals(newDate);
        }
        return oldValue.equals(newValue);
    }

    private static Method getMethodByName(Method[] oldMethods, String filedName) {
        String methodName = filedName.substring(0, 1).toUpperCase() + filedName.substring(1);
        for (Method method : oldMethods) {
            if (method.getName().equals("get" + methodName) || method.getName().equals("is" + methodName)) {
                return method;
            }
        }
        return null;
    }

}
