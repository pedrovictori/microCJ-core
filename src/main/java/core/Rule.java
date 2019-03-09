package core;

import com.bpodgursky.jbool_expressions.Expression;
import com.bpodgursky.jbool_expressions.parsers.ExprParser;
import com.bpodgursky.jbool_expressions.rules.RuleSet;
import graph.GeneGraph;

import java.util.HashMap;
import java.util.Map;

public class Rule {
	private Node origin;
	private Expression<String> rule;

	public Rule(Node origin, Expression<String> rule) {
		this.origin = origin;
		this.rule = rule;
	}

	public boolean checkStatus(GeneGraph geneGraph) {
		Map<String, Boolean> values = new HashMap<>();
		for (Node node : geneGraph.getGraph().vertexSet()) {
			values.put(node.getTag(), node.isActive());
		}

		Expression<String> resolved = RuleSet.assign(rule, values);

		return Boolean.parseBoolean(resolved.toString());
	}

	public static Rule ruleParser(Node origin, String stringRule) {
		//replace all operators with boolean symbols
		stringRule = stringRule.replace("and", "&")
				.replace("or", "|")
				.replace("not", "!");

		Expression<String> parsedExpression = RuleSet.simplify(ExprParser.parse(stringRule));

		return new Rule(origin, parsedExpression);
	}
}
