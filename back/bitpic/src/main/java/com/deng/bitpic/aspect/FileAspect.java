package com.deng.bitpic.aspect;

import com.deng.bitpic.dataobject.user.OrderMaster;
import com.deng.bitpic.enums.OrderEnum;
import com.deng.bitpic.enums.ResultEnum;
import com.deng.bitpic.exception.BitPicException;
import com.deng.bitpic.service.OrderMasterService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @description: 用户文件
 * @author: Deng
 * @create: 2019-03-11
 */
@Aspect
@Component
@Slf4j
public class FileAspect {

    @Autowired
    private OrderMasterService orderMasterService;


    @Pointcut("execution(public * com.deng.bitpic.controller.user.FileController.download*(..))")
    public void downloadVerify() {}



    @Before("downloadVerify()")
    public void doVerify() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        String url = request.getRequestURL().toString();
        String[] path = url.split("/");
        String userId = path[path.length - 2];
        String orderId = path[path.length - 1];

        OrderMaster orderMaster = orderMasterService.queryById(orderId);

        boolean u, s;
        u = orderMaster.getUserId().equals(userId);
        s = orderMaster.getStatus().equals(OrderEnum.STATUS_FINISHED.getCode());
        if (!u || !s) {
            log.error("[文件下载身份验证]: 非法访问订单数据 userId: {} orderId: {}", userId, orderId);
            throw new BitPicException(ResultEnum.PERMISSION_DENY);
        }
    }
}
