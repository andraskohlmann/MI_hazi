package top;

import environment.Activity;

public class WorkerThread extends Thread {
	
	private Visualizer vis;
	
	public WorkerThread(Visualizer v) {
		vis = v;
	}

	@Override
	public void run() {
		Activity[] pi = ExperimentManager.experiment();
		vis.start(pi);
	}
	
}
