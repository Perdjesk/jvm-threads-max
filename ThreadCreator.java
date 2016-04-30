public class ThreadCreator {
	public static void main(String[] args) {
		long nbThreads = 0;
		try {
			while (true) {
				new Thread(new Runnable() {
					public void run() {
						try {
							Thread.sleep(Long.MAX_VALUE);
						} catch (InterruptedException e) {
							System.err.println("a thread has been interupted");
						}
						System.err.println("ERROR: this code should not be executed");
					}
				}).start();
				nbThreads++;
			}
		} catch (Throwable e) {
			System.out.println("nbThreads created: " + nbThreads);
			e.printStackTrace();
			System.exit(1);
		}
	}
}
