package guat.lxy.bigdata.smart_shop.controller;

import guat.lxy.bigdata.smart_shop.dto.SearchDTO;
import com.github.pagehelper.PageInfo;
import guat.lxy.bigdata.smart_shop.entity.Category;
import guat.lxy.bigdata.smart_shop.entity.Product;
import guat.lxy.bigdata.smart_shop.service.CategoryService;
import guat.lxy.bigdata.smart_shop.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@Controller
@RequestMapping("/product")
public class ProductController {

    @Resource
    private ProductService productService;

    @Resource
    private CategoryService categoryService;

    // 商品列表分页+搜索
    @GetMapping("/list")
    public String list(Model model,
                       @RequestParam(defaultValue = "1") Integer pageNum,
                       @RequestParam(defaultValue = "5") Integer pageSize,
                       Integer catId, String name, Double minPrice, Double maxPrice){
        PageInfo<Product> pageInfo = productService.pageProduct(pageNum, pageSize, catId, name, minPrice, maxPrice);
        List<Category> catList = categoryService.getAll();
        model.addAttribute("page", pageInfo);
        model.addAttribute("catList", catList);
        SearchDTO search = new SearchDTO();
        search.setCategoryId(catId);
        search.setName(name);
        search.setMinPrice(minPrice);
        search.setMaxPrice(maxPrice);
        model.addAttribute("search", search);
        return "product/list";
    }

    // 跳转新增商品页面（修复点：传入空Product实体+分类列表）
    @GetMapping("/add")
    public String addPage(Model model){
        // 给下拉框加载所有分类
        model.addAttribute("catList", categoryService.getAll());
        // 关键：存入空Product，模板th:field绑定不会报错
        model.addAttribute("product", new Product());
        return "product/add";
    }

    // 提交新增商品
    @PostMapping("/add")
    public String add(Product product){
        productService.add(product);
        return "redirect:/product/list";
    }

    // 跳转编辑商品页面
    @GetMapping("/edit")
    public String editPage(@RequestParam Integer id, Model model){
        Product p = productService.getById(id);
        model.addAttribute("product", p);
        model.addAttribute("catList", categoryService.getAll());
        return "product/edit";
    }

    // 提交编辑商品
    @PostMapping("/edit")
    public String edit(Product product){
        productService.update(product);
        return "redirect:/product/list";
    }

    // 删除商品
    @GetMapping("/del")
    public String del(@RequestParam Integer id) {
        productService.delete(id);
        return "redirect:/product/list";
    }
}
