package guat.lxy.bigdata.smart_shop.controller;

import guat.lxy.bigdata.smart_shop.entity.Category;
import guat.lxy.bigdata.smart_shop.service.CategoryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import javax.annotation.Resource;
import java.util.List;

@Controller
@RequestMapping("/category")
public class CategoryController {
    @Resource
    private CategoryService categoryService;

    @GetMapping("/list")
    public String list(Model model){
        List<Category> list = categoryService.getAll();
        model.addAttribute("catList",list);
        return "category/list";
    }

    @PostMapping("/add")
    public String add(Category category){
        categoryService.add(category);
        // 新增一行：清空分类Redis缓存
        categoryService.clearCategoryCache();
        return "redirect:/category/list";
    }

    @PostMapping("/del")
    public String del(@RequestParam Integer id){
        categoryService.del(id);
        // 新增一行：清空分类Redis缓存
        categoryService.clearCategoryCache();
        return "redirect:/category/list";
    }
}

