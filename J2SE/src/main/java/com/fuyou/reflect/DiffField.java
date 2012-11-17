package com.fuyou.reflect;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author yubaofu created on  12-11-9 上午10:10
 * @version $Id$
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface DiffField {
    public String name();
    public String datePatter() default "yyyy-MM-dd hh:mm:ss";
}
