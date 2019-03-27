package graph;

import java.util.Map;

/**
 * @author Pedro Victori
 */
/*
Copyright 2019 Pedro Victori

Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */
public class MutationGroup {
	String name;
	Map<Node, Boolean> mutations; //a map with pairs of nodes and integers. A negative int means deactivation, zero means no effect (wild type) and a positive int means activation

	public MutationGroup(String name, Map<Node, Boolean> mutations) {
		this.name = name;
		this.mutations = mutations;
	}

	public String getName() {
		return name;
	}

	public Map<Node, Boolean> getMutations() {
		return mutations;
	}
}
