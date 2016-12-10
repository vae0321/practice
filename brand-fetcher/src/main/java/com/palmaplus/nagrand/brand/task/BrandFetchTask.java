package com.palmaplus.nagrand.brand.task;

import com.palmaplus.nagrand.brand.entity.Brand;
import com.palmaplus.nagrand.brand.entity.FetchErrorRecord;
import com.palmaplus.nagrand.brand.entity.ProcessRecord;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

import static com.palmaplus.nagrand.brand.Fetcher.coreExecutor;

/**
 * Created by sifan on 2016/6/30.
 */
public class BrandFetchTask implements Runnable {
    private static Logger logger = LoggerFactory.getLogger(BrandFetchTask.class);

    private String letterLink;
    private String pageLink;
    private String brandLink;

    BrandFetchTask(String brandLink) {
        this.brandLink = brandLink;
    }

    BrandFetchTask(String letterLink, String pageLink, String brandLink) {
        this.letterLink = letterLink;
        this.pageLink = pageLink;
        this.brandLink = brandLink;
    }

    @Override
    public void run() {
        try {
            Document brandDoc = Jsoup.connect(brandLink).get();
            Elements logo = brandDoc.select("div.c02-1-2-1 p img");
            String logoUrl = logo.attr("src");
            String logoName = logo.attr("title").trim().replace("品牌标志LOGO", "");
            coreExecutor.execute(new DownloadTask(logoUrl, logoName));

            String[] split = brandDoc.select("div.c02-1-2-2 div.a2 div.a2-1 p").text().split(" ");
            String point = split[0];
            int totalPoint = Integer.valueOf(point.substring(point.indexOf("：") + 1, point.lastIndexOf("分")));
            String rankSplit = split[1];
            long rank = Long.valueOf(rankSplit.substring(rankSplit.indexOf("第") + 1, rankSplit.lastIndexOf("位")));
            String brandName = brandDoc.select("div.c02-1-2-1 p").get(1).text();

            Brand brand = new Brand();
            brand.setId(brandLink.substring(brandLink.lastIndexOf("/") + 1));
            brand.setBrandName(brandName);
            brand.setLogo(logoName);
            brand.setTotalPoints(totalPoint);
            brand.setRank(rank);
            brand.setBrief(brandDoc.select("div.cpxl p.t").text());
            brand.setCategory(brandDoc.select("div.c02-1-3 span a").text().replace(" ", ","));
            coreExecutor.execute(new PersistTask<>(brand));

            ProcessRecord record = new ProcessRecord();
            record.setLastFetchTime(System.currentTimeMillis());
            record.setLastFetchBrand(brandLink);
            record.setLastFetchPage(pageLink);
            record.setLetter(letterLink);
            coreExecutor.execute(new PersistTask<>(record));

            logger.info("fetched brand:" + brand.toString());
        } catch (IOException e) {
            logger.warn("fetch brand link error: " + brandLink, e);
            FetchErrorRecord errorBrand = new FetchErrorRecord();
            errorBrand.setLink(brandLink);
            errorBrand.setType(FetchErrorRecord.Type.BRAND);
            coreExecutor.execute(new PersistTask<>(errorBrand));
        }
    }
}
