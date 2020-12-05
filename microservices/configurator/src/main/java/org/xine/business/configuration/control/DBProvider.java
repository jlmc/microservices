package org.xine.business.configuration.control;

import java.util.Collections;
import java.util.Map;

import org.xine.business.configuration.boundary.ProviderType;

@ProviderType(ProviderType.Type.REMOTE)
public class DBProvider implements MapProvider {

    @Override
    public Map<String, String> getConfiguration() {
        return Collections.emptyMap();
    }

}
