package com.palmaplus.nagrand.brand;

import com.palmaplus.nagrand.brand.entity.ProcessRecord;
import com.palmaplus.nagrand.brand.task.ErrorTask;
import com.palmaplus.nagrand.brand.task.LetterFetchTask;
import com.palmaplus.nagrand.brand.task.PersistTask;
import lombok.extern.log4j.Log4j2;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static com.palmaplus.nagrand.brand.factory.EntityFactory.findProcessRecords;

/**
 * Created by sifan on 2016/6/30.
 */
@Log4j2
public class Fetcher {

    public static ExecutorService fetchExecutor = Executors.newFixedThreadPool(3);
    public static ExecutorService coreExecutor = Executors.newFixedThreadPool(10);
    private static ScheduledExecutorService errorRecordExecutor = Executors.newSingleThreadScheduledExecutor();

    public static String BASE_URI = "http://i.paizi.com";

    private void start() {
        try {
            //brand index
            Document index = Jsoup.connect(BASE_URI).get();

            //all letter links
            Elements allLetterElements = index.select("div.con02").get(0).children();
            List<String> allLetterLinks = allLetterElements.stream().map(letterElement -> letterElement.attr("href")).collect(Collectors.toList());
            allLetterLinks.forEach(letterLink -> {
                        ProcessRecord processRecord = new ProcessRecord();
                        processRecord.setLetter(letterLink);
                        coreExecutor.execute(new PersistTask<>(processRecord));
                        fetchExecutor.execute(new LetterFetchTask(letterLink));
                    }
            );
        } catch (IOException e) {
            log.warn("fetch index error.");
        }
    }

    private void recover(List<ProcessRecord> processRecords) {
        processRecords.forEach(processRecord -> {
            String letterLink = processRecord.getLetter();
            String pageLink = processRecord.getLastFetchPage();
            String brandLink = processRecord.getLastFetchBrand();
            fetchExecutor.execute(new LetterFetchTask(letterLink, pageLink, brandLink));
        });
    }

    public static void main(String[] args) {
        Fetcher fetcher = new Fetcher();

        List<ProcessRecord> processRecords = findProcessRecords();
        if (processRecords == null || processRecords.size() < 1) {
            fetcher.start();
        } else {
            fetcher.recover(processRecords);
        }

        errorRecordExecutor.scheduleAtFixedRate(new ErrorTask(), 5, 5, TimeUnit.MINUTES);

        try {
            coreExecutor.awaitTermination(1, TimeUnit.DAYS);
            fetchExecutor.awaitTermination(1, TimeUnit.DAYS);

            errorRecordExecutor.shutdown();
        } catch (InterruptedException ignored) {
        }

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            errorRecordExecutor.shutdown();
            coreExecutor.shutdown();
            fetchExecutor.shutdown();
        }));
    }
}
