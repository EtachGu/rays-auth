package com.auth.mapper;

import com.auth.entity.OAuthUser;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface OAuthUserMapper extends Mapper<OAuthUser> {
    OAuthUser queryByUserName(String userName);

    List<String> queryUserPermission(String userName);
}