package guat.lxy.bigdata.smart_shop.service;

import guat.lxy.bigdata.smart_shop.entity.Category;
import java.util.List;

public interface CategoryService {
    List<Category> getAll();
    int add(Category category);
    int del(Integer id);
    void clearCategoryCache();
}
