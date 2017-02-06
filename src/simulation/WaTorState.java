package simulation;

import model.State;
import xml.XMLParser;

public class WaTorState extends State {
	
	private String nextValue;
	private int age;
	private int food;
	private int step;
	private boolean ate;
	
	public WaTorState(XMLParser configuration, String value, int stepVal, int cellAge, int noFoodTime) {
		super(configuration, value);
		nextValue = null;
		age = cellAge;
		food = noFoodTime;
		if(value.equals("empty")) age = -1;
		if(!value.equals("shark")) food = -1;
		step = stepVal;
		ate = false;
	}
	
	public void eat() {
		ate = true;
		food = 0;
	}
	
	public boolean ate() {
		return ate;
	}
	
	public void setNextValue(String value) {
		nextValue = value;
	}
	
	public String getNextValue() {
		return nextValue;
	}
	
	public int getStep() {
		return step;
	}
	
	public void incrStep() {
		step++;
	}
	
	public void setStep(int newStep) {
		step = newStep;
	}
	
	public int getCellAge() {
		return age;
	}
	
	public void setCellAge(int newAge) {
		age = newAge;
	}
	
	public int getNoFoodTime() {
		return food;
	}
	
	public void setNoFoodTime(int noFoodTime) {
		food = noFoodTime;
	}
}
