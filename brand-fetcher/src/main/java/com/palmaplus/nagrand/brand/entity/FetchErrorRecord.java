package com.palmaplus.nagrand.brand.entity;

import javax.persistence.*;
import lombok.Data;

/**
 * Created by sifan on 2016/6/30.
 */
@Data
@Entity
@Table(name = "fetch_error_record")
public class FetchErrorRecord {
    public enum Type {LETTER, PAGE, BRAND}

    @Id
    private String link;
    @Enumerated(EnumType.STRING)
    private Type type;
}
