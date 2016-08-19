package math.arithmetic.operand;

import java.util.ArrayList;
import java.util.List;

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
	
	public static List<Double> getValuesWithDivisionFactor(int size, double center, int divisionFactor) {
		List<Double> values = new ArrayList<>();
		
		if(divisionFactor == 1){
			// Si el factor de divisin es 1, pero la respuesta es, ej: 1,5, quiere decir
			// que el verdadero factor de divisin es 2.
			// esto puede ocurrir con la divisin de nmeros enteros.
			divisionFactor = getDivisor(center);			
		}
		
		if(divisionFactor >= 2000){
			System.out.println("Valor de divisor muy alto");
			return null;
		}else{
			for(int i = 0; i < size; i++){
				double value = ((i*1.0 - size / 2 ) / divisionFactor) + center ;
				values.add(value);
			}
			
			return values;
		}
		
		
	}
	
	
	
	public static String getValueString(ArithmeticVariable variable) {
		String result = "";
		
		if(variable.getValue() % 1 == 0)
			result = ""+variable.getValue().intValue();
		else
			result = getValueString(variable.getValue());
		
		return "{"+result+"}";
	}
	
	public static String getValueString(Double value) {
		String result = "";
		
		if(value % 1 == 0)
			result = ""+value.intValue();
		else{
			result = ""+"\\frac{"+getDividend(value)+"}{"+getDivisor(value)+"}";
		}
		
		return "{"+result+"}";
	}
	
	public static int getDividend(double number) {
		return (int) Math.round(number * getDivisor(number));
	}
	
	public static int getDivisor(double number) {
		int divisor = 1; 
		while((number * divisor) % 1 != 0){
			divisor++;
//			System.out.print(". ");
			if(divisor % 100 == 0){
				System.out.println();
			}			
			if(Math.abs(truncateDecimals(3, number * divisor) - Math.round(number * divisor)) < 0.003){
				break;
			}
			
		}
//		System.out.println("\ndivisor: "+divisor);
		return divisor;
	}
	
	public static double truncateDecimals(int decimals, double value){
		return Math.floor(value * Math.pow(10, decimals)) / Math.pow(10, decimals);
	}
	
	/**
	 *
	 * @param nearValue Valor de donde se sacan los dems valores (que sern cercanos a este). Naturalmente ser el resultado.
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
	
	
	public static void main(String[] args) {
//		List<Double> values = getValuesWithDivisionFactor(20, 10, 12);
//		for(int i = 0; i < values.size(); i++){
//			System.out.print(values.get(i)+ " - ");
//		}
//		System.out.println();
//		values = getValuesWithDivisionFactor(20, 10, 1);
//		for(int i = 0; i < values.size(); i++){
//			System.out.print(values.get(i)+ " - ");
//		}
		
	}
	
}


