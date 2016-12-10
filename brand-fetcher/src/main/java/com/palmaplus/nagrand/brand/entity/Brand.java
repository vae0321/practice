package com.palmaplus.nagrand.brand.entity;

/**
 * Created by sifan on 2016/6/30.
 */
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Integer getTotalPoints() {
        return totalPoints;
    }

    public void setTotalPoints(Integer totalPoints) {
        this.totalPoints = totalPoints;
    }

    public Long getRank() {
        return rank;
    }

    public void setRank(Long rank) {
        this.rank = rank;
    }

    public String getBrief() {
        return brief;
    }

    public void setBrief(String brief) {
        this.brief = brief;
    }

    @Override
    public String toString() {
        return "Brand{" +
                "id=" + id +
                ", brandName='" + brandName + '\'' +
                ", logo='" + logo + '\'' +
                ", category='" + category + '\'' +
                ", totalPoints=" + totalPoints +
                ", rank=" + rank +
                ", brief='" + brief + '\'' +
                '}';
    }
}
