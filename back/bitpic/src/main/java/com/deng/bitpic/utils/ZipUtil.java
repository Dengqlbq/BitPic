package com.deng.bitpic.utils;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;

/**
 * @description: 打包
 * @author: Deng
 * @create: 2019-02-10
 */
public class ZipUtil {

    /**
     * src 整个目录打包保存为 desPathName
     * @param src 源目录
     * @param desPathName 目的目录 + 文件名
     * @throws ZipException
     */
    public static void zipFolder(String src, String desPathName, String password) throws ZipException {
        ZipFile zipFile = new ZipFile(desPathName);
        ZipParameters parameters = new ZipParameters();

        parameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);
        parameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL);
        parameters.setEncryptFiles(true);
        parameters.setEncryptionMethod(Zip4jConstants.ENC_METHOD_STANDARD);
        parameters.setPassword(password.toCharArray());

        zipFile.addFolder(src, parameters);
    }
}
