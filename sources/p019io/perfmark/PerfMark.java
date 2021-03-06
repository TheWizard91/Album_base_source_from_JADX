package p019io.perfmark;

import java.util.logging.Level;
import java.util.logging.Logger;

/* renamed from: io.perfmark.PerfMark */
public final class PerfMark {
    private static final Impl impl;

    static {
        Impl instance = null;
        Level level = Level.WARNING;
        Throwable err = null;
        Class<?> clz = null;
        try {
            clz = Class.forName("io.perfmark.impl.SecretPerfMarkImpl$PerfMarkImpl");
        } catch (ClassNotFoundException e) {
            level = Level.FINE;
            err = e;
        } catch (Throwable t) {
            err = t;
        }
        if (clz != null) {
            try {
                instance = (Impl) clz.asSubclass(Impl.class).getConstructor(new Class[]{Tag.class}).newInstance(new Object[]{Impl.NO_TAG});
            } catch (Throwable t2) {
                err = t2;
            }
        }
        if (instance != null) {
            impl = instance;
        } else {
            impl = new Impl(Impl.NO_TAG);
        }
        if (err != null) {
            Logger.getLogger(PerfMark.class.getName()).log(level, "Error during PerfMark.<clinit>", err);
        }
    }

    public static void setEnabled(boolean value) {
        impl.setEnabled(value);
    }

    public static void startTask(String taskName, Tag tag) {
        impl.startTask(taskName, tag);
    }

    public static void startTask(String taskName) {
        impl.startTask(taskName);
    }

    public static void event(String eventName, Tag tag) {
        impl.event(eventName, tag);
    }

    public static void event(String eventName) {
        impl.event(eventName);
    }

    public static void stopTask(String taskName, Tag tag) {
        impl.stopTask(taskName, tag);
    }

    public static void stopTask(String taskName) {
        impl.stopTask(taskName);
    }

    public static Tag createTag() {
        return Impl.NO_TAG;
    }

    public static Tag createTag(long id) {
        return impl.createTag("", id);
    }

    public static Tag createTag(String name) {
        return impl.createTag(name, Long.MIN_VALUE);
    }

    public static Tag createTag(String name, long id) {
        return impl.createTag(name, id);
    }

    @Deprecated
    public static Link link() {
        return Impl.NO_LINK;
    }

    public static Link linkOut() {
        return impl.linkOut();
    }

    public static void linkIn(Link link) {
        impl.linkIn(link);
    }

    public static void attachTag(Tag tag) {
        impl.attachTag(tag);
    }

    private PerfMark() {
    }
}
