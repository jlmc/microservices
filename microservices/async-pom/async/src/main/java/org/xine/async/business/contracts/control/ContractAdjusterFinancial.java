package org.xine.async.business.contracts.control;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.concurrent.Future;

import javax.ejb.AsyncResult;
import javax.ejb.Asynchronous;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.xine.async.business.contracts.entity.Contract;
import org.xine.async.business.contracts.entity.Part;

@Stateless
public class ContractAdjusterFinancial {

    @PersistenceContext(unitName = "async")
    EntityManager em;

    @Asynchronous
    public Future<BigDecimal> ajusterContract(Double percentage, Contract contract) {
        contract = this.em.merge(contract);

        final LocalDateTime now = LocalDateTime.now();

        BigDecimal sumOfParts = BigDecimal.ZERO;
        for (final Part part : contract.getParts()) {
            if (part.getDueDate().compareTo(now) > 0) {
                part.add(percentage);
            }
            sumOfParts = sumOfParts.add(part.getValue());
        }

        contract.setBalance(sumOfParts);

        return new AsyncResult<>(contract.getBalance());
    }

}
