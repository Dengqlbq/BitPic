package com.deng.bitpic.aspect;

import com.deng.bitpic.constant.CookieConstant;
import com.deng.bitpic.constant.RedisConstant;
import com.deng.bitpic.enums.ResultEnum;
import com.deng.bitpic.exception.BitPicException;
import com.deng.bitpic.utils.CookieUtil;
import io.netty.util.internal.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import redis.clients.jedis.Jedis;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @description: 用户数据
 * @author: Deng
 * @create: 2019-03-11
 */
@Aspect
@Component
@Slf4j
public class DataAspect {

    /**
     * RedisRepository和其他Repository不同，不能在aspect内autowrite
     *
     */

    public static final Pattern PATTERN = Pattern.compile("((list)|(delete)|(deleteAll)|(author)|(pic)|(download))/(\\w{32})");

    @Pointcut("execution(public * com.deng.bitpic.controller.user.*.*(..))" + "&&" +
              "!execution(public * com.deng.bitpic.controller.user.AuthenticateController.*(..))")
    public void verify() {}

    @Before("verify()")
    public void doVerify() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        String userId;
        String method = request.getMethod().toUpperCase();
        if (method.equals("POST") || method.equals("PUT") || method.equals("PATCH")) {
            userId = request.getParameter("userId");
        } else {
            Matcher m = PATTERN.matcher(request.getRequestURL());
            if (!m.find()) {
                log.info("[身份验证]: 无法获取userId {}", request.getRequestURL().toString());
                throw new BitPicException(ResultEnum.PERMISSION_DENY);
            }
            userId = m.group(0).split("/")[1];
        }

        if (StringUtil.isNullOrEmpty(userId)) {
            log.error("[身份验证]: 无法获取userId");
            throw new BitPicException(ResultEnum.PERMISSION_DENY);
        }

        Cookie cookie = CookieUtil.get(request, CookieConstant.TOKEN);
        if (cookie == null) {
            log.error("[身份验证]: cookie中无token");
            throw new BitPicException(ResultEnum.PERMISSION_DENY);
        }

        Jedis redis = new Jedis("127.0.0.1", 6379);

        String cToken = cookie.getValue();
        String rToken = redis.get(String.format(RedisConstant.TOKEN_PREFIX, userId));
        if (!cToken.equals(rToken)) {
            log.error("[身份验证]: token不相等");
            redis.close();
            throw new BitPicException(ResultEnum.PERMISSION_DENY);
        }
        redis.close();
    }
}
