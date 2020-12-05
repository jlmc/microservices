package org.xine.basicserialization.business.boundary;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import org.xine.xebuy.business.plugin.serializer.Serialization;
import org.xine.xebuy.business.plugin.serializer.Serialization.PlanType;
import org.xine.xebuy.business.plugin.serializer.Serializer;

@Serialization(plantype = PlanType.DEFAULT)
public class JavaSerializer implements Serializer {

    @Override
    public byte[] serialize(Serializer object) {
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();

        try {
            final ObjectOutputStream out = new ObjectOutputStream(baos);
            out.writeObject(object);
            out.flush();
        } catch (final IOException e) {
            throw new RuntimeException("cannot  serialize: " + object, e);
        }

        return baos.toByteArray();
    }

}
