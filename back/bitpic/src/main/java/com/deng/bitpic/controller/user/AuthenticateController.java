package com.deng.bitpic.controller.user;

import com.deng.bitpic.constant.CookieConstant;
import com.deng.bitpic.constant.RedisConstant;
import com.deng.bitpic.dataobject.user.User;
import com.deng.bitpic.enums.RegisterEnum;
import com.deng.bitpic.enums.ResultEnum;
import com.deng.bitpic.form.LoginForm;
import com.deng.bitpic.form.RegisterForm;
import com.deng.bitpic.repository.RedisRepository;
import com.deng.bitpic.service.UserService;
import com.deng.bitpic.utils.CookieUtil;
import com.deng.bitpic.utils.KeyUtil;
import com.deng.bitpic.vo.ResultVO;
import com.deng.bitpic.vo.UserVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.concurrent.TimeUnit;


/**
 * @description: 用户认证（登陆，登出，注册）
 * @author: Deng
 * @create: 2019-01-17
 */
@RestController
@Slf4j
@RequestMapping("/user/authenticate")
public class AuthenticateController {

    @Autowired
    private UserService userService;

    @Autowired
    private RedisRepository redisRepository;

    /**
     * 用户登陆
     * @param form 登陆表单
     * @param response Http响应
     * @return 结果（成功会设置token）
     */
    @PostMapping("/login")
    public ResultVO login(@Valid LoginForm form, BindingResult bindingResult, HttpServletResponse response) {
        boolean b, u;
        b = bindingResult.hasErrors();
        u = userService.check(form.getPhone(), form.getPassword());

        if (b || !u) {
            return ResultVO.of(ResultEnum.LOGIN_FAIL);
        }

        UserVO userVO = userService.createUserVO(form.getPhone());

        // 登陆成功生成token
        String token = KeyUtil.randomUUID();
        // 设置token到redis
        redisRepository.set(String.format(RedisConstant.TOKEN_PREFIX, userVO.getId()), token, RedisConstant.EXPIRE, TimeUnit.SECONDS);
        // 设置token到浏览器cookie
        CookieUtil.set(response, CookieConstant.TOKEN, token, CookieConstant.EXPIRE);

        return ResultVO.of(ResultEnum.LOGIN_SUCCESS, userVO);
    }

    /**
     * 用户退出
     * @param userId 用户ID
     * @param request request
     * @param response response
     * @return 结果
     */
    @GetMapping("/logout/{userId}")
    public ResultVO logout(@PathVariable("userId") String userId, HttpServletRequest request, HttpServletResponse response) {
        // 1. 查询cookie，并对比cookie中的token与redis中的token
        Cookie cookie = CookieUtil.get(request, CookieConstant.TOKEN);
        String token = redisRepository.get(String.format(RedisConstant.TOKEN_PREFIX, userId));

        if (cookie != null && token.equals(cookie.getValue())) {
            // 2. 清除redis的token
            redisRepository.delete(String.format(RedisConstant.TOKEN_PREFIX, userId));
            // 3. 清除cookie
            CookieUtil.set(response, CookieConstant.TOKEN, null, 0);
            return ResultVO.of(ResultEnum.OPERATION_SUCCESS);
        }
        
        return ResultVO.of(ResultEnum.OPERATION_FAIL);
    }

    /**
     * 用户注册（数据前后端都验证）
     * @param form 注册表单
     * @param bindingResult result
     * @return 结果
     */
    @PostMapping("/register")
    public ResultVO register(@Valid RegisterForm form, BindingResult bindingResult, HttpServletResponse response) {
        boolean b, u;
        b = bindingResult.hasErrors();
        u = userService.findByPhone(form.getPhone()) == null;
        if (b || !u) {
            return ResultVO.of(ResultEnum.REGISTER_FAIL);
        }

        log.info("[用户注册]: 正在注册用户: {}", form.getPhone());

        String uuid = KeyUtil.randomUUID();
        boolean photographer = form.getType().equals(RegisterEnum.PHOTOGRAPHER.getCode());
        User user = new User(uuid, form.getPhone(), form.getName(), form.getPassword(), photographer);

        if (userService.save(user) != null) {
            log.info("[用户注册]: 注册成功 {}", form.getPhone());

            String token = KeyUtil.randomUUID();
            redisRepository.set(String.format(RedisConstant.TOKEN_PREFIX, uuid), token, RedisConstant.EXPIRE, TimeUnit.SECONDS);
            CookieUtil.set(response, CookieConstant.TOKEN, token, CookieConstant.EXPIRE);

            UserVO userVO = new UserVO(uuid, form.getName(), form.getPhone(), photographer);
            return ResultVO.of(ResultEnum.REGISTER_SUCCESS, userVO);
        } else {
            log.error("[用户注册]: 注册失败 {}", form.getPhone());
            return ResultVO.of(ResultEnum.REGISTER_FAIL);
        }
    }
}
