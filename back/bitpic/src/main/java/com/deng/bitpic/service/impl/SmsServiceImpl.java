package com.deng.bitpic.service.impl;

import com.deng.bitpic.enums.SmsEnum;
import com.deng.bitpic.service.SmsService;
import com.github.qcloudsms.SmsSingleSender;
import com.github.qcloudsms.SmsSingleSenderResult;
import com.github.qcloudsms.httpclient.HTTPException;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Random;

/**
 * @description: 短信验证码
 * @author: Deng
 * @create: 2019-02-02
 */
@Service
@Slf4j
public class SmsServiceImpl implements SmsService {

//    TODO 按腾讯云规则配置好短信引用，然后，取消下面代码注释即可
//    @Value("${sms.appid}")
//    private int APP_ID;
//
//    @Value("${sms.appkey}")
//    private String APP_KEY;
//
//    @Value("${sms.templateId.registered}")
//    private int TEMPLATE_REGISTERED;
//
//    @Value("${sms.templateId.phone}")
//    private int TEMPLATE_PHONE;
//
//    @Value("${sms.templateId.password}")
//    private int TEMPLATE_PASSWORD;
//
//    @Value("${sms.templateId.pay}")
//    private int TEMPLATE_PAY;
//
//    @Value("${sms.sign}")
//    private String SIGN;

    @Override
    public String sendCode(String phone, Integer type) {
        // 对应短信内容模板插值
        String[] params = new String[1];
        params[0] = createCode();

//        TODO 使用腾讯云短信服务发送验证码，按腾讯云规则配置好短信引用，然后，取消下面代码注释即可

//        int templateId;
//        if (type.equals(SmsEnum.PHONE.getCode())) {
//            templateId = TEMPLATE_PHONE;
//        } else if (type.equals(SmsEnum.PASSWORD.getCode())) {
//            templateId = TEMPLATE_PASSWORD;
//        } else if (type.equals(SmsEnum.PAY.getCode())) {
//            templateId = TEMPLATE_PAY;
//        } else {
//            templateId = TEMPLATE_REGISTERED;
//        }
//
//        SmsSingleSender sender = new SmsSingleSender(APP_ID, APP_KEY);
//        try {
//            SmsSingleSenderResult result = sender.sendWithParam("86", phone, templateId, params, SIGN, "", "");
//            log.info(result.toString());
//        } catch (HTTPException e) {
//            // HTTP响应码错误
//            log.error("[验证码]: HTTP响应码错误");
//            e.printStackTrace();
//        } catch (JSONException e) {
//            // json解析错误
//            log.error("[验证码]: json解析错误");
//            e.printStackTrace();
//        } catch (IOException e) {
//            // 网络IO错误
//            log.error("[验证码]: 网络IO错误");
//            e.printStackTrace();
//        }

        return params[0];
    }

    @Override
    public String createCode() {
        Integer code = 100000 + new Random().nextInt(899999);
        return code.toString();
    }
}
