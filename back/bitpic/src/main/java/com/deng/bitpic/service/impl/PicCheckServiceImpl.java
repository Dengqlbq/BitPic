package com.deng.bitpic.service.impl;

import com.deng.bitpic.constant.PasswordConstant;
import com.deng.bitpic.constant.ThumbnailsConstant;
import com.deng.bitpic.dataobject.PicInfo;
import com.deng.bitpic.dataobject.admin.PicCheck;
import com.deng.bitpic.enums.CheckEnum;
import com.deng.bitpic.enums.PicInfoEnum;
import com.deng.bitpic.repository.PicCheckRepository;
import com.deng.bitpic.service.PicCheckService;
import com.deng.bitpic.service.PicInfoService;
import com.deng.bitpic.utils.IpfsUtil;
import com.deng.bitpic.utils.PicInfoUtil;
import com.deng.bitpic.utils.ThumbnailsUtil;
import com.deng.bitpic.utils.ZipUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import net.lingala.zip4j.exception.ZipException;

/**
 * @description: 作品审核
 * @author: Deng
 * @create: 2019-02-10
 */
@Slf4j
@Service
public class PicCheckServiceImpl implements PicCheckService {

    @Autowired
    private PicCheckRepository picCheckRepository;

    @Autowired
    private PicInfoService picInfoService;

    @Value("${file.root}")
    private String FILE_STORE_ROOT_PATH;

    @Value("${file.download-file-name}")
    private String ZIP_FILE_NAME;

    @Value("${img-server.root}")
    private String IMG_SERVER_ROOT_PATH;

    @Override
    public PicCheck save(PicCheck picCheck) {
        return picCheckRepository.save(picCheck);
    }

    @Override
    public PicCheck queryByNumber(String number) {
        return picCheckRepository.queryByNumber(number);
    }

    @Override
    public List<PicCheck> getWaitingList(Pageable pageable) {
        return picCheckRepository.queryByStatusOrderByCreateTimeAsc(CheckEnum.STATUS_WAIT.getCode(), pageable).getContent();
    }

    @Override
    public void acceptPic(String userId, String number) {
        executePic(number, CheckEnum.STATUS_ACCEPT.getCode(), null);

        String fileDir = FILE_STORE_ROOT_PATH + userId + "/" + number + "/";
        String desDir = IMG_SERVER_ROOT_PATH + userId + "/" + number + "/";
        String zipFilePathName = fileDir + ZIP_FILE_NAME;

        PicInfo picInfo = picInfoService.queryByNumber(number);

        try {
            // 填充信息
            PicInfoUtil.fillingFromPath(picInfo, fileDir);
            picInfo.setCheckStatus(CheckEnum.STATUS_ACCEPT.getCode());
            picInfo.setStatus(PicInfoEnum.STATUS_UP.getCode());
            picInfoService.save(picInfo);

            // 制作略缩图并保存到图片服务器相应目录
            ThumbnailsUtil.thumFolder(fileDir, desDir, ThumbnailsConstant.BIG_SCALE, true);

            // 打包作品  TODO 密码生成
            ZipUtil.zipFolder(fileDir, zipFilePathName, PasswordConstant.PASSWORD);

            // 压缩包上传到IPFS网络
//            TODO 压缩包上传到IPFS网络，取消下面代码注释即可
//            String ipfs = IpfsUtil.upload(zipFilePathName);

//            picInfo.setIpfs(ipfs);
            picInfoService.save(picInfo);
        } catch (IOException e) {
            log.error("[作品审核]: 制作略缩图失败或上传到IPFS网络失败 {} {}", fileDir, desDir);
        } catch (ZipException e) {
            log.error("[作品审核]: 打包作品失败 {}", fileDir);
        }


    }

    @Override
    public void denyPic(String number, String reason) {
        executePic(number, CheckEnum.STATUS_DENY.getCode(), reason);
        picInfoService.deleteByNumber(number);
    }

    @Override
    public void executePic(String number, Integer status, String reason) {
        PicCheck picCheck = queryByNumber(number);
        if (picCheck != null) {
            picCheck.setStatus(status);
            if (reason != null) {
                picCheck.setReason(reason);
            }
            picCheck.setUpdateTime(new Date());
            save(picCheck);
        }
    }

    @Override
    public List<PicCheck> queryByUserIdAndStatusOrderByCreateTimeDesc(String userId, Integer status, Pageable pageable) {
        return picCheckRepository.queryByUserIdAndStatusOrderByCreateTimeDesc(userId, status, pageable).getContent();
    }
}
