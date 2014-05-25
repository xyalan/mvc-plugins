package com.hialan.hv.commons.lang;

import java.util.Locale;

public enum Language {

    zh_CN(0, "zh-CN", "zh", "cn", Locale.CHINA),
    en_US(1, "en-US", "en", "us", Locale.US);
//    zh_TW("zh_TW", "zh", "tw", Locale.TAIWAN),
//    ja_JP("ja_JP", "ja", "jp", Locale.JAPAN),
//    ko_KR("ko_KR", "ko", "kr", Locale.KOREA),
//    fr_FR("fr_FR", "fr", "fr", Locale.FRANCE);

    private int id;
    private String code;
    private String lang;
    private String country;
    private Locale locale;

    Language(int id, String code, String lang, String country, Locale locale) {
        this.id = id;
        this.code = code;
        this.lang = lang;
        this.country = country;
        this.locale = locale;
    }

    public static Language fromCode(String code) {
        for (Language language : Language.values()) {
            if (language.getCode().equals(code)) {
                return language;
            }
        }
        return null;
    }

    public boolean isZhCn() {
        return this == zh_CN;
    }

    public boolean isEnglish() {
        return this == en_US;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }
}