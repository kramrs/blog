package com.kramrs.controller;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;
import cn.hutool.core.codec.Base64;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.RandomUtil;
import com.kramrs.constant.RedisConstant;
import com.kramrs.model.vo.Result;
import com.kramrs.model.vo.response.CaptchaResp;
import com.kramrs.service.RedisService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static cn.hutool.core.img.ImgUtil.toBufferedImage;
import static com.kramrs.constant.CommonConstant.KRAMRS_ADMIN;
import static com.kramrs.constant.CommonConstant.KRAMRS_VIEW;

/**
 * @Author: kramrs
 * @Description: 验证码操作处理
 */
@Slf4j
@Api(tags = "验证码模块")
@RestController
public class CaptchaController {

    @Autowired
    private RedisService redisService;

    @ApiOperation(value = "获取验证码")
    @GetMapping("/captcha")
    public Result<CaptchaResp> getCaptcha(String type) {

        Assert.notBlank(type, "type不能为空");
        Assert.isTrue(KRAMRS_VIEW.equals(type) || KRAMRS_ADMIN.equals(type), "平台类型错误");

        //定义图形验证码的长、宽、验证码位数、干扰线数量
        LineCaptcha lineCaptcha = CaptchaUtil.createLineCaptcha(150, 50, 4, 80);
        //设置背景颜色
        lineCaptcha.setBackground(new Color(249, 251, 220));
        //生成四位验证码
        String code = RandomUtil.randomNumbers(4);

        Image image = lineCaptcha.createImage(code);

        String verifyCode = UUID.randomUUID().toString().replace("-", "");

        // 验证码存入redis
        redisService.setObject(RedisConstant.CODE_KEY + type + verifyCode, code, RedisConstant.CODE_EXPIRE_TIME, TimeUnit.MINUTES);

        CaptchaResp captchaResp = new CaptchaResp();
        captchaResp.setCaptchaUUID(verifyCode);

        BufferedImage bufferedImage = toBufferedImage(image);

        // 创建一个ByteArrayOutputStream，用于存储图片数据
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        try {
            // 写入图片数据到ByteArrayOutputStream
            ImageIO.write(bufferedImage, "jpeg", baos);

            captchaResp.setCode(Base64.encode(baos.toByteArray()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return Result.success(captchaResp);
    }
}
