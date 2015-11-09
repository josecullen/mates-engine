import static org.junit.Assert.*;
import analyzer.TruthOption;

import org.junit.Test;

import analyzer.TruthAnalyzer;
public class TruthAnalyzerTest {

	@Test
	public void analyzeOptions() {
		assertEquals(TruthOption.CONTINGENCY, TruthAnalyzer.analyzeTruthOptions("p and q"));
		assertEquals(TruthOption.CONTINGENCY, TruthAnalyzer.analyzeTruthOptions("p or q"));
		assertEquals(TruthOption.CONTRADICTION, TruthAnalyzer.analyzeTruthOptions("p and -p"));
		assertEquals(TruthOption.TAUTOLOGY, TruthAnalyzer.analyzeTruthOptions("p or -p"));
		
		assertEquals(TruthOption.CONTINGENCY, TruthAnalyzer.analyzeTruthOptions("p or (p and q)"));
		assertEquals(TruthOption.CONTINGENCY, TruthAnalyzer.analyzeTruthOptions("p or (r and s)"));
		assertEquals(TruthOption.CONTINGENCY, TruthAnalyzer.analyzeTruthOptions("p then -(p and q)"));
	
		assertEquals(TruthOption.CONTRADICTION, TruthAnalyzer.analyzeTruthOptions("(p then p) then -(p or -p)"));
		assertEquals(TruthOption.TAUTOLOGY, TruthAnalyzer.analyzeTruthOptions("-((p then p) then -(p or -p))"));
	}

}
