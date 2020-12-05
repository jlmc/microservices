package org.xine.hessian.business.boundary;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.xine.xebuy.business.plugin.serializer.Serialization;
import org.xine.xebuy.business.plugin.serializer.Serialization.PlanType;
import org.xine.xebuy.business.plugin.serializer.Serializer;

import com.caucho.hessian.io.Hessian2StreamingOutput;

@Serialization(plantype = PlanType.OPTIMIZED)
public class HessianSerializer implements Serializer {

    @Override
    public byte[] serialize(Serializer object) {
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();

        final Hessian2StreamingOutput out = new Hessian2StreamingOutput(baos);
        try {
            out.writeObject(object);
            out.flush();
        } catch (final IOException iOException) {
            throw new RuntimeException("Cannot serialize: " + object, iOException);
        }

        return baos.toByteArray();
    }

}
