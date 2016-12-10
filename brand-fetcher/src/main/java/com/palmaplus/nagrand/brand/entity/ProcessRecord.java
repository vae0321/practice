package com.palmaplus.nagrand.brand.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by sifan on 2016/6/30.
 */
@Entity
@Table(name = "process_record")
public class ProcessRecord {
    @Id
    private String letter;
    private String lastFetchPage;
    private String lastFetchBrand;
    private Long lastFetchTime;

    public String getLetter() {
        return letter;
    }

    public void setLetter(String letter) {
        this.letter = letter;
    }

    public String getLastFetchPage() {
        return lastFetchPage;
    }

    public void setLastFetchPage(String page) {
        this.lastFetchPage = page;
    }

    public String getLastFetchBrand() {
        return lastFetchBrand;
    }

    public void setLastFetchBrand(String lastRead) {
        this.lastFetchBrand = lastRead;
    }

    public Long getLastFetchTime() {
        return lastFetchTime;
    }

    public void setLastFetchTime(Long lastReadTime) {
        this.lastFetchTime = lastReadTime;
    }
}
