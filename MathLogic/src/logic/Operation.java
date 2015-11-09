package logic;

public enum Operation {
	AND, OR, THEN;
	
	String getString(){
		switch (this) {
		case AND:
			return "and";
		case OR:
			return "or";
		case THEN:
			return "then";

		default:
			return "Operación no implementada";			
		}
	}
	
	public static Operation getOperation(String operationString){
		switch (operationString) {
		case "and":
			return AND;
		case "or":
			return OR;
		case "then":
			return THEN;
		default:
			return null;
		}
	}
	
	public static Operation getRandom(){
		int index = (int) (Math.random()*Operation.values().length);
		
		return Operation.values()[index];
	}
	
}
