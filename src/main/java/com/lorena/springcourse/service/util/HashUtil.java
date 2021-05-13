package com.lorena.springcourse.service.util;

import org.apache.commons.codec.digest.DigestUtils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class HashUtil {

    public static String getSecureHash(String text){
         return DigestUtils.sha256Hex(text);
    }
}
