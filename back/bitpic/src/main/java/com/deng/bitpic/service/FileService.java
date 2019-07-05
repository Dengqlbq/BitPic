package com.deng.bitpic.service;


import com.deng.bitpic.enums.FileEnum;
import io.netty.util.internal.StringUtil;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

/**
 * @description: 文件上传下载
 * @author: Deng
 * @create: 2019-02-01
 */
public interface FileService {

    /**
     * 上传认证文件
     * @param userId 摄影师ID
     * @param file 认证文件
     * @return 保存数量
     */
    void uploadAuthentication(String userId, MultipartFile file);

    /**
     * 上传作品及相关文件
     * @param userId 摄影师ID
     * @param number 作品编号
     * @param fileEnum 文件类型
     * @param fields 作品集
     * @return 保存数量
     */
    void uploadPictures(String userId, String number, FileEnum fileEnum, MultipartFile[] fields);

    /**
     * 上传头像
     * @param userId 用户ID
     * @param file 头像文件
     */
    void uploadAvatar(String userId, MultipartFile file);

    /**
     * 处理上传文件
     * @param path 路径
     * @param file 文件
     * @param saveFileName 保存文件名
     * @return 是否上传成功
     */
    Boolean executeUpload(String path, MultipartFile file, String saveFileName);

    /**
     * 获取文件上传目录
     * @param userId 用户ID
     * @param number 作品编号
     * @param fileEnum 文件类型枚举
     * @return 上传目录
     */
    String getUploadPath(String userId, String number, FileEnum fileEnum);

    /**
     * 获取下载文件
     * @param userId
     * @param orderId
     * @return 作品压缩包
     */
    File getDownloadFile(String userId, String orderId);
}
