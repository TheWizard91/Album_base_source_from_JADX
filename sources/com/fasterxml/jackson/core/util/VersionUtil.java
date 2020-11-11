package com.fasterxml.jackson.core.util;

import com.bumptech.glide.load.Key;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.core.Versioned;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Properties;
import java.util.regex.Pattern;

public class VersionUtil {
    public static final String PACKAGE_VERSION_CLASS_NAME = "PackageVersion";
    @Deprecated
    public static final String VERSION_FILE = "VERSION.txt";
    private static final Pattern VERSION_SEPARATOR = Pattern.compile("[-_./;:]");
    private final Version _version;

    protected VersionUtil() {
        Version version;
        try {
            version = versionFor(getClass());
        } catch (Exception e) {
            System.err.println("ERROR: Failed to load Version information for bundle (via " + getClass().getName() + ").");
            version = null;
        }
        this._version = version == null ? Version.unknownVersion() : version;
    }

    public Version version() {
        return this._version;
    }

    public static Version versionFor(Class<?> cls) {
        InputStreamReader inputStreamReader;
        Version packageVersionFor = packageVersionFor(cls);
        if (packageVersionFor != null) {
            return packageVersionFor;
        }
        InputStream resourceAsStream = cls.getResourceAsStream(VERSION_FILE);
        if (resourceAsStream == null) {
            return Version.unknownVersion();
        }
        try {
            inputStreamReader = new InputStreamReader(resourceAsStream, Key.STRING_CHARSET_NAME);
            Version doReadVersion = doReadVersion(inputStreamReader);
            try {
                inputStreamReader.close();
            } catch (IOException e) {
            }
            try {
                resourceAsStream.close();
                return doReadVersion;
            } catch (IOException e2) {
                throw new RuntimeException(e2);
            }
        } catch (UnsupportedEncodingException e3) {
            try {
                try {
                    return Version.unknownVersion();
                } catch (IOException e4) {
                    throw new RuntimeException(e4);
                }
            } finally {
                try {
                    resourceAsStream.close();
                } catch (IOException e42) {
                    throw new RuntimeException(e42);
                }
            }
        } catch (Throwable th) {
            try {
                inputStreamReader.close();
            } catch (IOException e5) {
            }
            throw th;
        }
    }

