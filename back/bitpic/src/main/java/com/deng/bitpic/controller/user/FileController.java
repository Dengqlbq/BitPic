package com.deng.bitpic.controller.user;

import com.deng.bitpic.dataobject.user.User;
import com.deng.bitpic.enums.FileEnum;
import com.deng.bitpic.enums.ResultEnum;
import com.deng.bitpic.service.FileService;
import com.deng.bitpic.service.UserService;
import com.deng.bitpic.utils.KeyUtil;
import com.deng.bitpic.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;

/**
 * @description: 文件上传下载
 * @author: Deng
 * @create: 2019-02-03
 */
@RestController
@Slf4j
@RequestMapping("/user/file")
public class FileController {

    @Autowired
    private FileService fileService;

    @Autowired
    private UserService userService;

    // TODO 文件aspect

    /**
     * 上传认证文件
     * @param userId 用户ID
     * @param file 文件
     * @return 结果
     */
    @PostMapping("/upload/authenticate")
    public ResultVO authenticate(@RequestParam("userId") String userId, @RequestParam("file") MultipartFile file) {
        fileService.uploadAuthentication(userId, file);
        return ResultVO.of(ResultEnum.OPERATION_SUCCESS);
    }

    /**
     * 上传作品相关文件
     * @param userId 用户ID
     * @param seed key种子
     * @param files 作品相关文件
     * @return 结果
     */
    @PostMapping("/upload/pictures")
    public ResultVO pictures(@RequestParam("userId") String userId, @RequestParam("seed") String seed, @RequestParam("type") Integer type, @RequestParam("files") MultipartFile[] files) {
        if (type.equals(FileEnum.TYPE_PIC.getCode())) {
            fileService.uploadPictures(userId, KeyUtil.getKey(seed), FileEnum.TYPE_PIC, files);
        } else {
            fileService.uploadPictures(userId, KeyUtil.getKey(seed), FileEnum.TYPE_PIC_CHECK, files);
        }

        return ResultVO.of(ResultEnum.OPERATION_SUCCESS);
    }

    /**
     * 上传头像文件
     * @param userId 用户ID
     * @param file 头像文件
     * @return 结果
     */
    @PostMapping("/upload/avatar")
    public ResultVO avatar(@RequestParam("userId") String userId, @RequestParam("file") MultipartFile file) {
        fileService.uploadAvatar(userId, file);
        User user = userService.findById(userId);
        user.setHaveAvatar(true);
        userService.save(user);

        return ResultVO.of(ResultEnum.OPERATION_SUCCESS);
    }

    /**
     * 下载作品
     * @param userId 用户ID
     * @param orderId 订单编号
     * @param response response
     * @return 结果
     */
    @GetMapping("/download/{userId}/{orderId}")
    public ResultVO download(@PathVariable("userId") String userId,@PathVariable("orderId") String orderId, HttpServletResponse response) {
        File file = fileService.getDownloadFile(userId, orderId);
        response.setContentType("application/force-download");
        response.addHeader("Content-Disposition", "attachment;fileName=" + file.getName());

        try {
            byte[] bytes = new byte[1024];
            FileInputStream fis = new FileInputStream(file);
            BufferedInputStream bis = new BufferedInputStream(fis);
            OutputStream os = response.getOutputStream();

            int i = bis.read(bytes);
            while (i != -1) {
                os.write(bytes, 0, i);
                i = bis.read(bytes);
            }

        } catch (Exception e) {
            log.error("[文件下载]: 下载出错: {}", e);
        }
        // 下载文件要返回null
        return null;
    }
}
