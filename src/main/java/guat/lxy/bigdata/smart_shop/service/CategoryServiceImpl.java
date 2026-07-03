package guat.lxy.bigdata.smart_shop.service;

import guat.lxy.bigdata.smart_shop.entity.Category;
import guat.lxy.bigdata.smart_shop.mapper.CategoryMapper;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Resource
    private CategoryMapper categoryMapper;

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    private static final String CATEGORY_CACHE_KEY = "category:all";

    @Override
    public List<Category> getAll() {
        List<Category> cacheList = (List<Category>) redisTemplate.opsForValue().get(CATEGORY_CACHE_KEY);
        if (cacheList != null && cacheList.size() > 0) {
            return cacheList;
        }
        List<Category> dbList = categoryMapper.getAllCategory();
        redisTemplate.opsForValue().set(CATEGORY_CACHE_KEY, dbList, 1, TimeUnit.DAYS);
        return dbList;
    }

    @Override
    public int add(Category category) {
        int rows = categoryMapper.insert(category);
        clearCategoryCache();
        return rows;
    }

    @Override
    public int del(Integer id) {
        int rows = categoryMapper.delete(id);
        clearCategoryCache();
        return rows;
    }

    @Override
    public void clearCategoryCache() {
        redisTemplate.delete(CATEGORY_CACHE_KEY);
    }
}
