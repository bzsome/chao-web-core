# cydy-core

#### 介绍
Web继承框架，免配置

### 1.web-security-shiro
  权限控制框架，可以无需任何配置。只需要实现方法：
  
    @Service
    public class LoginServiceImpl extends JwtService {
        @Override
        public ShiroUser getUserByName(String username) {
            //从数据库中根据用户名查询用户，并返回全新等信息
            return getByUsername(username);
        }
     }
     
  可选的token令牌管理过期。只需重写方法： 
  
    @Service
    public class LoginServiceImpl extends JwtService {
        @Override
        public void saveCache(String username, String token) {
           // 保存至缓存or数据库
        }
    
        @Override
        public boolean verifyByCache(String token) {
           // 从缓存or数据库中验证token
        }
    
        @Override
        public void removeByCache(String token) {
           // 从缓存or数据库中删除token
        }
     }
   