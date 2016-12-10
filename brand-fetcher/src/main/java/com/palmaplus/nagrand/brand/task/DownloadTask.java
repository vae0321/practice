package com.palmaplus.nagrand.brand.task;

import com.palmaplus.nagrand.brand.Fetcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by sifan on 2016/6/30.
 */
public class DownloadTask implements Runnable {
    private static Logger logger = LoggerFactory.getLogger(DownloadTask.class);

    private String logoUrl;
    private String logoName;

    public DownloadTask(String logoUrl, String logoName) {
        this.logoUrl = logoUrl;
        this.logoName = logoName;
    }

    @Override
    public void run() {
        try {
            URL url = new URL(logoUrl);
            URLConnection conn = url.openConnection();
            InputStream is = conn.getInputStream();
            byte[] bytes = new byte[2048];

            File logoDir = new File(Fetcher.class.getResource("/").getPath() + "logo");
            if (!logoDir.exists()) {
                logoDir.mkdir();
            }
            OutputStream os = new FileOutputStream(new File(logoDir, logoName + ".png"));
            int length;
            while ((length = is.read(bytes)) != -1) {
                os.write(bytes, 0, length);
            }
            os.close();
            is.close();
        } catch (IOException e) {
            logger.warn(String.format("download %s logo failed.", logoName), e);
        }
    }
}