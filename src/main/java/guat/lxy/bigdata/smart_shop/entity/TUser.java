package guat.lxy.bigdata.smart_shop.entity;

import lombok.Data;
import java.util.List;

@Data
public class TUser {
    private Integer id;
    private String username;
    private String password;
    private Integer active;
    private List<TRole>roles;
}
