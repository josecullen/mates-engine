package math.arithmetic.operand;

import static org.junit.Assert.*;

import org.junit.Test;

import exceptions.DivisorTooLongException;

public class ArithmeticVariableUtilTest {

	@Test
	public void getDivisorTest() {

		for (int divisor = 1; divisor < 100; divisor++) {
			System.out.println("Divisor: " + divisor);
			for (int divisible = 0; divisible < divisor; divisible++) {
				double number = 1.0 / divisor * divisible;
				System.out.println("Number : " + number + "\tDivisor:" + ArithmeticVariableUtil.getDivisor(number));
			}
			System.out.println();
		}

	}

	@Test
	public void getValuesWithDivisionFactorTest() {
		for (int divisor = 1; divisor < 12; divisor++) {

			System.out.println("Divisor: " + divisor);
			for (int divisible = 0; divisible < divisor; divisible++) {
				double number = 1.0 / divisor * divisible;
				System.out.println(ArithmeticVariableUtil.getValuesWithDivisionFactor(20, number, divisor));
			}
		}
		System.out.println();
	}

}
