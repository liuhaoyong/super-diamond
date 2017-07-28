package com.github.diamond.facade.common;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public abstract class Printable {

    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }

    public String buildMDCKey() {
        return "";
    }

    public static String toJSONString(Object o) {
        return ToStringBuilder.reflectionToString(o, ToStringStyle.JSON_STYLE);
    }

}
