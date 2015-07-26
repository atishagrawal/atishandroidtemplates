


package ie.lc.mathApp;





/**
 * Spawns a thread which executes a callback at a set frequency.
 * @see Callback
 */
public class CallbackThread
{
	private Thread   thread;
	private Callback callback;
	private long 	 interval;
	private boolean  threadExecute;
	
	
	
	
	
	/**
	 * Starts a callback thread and runs it immediately.
	 * @param frequencyMillisecs How often to execute the callback.
	 * @param callback The callback to execute.
	 */
	public CallbackThread( long frequencyMillisecs, Callback callback ) {
		this.callback      = callback;
		this.interval      = frequencyMillisecs;
		this.threadExecute = true;
		
		this.thread = new Thread() {
			public void run() {
				threadLoop();
			}
		};
		
		thread.start();
	}
	
	
	
	
	
	/**
	 * Halts callback execution and joins the thread.
	 * The calling thread waits until this operation completes.
	 */
	public void join() {
		threadExecute = false;
		
		while (thread.isAlive()) {
			try {
				thread.join();
			}
			catch (InterruptedException ex) {
				Thread.interrupted();
			}
		}
	}
	
	
	
	
	
	private void threadLoop() {
		while (threadExecute) {
			callback.execute();
		    Util.sleep( interval );
		}
	}
}











