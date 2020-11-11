package androidx.navigation;

import android.net.Uri;
import android.os.Bundle;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class NavDeepLink {
    private static final Pattern SCHEME_PATTERN = Pattern.compile("^[a-zA-Z]+[+\\w\\-.]*:");
    private final String mAction;
    private final ArrayList<String> mArguments;
    private boolean mExactDeepLink;
    private boolean mIsParameterizedQuery;
    private final String mMimeType;
    private Pattern mMimeTypePattern;
    private final Map<String, ParamQuery> mParamArgMap;
    private Pattern mPattern;
    private final String mUri;

    NavDeepLink(String uri, String action, String mimeType) {
        String str = uri;
        String str2 = mimeType;
        this.mArguments = new ArrayList<>();
        this.mParamArgMap = new HashMap();
        this.mPattern = null;
        this.mExactDeepLink = false;
        this.mIsParameterizedQuery = false;
        this.mMimeTypePattern = null;
        this.mUri = str;
        this.mAction = action;
        this.mMimeType = str2;
        if (str != null) {
            Uri parameterizedUri = Uri.parse(uri);
            int i = 1;
            this.mIsParameterizedQuery = parameterizedUri.getQuery() != null;
            StringBuilder uriRegex = new StringBuilder("^");
            if (!SCHEME_PATTERN.matcher(str).find()) {
                uriRegex.append("http[s]?://");
            }
            Pattern fillInPattern = Pattern.compile("\\{(.+?)\\}");
            if (this.mIsParameterizedQuery) {
                Matcher matcher = Pattern.compile("(\\?)").matcher(str);
                if (matcher.find()) {
                    buildPathRegex(str.substring(0, matcher.start()), uriRegex, fillInPattern);
                }
                this.mExactDeepLink = false;
                for (String paramName : parameterizedUri.getQueryParameterNames()) {
                    StringBuilder argRegex = new StringBuilder();
                    String queryParam = parameterizedUri.getQueryParameter(paramName);
                    Matcher matcher2 = fillInPattern.matcher(queryParam);
                    int appendPos = 0;
                    ParamQuery param = new ParamQuery();
                    while (matcher2.find()) {
                        param.addArgumentName(matcher2.group(i));
                        argRegex.append(Pattern.quote(queryParam.substring(appendPos, matcher2.start())));
                        argRegex.append("(.+?)?");
                        appendPos = matcher2.end();
                        String str3 = action;
                        i = 1;
                    }
                    ParamQuery param2 = param;
                    if (appendPos < queryParam.length()) {
                        argRegex.append(Pattern.quote(queryParam.substring(appendPos)));
                    }
                    param2.setParamRegex(argRegex.toString().replace(".*", "\\E.*\\Q"));
                    this.mParamArgMap.put(paramName, param2);
                    String str4 = action;
                    i = 1;
                }
            } else {
                this.mExactDeepLink = buildPathRegex(str, uriRegex, fillInPattern);
            }
            this.mPattern = Pattern.compile(uriRegex.toString().replace(".*", "\\E.*\\Q"));
        }
        if (str2 == null) {
            return;
        }
        if (Pattern.compile("^[\\s\\S]+/[\\s\\S]+$").matcher(str2).matches()) {
            MimeType splitMimeType = new MimeType(str2);
            this.mMimeTypePattern = Pattern.compile(("^(" + splitMimeType.mType + "|[*]+)/(" + splitMimeType.mSubType + "|[*]+)$").replace("*|[*]", "[\\s\\S]"));
            return;
        }
        throw new IllegalArgumentException("The given mimeType " + str2 + " does not match to required \"type/subtype\" format");
    }

    NavDeepLink(String uri) {
        this(uri, (String) null, (String) null);
    }

    private boolean buildPathRegex(String uri, StringBuilder uriRegex, Pattern fillInPattern) {
        Matcher matcher = fillInPattern.matcher(uri);
        int appendPos = 0;
        boolean exactDeepLink = !uri.contains(".*");
        while (matcher.find()) {
            this.mArguments.add(matcher.group(1));
            uriRegex.append(Pattern.quote(uri.substring(appendPos, matcher.start())));
            uriRegex.append("(.+?)");
            appendPos = matcher.end();
            exactDeepLink = false;
        }
        if (appendPos < uri.length()) {
            uriRegex.append(Pattern.quote(uri.substring(appendPos)));
        }
        uriRegex.append("($|(\\?(.)*))");
        return exactDeepLink;
    }

    /* access modifiers changed from: package-private */
    public boolean matches(Uri uri) {
        return matches(new NavDeepLinkRequest(uri, (String) null, (String) null));
    }

    /* access modifiers changed from: package-private */
    public boolean matches(NavDeepLinkRequest deepLinkRequest) {
        if (matchUri(deepLinkRequest.getUri()) && matchAction(deepLinkRequest.getAction())) {
            return matchMimeType(deepLinkRequest.getMimeType());
        }
        return false;
    }

    private boolean matchUri(Uri uri) {
        boolean z = uri == null;
        Pattern pattern = this.mPattern;
        if (z == (pattern != null)) {
            return false;
        }
        if (uri == null || pattern.matcher(uri.toString()).matches()) {
            return true;
        }
        return false;
    }

    private boolean matchAction(String action) {
        boolean z = action == null;
        String str = this.mAction;
        if (z == (str != null)) {
            return false;
        }
        if (action == null || str.equals(action)) {
            return true;
        }
        return false;
    }

    private boolean matchMimeType(String mimeType) {
        if ((mimeType == null) == (this.mMimeType != null)) {
            return false;
        }
        if (mimeType == null || this.mMimeTypePattern.matcher(mimeType).matches()) {
            return true;
        }
        return false;
    }

    /* access modifiers changed from: package-private */
    public boolean isExactDeepLink() {
        return this.mExactDeepLink;
    }

    public String getUriPattern() {
        return this.mUri;
    }

    public String getAction() {
        return this.mAction;
    }

    public String getMimeType() {
        return this.mMimeType;
    }

    /* access modifiers changed from: package-private */
    public int getMimeTypeMatchRating(String mimeType) {
        if (this.mMimeType == null || !this.mMimeTypePattern.matcher(mimeType).matches()) {
            return -1;
        }
        return new MimeType(this.mMimeType).compareTo(new MimeType(mimeType));
    }

    /* access modifiers changed from: package-private */
    public Bundle getMatchingArguments(Uri deepLink, Map<String, NavArgument> arguments) {
        Map<String, NavArgument> map = arguments;
        Matcher matcher = this.mPattern.matcher(deepLink.toString());
        Bundle bundle = null;
        if (!matcher.matches()) {
            return null;
        }
        Bundle bundle2 = new Bundle();
        int size = this.mArguments.size();
        for (int index = 0; index < size; index++) {
            String argumentName = this.mArguments.get(index);
            if (parseArgument(bundle2, argumentName, Uri.decode(matcher.group(index + 1)), map.get(argumentName))) {
                return null;
            }
        }
        if (this.mIsParameterizedQuery != 0) {
            for (String paramName : this.mParamArgMap.keySet()) {
                Matcher argMatcher = null;
                ParamQuery storedParam = this.mParamArgMap.get(paramName);
                String inputParams = deepLink.getQueryParameter(paramName);
                if (inputParams != null) {
                    argMatcher = Pattern.compile(storedParam.getParamRegex()).matcher(inputParams);
                    if (!argMatcher.matches()) {
                        return bundle;
                    }
                }
                int index2 = 0;
                while (index2 < storedParam.size()) {
                    String value = null;
                    if (argMatcher != null) {
                        value = Uri.decode(argMatcher.group(index2 + 1));
                    }
                    String argName = storedParam.getArgumentName(index2);
                    NavArgument argument = map.get(argName);
                    if (argument != null && (value == null || value.replaceAll("[{}]", "").equals(argName))) {
                        if (argument.getDefaultValue() != null) {
                            value = argument.getDefaultValue().toString();
                        } else if (argument.isNullable()) {
                            value = null;
                        }
                    }
                    if (parseArgument(bundle2, argName, value, argument)) {
                        return null;
                    }
                    index2++;
                    bundle = null;
                    map = arguments;
                }
                Bundle bundle3 = bundle;
                map = arguments;
            }
            Uri uri = deepLink;
        } else {
            Uri uri2 = deepLink;
        }
        return bundle2;
    }

    private boolean parseArgument(Bundle bundle, String name, String value, NavArgument argument) {
        if (argument != null) {
            try {
                argument.getType().parseAndPut(bundle, name, value);
                return false;
            } catch (IllegalArgumentException e) {
                return true;
            }
        } else {
            bundle.putString(name, value);
            return false;
        }
    }

    private static class ParamQuery {
        private ArrayList<String> mArguments = new ArrayList<>();
        private String mParamRegex;

        ParamQuery() {
        }

        /* access modifiers changed from: package-private */
        public void setParamRegex(String paramRegex) {
            this.mParamRegex = paramRegex;
        }

        /* access modifiers changed from: package-private */
        public String getParamRegex() {
            return this.mParamRegex;
        }

        /* access modifiers changed from: package-private */
        public void addArgumentName(String name) {
            this.mArguments.add(name);
        }

        /* access modifiers changed from: package-private */
        public String getArgumentName(int index) {
            return this.mArguments.get(index);
        }

        public int size() {
            return this.mArguments.size();
        }
    }

    private static class MimeType implements Comparable<MimeType> {
        String mSubType;
        String mType;

        MimeType(String mimeType) {
            String[] typeAndSubType = mimeType.split("/", -1);
            this.mType = typeAndSubType[0];
            this.mSubType = typeAndSubType[1];
        }

        public int compareTo(MimeType o) {
            int result = 0;
            if (this.mType.equals(o.mType)) {
                result = 0 + 2;
            }
            if (this.mSubType.equals(o.mSubType)) {
                return result + 1;
            }
            return result;
        }
    }

    public static final class Builder {
        private String mAction;
        private String mMimeType;
        private String mUriPattern;

        Builder() {
        }

        public static Builder fromUriPattern(String uriPattern) {
            Builder builder = new Builder();
            builder.setUriPattern(uriPattern);
            return builder;
        }

        public static Builder fromAction(String action) {
            if (!action.isEmpty()) {
                Builder builder = new Builder();
                builder.setAction(action);
                return builder;
            }
            throw new IllegalArgumentException("The NavDeepLink cannot have an empty action.");
        }

        public static Builder fromMimeType(String mimeType) {
            Builder builder = new Builder();
            builder.setMimeType(mimeType);
            return builder;
        }

        public Builder setUriPattern(String uriPattern) {
            this.mUriPattern = uriPattern;
            return this;
        }

        public Builder setAction(String action) {
            if (!action.isEmpty()) {
                this.mAction = action;
                return this;
            }
            throw new IllegalArgumentException("The NavDeepLink cannot have an empty action.");
        }

        public Builder setMimeType(String mimeType) {
            this.mMimeType = mimeType;
            return this;
        }

        public NavDeepLink build() {
            return new NavDeepLink(this.mUriPattern, this.mAction, this.mMimeType);
        }
    }
}
