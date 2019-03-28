package graph;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.*;
import java.util.HashMap;
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
	private String name;
	private Map<String, Boolean> mutations; //a map with pairs of nodes' tags and integers. A negative int means deactivation, zero means no effect (wild type) and a positive int means activation
	private int cellCount;

	public MutationGroup(String name, Map<String, Boolean> mutations) {
		this.name = name;
		this.mutations = mutations;
	}

	public String getName() {
		return name;
	}

	public Map<String, Boolean> getMutations() {
		return mutations;
	}

	public void changeCellCount(int change) {
		cellCount += change;
	}

	public int getCellCount() {
		return cellCount;
	}

	/**
	 * Parses the mutations file and return it as a Map with every MutationGroup as a key and a double value that represents the percentage of cells in the whole tumor that have that MutationGroup
	 * @param mutationsFile
	 * @return a Map of MutationGroup keys and Double values
	 */
	public static Map<MutationGroup, Double> parseMutationsFile(String mutationsFile){
		Reader in = null;
		try {
			in = new FileReader(MutationGroup.class.getClassLoader().getResource(mutationsFile).getPath());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		CSVParser records = null;
		try {
			records = CSVFormat.EXCEL.withFirstRecordAsHeader().parse(in);
		} catch (IOException e) {
			e.printStackTrace();
		}

		Map<Integer, String> headersByIndexes = new HashMap<>(); //the parsed header map has the name of the headers as keys and their indexes as values, we need it the other way around.
		for (String s : records.getHeaderMap().keySet()) {
			headersByIndexes.put(records.getHeaderMap().get(s), s);
		}

		Map<MutationGroup, Double> groupsMap = new HashMap<>();
		for (CSVRecord record : records) { //every record (row) is a mutation group
			Map<String, Boolean> mutations = new HashMap<>();
			/* Iterates over every gene
			* Starts at 2 because columns 0 and 1 are the name of the group and the percentage, respectively */
			for (int i = 2; i < record.size(); i++) {
				String nodeTag = headersByIndexes.get(i);
				Boolean mutation;
				if(record.get(nodeTag).equals("1")) mutation = true;
				else if(record.get(nodeTag).equals("0")) mutation = false;
				else if(record.get(nodeTag).equals("NA")) mutation = null;
				else throw new IllegalArgumentException("Incorrect mutations file");
				mutations.put(nodeTag, mutation);
			}

			MutationGroup mutationGroup = new MutationGroup(record.get("group"), mutations);
			groupsMap.put(mutationGroup, Double.valueOf(record.get("percent")));
		}
		return groupsMap;
	}
}
