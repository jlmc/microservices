package org.xine.cdidemos.business.tax.entity;

import java.time.Year;

public class Employeer {
	private Duty duty;
	private Year yearOfAdmission;
	private Instruction instruction;

	protected Employeer() {
	}

	protected Employeer(Duty duty, Instruction instruction, Year yearOfAdmission) {
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

		public Builder() {
			this.YearOfAdmission = Year.now();
			this.instructionDuty = Instruction.GRADUATION;
			this.instructionEmploee = Instruction.GRADUATION;
		}

		public Builder withBaseSalary(double baseSalary) {
			this.baseSalary = baseSalary;
			return this;
		}

		public Builder withYearOfAdmission(Year year) {
			this.YearOfAdmission = Year.now();
			return this;
		}

		public Builder withInstructionDuty(Instruction instructionDuty) {
			this.instructionDuty = instructionDuty;
			return this;
		}

		public Builder withInstructionEmploee(Instruction instructionEmploee) {
			this.instructionEmploee = instructionEmploee;
			return this;
		}

		public Employeer build() {
			final Duty duty = new Duty(this.baseSalary, this.instructionDuty);
			return new Employeer(duty, this.instructionEmploee, this.YearOfAdmission);
		}
	}

}
