package logic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import factory.OperationFactory;

public class OperandNode extends LogicNode {
	String name;
	boolean value;	
	boolean useValuesRatherNames = false;
	
	public OperandNode(boolean value){
		this.value = value;
	}
	public OperandNode(String name){
		setName(name);
	}
	public OperandNode(String name, boolean value){
		setName(name);
		setValue(value);
	}
	public OperandNode(String name, boolean value, boolean sign){
		setName(name);
		setValue(value);
		setSign(sign);
	}
	
	
	public boolean getValue(){		
		return sign ? !value : value;
	}
	public void setValue(boolean value){
		this.value = value;
	}
	public String getName(){
		return name;
	}
	public void setName(String name){
		name = name.trim();
		if(name.startsWith("-")){
			setSign(true);
			name = name.substring(1);
			name = name.trim();
		}
		this.name = name;
	}
	
	@Override
	boolean getResult() {		
		return getValue();
	}

	@Override
	public void broadcast(String key, Object value) {
		switch(key){
		case "values":
			setValue(((HashMap<String, Boolean>)value).get(name));
			break;
		case "useValuesRatherNames":
			if(((ArrayList<String>)value).contains(name)){
				useValuesRatherNames = true; 
			}
			
		}
	}

	@Override
	public String getExpression() {
		
		if(useValuesRatherNames){	
			useValuesRatherNames = false;
			return ((sign && value) || (!sign && !value)) ? "F" : "T";
		}
		
		return sign ? "-"+name : name;
	}
	@Override
	HashMap<String, Boolean> getVariables(HashMap<String, Boolean> map) {
		map.put(getName(), getValue());
		return map;
	}
	@Override
	void randomize(int zeroProb, String names[], HashMap<String, Boolean> map) {
		setName(OperationFactory.randomName(names));
		setValue(map.get(getName()));
		setSign(OperationFactory.randomBoolean(zeroProb));

	}

}
