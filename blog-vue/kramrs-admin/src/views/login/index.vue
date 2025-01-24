<template>
  <div class="login">
    <el-form ref="ruleFormRef" :model="loginForm" :rules="rules" class="login-form">
      <h3 class="title">博客后台管理系统</h3>
      <el-form-item prop="username">
        <el-input v-model="loginForm.username" type="text" size="large" placeholder="账号">
          <template #prefix>
            <svg-icon icon-class="user"></svg-icon>
          </template>
        </el-input>
      </el-form-item>
      <el-form-item prop="password">
        <el-input v-model="loginForm.password" type="password" show-password size="large" placeholder="密码"
                  @keyup.enter="handleLogin(ruleFormRef)">
          <template #prefix>
            <svg-icon icon-class="password"></svg-icon>
          </template>
        </el-input>
      </el-form-item>
      <el-form-item prop="code">
        <el-input v-model="loginForm.code" type="text" size="large" placeholder="验证码"
                  @keyup.enter="handleLogin(ruleFormRef)" style="width: 63%">
          <template #prefix>
            <svg-icon icon-class="key"></svg-icon>
          </template>
        </el-input>
        <img :src="captcha" alt="codeImage" @click="initCaptcha" class="login-code-img"/>
      </el-form-item>
      <el-form-item>
        <el-button :loading="loading" type="primary" @click.prevent="handleLogin(ruleFormRef)"
                   style="width:100%;">
          <span v-if="!loading">登 录</span>
          <span v-else>登 录 中...</span>
        </el-button>
      </el-form-item>
    </el-form>
    <!--  底部  -->
    <div class="el-login-footer">
      <span>Copyright © 2022 - {{ new Date().getFullYear() }} By Kramrs</span>
    </div>
  </div>
</template>

<script setup lang="ts">
import router from "@/router";
import useStore from '@/store';
import {ElNotification, FormInstance, FormRules} from 'element-plus';
import {onMounted, reactive, ref} from 'vue';
import {LoginForm} from "@/api/login/types";
import {debounce} from "@/utils/debounce";
import {getCaptcha} from "@/api/login";
import {encodeRSA} from "@/utils/secret";

const {user} = useStore();
const ruleFormRef = ref<FormInstance>();
const loading = ref(false);
const captcha = ref("");
const loginForm = reactive<LoginForm>({
  username: "",
  password: "",
  code: "",
  captchaUUID: "",
  type: "KramrsAdmin"
});
const rules = reactive<FormRules>({
  username: [{required: true, message: "请输入用户名", trigger: "blur"}],
  password: [{required: true, message: "请输入密码", trigger: "blur"}, {
    min: 6,
    message: "密码不能少于6位",
    trigger: "blur"
  }],
  code: [{required: true, message: "请输入验证码", trigger: "blur"}],
});
const handleLogin = async (formEl: FormInstance | undefined) => {
  if (!formEl) return;
  await formEl.validate((valid) => {
    if (valid) {
      loading.value = true;
      const password = encodeRSA(loginForm.password);
      if (typeof password === 'string') {
        loginForm.password = password;
      }
      user.LogIn(loginForm).then(() => {
        router.push({path: "/"});
        loading.value = false;
        ElNotification({
          message: "登陆成功",
          type: "success",
          duration: 2 * 1000,
        });
      }).catch(() => {
        loading.value = false;
      });
    } else {
      return false;
    }
  })
};
const initCaptcha = debounce(() => {
  getCaptcha().then(({data}) => {
    if (data.flag) {
      captcha.value = "data:image/gif;base64," + data.data.code;
      loginForm.captchaUUID = data.data.captchaUUID;
    }
  });
}, 500);
onMounted(() => {
  initCaptcha();
});
</script>
<style lang="scss" scoped>
.login {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100%;
  background-image: url("https://picture.kramrs.space/config/5a08159479ba344dec5813e61fb6f79c.png");
  background-size: cover;
}

.title {
  margin: 0 auto 30px auto;
  text-align: center;
  color: #707070;
}

.login-form {
  border-radius: 6px;
  background: #ffffff;
  width: 400px;
  padding: 25px 25px 5px 25px;
}

.login-tip {
  font-size: 13px;
  text-align: center;
  color: #bfbfbf;
}

.el-login-footer {
  height: 40px;
  line-height: 40px;
  position: fixed;
  bottom: 0;
  width: 100%;
  text-align: center;
  color: #fff;
  font-family: Arial;
  font-size: 12px;
  letter-spacing: 1px;
}

.login-code-img {
  height: 38px;
  margin-left: 10px;
}
</style>