package io.github.jlmc.chassis.jsonb;

import javax.json.bind.annotation.JsonbAnnotation;
import javax.json.bind.annotation.JsonbNillable;
import javax.json.bind.annotation.JsonbPropertyOrder;
import javax.json.bind.annotation.JsonbVisibility;
import javax.json.bind.config.PropertyOrderStrategy;
import java.lang.annotation.*;

@Documented
@JsonbAnnotation
@JsonbNillable(true)
@JsonbPropertyOrder(PropertyOrderStrategy.LEXICOGRAPHICAL)
@JsonbVisibility(FieldAccessStrategy.class)
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.ANNOTATION_TYPE, ElementType.TYPE, ElementType.PACKAGE})
public @interface JsonbRepresentation {
}

