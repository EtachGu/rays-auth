# OAuth Server 

基于Spring Security OAuth2.0提供授权与认证中心服务



## 总体架构图


## API

接口名称 | HTTP方法 | 说明 
-------- | -------- | -------- 
/oauth/token | | 获取访问令牌  (the token endpoint)
/oauth/authorize | | 授权 (the authorization endpoint)
/oauth/check_token | |校验Token  (used by Resource Servers to decode access tokens)
/oauth/token_key | |获取 JWT token


## 页面

路由 | HTTP方法 | 说明 
-------- | -------- | -------- 
/oauth/confirm_access |GET |确认访问 approval (user posts approval for grants here)
/oauth/error |GET | 失败的页面 (used to render errors in the authorization server)


## 模块

序号 | 接口 | 方法 | 说明 | 备注
---  | ---  | ---  | ---  | ----
1  | /oauth/token | POST | 获取访问令牌 | 
2  | /oauth/authorize | GET | 授权 | 
3  | /oauth/check_token | GET/POST | 校验Token，获取token信息 | 
4  | /rest/access_tokens | GET | 获取所有访问令牌 | 
5  | /rest/revoke_token/{token} | POST | 吊销访问令牌 | 
6  | /rest/revoke_refresh_token/{token} | POST | 吊销刷新令牌 | 
7  | /rest/clients/{client_id} | GET/DELETE/PUT | 查询，删除，修改客户端 | 
8  | /rest/clients | POST | 创建客户端 | 
9  | /rest/users/{username} | GET/DELETE/PUT | 查询，删除，修改用户 | 
10  | /rest/users | POST | 创建用户 | 






## 参考

[1] http://projects.spring.io/spring-security-oauth/docs/oauth2.html 