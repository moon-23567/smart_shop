package guat.lxy.bigdata.smart_shop.mapper;

import guat.lxy.bigdata.smart_shop.entity.Product;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import java.util.List;

public interface ProductMapper {
    @Select("SELECT p.*, c.name category_name FROM product p LEFT JOIN category c ON p.cat_id = c.id WHERE p.id = #{id}")
    Product getProductById(Integer id);
    List<Product> queryProductByCondition(@Param("catId") Integer catId,
                                          @Param("name") String name);
    int insertProduct(Product product);
    int updateProduct(Product product);
    int deleteProduct(Integer id);
}

