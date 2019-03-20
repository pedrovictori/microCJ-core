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
	private Node origin;
	private Expression<String> rule;

	public Rule(Node origin, Expression<String> rule) {
		this.origin = origin;
		this.rule = rule;
	}

	public boolean checkStatus(Map<String, Boolean> values) {
		Expression<String> resolved = RuleSet.assign(rule, values);

		return Boolean.parseBoolean(resolved.toString());
	}

	public Node getOrigin() {
		return origin;
	}

	public Expression<String> getRule() {
		return rule;
	}

	static Rule ruleParser(Node origin, String stringRule) {
		//replace all operators with boolean symbols
		stringRule = stringRule.replace("and", "&")
				.replace("or", "|")
				.replace("not", "!");

		Expression<String> parsedExpression = RuleSet.simplify(ExprParser.parse(stringRule));

		return new Rule(origin, parsedExpression);
	}
}
