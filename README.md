# OAuth Server 

基于Spring Security OAuth2.0提供授权与认证中心服务



# 总体架构图


# API

接口名称 | HTTP方法 | 说明 
-------- | -------- | -------- 
/oauth/token | | 获取访问令牌  (the token endpoint)
/oauth/authorize | | 授权 (the authorization endpoint)
/oauth/check_token | |校验Token  (used by Resource Servers to decode access tokens)
/oauth/token_key | |获取 JWT token

# 页面

路由 | HTTP方法 | 说明 
-------- | -------- | -------- 
/oauth/confirm_access |GET |确认访问 approval (user posts approval for grants here)
/oauth/error |GET | 失败的页面 (used to render errors in the authorization server)



# 参考

[1] http://projects.spring.io/spring-security-oauth/docs/oauth2.html 