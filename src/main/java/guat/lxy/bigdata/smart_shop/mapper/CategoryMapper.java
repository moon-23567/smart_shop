package guat.lxy.bigdata.smart_shop.mapper;

import guat.lxy.bigdata.smart_shop.entity.Category;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import java.util.List;

public interface CategoryMapper {
    @Select("SELECT * FROM category")
    List<Category> getAllCategory();
    // 新增注解，匹配实体字段 id,name,descp
    @Insert("INSERT INTO category(name,descp) VALUES(#{name},#{descp})")
    int insert(Category category);
    @Delete("DELETE FROM category WHERE id = #{id}")
    int delete(Integer id);
}


