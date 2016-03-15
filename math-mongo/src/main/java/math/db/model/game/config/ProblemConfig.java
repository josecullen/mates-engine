package math.db.model.game.config;

import java.util.List;

import org.mongodb.morphia.annotations.Embedded;

@Embedded
public class ProblemConfig {
	private ProblemType problemType;
	private List<VariableConfig> variableConfigs;
	private String operations;
	private String form;
	private int repetitions;


	public ProblemConfig() {}

	public ProblemConfig(ProblemType problemType, List<VariableConfig> variableConfigs) {
		this.problemType = problemType;
		this.variableConfigs = variableConfigs;
	}

	public ProblemConfig(
			ProblemType problemType, 
			List<VariableConfig> variableConfigs, 
			String form,
			String operations,
			int repetitions) {
		this.problemType = problemType;
		this.variableConfigs = variableConfigs;
		this.form = form;
		this.operations = operations;
		this.repetitions = repetitions;
	}

	public String getForm() {
		return form;
	}

	public void setForm(String form) {
		this.form = form;
	}

	public String getOperations() {
		return operations;
	}

	public void setOperations(String operations) {
		this.operations = operations;
	}

	public List<VariableConfig> getVariableConfigs() {
		return variableConfigs;
	}

	public void setVariableConfigs(List<VariableConfig> variableConfigs) {
		this.variableConfigs = variableConfigs;
	}

	public ProblemType getProblemType() {
		return problemType;
	}

	public void setProblemType(ProblemType problemType) {
		this.problemType = problemType;
	}
	
	public int getRepetitions() {
		return repetitions;
	}

	public void setRepetitions(int repetitions) {
		this.repetitions = repetitions;
	}
}
