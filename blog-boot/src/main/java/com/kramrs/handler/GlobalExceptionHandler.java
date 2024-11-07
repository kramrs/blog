package com.kramrs.handler;

import cn.dev33.satoken.exception.DisableServiceException;
import cn.dev33.satoken.exception.NotLoginException;
import cn.dev33.satoken.exception.NotPermissionException;
import cn.dev33.satoken.exception.NotRoleException;
import com.kramrs.exception.ServiceException;
import com.kramrs.model.vo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Objects;

import static com.kramrs.enums.StatusCodeEnum.*;

/**
 * @Author: kramrs
 * @Description: 全局异常处理
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 处理业务异常
     */
    @ExceptionHandler(value = ServiceException.class)
    public Result<?> handleServiceException(ServiceException e) {
        log.error("", e);
        return Result.fail(e.getMessage());
    }

    /**
     * 处理Assert异常
     */
    @ExceptionHandler(value = IllegalArgumentException.class)
    public Result<?> handleIllegalArgumentException(IllegalArgumentException e) {
        log.error("", e);
        return Result.fail(e.getMessage());
    }

    /**
     * 处理参数校验异常
     */
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public Result<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error("", e);
        return Result.fail(VALID_ERROR.getCode(), Objects.requireNonNull(e.getBindingResult().getFieldError()).getDefaultMessage());
    }

    /**
     * 处理权限不足
     */
    @ExceptionHandler(value = NotPermissionException.class)
    public Result<?> handleNotPermissionException(NotPermissionException npe) {
        log.error("", npe);
        return Result.fail("权限不足");
    }

    /**
     * 处理账号封禁
     */
    @ExceptionHandler(value = DisableServiceException.class)
    public Result<?> handleDisableServiceExceptionException(DisableServiceException dse) {
        log.error("", dse);
        return Result.fail("此账号已被禁止访问服务");
    }

    /**
     * 处理无此角色异常
     */
    @ExceptionHandler(value = NotRoleException.class)
    public Result<?> handleNotRoleException(NotRoleException nre) {
        log.error("", nre);
        return Result.fail("权限不足");
    }

    /**
     * 处理SaToken异常
     */
    @ExceptionHandler(value = NotLoginException.class)
    public Result<?> handlerNotLoginException(NotLoginException nle) {
        // 判断场景值，定制化异常信息
        String message;
        if (nle.getType().equals(NotLoginException.NOT_TOKEN)) {
            message = "未提供token";
        } else if (nle.getType().equals(NotLoginException.INVALID_TOKEN)) {
            message = "token无效";
        } else if (nle.getType().equals(NotLoginException.TOKEN_TIMEOUT)) {
            message = "token已过期";
        } else {
            message = "当前会话未登录";
        }
        log.error("", nle);
        // 返回给前端
        return Result.fail(UNAUTHORIZED.getCode(), message);
    }

    /**
     * 处理系统异常
     */
    @ExceptionHandler(value = Exception.class)
    public Result<?> handleSystemException(Exception e) {
        log.error("", e);
        return Result.fail(SYSTEM_ERROR.getCode(), SYSTEM_ERROR.getMsg());
    }

}