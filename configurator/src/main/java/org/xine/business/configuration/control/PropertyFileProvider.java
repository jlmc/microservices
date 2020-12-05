package org.xine.business.configuration.control;

import java.util.HashMap;
import java.util.Map;

import org.xine.business.configuration.boundary.ProviderType;

@ProviderType(ProviderType.Type.LOCAL)
public class PropertyFileProvider implements MapProvider {

    @Override
    public Map<String, String> getConfiguration() {
        return new HashMap<String, String>() {
            private static final long serialVersionUID = 1L;
            {
                put("maxNumberOfRegistrations", "41");
            }
        };
    }

}
