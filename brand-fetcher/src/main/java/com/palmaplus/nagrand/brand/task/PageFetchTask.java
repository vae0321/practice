package com.palmaplus.nagrand.brand.task;

import com.palmaplus.nagrand.brand.entity.FetchErrorRecord;
import org.apache.logging.log4j.util.Strings;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import static com.palmaplus.nagrand.brand.Fetcher.coreExecutor;
import static com.palmaplus.nagrand.brand.Fetcher.fetchExecutor;

/**
 * Created by sifan on 2016/6/30.
 */
public class PageFetchTask implements Runnable {
    private static Logger logger = LoggerFactory.getLogger(DownloadTask.class);

    private String letterLink;
    private String pageLink;
    private String brandLink;

    public PageFetchTask(String pageLink) {
        this.pageLink = pageLink;
    }

    PageFetchTask(String letterLink, String pageLink, String brandLink) {
        this.letterLink = letterLink;
        this.pageLink = pageLink;
        this.brandLink = brandLink;
    }

    @Override
    public void run() {
        try {
            Document pageDoc = Jsoup.connect(pageLink).get();
            Elements brandElements = pageDoc.select("div.c03-3-1 ul li a");

            List<String> brandLinks = brandElements.stream().map(brandElement -> brandElement.attr("href")).collect(Collectors.toList());
            if (Strings.isNotEmpty(brandLink)) {
                int brandIndex = brandLinks.lastIndexOf(brandLink);
                if (brandIndex > 0) {
                    brandLinks.removeAll(brandLinks.subList(0, brandIndex));
                }
            }
            brandLinks.forEach(brandLink -> fetchExecutor.execute(new BrandFetchTask(letterLink, pageLink, brandLink)));
        } catch (IOException e) {
            logger.warn("fetch letter page error:" + pageLink, e);
            FetchErrorRecord errorPage = new FetchErrorRecord();
            errorPage.setLink(pageLink);
            errorPage.setType(FetchErrorRecord.Type.PAGE);
            coreExecutor.execute(new PersistTask<>(errorPage));
        }
    }
}
