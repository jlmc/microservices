package org.xine.async.business.contracts.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

/**
 * Entity implementation class for Entity: Contract
 *
 */
@Entity
@Table(name="t_contract")
public class Contract implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String client;
    private BigDecimal balance;

    @ElementCollection
    @CollectionTable(name = "t_part", joinColumns = @JoinColumn(name = "contract_Id", foreignKey = @ForeignKey(name = "FK_T_CONTRACT")))
    private Set<Part> parts = new HashSet<>(0);

    public Contract() {
        super();
    }
    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getClient() {
        return this.client;
    }

    public void setClient(String client) {
        this.client = client;
    }
    public BigDecimal getBalance() {
        return this.balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public Set<Part> getParts() {
        return Collections.unmodifiableSet(this.parts);
    }

    protected void setParts(Set<Part> parts) {
        this.parts = parts;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
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
        final Contract other = (Contract) obj;
        return Objects.equals(this.id, other.id);
    }

    public BigDecimal ajuster(Double percentage) {
        final BigDecimal multiply = this.balance.multiply(BigDecimal.valueOf(percentage));
        this.balance = this.balance.add(multiply);
        return this.balance;
    }
   
}