    public static Version packageVersionFor(Class<?> cls) {
        try {
            Class<?> cls2 = Class.forName(cls.getPackage().getName() + "." + PACKAGE_VERSION_CLASS_NAME, true, cls.getClassLoader());
            if (cls2 == null) {
                return null;
            }
            try {
                Object newInstance = cls2.newInstance();
                if (newInstance instanceof Versioned) {
                    return ((Versioned) newInstance).version();
                }
                throw new IllegalArgumentException("Bad version class " + cls2.getName() + ": does not implement " + Versioned.class.getName());
            } catch (RuntimeException e) {
                throw e;
            } catch (Exception e2) {
                throw new IllegalArgumentException("Failed to instantiate " + cls2.getName() + " to find version information, problem: " + e2.getMessage(), e2);
            }
        } catch (Exception e3) {
            return null;
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:18:0x002b, code lost:
        r5 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:20:?, code lost:
        r0.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:24:0x0033, code lost:
        r1 = null;
        r2 = null;
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Removed duplicated region for block: B:18:0x002b A[ExcHandler: all (r5v7 'th' java.lang.Throwable A[CUSTOM_DECLARE]), Splitter:B:1:0x0007] */
    /* JADX WARNING: Removed duplicated region for block: B:30:0x0040  */
    /* JADX WARNING: Removed duplicated region for block: B:32:0x0046  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static com.fasterxml.jackson.core.Version doReadVersion(java.io.Reader r5) {
        /*
            java.io.BufferedReader r0 = new java.io.BufferedReader
            r0.<init>(r5)
            r5 = 0
            java.lang.String r1 = r0.readLine()     // Catch:{ IOException -> 0x0032, all -> 0x002b }
            if (r1 == 0) goto L_0x0024
            java.lang.String r2 = r0.readLine()     // Catch:{ IOException -> 0x0021, all -> 0x002b }
            if (r2 == 0) goto L_0x001d
            java.lang.String r5 = r0.readLine()     // Catch:{ IOException -> 0x001b, all -> 0x002b }
            r4 = r2
            r2 = r5
            r5 = r4
            goto L_0x0025
        L_0x001b:
            r3 = move-exception
            goto L_0x0035
        L_0x001d:
            r4 = r2
            r2 = r5
            r5 = r4
            goto L_0x0025
        L_0x0021:
            r2 = move-exception
            r2 = r5
            goto L_0x0035
        L_0x0024:
            r2 = r5
        L_0x0025:
            r0.close()     // Catch:{ IOException -> 0x0029 }
            goto L_0x003e
        L_0x0029:
            r0 = move-exception
            goto L_0x003e
        L_0x002b:
            r5 = move-exception
            r0.close()     // Catch:{ IOException -> 0x0030 }
            goto L_0x0031
        L_0x0030:
            r0 = move-exception
        L_0x0031:
            throw r5
        L_0x0032:
            r1 = move-exception
            r1 = r5
            r2 = r1
        L_0x0035:
            r0.close()     // Catch:{ IOException -> 0x0039 }
            goto L_0x003b
        L_0x0039:
            r0 = move-exception
        L_0x003b:
            r4 = r2
            r2 = r5
            r5 = r4
        L_0x003e:
            if (r5 == 0) goto L_0x0044
            java.lang.String r5 = r5.trim()
        L_0x0044:
            if (r2 == 0) goto L_0x004a
            java.lang.String r2 = r2.trim()
        L_0x004a:
            com.fasterxml.jackson.core.Version r5 = parseVersion(r1, r5, r2)
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.fasterxml.jackson.core.util.VersionUtil.doReadVersion(java.io.Reader):com.fasterxml.jackson.core.Version");
    }

    public static Version mavenVersionFor(ClassLoader classLoader, String str, String str2) {
        InputStream resourceAsStream = classLoader.getResourceAsStream("META-INF/maven/" + str.replaceAll("\\.", "/") + "/" + str2 + "/pom.properties");
        if (resourceAsStream != null) {
            try {
                Properties properties = new Properties();
                properties.load(resourceAsStream);
                Version parseVersion = parseVersion(properties.getProperty("version"), properties.getProperty("groupId"), properties.getProperty("artifactId"));
                try {
                    resourceAsStream.close();
                } catch (IOException e) {
                }
                return parseVersion;
            } catch (IOException e2) {
                try {
                    resourceAsStream.close();
                } catch (IOException e3) {
                }
            } catch (Throwable th) {
                try {
                    resourceAsStream.close();
                } catch (IOException e4) {
                }
                throw th;
            }
        }
        return Version.unknownVersion();
    }

    @Deprecated
    public static Version parseVersion(String str) {
        return parseVersion(str, (String) null, (String) null);
    }

    public static Version parseVersion(String str, String str2, String str3) {
        String str4 = null;
        if (str == null) {
            return null;
        }
        String trim = str.trim();
        if (trim.length() == 0) {
            return null;
        }
        String[] split = VERSION_SEPARATOR.split(trim);
        int i = 0;
        int parseVersionPart = parseVersionPart(split[0]);
        int parseVersionPart2 = split.length > 1 ? parseVersionPart(split[1]) : 0;
        if (split.length > 2) {
            i = parseVersionPart(split[2]);
        }
        int i2 = i;
        if (split.length > 3) {
            str4 = split[3];
        }
        return new Version(parseVersionPart, parseVersionPart2, i2, str4, str2, str3);
    }

    protected static int parseVersionPart(String str) {
        String str2 = str.toString();
        int length = str2.length();
        int i = 0;
        for (int i2 = 0; i2 < length; i2++) {
            char charAt = str2.charAt(i2);
            if (charAt > '9' || charAt < '0') {
                break;
            }
            i = (i * 10) + (charAt - '0');
        }
        return i;
    }

    public static final void throwInternal() {
        throw new RuntimeException("Internal error: this code path should never get executed");
    }
}
