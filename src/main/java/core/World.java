package core;

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

	World() {
		// TODO: 21/03/2019 complete
	}

	public Tumor getTumor() {
		return tumor;
	}

	/**
	 * Retrieves and removes the head of the update queue, waiting if necessary until an element becomes available.
	 * The head will be the element with the highest UpdateFlag priority
	 * @return an Update that has been removed from the head of the update queue
	 * @throws InterruptedException
	 */
	public Update<UpdateFlag, Updatable> takeUpdateFromQueue() throws InterruptedException {
		return updateQueue.take();
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
