package guat.lxy.bigdata.smart_shop.mapper;

import guat.lxy.bigdata.smart_shop.entity.TUser;
import org.apache.ibatis.annotations.Select;
import guat.lxy.bigdata.smart_shop.entity.TRole;
import java.util.List;

public interface UserMapper {
    @Select("select id,username,password,active from t_user where username=#{username}")
    TUser selectUserByName(String username);
    @Select("select r.role from t_user_role ur left join t_role r on ur.role_id=r.id where ur.user_id=#{userId}")
    List<TRole> selectRolesByUserId(Integer userId);
}

