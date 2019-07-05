package com.deng.bitpic.service.impl;

import com.deng.bitpic.constant.PasswordConstant;
import com.deng.bitpic.constant.ThumbnailsConstant;
import com.deng.bitpic.dataobject.PicInfo;
import com.deng.bitpic.dataobject.admin.AuthorCheck;
import com.deng.bitpic.dataobject.admin.PicCheck;
import com.deng.bitpic.dataobject.user.OrderDetail;
import com.deng.bitpic.dataobject.user.OrderMaster;
import com.deng.bitpic.enums.FileEnum;
import com.deng.bitpic.enums.ResultEnum;
import com.deng.bitpic.exception.BitPicException;
import com.deng.bitpic.service.*;
import com.deng.bitpic.utils.*;
import lombok.extern.slf4j.Slf4j;
import net.lingala.zip4j.exception.ZipException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * @description: 文件上传下载
 * @author: Deng
 * @create: 2019-02-01
 */
@Service
@Slf4j
public class FileServiceImpl implements FileService {

    @Value("${file.root}")
    private String FILE_STORE_ROOT_PATH;

    @Value("${img-server.root}")
    private String IMG_SERVER_ROOT_PATH;

    @Value("${file.authenticate-path}")
    private String AUTHENTICATE_PATH;

    @Value("${file.download-file-name}")
    private String DOWNLOAD_FILE_NAME;

    @Value("${file.authenticate-file-name}")
    private String AUTHENTICATE_FILE_NAME;

    @Value("${file.avatar-path}")
    private String AVATAR_PATH;

    @Value("${file.avatar-file-name}")
    private String AVATAR_FILE_NAME;

    @Value("${file.pic-check-path}")
    private String PIC_CHECK_PATH;

    @Value("${file.certificate-file-name}")
    private String CERTIFICATE_FILE_NAME;

    @Value("${aes.key}")
    private String ENCRYPT_KEY;

    @Autowired
    private PicInfoService picInfoService;

    @Autowired
    private PicCheckService picCheckService;

    @Autowired
    private AuthorCheckService authorCheckService;

    @Autowired
    private OrderDetailService orderDetailService;

    @Autowired
    private OrderMasterService orderMasterService;

    @Autowired
    private OrderService orderService;

    @Override
    public void uploadAuthentication(String userId, MultipartFile file) {
        String path = getUploadPath(userId, null, FileEnum.TYPE_AUTHENTICATE);
        if (!executeUpload(path, file, AUTHENTICATE_FILE_NAME)) {
            throw new BitPicException(ResultEnum.OPERATION_FAIL);
        }

        authorCheckService.deleteByUserId(userId);
        authorCheckService.save(new AuthorCheck(userId));
    }

    @Override
    public void uploadPictures(String userId, String number, FileEnum fileEnum, MultipartFile[] files) {
        String path = getUploadPath(userId, number,  fileEnum);
        // 边上传边重命名
        int count = 0;
        String[] formats = new String[files.length];
        for (MultipartFile file : files) {
            String suffix = FileUtil.getSuffix(file);
            if (!executeUpload(path, file, count + "." + suffix)) {
                FileUtil.clearFileInFolder(path, FileEnum.TYPE_ALL);
                throw new BitPicException(ResultEnum.OPERATION_FAIL);
            }
            formats[count] = suffix;
            count ++;
        }

        PicCheck picCheck;
        if (fileEnum == FileEnum.TYPE_PIC) {
            picCheck = new PicCheck(number, userId, formats);
            String desDir = IMG_SERVER_ROOT_PATH + userId + "/" + number + "/";
            try {
                ThumbnailsUtil.thumFolder(path, desDir, ThumbnailsConstant.SMALL_SCALE, false);
            } catch (IOException e) {
                log.error("[作品上传]: 制作略缩图错误");
            }

        } else {
            picCheck = picCheckService.queryByNumber(number);
            picCheck.setAuthFormats(formats);
        }
        picCheckService.save(picCheck);
    }

    @Override
    public void uploadAvatar(String userId, MultipartFile file) {
        String path = getUploadPath(userId, null, FileEnum.TYPE_AVATAR);
        if (!executeUpload(path, file, AVATAR_FILE_NAME)) {
            throw new BitPicException(ResultEnum.OPERATION_FAIL);
        }
    }

