package retrofit2;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class Invocation {
    private final List<?> arguments;
    private final Method method;

    /* renamed from: of */
    public static Invocation m237of(Method method2, List<?> arguments2) {
        Utils.checkNotNull(method2, "method == null");
        Utils.checkNotNull(arguments2, "arguments == null");
        return new Invocation(method2, new ArrayList(arguments2));
    }

    Invocation(Method method2, List<?> arguments2) {
        this.method = method2;
        this.arguments = Collections.unmodifiableList(arguments2);
    }

    public Method method() {
        return this.method;
    }

    public List<?> arguments() {
        return this.arguments;
    }

    public String toString() {
        return String.format("%s.%s() %s", new Object[]{this.method.getDeclaringClass().getName(), this.method.getName(), this.arguments});
    }
}
