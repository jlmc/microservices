package org.xine.bookshop.business.restserialization.control;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import javax.ws.rs.Consumes;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;

@Provider
@Consumes(CustomMediaType.SERIALIZATION_JAVA)
public class JavaSerializer implements MessageBodyWriter<Object> {

	public boolean isWriteable(final Class<?> type, final Type genericType, final Annotation[] annotations,
			final MediaType mediaType) {
		return Serializable.class.isAssignableFrom(type);
	}

	public long getSize(final Object t, final Class<?> type, final Type genericType, final Annotation[] annotations,
			final MediaType mediaType) {
		// according the documentation
		// length in bytes or -1 if the length cannot be determined in advance.
		return -1;
	}

	public void writeTo(final Object t, final Class<?> type, final Type genericType, final Annotation[] annotations,
			final MediaType mediaType, final MultivaluedMap<String, Object> httpHeaders,
			final OutputStream entityStream) throws IOException, WebApplicationException {
		final ObjectOutputStream os = new ObjectOutputStream(entityStream);
		os.writeObject(t);
	}

}
