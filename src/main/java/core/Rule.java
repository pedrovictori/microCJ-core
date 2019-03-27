package core;
/**
 * @author Pedro Victori
 */
/*
Copyright 2019 Pedro Victori

Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

import com.bpodgursky.jbool_expressions.Expression;
import com.bpodgursky.jbool_expressions.parsers.ExprParser;
import com.bpodgursky.jbool_expressions.rules.RuleSet;

import java.util.Map;

public class Rule {
	private Expression<String> rule;

	Rule(Expression<String> rule) {
		this.rule = rule;
	}

	/**
	 * Return the result of the boolean rule using a map of nodes' tags and their values as input
	 * @param values A Map of tags and boolean activation values.
	 * @return the result of computing the boolean rule with the specified values.
	 */
	boolean computeRule(Map<String, Boolean> values) {
		Expression<String> resolved = RuleSet.assign(rule, values);

		return Boolean.parseBoolean(resolved.toString());
	}

	static Rule ruleParser(String stringRule) {
		//replace all operators with boolean symbols
		stringRule = stringRule.replace("and", "&")
				.replace("or", "|")
				.replace("not", "!");

		Expression<String> parsedExpression = RuleSet.simplify(ExprParser.parse(stringRule));

		return new Rule(parsedExpression);
	}
}
