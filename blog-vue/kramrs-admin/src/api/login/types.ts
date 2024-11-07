/**
 * 登录信息
 */
export interface LoginForm {
    /**
     * 用户名
     */
    username: string;
    /**
     * 密码
     */
    password: string;
    /**
     * 验证码
     */
    code: string;
    /**
     * 验证码UUID
     */
    captchaUUID: string;
    /**
     * 平台类型
     */
    type: string;
}

/**
 * 验证码信息
 */
export interface CaptchaInfo {
    /**
     * 验证码UUID
     */
    captchaUUID: string;
    /**
     * 验证码
     */
    code: string;
}
