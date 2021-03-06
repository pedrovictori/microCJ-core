package graph;

import core.Identifier;

import java.util.Map;
import java.util.Optional;

/**
 * @author Pedro Victori
 */
/*
Copyright 2019 Pedro Victori

Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

public class Gene extends Identifier implements Node {
    private String tag;
    private boolean active;
    private Rule rule;
    private Boolean mutation; //optional, can be null

    public Gene(String tag) {
        this.tag = tag;
    }

    public Gene(String tag, boolean active) {
        this.tag = tag;
        this.active = active;
    }

    public Gene(String tag, String rule) {
        this(tag);
        this.rule = Rule.ruleParser(rule);
    }

    public Gene(String tag, String rule, boolean active) {
        this(tag, active);
        this.rule = Rule.ruleParser(rule);
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
    public boolean computeState(Map<String, Boolean> values) {
        boolean result = rule.computeRule(values);
        return getMutation().orElse(result); //returns the mutation if present, the computed result otherwise
    }

    @Override
    public void applyMutation(Boolean value) {
        mutation = value;
    }

    /**
     * Returns an Optional with a Boolean value that might be null. true means activation, false deactivation, null no effect (wild type).
     * @return An empty Optional or an Optional containing a Boolean value.
     */
    Optional<Boolean> getMutation() {
            return Optional.ofNullable(mutation);
    }

    @Override
    public String toString() {
        return tag;
    }
}
