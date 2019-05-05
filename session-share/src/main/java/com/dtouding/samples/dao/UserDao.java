package com.dtouding.samples.dao;

import com.dtouding.samples.po.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserDao {

    @Select("select t.id, t.username, t.`password`, t.email, t.phone, t.create_time createTime, t.update_time updateTime from t_user t" +
            " where t.username=#{username}")
    User getUser(@Param("username") String username);

}
