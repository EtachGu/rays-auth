package com.auth.mapper;

import com.auth.entity.OAuthUser;
import tk.mybatis.mapper.common.Mapper;

public interface OAuthUserMapper extends Mapper<OAuthUser> {
    OAuthUser queryByUserName(String userName);
}