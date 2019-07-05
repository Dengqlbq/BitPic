package com.deng.bitpic.utils;

import com.deng.bitpic.constant.ThumbnailsConstant;
import com.deng.bitpic.enums.ResultEnum;
import com.deng.bitpic.exception.BitPicException;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

/**
 * @description: 略缩图
 * @author: Deng
 * @create: 2019-02-10
 */
@Slf4j
public class ThumbnailsUtil {

    /**
     * 为 src 目录内所有图片生成略缩图并保存到 des 目录
     * @param src 源目录
     * @param des 目的目录
     * @param scale 缩小比列
     * @param mark 是否打水印
     */
    public static void thumFolder(String src, String des, float scale, Boolean mark) throws IOException{
        File srcDir = new File(src);
        File desDir = new File(des);
        File markFile = null;
        String namePrefix = "";

        if (mark) {
            markFile = new File(ThumbnailsConstant.MARK_FILE_PATH_NAME);
            if (!markFile.exists()) {
                log.error("[略缩图]: 水印文件不存在");
                throw new BitPicException(ResultEnum.FILE_NOT_EXISTS);
            }
        }

        boolean s;
        s = srcDir.exists();
        File[] files = FileUtil.listFiles(srcDir);
        if (!s || files == null) {
            log.error("[略缩图]: 源文件目录为空");
            throw new BitPicException(ResultEnum.DIR_EMPTY);
        }

        if (!desDir.exists()) {
            desDir.mkdirs();
        } else {
            namePrefix = ThumbnailsConstant.NAME_PREFIX;
        }

        int count = 0;
        FileUtil.sortByNumberName(files);
        for (File file : files) {
            if (FileUtil.isImg(file)) {
                String saveFileName = des + count + namePrefix + "." + ThumbnailsConstant.SUFFIX;
                if (mark) {
                    Thumbnails.of(file)
                            .scale(scale)
                            .watermark(Positions.CENTER, ImageIO.read(markFile), 0.6f)
                            .watermark(Positions.TOP_RIGHT, ImageIO.read(markFile), 0.6f)
                            .watermark(Positions.TOP_LEFT, ImageIO.read(markFile), 0.6f)
                            .watermark(Positions.BOTTOM_RIGHT, ImageIO.read(markFile), 0.6f)
                            .watermark(Positions.BOTTOM_LEFT, ImageIO.read(markFile), 0.6f)
                            .toFile(saveFileName);
                } else {
                    Thumbnails.of(file)
                            .scale(scale)
                            .toFile(saveFileName);
                }
                count ++;
            }
        }
    }
}
