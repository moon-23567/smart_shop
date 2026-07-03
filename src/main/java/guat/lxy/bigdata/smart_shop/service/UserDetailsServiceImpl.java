package guat.lxy.bigdata.smart_shop.service;

import guat.lxy.bigdata.smart_shop.entity.TRole;
import guat.lxy.bigdata.smart_shop.entity.TUser;
import guat.lxy.bigdata.smart_shop.mapper.UserMapper;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Resource
    private UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 1.单独查用户基础信息
        TUser user = userMapper.selectUserByName(username);
        if (user == null) {
            throw new UsernameNotFoundException("账号不存在");
        }
        // 2.根据用户id单独查询角色集合
        List<TRole> roleList = userMapper.selectRolesByUserId(user.getId());
        user.setRoles(roleList);

        // 控制台打印角色，用于调试
        System.out.println("当前登录用户角色集合：" + roleList);

        List<GrantedAuthority> authList = new ArrayList<>();
        if (roleList != null) {
            roleList.forEach(role -> authList.add(new SimpleGrantedAuthority(role.getRole())));
        }
        return new User(user.getUsername(), user.getPassword(), authList);
    }
}
