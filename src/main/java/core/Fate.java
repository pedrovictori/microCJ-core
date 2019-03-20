package core;

public enum Fate {
	PROLIFERATION((cell, tumor) -> {
		tumor.proliferate(cell);

	}),
	GROWTH_ARREST((cell, tumor) -> {
		cell.arrest();
	}),
	APOPTOSIS((cell, tumor) -> {
		tumor.apoptose(cell);

	}),
	NECROSIS((cell, tumor) -> {
		cell.necrotize();

	});

	private FateExecution executionRule;

	Fate(FateExecution executionRule) {
		this.executionRule = executionRule;
	}

	FateExecution getExecutionRule() {
		return executionRule;
	}
}
