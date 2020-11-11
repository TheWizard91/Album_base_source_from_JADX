package com.fasterxml.jackson.databind.util;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

public class ISO8601Utils {
    private static final String GMT_ID = "GMT";
    private static final TimeZone TIMEZONE_GMT = TimeZone.getTimeZone(GMT_ID);

    public static TimeZone timeZoneGMT() {
        return TIMEZONE_GMT;
    }

    public static String format(Date date) {
        return format(date, false, TIMEZONE_GMT);
    }

    public static String format(Date date, boolean z) {
        return format(date, z, TIMEZONE_GMT);
    }

    public static String format(Date date, boolean z, TimeZone timeZone) {
        GregorianCalendar gregorianCalendar = new GregorianCalendar(timeZone, Locale.US);
        gregorianCalendar.setTime(date);
        StringBuilder sb = new StringBuilder("yyyy-MM-ddThh:mm:ss".length() + (z ? ".sss".length() : 0) + (timeZone.getRawOffset() == 0 ? "Z" : "+hh:mm").length());
        padInt(sb, gregorianCalendar.get(1), "yyyy".length());
        char c = '-';
        sb.append('-');
        padInt(sb, gregorianCalendar.get(2) + 1, "MM".length());
        sb.append('-');
        padInt(sb, gregorianCalendar.get(5), "dd".length());
        sb.append('T');
        padInt(sb, gregorianCalendar.get(11), "hh".length());
        sb.append(':');
        padInt(sb, gregorianCalendar.get(12), "mm".length());
        sb.append(':');
        padInt(sb, gregorianCalendar.get(13), "ss".length());
        if (z) {
            sb.append('.');
            padInt(sb, gregorianCalendar.get(14), "sss".length());
        }
        int offset = timeZone.getOffset(gregorianCalendar.getTimeInMillis());
        if (offset != 0) {
            int i = offset / 60000;
            int abs = Math.abs(i / 60);
            int abs2 = Math.abs(i % 60);
            if (offset >= 0) {
                c = '+';
            }
            sb.append(c);
            padInt(sb, abs, "hh".length());
            sb.append(':');
            padInt(sb, abs2, "mm".length());
        } else {
            sb.append('Z');
        }
        return sb.toString();
    }

    public static Date parse(String str) {
        int i;
        String str2 = str;
        try {
            int parseInt = parseInt(str2, 0, 4);
            checkOffset(str2, 4, '-');
            int parseInt2 = parseInt(str2, 5, 7);
            checkOffset(str2, 7, '-');
            int parseInt3 = parseInt(str2, 8, 10);
            checkOffset(str2, 10, 'T');
            int parseInt4 = parseInt(str2, 11, 13);
            checkOffset(str2, 13, ':');
            int parseInt5 = parseInt(str2, 14, 16);
            checkOffset(str2, 16, ':');
            int i2 = 19;
            int parseInt6 = parseInt(str2, 17, 19);
            if (str2.charAt(19) == '.') {
                checkOffset(str2, 19, '.');
                i = parseInt(str2, 20, 23);
                i2 = 23;
            } else {
                i = 0;
            }
            char charAt = str2.charAt(i2);
            String str3 = GMT_ID;
            if (charAt == '+' || charAt == '-') {
                str3 = str3 + str2.substring(i2);
            } else if (charAt != 'Z') {
                throw new IndexOutOfBoundsException("Invalid time zone indicator " + charAt);
            }
            TimeZone timeZone = TimeZone.getTimeZone(str3);
            if (timeZone.getID().equals(str3)) {
                GregorianCalendar gregorianCalendar = new GregorianCalendar(timeZone);
                gregorianCalendar.setLenient(false);
                gregorianCalendar.set(1, parseInt);
                gregorianCalendar.set(2, parseInt2 - 1);
                gregorianCalendar.set(5, parseInt3);
                gregorianCalendar.set(11, parseInt4);
                gregorianCalendar.set(12, parseInt5);
                gregorianCalendar.set(13, parseInt6);
                gregorianCalendar.set(14, i);
                return gregorianCalendar.getTime();
            }
            throw new IndexOutOfBoundsException();
        } catch (IndexOutOfBoundsException e) {
            throw new IllegalArgumentException("Failed to parse date " + str2, e);
        } catch (NumberFormatException e2) {
            throw new IllegalArgumentException("Failed to parse date " + str2, e2);
        } catch (IllegalArgumentException e3) {
            throw new IllegalArgumentException("Failed to parse date " + str2, e3);
        }
    }

    private static void checkOffset(String str, int i, char c) throws IndexOutOfBoundsException {
        char charAt = str.charAt(i);
        if (charAt != c) {
            throw new IndexOutOfBoundsException("Expected '" + c + "' character but found '" + charAt + "'");
        }
    }

    private static int parseInt(String str, int i, int i2) throws NumberFormatException {
        if (i < 0 || i2 > str.length() || i > i2) {
            throw new NumberFormatException(str);
        }
        int i3 = 0;
        if (i < i2) {
            int i4 = i + 1;
            int digit = Character.digit(str.charAt(i), 10);
            if (digit >= 0) {
                int i5 = i4;
                i3 = -digit;
                i = i5;
            } else {
                throw new NumberFormatException("Invalid number: " + str);
            }
        }
        while (i < i2) {
            int i6 = i + 1;
            int digit2 = Character.digit(str.charAt(i), 10);
            if (digit2 >= 0) {
                i3 = (i3 * 10) - digit2;
                i = i6;
            } else {
                throw new NumberFormatException("Invalid number: " + str);
            }
        }
        return -i3;
    }

    private static void padInt(StringBuilder sb, int i, int i2) {
        String num = Integer.toString(i);
        for (int length = i2 - num.length(); length > 0; length--) {
            sb.append('0');
        }
        sb.append(num);
    }
}
