package com.deng.bitpic.utils;

import com.deng.bitpic.dataobject.PicInfo;
import com.deng.bitpic.enums.PicInfoEnum;
import com.deng.bitpic.enums.ResultEnum;
import com.deng.bitpic.exception.BitPicException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.imaging.ImageInfo;
import org.apache.commons.imaging.ImageReadException;
import org.apache.commons.imaging.Imaging;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Date;

/**
 * @description: 作品信息
 * @author: Deng
 * @create: 2019-02-13
 */
@Slf4j
public class PicInfoUtil {

    /**
     * 根据作品目录内作品文件信息填充数据
     * @param picInfo 作品信息（不完整）
     * @param path 作品目录
     * @return picInfo （信息完整)
     */
    public static PicInfo fillingFromPath(PicInfo picInfo, String path) {
        File dir = new File(path);
        if (!dir.exists()) {
            log.error("[PicInfo信息填充]: 目录不存在 {}", path);
            throw new BitPicException(ResultEnum.DIR_NOT_EXISTS);
        }

        File[] files = FileUtil.listFiles(dir);
        if (files == null) {
            log.error("[PicInfo信息填充]: 目录为空 {}", path);
            throw new BitPicException(ResultEnum.DIR_EMPTY);
        }

        // 长宽（像素)，长宽 (厘米)，DPI，方向，格式
        Integer[][] sizePixel = new Integer[files.length][2];
        Float[][] sizeCentimetre = new Float[files.length][2];
        Integer[] dpis = new Integer[files.length];
        Integer[] directions = new Integer[files.length];
        String[] formats = new String[files.length];

        int count = 0;
        DecimalFormat df = new DecimalFormat("0.00");
        FileUtil.sortByNumberName(files);
        for (File file : files) {
            try {
                ImageInfo info = Imaging.getImageInfo(file);
                int dpi = info.getPhysicalHeightDpi();
                int width = info.getWidth();
                int height = info.getHeight();
                // 英寸转厘米
                float widthCm = Float.parseFloat(df.format(info.getPhysicalWidthInch() * 2.54f));
                float heightCm = Float.parseFloat(df.format(info.getPhysicalHeightInch() * 2.54f));

                sizePixel[count][0] = width;
                sizePixel[count][1] = height;
                sizeCentimetre[count][0] = widthCm;
                sizeCentimetre[count][1] = heightCm;
                dpis[count] = dpi;
                directions[count] = widthCm > heightCm ? PicInfoEnum.DIRECTION_HORIZONTAL.getCode() : PicInfoEnum.DIRECTION_VERTICAL.getCode();
                formats[count] = FileUtil.getSuffix(file);
                count ++;

            } catch (ImageReadException e) {
                // TODO 处理
            } catch (IOException e) {
            }
        }


        picInfo.setSizePixel(sizePixel);
        picInfo.setSizeCentimetre(sizeCentimetre);
        picInfo.setDpi(dpis);
        picInfo.setDirections(directions);
        picInfo.setFormats(formats);
        picInfo.setGroup(count > 1);
        picInfo.setUpdateTime(new Date());

        return picInfo;
    }
}
