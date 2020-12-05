package org.xine.business.reports.entity;

import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    protected Report() {
    }

    public String getName() {
        return this.name;
    }

    public Long getId() {
        return this.id;
    }

    protected void setId(final Long id) {
        this.id = id;
    }

    protected void setName(final String name) {
        this.name = name;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Report other = (Report) obj;
        if (this.id == null) {
            if (other.id != null) {
                return false;
            }
        } else if (!this.id.equals(other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Report [id=" + this.id + ", name=" + this.name + "]";
    }

    public static class Builder {
        private final Report report;

        private Builder() {
            this.report = new Report();
        }

        public static Builder init() {
            return new Builder();
        }

        public Builder name(final String name) {
            this.report.setName(name);
            return this;
        }

        public Builder withId(final Long id) {
            this.report.setId(id);
            return this;
        }

        public Report build() {
            return this.report;
        }

    }
}
