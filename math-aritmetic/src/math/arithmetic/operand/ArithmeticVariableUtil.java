package math.arithmetic.operand;

public class ArithmeticVariableUtil {
	public static double getValueWithDivisionFactor(double min, double max, int divisionFactor){
		double value = (Math.random() * (max - min)) + min;
		double decimals = (int)((value - (int)value)*100);			
		long appliedFactor = Math.round(decimals / (100 / divisionFactor));
		double addDecimals = 0;
		
		if(appliedFactor != 0){
			addDecimals = ((double)appliedFactor) / ((double)divisionFactor);
		}
		
		value =  (value - (value - (int)value)) + addDecimals;
		return value;
	}
	
	public static String getValueString(ArithmeticVariable variable){
		String result = "";
		
		if(variable.getValue() % 1 == 0)
			result = ""+variable.getValue().intValue();
		else
			result = getValueString(variable.getValue());//""+new DecimalFormat("#.##").format(variable.getValue());
		
		
//		if(variable.getSign())
//			result = "-"+result;			
		
		return "{"+result+"}";
	}
	
	public static String getValueString(Double value){
		String result = "";
		
		if(value % 1 == 0)
			result = ""+value.intValue();
		else{
			result = ""+"\\frac{"+getDividend(value)+"}{"+getDivisor(value)+"}";
		}
		
		return "{"+result+"}";
	}
	
	public static int getDividend(double number){
		return (int) Math.round(number * getDivisor(number));
	}
	
	public static int getDivisor(double number){
		int divisor = 1; 
		while((number * divisor) % 1 != 0){
			divisor++;
			
			if(Math.abs(truncateDecimals(3, number * divisor) - Math.round(number * divisor)) < 0.003){
				break;
			}
			
		}
		return divisor;
	}
	
	public static double truncateDecimals(int decimals, double value){
		return Math.floor(value * Math.pow(10, decimals)) / Math.pow(10, decimals);
	}
	
	/**
	 * 
	 * @param nearValue Valor de donde se sacan los demás valores (que serán cercanos a este). Naturalmente será el resultado. 
	 * @param answers
	 * @return
	 */
	public static double getNoRepeatedAnswer(double nearValue, double[] answers, int divisionFactor){
		double answer = nearValue + (getValueWithDivisionFactor(0,20, divisionFactor) - 10);

		for(Double ans : answers){
			if(ans == answer){ 
				answer = getNoRepeatedAnswer(nearValue, answers, divisionFactor);
			}
		}
		return answer;
	}
	
}
