package io.github.jlmc.service.config;

import org.eclipse.microprofile.config.Config;
import org.eclipse.microprofile.config.ConfigProvider;

import java.net.URI;

public class Configurations {

    public static URI getServerMetricsAndHealthBasePath() {
        return getURI("admin.uri");
    }

    public static URI getAppResourcesUri() {
        return getURI("app.resources.uri");
    }

    public static String extractResourceIdentifier(final URI location) {
        final String s = location.toString();
        return s.replaceFirst(".*/([^/?]+).*", "$1");
    }

    static URI getURI(String key) {
        final Config config = ConfigProvider.getConfig();
        return config.getValue(key, URI.class);
    }

    static String getValue(String key) {
        final Config config = ConfigProvider.getConfig();
        return config.getValue(key, String.class);
    }

    static boolean getBooleanValue(String key) {
        final var config = ConfigProvider.getConfig();
        return config.getOptionalValue(key, Boolean.class)
                .orElse(false);
    }
}
