### 特性
1. 在super-diamond的基础上做了一些修改，bug修复, 如换行问题，更新时内存飙高问题。
2. 使用mybatis框架替换原有写死的sql操作，解决数据库表大小写敏感的问题
3. 优化后台界面体验，样式更加大气。
4. 增加配置修改历史记录功能，可查看和恢复到某个历史版本
5. 在修改配置项时不自动推送客户端， 增加一个发布按钮，在所有配置修改完成并确认后再点击该按钮推送变更到客户端。

#### 部署和使用指南
https://github.com/melin/super-diamond



# 配置中心服务列表

## 接口名称：编辑服务

**请求地址(POST)：**/tuhu_configuration_center/edit
**请求报文示例：**{"type":"development","projCode":"cashier-core","moduleName":"dimensionDef","configKey":"test","oldConfigKey":"test","configValue":"testttt","configDesc":"test","userCode":"quyinjun","md5Passwd":"25d55ad283aa400af464c76d713c07ad"}
**请求参数说明：**

```
type:默认development, 环境指定
projCode:项目编码
moduleName:模块名称
configKey:配置的key
oldConfigKey:原有的配置key
configValue:配置的值 
configDesc:配置描述
userCode:登陆用户名(此用户必须经过daimond后台进行正确的授权才能使用)
md5Passwd:登陆用户对应的密码的md5值
```

说明：

​	configKey不为空，oldConfigKey为空，则新增config

​	configKey为空，oldConfigKey不为空，则删除oldConfig

​	configKey==oldConfigKey,为更新

​	configKey！=oldConfigKey，删除oldConfig, 增加config

**响应参数：**(只有code==SUCCESS时业务处理正常，其它都为异常)

```
{
	code: "SUCCESS"
	msg: "OK"
}
```

响应编码：

```
SUCCESS("OK"),
FAIL("业务处理异常"),
NOT_FOUND_USER("用户不存在"),
ERROR_PASSWORD("密码校验不通过"),
NOT_FOUND_PROJECT("项目不存在"),
NOT_FOUND_MODULE("模块不存在"),
MUTIPLE_CONFIG("找到了多条配置"),
NOT_FOUND_CONFIG("未找到相应配置"),
CANNOT_ADD_SAME_CONFIG("不允许新增同样配置"),
NOT_FOUND_OLD_CONFIG("没有找到老的配置"),
MUTIPLE_OLD_CONFIG("找到了多条老配置"),
DO_NOTHING("没有做任何事情");
```

说明：调用配置中心编辑服务后，必须由daimond后台进行审核并发布才让项目实时能起作用。