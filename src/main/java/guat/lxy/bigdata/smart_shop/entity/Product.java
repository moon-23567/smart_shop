package guat.lxy.bigdata.smart_shop.entity;

import lombok.Data;
import guat.lxy.bigdata.smart_shop.entity.Category;
import java.time.LocalDate;
import java.util.Date;
import java.io.Serializable;

@Data
public class Product implements Serializable {
    private Integer id;
    private String name;
    private Double price;
    private String descp;
    private Integer catId;
    private String categoryName;
    private LocalDate releaseDate;
    private Category category;
    private String imgUrl;
    private static final long serialVersionUID = 1L;
}
