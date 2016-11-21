import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Arrays;

/**
 * Created by sifan on 2016/11/14.
 */
public class MdbCopy {
    public static void main(String[] args) throws Exception{
        String inputFolderPath = "Z:\\20161118\\飞凡\\飞凡数据上线";
        String outFolderPath = "Z:\\20161118\\飞凡";
        File inputFolder = new File(inputFolderPath);
        File[] subFolders = inputFolder.listFiles();
        FilenameFilter fileFilter = (dir, name) -> name.endsWith(".mdb");
        Arrays.stream(subFolders).forEach(s -> {
            String folderName = s.getName();
            File mdbFile = s.listFiles(fileFilter)[0];
            try {
                FileUtils.copyFile(mdbFile, new File(outFolderPath + File.separator + folderName + ".mdb"));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }
}
