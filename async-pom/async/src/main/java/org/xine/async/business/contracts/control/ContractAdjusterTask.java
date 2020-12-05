package org.xine.async.business.contracts.control;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.concurrent.Callable;

import org.xine.async.business.contracts.entity.Contract;
import org.xine.async.business.contracts.entity.Part;

public class ContractAdjusterTask implements Callable<BigDecimal> {

    private final Contract contract;
    private final Double percentage;

    public ContractAdjusterTask(Contract contract, Double percentage) {
        this.contract = contract;
        this.percentage = percentage;
    }

    @Override
    public BigDecimal call() throws Exception {
        final LocalDateTime now = LocalDateTime.now();

        BigDecimal sumOfParts = BigDecimal.ZERO;
        for (final Part part : this.contract.getParts()) {
            if (part.getDueDate().compareTo(now) > 0) {
                part.add(this.percentage);
            }
            sumOfParts = sumOfParts.add(part.getValue());
        }

        this.contract.setBalance(sumOfParts);

        return this.contract.getBalance();
    }

}
