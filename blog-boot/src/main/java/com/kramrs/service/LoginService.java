package com.kramrs.service;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.captcha.generator.RandomGenerator;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.lang.Validator;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.kramrs.constant.CommonConstant;
import com.kramrs.constant.MqConstant;
import com.kramrs.constant.RedisConstant;
import com.kramrs.entity.SiteConfig;
import com.kramrs.entity.User;
import com.kramrs.entity.UserRole;
import com.kramrs.enums.LoginTypeEnum;
import com.kramrs.enums.RoleEnum;
import com.kramrs.mapper.UserMapper;
import com.kramrs.mapper.UserRoleMapper;
import com.kramrs.model.dto.MailDTO;
import com.kramrs.model.vo.request.CodeReq;
import com.kramrs.model.vo.request.LoginReq;
import com.kramrs.model.vo.request.RegisterReq;
import com.kramrs.strategy.context.SocialLoginStrategyContext;
import com.kramrs.utils.RSAUtil;
import com.kramrs.utils.SecurityUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.TimeUnit;

import static com.kramrs.constant.CommonConstant.KRAMRS_ADMIN;
import static com.kramrs.constant.CommonConstant.KRAMRS_VIEW;

/**
 * @Author: kramrs
 * @Description: 登录服务
 */
@Slf4j
@Service
public class LoginService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Autowired
    private RabbitTemplate rabbitTemplate;

//    @Autowired
//    private EmailService emailService;

    @Autowired
    private RedisService redisService;

    @Autowired
    private SocialLoginStrategyContext socialLoginStrategyContext;

//    @Autowired
//    private ThreadPoolTaskExecutor threadPoolTaskExecutor;

    public String login(LoginReq login) throws Exception {
        // 校验验证码
        verifyCaptcha(login);
        String password = RSAUtil.decrypt(login.getPassword(), RSAUtil.private_key);
        login.setPassword(password);
        User user = userMapper.selectOne(new LambdaQueryWrapper<User>()
                .select(User::getId)
                .eq(User::getUsername, login.getUsername())
                .eq(User::getPassword, SecurityUtils.sha256Encrypt(login.getPassword())));
        Assert.notNull(user, "用户不存在或密码错误");
        // 校验指定账号是否已被封禁，如果被封禁则抛出异常 `DisableServiceException`
        StpUtil.checkDisable(user.getId());
        // 通过校验后，再进行登录
        StpUtil.login(user.getId());
        return StpUtil.getTokenValue();
    }

    /**
     * 校验验证码
     *
     * @param loginReq 登录信息
     */
    public void verifyCaptcha(LoginReq loginReq) {
        Assert.isTrue(KRAMRS_VIEW.equals(loginReq.getType()) || KRAMRS_ADMIN.equals(loginReq.getType()), "平台类型错误");
        String redisKey = RedisConstant.CODE_KEY + loginReq.getType() + loginReq.getCaptchaUUID();
        String sysCode = redisService.getObject(redisKey);
        Assert.notBlank(sysCode, "验证码已过期！");
        Assert.isTrue(sysCode.equals(loginReq.getCode()), "验证码错误，请重新输入！");
//        redisService.deleteObject(redisKey);
    }

    public void sendCode(String username) {
        Assert.isTrue(Validator.isEmail(username), "请输入正确的邮箱！");
        RandomGenerator randomGenerator = new RandomGenerator("0123456789", 6);
        String code = randomGenerator.generate();
        MailDTO mailDTO = MailDTO.builder()
                .toEmail(username)
                .subject(CommonConstant.CAPTCHA)
                .content("您的验证码为 " + code + " 有效期为" + RedisConstant.CODE_EXPIRE_TIME + "分钟")
                .build();
        // 验证码存入消息队列
        rabbitTemplate.convertAndSend(MqConstant.EMAIL_EXCHANGE, MqConstant.EMAIL_SIMPLE_KEY, mailDTO);
//        CompletableFuture.runAsync(() -> emailService.sendSimpleMail(mailDTO), threadPoolTaskExecutor);
        // 验证码存入redis
        redisService.setObject(RedisConstant.EMAIL_CODE_KEY + username, code, RedisConstant.CODE_EXPIRE_TIME, TimeUnit.MINUTES);
    }

    @Transactional(rollbackFor = Exception.class)
    public void register(RegisterReq register) {
        verifyEmailCode(register.getUsername(), register.getCode());
        User user = userMapper.selectOne(new LambdaQueryWrapper<User>()
                .select(User::getUsername)
                .eq(User::getUsername, register.getUsername()));
        Assert.isNull(user, "邮箱已注册！");
        SiteConfig siteConfig = redisService.getObject(RedisConstant.SITE_SETTING);
        // 添加用户
        User newUser = User.builder()
                .username(register.getUsername())
                .email(register.getUsername())
                .nickname(CommonConstant.USER_NICKNAME + IdWorker.getId())
                .avatar(siteConfig.getUserAvatar())
                .password(SecurityUtils.sha256Encrypt(register.getPassword()))
                .loginType(LoginTypeEnum.EMAIL.getLoginType())
                .isDisable(CommonConstant.FALSE)
                .build();
        userMapper.insert(newUser);
        // 绑定用户角色
        UserRole userRole = UserRole.builder()
                .userId(newUser.getId())
                .roleId(RoleEnum.USER.getRoleId())
                .build();
        userRoleMapper.insert(userRole);
    }

    @Transactional(rollbackFor = Exception.class)
    public String giteeLogin(CodeReq data) {
        return socialLoginStrategyContext.executeLoginStrategy(data, LoginTypeEnum.GITEE);
    }

    @Transactional(rollbackFor = Exception.class)
    public String githubLogin(CodeReq data) {
        return socialLoginStrategyContext.executeLoginStrategy(data, LoginTypeEnum.GITHUB);
    }

    @Transactional(rollbackFor = Exception.class)
    public String qqLogin(CodeReq data) {
        return socialLoginStrategyContext.executeLoginStrategy(data, LoginTypeEnum.QQ);
    }

    /**
     * 校验验证码
     *
     * @param username 用户名
     * @param code     验证码
     */
    public void verifyEmailCode(String username, String code) {
        String sysCode = redisService.getObject(RedisConstant.EMAIL_CODE_KEY + username);
        Assert.notBlank(sysCode, "验证码未发送或已过期！");
        Assert.isTrue(sysCode.equals(code), "验证码错误，请重新输入！");
    }

}
