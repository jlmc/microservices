package org.xine.xebuy.business.plugin.serializer;

import javax.enterprise.util.AnnotationLiteral;

public class SerializationType extends AnnotationLiteral<Serialization> implements Serialization {

    private static final long serialVersionUID = 1L;

    PlanType type;

    public SerializationType(PlanType type) {
        this.type = type;
    }

    @Override
    public PlanType plantype() {
        return type;
    }

}
