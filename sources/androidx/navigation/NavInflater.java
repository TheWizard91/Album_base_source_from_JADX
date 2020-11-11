package androidx.navigation;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import androidx.navigation.NavArgument;
import androidx.navigation.NavDeepLink;
import androidx.navigation.NavOptions;
import androidx.navigation.common.C2156R;
import java.io.IOException;
import org.xmlpull.v1.XmlPullParserException;

public final class NavInflater {
    public static final String APPLICATION_ID_PLACEHOLDER = "${applicationId}";
    private static final String TAG_ACTION = "action";
    private static final String TAG_ARGUMENT = "argument";
    private static final String TAG_DEEP_LINK = "deepLink";
    private static final String TAG_INCLUDE = "include";
    private static final ThreadLocal<TypedValue> sTmpValue = new ThreadLocal<>();
    private Context mContext;
    private NavigatorProvider mNavigatorProvider;

    public NavInflater(Context context, NavigatorProvider navigatorProvider) {
        this.mContext = context;
        this.mNavigatorProvider = navigatorProvider;
    }

    /* JADX WARNING: Removed duplicated region for block: B:16:0x004e A[Catch:{ Exception -> 0x0058, all -> 0x0056 }] */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x001c A[Catch:{ Exception -> 0x0058, all -> 0x0056 }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public androidx.navigation.NavGraph inflate(int r10) {
        /*
            r9 = this;
            android.content.Context r0 = r9.mContext
            android.content.res.Resources r0 = r0.getResources()
            android.content.res.XmlResourceParser r1 = r0.getXml(r10)
            android.util.AttributeSet r2 = android.util.Xml.asAttributeSet(r1)
        L_0x000e:
            int r3 = r1.next()     // Catch:{ Exception -> 0x0058 }
            r4 = r3
            r5 = 2
            if (r3 == r5) goto L_0x001a
            r3 = 1
            if (r4 == r3) goto L_0x001a
            goto L_0x000e
        L_0x001a:
            if (r4 != r5) goto L_0x004e
            java.lang.String r3 = r1.getName()     // Catch:{ Exception -> 0x0058 }
            androidx.navigation.NavDestination r5 = r9.inflate(r0, r1, r2, r10)     // Catch:{ Exception -> 0x0058 }
            boolean r6 = r5 instanceof androidx.navigation.NavGraph     // Catch:{ Exception -> 0x0058 }
            if (r6 == 0) goto L_0x002f
            r6 = r5
            androidx.navigation.NavGraph r6 = (androidx.navigation.NavGraph) r6     // Catch:{ Exception -> 0x0058 }
            r1.close()
            return r6
        L_0x002f:
            java.lang.IllegalArgumentException r6 = new java.lang.IllegalArgumentException     // Catch:{ Exception -> 0x0058 }
            java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0058 }
            r7.<init>()     // Catch:{ Exception -> 0x0058 }
            java.lang.String r8 = "Root element <"
            java.lang.StringBuilder r7 = r7.append(r8)     // Catch:{ Exception -> 0x0058 }
            java.lang.StringBuilder r7 = r7.append(r3)     // Catch:{ Exception -> 0x0058 }
            java.lang.String r8 = "> did not inflate into a NavGraph"
            java.lang.StringBuilder r7 = r7.append(r8)     // Catch:{ Exception -> 0x0058 }
            java.lang.String r7 = r7.toString()     // Catch:{ Exception -> 0x0058 }
            r6.<init>(r7)     // Catch:{ Exception -> 0x0058 }
            throw r6     // Catch:{ Exception -> 0x0058 }
        L_0x004e:
            org.xmlpull.v1.XmlPullParserException r3 = new org.xmlpull.v1.XmlPullParserException     // Catch:{ Exception -> 0x0058 }
            java.lang.String r5 = "No start tag found"
            r3.<init>(r5)     // Catch:{ Exception -> 0x0058 }
            throw r3     // Catch:{ Exception -> 0x0058 }
        L_0x0056:
            r3 = move-exception
            goto L_0x0084
        L_0x0058:
            r3 = move-exception
            java.lang.RuntimeException r4 = new java.lang.RuntimeException     // Catch:{ all -> 0x0056 }
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ all -> 0x0056 }
            r5.<init>()     // Catch:{ all -> 0x0056 }
            java.lang.String r6 = "Exception inflating "
            java.lang.StringBuilder r5 = r5.append(r6)     // Catch:{ all -> 0x0056 }
            java.lang.String r6 = r0.getResourceName(r10)     // Catch:{ all -> 0x0056 }
            java.lang.StringBuilder r5 = r5.append(r6)     // Catch:{ all -> 0x0056 }
            java.lang.String r6 = " line "
            java.lang.StringBuilder r5 = r5.append(r6)     // Catch:{ all -> 0x0056 }
            int r6 = r1.getLineNumber()     // Catch:{ all -> 0x0056 }
            java.lang.StringBuilder r5 = r5.append(r6)     // Catch:{ all -> 0x0056 }
            java.lang.String r5 = r5.toString()     // Catch:{ all -> 0x0056 }
            r4.<init>(r5, r3)     // Catch:{ all -> 0x0056 }
            throw r4     // Catch:{ all -> 0x0056 }
        L_0x0084:
            r1.close()
            throw r3
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.navigation.NavInflater.inflate(int):androidx.navigation.NavGraph");
    }

    private NavDestination inflate(Resources res, XmlResourceParser parser, AttributeSet attrs, int graphResId) throws XmlPullParserException, IOException {
        Resources resources = res;
        AttributeSet attributeSet = attrs;
        NavDestination dest = this.mNavigatorProvider.getNavigator(parser.getName()).createDestination();
        dest.onInflate(this.mContext, attributeSet);
        int innerDepth = parser.getDepth() + 1;
        while (true) {
            int next = parser.next();
            int type = next;
            if (next == 1) {
                break;
            }
            int depth = parser.getDepth();
            int depth2 = depth;
            if (depth < innerDepth && type == 3) {
                break;
            } else if (type == 2 && depth2 <= innerDepth) {
                String name = parser.getName();
                if (TAG_ARGUMENT.equals(name)) {
                    inflateArgumentForDestination(resources, dest, attributeSet, graphResId);
                } else {
                    int i = graphResId;
                    if (TAG_DEEP_LINK.equals(name)) {
                        inflateDeepLink(resources, dest, attributeSet);
                    } else if (TAG_ACTION.equals(name)) {
                        inflateAction(res, dest, attrs, parser, graphResId);
                    } else if (TAG_INCLUDE.equals(name) && (dest instanceof NavGraph)) {
                        TypedArray a = resources.obtainAttributes(attributeSet, C2150R.styleable.NavInclude);
                        ((NavGraph) dest).addDestination(inflate(a.getResourceId(C2150R.styleable.NavInclude_graph, 0)));
                        a.recycle();
                    } else if (dest instanceof NavGraph) {
                        ((NavGraph) dest).addDestination(inflate(res, parser, attrs, graphResId));
                    }
                }
            }
        }
        return dest;
    }

    private void inflateArgumentForDestination(Resources res, NavDestination dest, AttributeSet attrs, int graphResId) throws XmlPullParserException {
        TypedArray a = res.obtainAttributes(attrs, C2156R.styleable.NavArgument);
        String name = a.getString(C2156R.styleable.NavArgument_android_name);
        if (name != null) {
            dest.addArgument(name, inflateArgument(a, res, graphResId));
            a.recycle();
            return;
        }
        throw new XmlPullParserException("Arguments must have a name");
    }

    private void inflateArgumentForBundle(Resources res, Bundle bundle, AttributeSet attrs, int graphResId) throws XmlPullParserException {
        TypedArray a = res.obtainAttributes(attrs, C2156R.styleable.NavArgument);
        String name = a.getString(C2156R.styleable.NavArgument_android_name);
        if (name != null) {
            NavArgument argument = inflateArgument(a, res, graphResId);
            if (argument.isDefaultValuePresent()) {
                argument.putDefaultValue(name, bundle);
            }
            a.recycle();
            return;
        }
        throw new XmlPullParserException("Arguments must have a name");
    }

    private NavArgument inflateArgument(TypedArray a, Resources res, int graphResId) throws XmlPullParserException {
        NavArgument.Builder argumentBuilder = new NavArgument.Builder();
        boolean z = false;
        argumentBuilder.setIsNullable(a.getBoolean(C2156R.styleable.NavArgument_nullable, false));
        ThreadLocal<TypedValue> threadLocal = sTmpValue;
        TypedValue value = threadLocal.get();
        if (value == null) {
            value = new TypedValue();
            threadLocal.set(value);
        }
        int defaultValue = null;
        NavType navType = null;
        String argType = a.getString(C2156R.styleable.NavArgument_argType);
        if (argType != null) {
            navType = NavType.fromArgType(argType, res.getResourcePackageName(graphResId));
        }
        if (a.getValue(C2156R.styleable.NavArgument_android_defaultValue, value)) {
            if (navType == NavType.ReferenceType) {
                if (value.resourceId != 0) {
                    defaultValue = Integer.valueOf(value.resourceId);
                } else if (value.type == 16 && value.data == 0) {
                    defaultValue = 0;
                } else {
                    throw new XmlPullParserException("unsupported value '" + value.string + "' for " + navType.getName() + ". Must be a reference to a resource.");
                }
            } else if (value.resourceId != 0) {
                if (navType == null) {
                    navType = NavType.ReferenceType;
                    defaultValue = Integer.valueOf(value.resourceId);
                } else {
                    throw new XmlPullParserException("unsupported value '" + value.string + "' for " + navType.getName() + ". You must use a \"" + NavType.ReferenceType.getName() + "\" type to reference other resources.");
                }
            } else if (navType == NavType.StringType) {
                defaultValue = a.getString(C2156R.styleable.NavArgument_android_defaultValue);
            } else {
                int i = value.type;
                if (i == 3) {
                    String stringValue = value.string.toString();
                    if (navType == null) {
                        navType = NavType.inferFromValue(stringValue);
                    }
                    defaultValue = navType.parseValue(stringValue);
                } else if (i == 4) {
                    navType = checkNavType(value, navType, NavType.FloatType, argType, "float");
                    defaultValue = Float.valueOf(value.getFloat());
                } else if (i == 5) {
                    navType = checkNavType(value, navType, NavType.IntType, argType, "dimension");
                    defaultValue = Integer.valueOf((int) value.getDimension(res.getDisplayMetrics()));
                } else if (i == 18) {
                    navType = checkNavType(value, navType, NavType.BoolType, argType, "boolean");
                    if (value.data != 0) {
                        z = true;
                    }
                    defaultValue = Boolean.valueOf(z);
                } else if (value.type < 16 || value.type > 31) {
                    throw new XmlPullParserException("unsupported argument type " + value.type);
                } else {
                    navType = checkNavType(value, navType, NavType.IntType, argType, "integer");
                    defaultValue = Integer.valueOf(value.data);
                }
            }
        }
        if (defaultValue != null) {
            argumentBuilder.setDefaultValue(defaultValue);
        }
        if (navType != null) {
            argumentBuilder.setType(navType);
        }
        return argumentBuilder.build();
    }

    private static NavType checkNavType(TypedValue value, NavType navType, NavType expectedNavType, String argType, String foundType) throws XmlPullParserException {
        if (navType == null || navType == expectedNavType) {
            return navType != null ? navType : expectedNavType;
        }
        throw new XmlPullParserException("Type is " + argType + " but found " + foundType + ": " + value.data);
    }

    private void inflateDeepLink(Resources res, NavDestination dest, AttributeSet attrs) throws XmlPullParserException {
        TypedArray a = res.obtainAttributes(attrs, C2156R.styleable.NavDeepLink);
        String uri = a.getString(C2156R.styleable.NavDeepLink_uri);
        String action = a.getString(C2156R.styleable.NavDeepLink_action);
        String mimeType = a.getString(C2156R.styleable.NavDeepLink_mimeType);
        if (!TextUtils.isEmpty(uri) || !TextUtils.isEmpty(action) || !TextUtils.isEmpty(mimeType)) {
            NavDeepLink.Builder builder = new NavDeepLink.Builder();
            if (uri != null) {
                builder.setUriPattern(uri.replace(APPLICATION_ID_PLACEHOLDER, this.mContext.getPackageName()));
            }
            if (!TextUtils.isEmpty(action)) {
                builder.setAction(action.replace(APPLICATION_ID_PLACEHOLDER, this.mContext.getPackageName()));
            }
            if (mimeType != null) {
                builder.setMimeType(mimeType.replace(APPLICATION_ID_PLACEHOLDER, this.mContext.getPackageName()));
            }
            dest.addDeepLink(builder.build());
            a.recycle();
            return;
        }
        throw new XmlPullParserException("Every <deepLink> must include at least one of app:uri, app:action, or app:mimeType");
    }

    private void inflateAction(Resources res, NavDestination dest, AttributeSet attrs, XmlResourceParser parser, int graphResId) throws IOException, XmlPullParserException {
        Resources resources = res;
        AttributeSet attributeSet = attrs;
        TypedArray a = resources.obtainAttributes(attributeSet, C2156R.styleable.NavAction);
        int id = a.getResourceId(C2156R.styleable.NavAction_android_id, 0);
        NavAction action = new NavAction(a.getResourceId(C2156R.styleable.NavAction_destination, 0));
        NavOptions.Builder builder = new NavOptions.Builder();
        builder.setLaunchSingleTop(a.getBoolean(C2156R.styleable.NavAction_launchSingleTop, false));
        builder.setPopUpTo(a.getResourceId(C2156R.styleable.NavAction_popUpTo, -1), a.getBoolean(C2156R.styleable.NavAction_popUpToInclusive, false));
        builder.setEnterAnim(a.getResourceId(C2156R.styleable.NavAction_enterAnim, -1));
        builder.setExitAnim(a.getResourceId(C2156R.styleable.NavAction_exitAnim, -1));
        builder.setPopEnterAnim(a.getResourceId(C2156R.styleable.NavAction_popEnterAnim, -1));
        builder.setPopExitAnim(a.getResourceId(C2156R.styleable.NavAction_popExitAnim, -1));
        action.setNavOptions(builder.build());
        Bundle args = new Bundle();
        int innerDepth = parser.getDepth() + 1;
        while (true) {
            int next = parser.next();
            int type = next;
            if (next == 1) {
                int i = graphResId;
                break;
            }
            int depth = parser.getDepth();
            int depth2 = depth;
            if (depth < innerDepth && type == 3) {
                int i2 = graphResId;
                break;
            } else if (type == 2 && depth2 <= innerDepth) {
                if (TAG_ARGUMENT.equals(parser.getName())) {
                    inflateArgumentForBundle(resources, args, attributeSet, graphResId);
                } else {
                    int i3 = graphResId;
                }
            }
        }
        if (!args.isEmpty()) {
            action.setDefaultArguments(args);
        }
        dest.putAction(id, action);
        a.recycle();
    }
}
