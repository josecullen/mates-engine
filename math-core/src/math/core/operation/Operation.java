package math.core.operation;

public abstract class Operation<R> {
	boolean sign;
	
	public abstract R operate(R left, R right);
	public abstract String getExpression();
	public abstract String getLaTex();
	public boolean getSign(){
		return sign;
	}
	public void setSign(boolean sign){
		this.sign = sign;
	}
}
