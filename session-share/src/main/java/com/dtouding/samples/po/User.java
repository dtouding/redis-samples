package com.dtouding.samples.po;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class User {

    private Integer id;

    private String username;

    private String password;

    private String email;

    private String phone;

    private Date createTime;

    private Date updateTime;

}
