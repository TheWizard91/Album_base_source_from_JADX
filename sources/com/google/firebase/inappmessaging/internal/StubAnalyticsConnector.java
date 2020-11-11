package com.google.firebase.inappmessaging.internal;

import android.os.Bundle;
import com.google.firebase.analytics.connector.AnalyticsConnector;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class StubAnalyticsConnector implements AnalyticsConnector {
    public static final StubAnalyticsConnector instance = new StubAnalyticsConnector();

    private StubAnalyticsConnector() {
    }

    public void logEvent(String s, String s1, Bundle bundle) {
    }

    public void setUserProperty(String s, String s1, Object o) {
    }

    public Map<String, Object> getUserProperties(boolean b) {
        return null;
    }

    public AnalyticsConnectorHandle registerAnalyticsConnectorListener(String s, AnalyticsConnector.AnalyticsConnectorListener analyticsConnectorListener) {
        return AnalyticsConnectorHandle.instance;
    }

    public void setConditionalUserProperty(AnalyticsConnector.ConditionalUserProperty conditionalUserProperty) {
    }

    public void clearConditionalUserProperty(String s, String s1, Bundle bundle) {
    }

    public List<AnalyticsConnector.ConditionalUserProperty> getConditionalUserProperties(String s, String s1) {
        return null;
    }

    public int getMaxUserProperties(String s) {
        return 0;
    }

    private static class AnalyticsConnectorHandle implements AnalyticsConnector.AnalyticsConnectorHandle {
        static final AnalyticsConnectorHandle instance = new AnalyticsConnectorHandle();

        private AnalyticsConnectorHandle() {
        }

        public void unregister() {
        }

        public void registerEventNames(Set<String> set) {
        }

        public void unregisterEventNames() {
        }
    }
}
