package com.palmaplus.nagrand.brand.entity;

import javax.persistence.*;

/**
 * Created by sifan on 2016/6/30.
 */
@Entity
@Table(name = "fetch_error_record")
public class FetchErrorRecord {
    public enum Type {LETTER, PAGE, BRAND}

    @Id
    private String link;
    @Enumerated(EnumType.STRING)
    private Type type;

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }
}
