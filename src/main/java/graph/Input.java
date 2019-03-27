package graph;

import core.Identifier;

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

public class Input extends Identifier implements Node{
	private String tag;
	private boolean active;

	public Input(String tag) {
		this.tag = tag;
	}

	public Input(String tag, boolean active) {
		this.tag = tag;
		this.active = active;
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
	public boolean computeState(Map<String, Boolean> values) { //this method is here just to comply with the Node interface, but it does nothing.
		return isActive(); //Nothing to compute, the logic of the network can't affect the activation status of an input node (only external signals can).
	}

	@Override
	public void applyMutation(Boolean value) {
		//do  nothing, inputs don't mutate.
	}

	@Override
	public String toString() {
		return tag;
	}
}
