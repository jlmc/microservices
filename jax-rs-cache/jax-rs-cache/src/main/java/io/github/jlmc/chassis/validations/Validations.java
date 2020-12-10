package io.github.jlmc.chassis.validations;

public final class Validations {

    public interface Creation{}
    public interface Update{}

    public interface SubscriptionCreation {}

    private Validations() {
        throw new AssertionError();
    }
}