    @Override
    public Boolean executeUpload(String path, MultipartFile file, String saveFileName) {
        String name = saveFileName == null ? file.getOriginalFilename() : saveFileName;
        File saveFile = new File(path + name);
        try {
            file.transferTo(saveFile);
        } catch (IOException e) {
            log.error("[文件上传]: 保存失败 file: {}", saveFile.getName());
            e.printStackTrace();
            return false;
        }

        return true;
    }

    @Override
    public String getUploadPath(String userId, String number, FileEnum fileEnum) {
        String userPath = FILE_STORE_ROOT_PATH + userId + "/";
        // 一开始忽略了头像不需审核应该直接上传到图片服务器
        if (fileEnum == FileEnum.TYPE_AVATAR) {
            userPath = IMG_SERVER_ROOT_PATH + userId + "/";
        }

        File userDir = new File(userPath);
        if (!userDir.exists()) {
            userDir.mkdir();
        }

        String filePath;
        if (fileEnum == FileEnum.TYPE_AUTHENTICATE) {
            filePath = userPath + AUTHENTICATE_PATH + "/";
        } else if (fileEnum == FileEnum.TYPE_AVATAR) {
            filePath = userPath + AVATAR_PATH + "/";
        } else if (fileEnum == FileEnum.TYPE_PIC_CHECK) {
            filePath = userPath + number + "/" + PIC_CHECK_PATH + "/";
        } else {
            // 作品目录
            filePath = userPath + number + "/";
        }

        File file = new File(filePath);
        if (!file.exists()) {
            file.mkdir();
        }

        return filePath;
    }

    @Override
    public File getDownloadFile(String userId, String orderId) {
        String srcPath = FILE_STORE_ROOT_PATH + userId + "/" + orderId + "/" + DOWNLOAD_FILE_NAME;
        File src = new File(srcPath);
        if (src.exists()) {
            return src;
        }

        // 情况太多，只考虑常见的
        String userPath = FILE_STORE_ROOT_PATH + userId + "/";
        File userDir = new File(userPath);
        if (!userDir.exists()) {
            userDir.mkdir();
        }

        String filePath = userPath + orderId + "/";
        File fileDir = new File(filePath);
        if (!fileDir.exists()) {
            fileDir.mkdir();
        }

        int count = 0;
        List<OrderDetail> list = orderDetailService.queryByMasterId(orderId);
        String[] numbers = new String[list.size()];
        for (OrderDetail detail : list) {
            String fp = FILE_STORE_ROOT_PATH + detail.getAuthorId() + "/" + detail.getNumber() + "/" + DOWNLOAD_FILE_NAME;
            FileUtil.copy(fp, filePath + count + DOWNLOAD_FILE_NAME);

            numbers[count] = detail.getNumber();
            count ++;

            // 第一次下载才真正处理，代码不该放在这里，不过为了方便
            orderService.executeDetail(detail);
        }

        try {
            String certificateFilePathName = filePath + CERTIFICATE_FILE_NAME;
            PrintWriter pw = new PrintWriter(new FileWriter(certificateFilePathName));
            String pattern = "%s : %s";
            pw.println("userId: " + userId);
            pw.println("orderId: " + orderId);
            pw.println("作品编号 + IPFS地址: ");

            for (String number : numbers) {
                PicInfo picInfo = picInfoService.queryByNumber(number);
                pw.println(String.format(pattern, number, picInfo.getIpfs()));
            }
            pw.close();

//            TODO 购买凭证上传到IPFS网络，取消下面代码注释即可
//            String ipfs = IpfsUtil.upload(certificateFilePathName);
//            PrintWriter pw2 = new PrintWriter(new FileWriter(certificateFilePathName, true));
//            pw2.println("certificate file ipfs encrypted address: " + AESUtil.encrypt(ipfs, ENCRYPT_KEY));
//            pw2.close();

            ZipUtil.zipFolder(filePath, filePath + DOWNLOAD_FILE_NAME, PasswordConstant.PASSWORD);

            OrderMaster orderMaster = orderMasterService.queryById(orderId);
            orderMaster.setHadDownload(true);
            orderMasterService.save(orderMaster);

            return new File(filePath + DOWNLOAD_FILE_NAME);
        } catch (ZipException e) {
            log.error("[下载作品]: 打包失败 {}", e.getMessage());
            return null;
        } catch (IOException e) {
            log.error("[文件下载]: 生成购买凭证出错 {}", e.getMessage());
            return null;
        }

    }
}
