package math.db.model.game.config;

import org.mongodb.morphia.annotations.Embedded;

@Embedded
public enum ProblemType {
	SIMPLE, EQUATION, LOGIC, SYSTEM_EQUATION, MODULE
}