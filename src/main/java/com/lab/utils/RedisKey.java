package com.lab.utils;

public class RedisKey {
    public static String nekoProfile(String uuid) {
        return String.format("app:neko:profile:%s", uuid);
    }
}
