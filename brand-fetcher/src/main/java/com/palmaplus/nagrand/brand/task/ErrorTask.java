package com.palmaplus.nagrand.brand.task;

import com.palmaplus.nagrand.brand.entity.FetchErrorRecord;
import com.palmaplus.nagrand.brand.factory.EntityFactory;

import java.util.List;

import static com.palmaplus.nagrand.brand.Fetcher.fetchExecutor;

/**
 * Created by sifan on 2016/7/1.
 */
public class ErrorTask implements Runnable {
    @Override
    public void run() {
        List<FetchErrorRecord> records = EntityFactory.findFetchErrorRecords();
        records.forEach(record -> {
            FetchErrorRecord.Type type = record.getType();
            if (type == FetchErrorRecord.Type.LETTER) {
                fetchExecutor.execute(new LetterFetchTask(record.getLink()));
            } else if (type == FetchErrorRecord.Type.PAGE) {
                fetchExecutor.execute(new PageFetchTask(record.getLink()));
            } else if (type == FetchErrorRecord.Type.BRAND) {
                fetchExecutor.execute(new BrandFetchTask(record.getLink()));
            } else {
                throw new RuntimeException(String.format("unsupported error type %s", type));
            }
            EntityFactory.deleteErrorRecord(record);
        });
    }
}
