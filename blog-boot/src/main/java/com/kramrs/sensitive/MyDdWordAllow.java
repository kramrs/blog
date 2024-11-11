package com.kramrs.sensitive;

import com.github.houbb.sensitive.word.api.IWordAllow;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: kramrs
 * @Description: 敏感测白名单
 */
@Component
public class MyDdWordAllow implements IWordAllow {

    @Override
    public List<String> allow() {
        List<String> list = new ArrayList<>();
        list.add("消息");
        return list;
    }

}
