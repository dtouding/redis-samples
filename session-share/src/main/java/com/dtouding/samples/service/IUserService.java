package com.dtouding.samples.service;

import com.dtouding.samples.common.RsResponse;
import com.dtouding.samples.po.User;

public interface IUserService {

    RsResponse<User> login(String username, String password);

}
