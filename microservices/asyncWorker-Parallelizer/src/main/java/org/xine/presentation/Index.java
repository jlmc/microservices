package org.xine.presentation;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import org.xine.business.push.boundary.Parallelizer;

@Named
@RequestScoped
public class Index {

    @Max(100)
    @Min(1)
    private int number = 10;

    @Inject
    Parallelizer parallelizer;

    public int getNumber() {
        return this.number;
    }

    public void setNumber(final int number) {
        this.number = number;
    }

    public Object basicexecution() {
        // final List<Long> execute =
        this.parallelizer.execute(this.number);
        return null;
    }

}
