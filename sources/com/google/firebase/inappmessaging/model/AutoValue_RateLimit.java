package com.google.firebase.inappmessaging.model;

import com.google.firebase.inappmessaging.model.RateLimit;

final class AutoValue_RateLimit extends RateLimit {
    private final long limit;
    private final String limiterKey;
    private final long timeToLiveMillis;

    private AutoValue_RateLimit(String limiterKey2, long limit2, long timeToLiveMillis2) {
        this.limiterKey = limiterKey2;
        this.limit = limit2;
        this.timeToLiveMillis = timeToLiveMillis2;
    }

    public String limiterKey() {
        return this.limiterKey;
    }

    public long limit() {
        return this.limit;
    }

    public long timeToLiveMillis() {
        return this.timeToLiveMillis;
    }

    public String toString() {
        return "RateLimit{limiterKey=" + this.limiterKey + ", limit=" + this.limit + ", timeToLiveMillis=" + this.timeToLiveMillis + "}";
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof RateLimit)) {
            return false;
        }
        RateLimit that = (RateLimit) o;
        if (this.limiterKey.equals(that.limiterKey()) && this.limit == that.limit() && this.timeToLiveMillis == that.timeToLiveMillis()) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        long j = this.limit;
        long j2 = this.timeToLiveMillis;
        return (((((1 * 1000003) ^ this.limiterKey.hashCode()) * 1000003) ^ ((int) (j ^ (j >>> 32)))) * 1000003) ^ ((int) (j2 ^ (j2 >>> 32)));
    }

    static final class Builder extends RateLimit.Builder {
        private Long limit;
        private String limiterKey;
        private Long timeToLiveMillis;

        Builder() {
        }

        public RateLimit.Builder setLimiterKey(String limiterKey2) {
            if (limiterKey2 != null) {
                this.limiterKey = limiterKey2;
                return this;
            }
            throw new NullPointerException("Null limiterKey");
        }

        public RateLimit.Builder setLimit(long limit2) {
            this.limit = Long.valueOf(limit2);
            return this;
        }

        public RateLimit.Builder setTimeToLiveMillis(long timeToLiveMillis2) {
            this.timeToLiveMillis = Long.valueOf(timeToLiveMillis2);
            return this;
        }

        public RateLimit build() {
            String missing = "";
            if (this.limiterKey == null) {
                missing = missing + " limiterKey";
            }
            if (this.limit == null) {
                missing = missing + " limit";
            }
            if (this.timeToLiveMillis == null) {
                missing = missing + " timeToLiveMillis";
            }
            if (missing.isEmpty()) {
                return new AutoValue_RateLimit(this.limiterKey, this.limit.longValue(), this.timeToLiveMillis.longValue());
            }
            throw new IllegalStateException("Missing required properties:" + missing);
        }
    }
}
