package core;

import update.PaceMaker;
import update.Updatable;
import update.Update;
import update.UpdateFlag;

import java.util.PriorityQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;

public enum World { //this is an enum to ensure it remains a singleton: one and only one instance can exist.
	INSTANCE;

	private Tumor tumor;
	private BlockingQueue<Update<UpdateFlag, Updatable>> updateQueue = new PriorityBlockingQueue<>();
	private PaceMaker paceMaker;

	World() {
		tumor = new Tumor(); // TODO: 22/03/2019 complete with user set parameters
		this.paceMaker = new PaceMaker();
	}

	public void start() {
		paceMaker.startClock();
	}

	public PaceMaker getPaceMaker() {
		return paceMaker;
	}

	/**
	 * Get the number of updates in the UpdateQueue
	 * @return an int with the number of updates remaining in the UpdateQueue
	 */
	public int getRemainingUpdates() {
		return updateQueue.size();
	}

	public Tumor getTumor() {
		return tumor;
	}

	public synchronized void update() {
		tumor.updateAllCells();
	}

	/**
	 * Retrieves and removes the head of the update queue, or returns null if there are not available updates.
	 * The head will be the element with the highest UpdateFlag priority
	 * @return an Update that has been removed from the head of the update queue or null if no available updates.
	 */
	public Update<UpdateFlag, Updatable> getUpdateFromQueue() {
		return updateQueue.poll();
	}

	/**
	 * Inserts the specified update into the queue, waiting if necessary for space to become available.
	 * @param update
	 * @throws InterruptedException
	 */
	public void addToUpdateQueue(Update<UpdateFlag, Updatable> update) throws InterruptedException {
		updateQueue.put(update);
	}
}
