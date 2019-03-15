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

public class Fate extends Identifier implements InNode{
	private String tag;
	private boolean active;
	private Rule rule;

	public Fate(String tag) {
		this.tag = tag;
	}

	public Fate(String tag, boolean active) {
		this.tag = tag;
		this.active = active;
	}

	public Fate(String tag, String rule) {
		this(tag);
		this.rule = Rule.ruleParser(this, rule);
	}


	public Fate(String tag, String rule, boolean active) {
		this(tag, active);
		this.rule = Rule.ruleParser(this, rule);
	}

	@Override
	public Rule getRule() {
		return rule;
	}

	@Override
	public String getTag() {
		return tag;
	}

	@Override
	public void setActive(boolean active) {
		this.active = active;
	}

	@Override
	public boolean isActive() {
		return active;
	}

	@Override
	public String toString() {
		return tag;
	}
}
