package com.cq.wh.nettystudy.msgpack;

import org.msgpack.annotation.Message;

/**
 * @Auther: wh
 * @Date: 2020/1/12 14:52
 * @Description:
 */
@Message
public class UserInfo {

    private String name;

    private Integer age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
