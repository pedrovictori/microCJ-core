package graph;
/**
 * @author Pedro Victori
 */
/*
Copyright 2019 Pedro Victori

Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

import core.Identifier;
import core.Node;

public class GeneLink extends Identifier {
	private Node target;
	private Node source;
	private Boolean positive;

	public GeneLink(Node source, Node target){
		this.target = target;
		this.source = source;
	}

	public GeneLink(Node source, Node target, boolean positive) {
		this(target, source);
		this.positive = positive;
	}

	public Node getTarget() {
		return target;
	}

	public Node getSource() {
		return source;
	}

	public boolean isPositive() {
		return positive;
	}

	@Override
	public String toString() {
		return ""; //todo remove this later, just for visualization testing
	}
}
