package org.xine.cdidemos.business.salaries.entity;

import java.time.Year;

public class Employeer {
    private Duty duty;
    private Year yearOfAdmission;
    private Instruction instruction;

    protected Employeer() {
    }

    protected Employeer(final Duty duty, final Instruction instruction, final Year yearOfAdmission) {
        this.duty = duty;
        this.instruction = instruction;
        this.yearOfAdmission = yearOfAdmission;
    }

    public Duty getDuty() {
        return this.duty;
    }

    public Instruction getInstruction() {
        return this.instruction;
    }

    public Year getYearOfAdmission() {
        return this.yearOfAdmission;
    }

    public static class Builder {
        private Year YearOfAdmission;
        private Instruction instructionEmploee;
        private Instruction instructionDuty;
        private double baseSalary;

        private Builder() {
            this.YearOfAdmission = Year.now();
            this.instructionDuty = Instruction.GRADUATION;
            this.instructionEmploee = Instruction.GRADUATION;
        }

        public static Builder init() {
            return new Builder();
        }

        public Builder withBaseSalary(final double baseSalary) {
            this.baseSalary = baseSalary;
            return this;
        }

        public Builder withYearOfAdmission(final Year year) {
            this.YearOfAdmission = Year.now();
            return this;
        }

        public Builder withInstructionDuty(final Instruction instructionDuty) {
            this.instructionDuty = instructionDuty;
            return this;
        }

        public Builder withInstructionEmploee(final Instruction instructionEmploee) {
            this.instructionEmploee = instructionEmploee;
            return this;
        }

        public Employeer build() {
            final Duty duty = new Duty(this.baseSalary, this.instructionDuty);
            return new Employeer(duty, this.instructionEmploee, this.YearOfAdmission);
        }
    }

}
