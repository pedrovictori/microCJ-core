package core;

@FunctionalInterface
interface FateExecution {
	void execute(Cell cell, Tumor tumor);
}
