package com.palmaplus.nagrand.brand.task;

import com.palmaplus.nagrand.brand.Fetcher;
import com.palmaplus.nagrand.brand.entity.FetchErrorRecord;
import org.apache.logging.log4j.util.Strings;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.palmaplus.nagrand.brand.Fetcher.fetchExecutor;

/**
 * Created by sifan on 2016/6/30.
 */
public class LetterFetchTask implements Runnable {
    private static Logger logger = LoggerFactory.getLogger(LetterFetchTask.class);

    private String letterLink;
    private String pageLink;
    private String brandLink;

    public LetterFetchTask(String letterLink) {
        this.letterLink = letterLink;
    }

    public LetterFetchTask(String letterLink, String pageLink, String brandLink) {
        this.letterLink = letterLink;
        this.pageLink = pageLink;
        this.brandLink = brandLink;
    }

    @Override
    public void run() {
        try {
            Document letterDoc = Jsoup.connect(letterLink).get();

            //fetch pages
            Element pageElement = letterDoc.select("div.pages").get(0);
            Elements pages = pageElement.children().select("a[href]");
            if (pages != null && pages.size() > 2) {
                pages.remove(pages.size() - 1);
                pages.remove(0);
            }

            //collect such letter's all page
            List<String> letterPageLinks = new ArrayList<>();
            letterPageLinks.add(pageElement.baseUri());
            if (pages != null) {
                letterPageLinks.addAll(pages.stream().map(page -> Fetcher.BASE_URI + "/" + page.attr("href")).collect(Collectors.toList()));
            }

            if (Strings.isNotEmpty(pageLink)) {
                int pageIndex = letterPageLinks.lastIndexOf(pageLink);
                if (pageIndex > 0) {
                    letterPageLinks.removeAll(letterPageLinks.subList(0, pageIndex));
                }
            }

            //traverse letter pages then parse every brand
            letterPageLinks.forEach(pageLink -> fetchExecutor.execute(new PageFetchTask(letterLink, pageLink, brandLink)));
        } catch (IOException e) {
            logger.warn("fetch letter links error:" + letterLink, e);
            FetchErrorRecord errorLetter = new FetchErrorRecord();
            errorLetter.setLink(letterLink);
            errorLetter.setType(FetchErrorRecord.Type.LETTER);
            fetchExecutor.execute(new PersistTask<>(errorLetter));
        }
    }
}
