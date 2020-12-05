package org.xine.async.business.contracts.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

import javax.persistence.Convert;
import javax.persistence.Embeddable;

import org.xine.async.business.JsonDateDeserializer;
import org.xine.async.business.JsonDateSerializer;
import org.xine.async.business.persistence.LocalDateTimeConverter;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@Embeddable
public class Part implements Serializable {

    private static final long serialVersionUID = 1L;

    @Convert(converter = LocalDateTimeConverter.class)
    @JsonDeserialize(using = JsonDateDeserializer.class)
    @JsonSerialize(using = JsonDateSerializer.class)
    private LocalDateTime dueDate;
    private BigDecimal value;

    protected Part() {
    }

    public static Part of(LocalDateTime dueDate, BigDecimal value) {
        final Part part = new Part();
        part.dueDate = dueDate;
        part.value = value;
        return part;
    }

    public LocalDateTime getDueDate() {
        return this.dueDate;
    }

    public BigDecimal getValue() {
        return this.value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.dueDate, this.value);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Part other = (Part) obj;
        if (this.dueDate == null) {
            if (other.dueDate != null) {
                return false;
            }
        } else if (!this.dueDate.equals(other.dueDate)) {
            return false;
        }
        if (this.value == null) {
            return other.value == null;
        } else return this.value.equals(other.value);
    }

    public BigDecimal add(Double percentage) {
        final BigDecimal multiply = this.value.multiply(BigDecimal.valueOf(percentage));
        this.value = this.value.add(multiply);
        return this.value;
    }

    public Part plus(BigDecimal plus) {
        return of(LocalDateTime.of(this.dueDate.toLocalDate(), this.dueDate.toLocalTime()), this.value.add(plus));
    }
}
