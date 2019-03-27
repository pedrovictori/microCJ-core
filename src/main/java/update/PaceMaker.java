package update;
/*
Copyright 2019 Pedro Victori

Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */
/**
 * @author Pedro Victori
 */

import core.World;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class PaceMaker {
	/**
	 * Default interval between steps, in milliseconds
	 */
	private static final long DEFAULT_INTERVAL = 300;
	private static final int THREAD_POOL_SIZE = 1; //right now only one clock task will be spawned at the same time.
	private long interval;
	private int step = 0;
	private List<Timed> listeners = new ArrayList<>();
	private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(THREAD_POOL_SIZE);

	public PaceMaker(long interval) {
		this.interval = interval;
	}

	public PaceMaker() {
		this(DEFAULT_INTERVAL);
	}

	public void startClock(long delay) {
		Timer timer = new Timer("clock", true);

		Runnable clock = () -> {
			System.out.println("step " + step);

			World.INSTANCE.update();
			step++;
			for (Timed task : listeners) {
				task.run();
			}
			System.out.println("finished " + step);
		};

		scheduler.scheduleAtFixedRate(clock, delay, delay, TimeUnit.MILLISECONDS);
	}

	public void startClock() {
		startClock(interval);
	}

	/**
	 * Adds a listener to the step counter. Whenever a new step is executed, the given task will be executed as well.
	 * @param task the task that will be executed every step.
	 */
	public void addListener(Timed task) {
		listeners.add(task);
	}
}
