package guat.lxy.bigdata.smart_shop.entity;

import lombok.Data;
import guat.lxy.bigdata.smart_shop.entity.Category;
import java.time.LocalDate;
import java.util.Date;

@Data
public class Product {
    private Integer id;
    private String name;
    private Double price;
    private String descp;
    private Integer catId;
    private String categoryName;
    private LocalDate releaseDate;
    private Category category;
    private String imgUrl;
}
