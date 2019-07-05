package com.deng.bitpic.utils;

import com.deng.bitpic.enums.FileEnum;
import com.deng.bitpic.enums.ResultEnum;
import com.deng.bitpic.exception.BitPicException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

/**
 * @description: 文件
 * @author: Deng
 * @create: 2019-02-12
 */
@Slf4j
public class FileUtil {

    /**
     * 删除目录内所有图片
     * @param path 目录
     */
    public static void clearImgInFolder(String path) {
        clearFileInFolder(path, FileEnum.TYPE_IMG);
    }

    /**
     * 清除目录中指定类型所有文件（目前只支持图片文件和所有文件类型）
     * @param path 目录
     * @param fileEnum 文件类型
     */
    public static void clearFileInFolder(String path, FileEnum fileEnum) {
        clearFileInFolder(new File(path), fileEnum);
    }

    public static void clearFileInFolder(File dir, FileEnum fileEnum) {
        if (dir.exists()) {
            File[] files = dir.listFiles();
            if (files != null) {
                for (File file : files) {
                    // 类型多了改switch
                    if (fileEnum == FileEnum.TYPE_IMG ) {
                        if (isImg(file)) {
                            file.delete();
                        }
                    } else {
                        file.delete();
                    }
                }
            }
        }
    }

    /**
     * 判断文件是否为图片
     * @param file 文件
     * @return 结果
     */
    public static Boolean isImg(File file) {
        // TODO 完善
        String suffix = getSuffix(file).toUpperCase();
        return suffix.endsWith("JPG") || suffix.endsWith("PNG") || suffix.endsWith("JPEG");
    }

    /**
     * 获取文件后缀名
     * @param file 文件
     * @return 后缀名
     */
    public static String getSuffix(File file) {
        return getSuffix(file.getName());
    }

    public static String getSuffix(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }

    public static String getSuffix(MultipartFile file) {
        String name = file.getOriginalFilename();
        return name == null ? null : getSuffix(name);
    }

    /**
     * 列出所有非隐藏，非目录文件
     * @param path 目录
     * @return 文件列表
     */
    public static File[] listFiles(String path) {
        return listFiles(new File(path));
    }

    public static File[] listFiles(File dir) {
        return dir.listFiles((e) -> !e.isHidden() && !e.isDirectory());
    }

    public static Boolean copy(String src, String des) {
        File srcFile = new File(src);
        File desFile = new File(des);
        if (!srcFile.exists()) {
            log.error("[文件移动]: 源文件不存在 {}", srcFile);
            throw new BitPicException(ResultEnum.FILE_NOT_EXISTS);
        }

        try {
            Files.copy(srcFile.toPath(), desFile.toPath());
            return true;
        } catch (IOException e) {
            log.error("[复制文件]: {}", e.getMessage());
            return false;
        }
    }

    /**
     * 根据数字文件名排序（例：12.png, 345.html)
     * @param files 文件数组
     */
    public static void sortByNumberName(File[] files) {
        File t;
        for (int i = 0; i < files.length - 1; i++) {
            for (int j = i + 1; j < files.length; j++) {
                if (compareFileNumberName(files[i].getName(), files[j].getName()) > 0) {
                    t = files[i];
                    files[i] = files[j];
                    files[j] = t;
                }
            }
        }
    }

    /**
     * 比较数字文件名
     * @param n1 文件名
     * @param n2 文件名
     * @return 结果
     */
    private static int compareFileNumberName(String n1, String n2) {
        String v1 = n1.split("\\.")[0];
        String v2 = n2.split("\\.")[0];
        try {
            return Integer.parseInt(v1) - Integer.parseInt(v2);
        } catch (NumberFormatException e) {
            return -1;
        }

    }
}
