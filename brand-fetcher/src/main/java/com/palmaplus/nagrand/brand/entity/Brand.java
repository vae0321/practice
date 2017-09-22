package com.palmaplus.nagrand.brand.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

/**
 * Created by sifan on 2016/6/30.
 */
@Data
@Entity
@Table(name = "brand")
public class Brand {

    @Id
    private String id;
    //品牌名称
    private String brandName;
    //品牌LOGO
    private String logo;
    //产品分类
    @Column(columnDefinition = "TEXT")
    private String category;
    //总分
    private Integer totalPoints;
    //综合排名
    private Long rank;
    //主体介绍
    @Column(columnDefinition = "TEXT")
    private String brief;
}
