package io.github.jlmc.service.books.entity;

import io.github.jlmc.chassis.jsonb.JsonbRepresentation;
import io.github.jlmc.chassis.validations.Validations;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Null;

@JsonbRepresentation

@Entity
public class Book {

    @Schema(hidden = true, readOnly = true)
    //@JsonbTransient

    @Null(groups = Validations.Creation.class)


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank
    private String title;

    @JsonbTransient
    @Version
    private int version;

    public Integer getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }
}
