package math.core.operand;

public interface Variable<R> {
	public R getValue();
	public void setValue(R value);
	public String getVariable();
	public boolean getSign();
	public void setSign(boolean sign);
	
	public void set(Variable<R> variable);
}
