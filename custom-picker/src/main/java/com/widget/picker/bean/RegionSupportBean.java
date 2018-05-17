package com.widget.picker.bean;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Description:地区数据实体
 * Created by Kevin.Li on 2018-01-10.
 */
public class RegionSupportBean {
    public int id;
    public String code;
    public String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setId(String id) {
        if (id != null && isInteger(id)) {
            setId(Integer.parseInt(id));
        } else {
            throw new RuntimeException("ID must be a number.");
        }
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /***
     * 判断 String 是否是 int
     *
     * @param input
     * @return
     */
    private boolean isInteger(String input) {
        Matcher mer = Pattern.compile("^[+-]?[0-9]+$").matcher(input);
        return mer.find();
    }
}
