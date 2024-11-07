package com.kramrs;

import com.kramrs.model.vo.response.UserBackResp;
import com.kramrs.utils.RSAUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BlogBootApplicationTests {

    @Test
    void contextLoads() {
        UserBackResp userBackResp = new UserBackResp();
        userBackResp.setIpAddress("192.168.1.1");
        userBackResp.setIpSource("山东省");
        userBackResp.setId(0);
        System.out.println(userBackResp);
    }

    @Test
    void test1() {
        //解密数据
        try {
            //生成公钥和私钥
//            RSAUtil.genKeyPair();
//            String publicKey = RSAUtil.keyMap.get(0);
//            //打印出来自己记录下
//            System.out.println("公钥:" + publicKey);
//            String privateKey = RSAUtil.keyMap.get(1);
//            //打印出来自己记录下
//            System.out.println("私钥:" + privateKey);

            //获取到后，可以放这里，测试下能不能正确加解密
//            publicKey = RSAUtil.public_key;
//            privateKey = RSAUtil.private_key;

            String orgData = "test";
            System.out.println("原数据：" + orgData);

            //加密
            String encryptStr = RSAUtil.encrypt(orgData, RSAUtil.public_key);
            System.out.println("加密结果：" + encryptStr);

            //解密
            String decryptStr = RSAUtil.decrypt(encryptStr, RSAUtil.private_key);
            System.out.println("解密结果：" + decryptStr);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
