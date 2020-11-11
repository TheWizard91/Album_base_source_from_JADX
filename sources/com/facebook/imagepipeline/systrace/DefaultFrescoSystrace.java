package com.facebook.imagepipeline.systrace;

import android.os.Build;
import android.os.Trace;
import com.facebook.imagepipeline.systrace.FrescoSystrace;
import kotlinx.coroutines.scheduling.WorkQueueKt;

public class DefaultFrescoSystrace implements FrescoSystrace.Systrace {
    public void beginSection(String name) {
    }

    public FrescoSystrace.ArgsBuilder beginSectionWithArgs(String name) {
        return FrescoSystrace.NO_OP_ARGS_BUILDER;
    }

    public void endSection() {
    }

    public boolean isTracing() {
        return false;
    }

    private static final class DefaultArgsBuilder implements FrescoSystrace.ArgsBuilder {
        private final StringBuilder mStringBuilder;

        public DefaultArgsBuilder(String name) {
            this.mStringBuilder = new StringBuilder(name);
        }

        public void flush() {
            if (this.mStringBuilder.length() > 127) {
                this.mStringBuilder.setLength(WorkQueueKt.MASK);
            }
            if (Build.VERSION.SDK_INT >= 18) {
                Trace.beginSection(this.mStringBuilder.toString());
            }
        }

        public FrescoSystrace.ArgsBuilder arg(String key, Object value) {
            String str;
            StringBuilder append = this.mStringBuilder.append(';').append(key).append('=');
            if (value == null) {
                str = "null";
            } else {
                str = value.toString();
            }
            append.append(str);
            return this;
        }

        public FrescoSystrace.ArgsBuilder arg(String key, int value) {
            this.mStringBuilder.append(';').append(key).append('=').append(Integer.toString(value));
            return this;
        }

        public FrescoSystrace.ArgsBuilder arg(String key, long value) {
            this.mStringBuilder.append(';').append(key).append('=').append(Long.toString(value));
            return this;
        }

        public FrescoSystrace.ArgsBuilder arg(String key, double value) {
            this.mStringBuilder.append(';').append(key).append('=').append(Double.toString(value));
            return this;
        }
    }
}
