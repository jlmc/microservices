package org.xine.cdidemos.business.salaries.entity;

public class Duty {

	private double baseSalary;
	private Instruction instruction;

	protected Duty() {
	}

	public Duty(double baseSalary, Instruction instruction) {
		this.baseSalary = baseSalary;
		this.instruction = instruction;
	}

	public double getBaseSalary() {
		return this.baseSalary;
	}

	public Instruction getInstruction() {
		return this.instruction;
	}
}
