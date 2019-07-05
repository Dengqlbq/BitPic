package com.deng.bitpic.utils;

import com.deng.bitpic.enums.FileEnum;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
public class FileUtilTest {

    private String filePath = "/Volumes/H/Java/store/1/ab/8.jpg";

    private String fileDir = "/Volumes/H/Java/store/1/ab/";

    @Test
    public void clearImgInFolder() {
        FileUtil.clearImgInFolder(fileDir);
    }

    @Test
    public void clearFileInFolder() {
        FileUtil.clearFileInFolder(fileDir, FileEnum.TYPE_ALL);
    }

    @Test
    public void clearFileInFolder1() {
    }

    @Test
    public void isImg() {
        log.info(FileUtil.isImg(new File(filePath)).toString());
    }

    @Test
    public void getSuffix() {
        log.info(FileUtil.getSuffix(new File(filePath)));
    }

    @Test
    public void sortByName() {
        File file = new File(fileDir);
        File[] files = FileUtil.listFiles(file);
        FileUtil.sortByNumberName(files);
        for (File file1 : files) {
            log.info("{}", file1.getName());
        }
    }

    @Test
    public void copy() {
        String src = "/Volumes/H/Java/store/1/10.jpeg";
        String des = "/Volumes/H/Java/store/1/1/1.jpeg";
        FileUtil.copy(src, des);
    }

    @Test
    public void write() {
        String filePath = "/Volumes/H/Java/store/1/1.txt";
        try {
            PrintWriter pw = new PrintWriter(new FileWriter(filePath));
            pw.println("abc ");
            pw.println("def ");
            pw.println("hef ");
            pw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void re() {
        String str = "http://localhost:8080/api/bitpic/user/check/pic/8c907133f831432aa93e41a0d7ddd50c/2/0";
        //http://localhost:8080/api/bitpic/user/picInfo/list/8c907133f831432aa93e41a0d7ddd50c/0
        String pattern = "((list)|(delete)|(deleteAll)|(author)|(pic))/(\\w{32})";
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(str);

        if (m.find()) {
            log.info(m.group(0).split("/")[1]);
        }
    }
}