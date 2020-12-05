package org.xine.bookshop.business.restserialization.control;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import javax.ws.rs.Consumes;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.Provider;

@Provider
@Consumes(CustomMediaType.SERIALIZATION_JAVA)
public class JavaDeserializer implements MessageBodyReader<Object> {

    public boolean isReadable(final Class<?> type,
            final Type genericType,
            final Annotation[] annotations,
            final MediaType mediaType) {
        return Serializable.class.isAssignableFrom(type);
    }

    public Object readFrom(final Class<Object> type, final Type genericType, final Annotation[] annotations,
            final MediaType mediaType, final MultivaluedMap<String, String> httpHeaders, final InputStream entityStream)
                    throws IOException, WebApplicationException {
        try {
            final ObjectInputStream in = new ObjectInputStream(entityStream);
            return in.readObject();
        } catch (final ClassNotFoundException ex) {
            throw new IOException("Cannot find class for: " + type.getName(), ex);
        }
    }

}
