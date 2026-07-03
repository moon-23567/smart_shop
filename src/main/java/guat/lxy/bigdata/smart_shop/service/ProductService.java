package guat.lxy.bigdata.smart_shop.service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import guat.lxy.bigdata.smart_shop.entity.Product;
import guat.lxy.bigdata.smart_shop.mapper.ProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.*;
import org.springframework.stereotype.Service;
import java.util.stream.Collectors;
import java.util.List;

@Service
public class ProductService {
    @Autowired
    private ProductMapper productMapper;

    // 分页查询（移除Redis手动缓存，仅使用注解缓存）
    public PageInfo<Product> pageProduct(Integer pageNum, Integer pageSize, Integer catId, String name, Double minPrice, Double maxPrice){
        // 先不分页，查询符合名称、分类的全部数据
        List<Product> allList = productMapper.queryProductByCondition(catId,name);
        // 打印数据库原始结果
        System.out.println("数据库查出总条数：" + allList.size());
        System.out.println("原始数据：" + allList);

        // Java过滤价格，原有过滤逻辑不动
        if(minPrice != null){
            allList = allList.stream().filter(p -> p.getPrice() >= minPrice).collect(Collectors.toList());
        }
        if(maxPrice != null){
            allList = allList.stream().filter(p -> p.getPrice() <= maxPrice).collect(Collectors.toList());
        }
        // 打印过滤后剩余条数
        System.out.println("价格过滤后剩余条数：" + allList.size());

        // 使用PageHelper自动分页（一次性返回全部数据，一页展示所有商品）
        PageInfo<Product> pageInfo = new PageInfo<>(allList);
        pageInfo.setPageNum(pageNum);
        pageInfo.setPageSize(pageSize);
        pageInfo.setTotal(allList.size());

        return pageInfo;
    }

    // 单商品本地缓存
    @Cacheable(value = "product",key = "#id")
    public Product getById(Integer id){
        System.out.println("执行SQL查询商品id:"+id);
        return productMapper.getProductById(id);
    }

    // 更新时刷新缓存
    @CachePut(value = "product",key = "#product.id")
    public Product update(Product product){
        productMapper.updateProduct(product);
        return product;
    }

    // 删除时清除缓存
    @CacheEvict(value = "product",key = "#id", allEntries = true)
    public void delete(Integer id){
        productMapper.deleteProduct(id);
    }

    // 新增清空全部商品缓存
    @CacheEvict(value = "product", allEntries = true)
    public int add(Product product){
        return productMapper.insertProduct(product);
    }
}
