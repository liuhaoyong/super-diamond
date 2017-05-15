package com.github.diamond.web.vo;

/**
 * Created by qinyin@wacai.com on 2015/6/23.
 */
public class CacheVo {
    private String cacheKey;
    private String md5Key;
    private String md5Value;

    public String getMd5Key() {
        return md5Key;
    }

    public void setMd5Key(String md5Key) {
        this.md5Key = md5Key;
    }

    public String getCacheKey() {
        return cacheKey;
    }

    public void setCacheKey(String cacheKey) {
        this.cacheKey = cacheKey;
    }

    public String getMd5Value() {
        return md5Value;
    }

    public void setMd5Value(String md5Value) {
        this.md5Value = md5Value;
    }

    @Override
    public String toString() {
        return "CacheVo{" +
                "cacheType='" + md5Key + '\'' +
                ", cacheKey='" + cacheKey + '\'' +
                ", md5Key='" + md5Value + '\'' +
                '}';
    }
}
