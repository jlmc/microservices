package org.xine.xebuy.business.plugin.serializer;

import javax.enterprise.util.AnnotationLiteral;

public class SearializationType extends AnnotationLiteral<Serialization> implements Serialization {

	private static final long serialVersionUID = 1L;

	Type type;

	public SearializationType(Type type) {
		this.type = type;
	}

	@Override
	public Type value() {
		return type;
	}

}
