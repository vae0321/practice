package com.palmaplus.nagrand.brand.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

/**
 * Created by sifan on 2016/6/30.
 */
@Data
@Entity
@Table(name = "process_record")
public class ProcessRecord {
    @Id
    private String letter;
    private String lastFetchPage;
    private String lastFetchBrand;
    private Long lastFetchTime;
}
