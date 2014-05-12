import it.sauronsoftware.cron4j.Scheduler;


public class HelloWorld {

	public static void main(String[] args) {
		// Prepares the task.
		MyTask task = new MyTask();
		// Creates the scheduler.
		Scheduler scheduler = new Scheduler();
		// Schedules the task, once every minute.
		scheduler.schedule("* * * * *", task);
		// Starts the scheduler.
		scheduler.start();
		// Stays alive for five minutes.
		
		try {
			Thread.sleep(24L * 60L * 60L * 1000L);
		} catch (InterruptedException e) {
			;
		}
		// Stops the scheduler.
		scheduler.stop();
	}
}
