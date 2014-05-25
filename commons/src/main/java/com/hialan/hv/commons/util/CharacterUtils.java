package com.hialan.hv.commons.util;


import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public  class CharacterUtils {
    private static Log logger = LogFactory.getLog(CharacterUtils.class);

    public static boolean isChineseCharacter(String chineseStr) {
        char[] charArray = chineseStr.toCharArray();
        for (char aCharArray : charArray) {
            if ((aCharArray >= 0x4e00) && (aCharArray <= 0x9fbb)) {
                return true;
            }
        }
        return false;
    }


    public static String subString(String str, int len, String elide) {
        if (str == null) {
            return "";
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            if (len <= 0) {
                if (elide != null) {
                    sb.append(elide);
                }
                break;
            }
            String substring = str.substring(i, i + 1);
            if (isChineseCharacter(substring)) {
                len -= 2;
            } else {
                len -= 1;
            }
            sb.append(substring);
        }

        return sb.toString();
    }

    private static final String BLANK = " ";

    public static String toFullName(String firstName, String lastName) {
        String trimLastName = StringUtils.trimToEmpty(lastName);
        String trimFirstName = StringUtils.trimToEmpty(firstName);
        boolean emptyLast = trimLastName.length() == 0;
        boolean emptyFirst = trimFirstName.length() == 0;

        if (emptyLast && emptyFirst) {
            return StringUtils.EMPTY;
        } else {
            String fullName = trimLastName + trimFirstName;
            if (emptyLast || emptyFirst) {
                return fullName;
            } else {
                boolean hasChinese = CharacterUtils.isChineseCharacter(fullName);
                return new StringBuilder()
                        .append(hasChinese ? trimLastName : trimFirstName)
                        .append(BLANK)
                        .append(hasChinese ? trimFirstName : trimLastName)
                        .toString();
            }
        }
    }

    public static void main(String[] args) {
        System.out.println(CharacterUtils.toFullName("张", "三"));
        System.out.println(CharacterUtils.toFullName("san", "张"));
        System.out.println(CharacterUtils.toFullName("三丰", "张"));
        System.out.println(CharacterUtils.toFullName("Ted", "zhang"));
        System.out.println(CharacterUtils.toFullName("David", "Backham"));
    }
}
