package org.xine.xebuy.business.serialization.boundary;

import org.xine.xebuy.business.plugin.serializer.Serializer;

public class NullSerializer implements Serializer {

    @Override
    public byte[] serialize(Serializer object) {
        return new byte[0];
    }

}
