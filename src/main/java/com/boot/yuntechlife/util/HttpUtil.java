package com.boot.yuntechlife.util;

import org.springframework.util.MultiValueMap;

import java.io.IOException;

public interface HttpUtil {
    String get(String url, MultiValueMap<String, String> params);

    String post(String url, MultiValueMap<String, String> params);
}
