import java.io.*;
import java.util.*;
import java.lang.Math;
import java.lang.Thread;
import java.time.LocalDateTime;  
import java.time.format.DateTimeFormatter;  
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReentrantLock;

public class MinotaursBirthdayParty implements Runnable{
	private static final Random rand = new Random();
	// 360 feet is how long the maze is from start to finish (excluding dead-ends)
	// Guests travel 1 foot per milisecond (which is in seconds in their time)
	// 360ms is the quickest a guest can finish the maze (no speeding allowed)
	private static final int mazeLength = 360;
	
	private static ReentrantLock lock = new ReentrantLock();
	private static int numGuests;
	private static int numEmptyPlatesFoundByZeroThread;
	private static int[] cupcakesEaten;
	private static String output = ""; 
	private static DateTimeFormatter format = DateTimeFormatter.ofPattern("HH:mm:ss"); 
	private static boolean labyrinthEnded = false;
	private static boolean cupCakePresent = true;
	
	private LocalDateTime currentTime;
	private int threadNum;

	public MinotaursBirthdayParty(int threadNum) {
		this.threadNum = threadNum;
	}

	public void zerothThread(int threadNum) {
		threadNum = 0;
		this.currentTime = LocalDateTime.now();
		if (cupcakesEaten[threadNum] > 0 || (threadNum == 0 && numEmptyPlatesFoundByZeroThread > 0)) {
			print(getTimeString(currentTime) + ": Guest #" + threadNum + " has re-entered the maze!");
		} else {
			print(getTimeString(currentTime) + ": Guest #" + threadNum + " has just entered the maze!");
		}

		int mazeCompletionTime = getMazeCompletionTime();
		try {
			Thread.sleep(mazeCompletionTime);
		} catch (InterruptedException e) {
			print(e.getMessage());
			e.printStackTrace();
		}
		currentTime = currentTime.plusSeconds(mazeCompletionTime);
		print(getTimeString(currentTime) + ": Guest #" + threadNum + " has finished the maze in " + mazeCompletionTime + " seconds!");

		if (threadNum == 0) {
			// The 0th thread does not take a cupcake and leaves one if necessary
			if (cupCakePresent) {
				// Leave the cupcake there
				print(getTimeString(currentTime) + ": Guest #" + threadNum + " has left the cupcake present!");
			} else {
				// Put a cupcake down
				numEmptyPlatesFoundByZeroThread++;
				cupCakePresent = true;
				print(getTimeString(currentTime) + ": Guest #" + threadNum + " has ordered a cupcake and left it!");
			}
		} else {
			// The guest only eats a cupcake if they haven't eaten one yet
			if (cupcakesEaten[threadNum] == 0) {
				cupCakePresent = false;
				cupcakesEaten[threadNum]++;
				print(getTimeString(currentTime) + ": Guest #" + threadNum + " has taken a cupcake!");
			} else {
				print(getTimeString(currentTime) + ": Guest #" + threadNum + " has left the cupcake present!");
			}
		}

		if (numEmptyPlatesFoundByZeroThread == (numGuests-1)) {
			if (labyrinthEnded == false) {
				labyrinthEnded = true;
				print("The number of empty plates found by the 0th thread is: " + numEmptyPlatesFoundByZeroThread
				+ " = numGuests-1 = " + numGuests + "-1, thus all guests have had the chance to enter the labyrinth!");
			}
			return;
		}
	}

	// Simulate guests entering the maze and receiving a cupcake at the end
	public void run() {
		if (numEmptyPlatesFoundByZeroThread == (numGuests-1)) {
			if (labyrinthEnded == false) {
				labyrinthEnded = true;
				print("The number of empty plates found by the 0th thread is: " + numEmptyPlatesFoundByZeroThread
				+ " = numGuests-1 = " + numGuests + "-1, thus all guests have had the chance to enter the labyrinth!");
			}
			return;
		}

		lock.lock();
		try {
			this.currentTime = LocalDateTime.now();
			if (cupcakesEaten[threadNum] > 0 || (threadNum == 0 && numEmptyPlatesFoundByZeroThread > 0)) {
				print(getTimeString(currentTime) + ": Guest #" + threadNum + " has re-entered the maze!");
			} else {
				print(getTimeString(currentTime) + ": Guest #" + threadNum + " has just entered the maze!");
			}

			int mazeCompletionTime = getMazeCompletionTime();
			try {
				Thread.sleep(mazeCompletionTime);
			} catch (InterruptedException e) {
				print(e.getMessage());
				e.printStackTrace();
			}
			currentTime = currentTime.plusSeconds(mazeCompletionTime);
			print(getTimeString(currentTime) + ": Guest #" + threadNum + " has finished the maze in " + mazeCompletionTime + " seconds!");

			if (threadNum == 0) {
				// The 0th thread does not take a cupcake and leaves one if necessary
				if (cupCakePresent) {
					// Leave the cupcake there
					print(getTimeString(currentTime) + ": Guest #" + threadNum + " has left the cupcake present!");
				} else {
					// Put a cupcake down
					numEmptyPlatesFoundByZeroThread++;
					cupCakePresent = true;
					print(getTimeString(currentTime) + ": Guest #" + threadNum + " has ordered a cupcake and left it!");
				}
			} else {
				// The guest only eats a cupcake if they haven't eaten one yet
				if (cupcakesEaten[threadNum] == 0) {
					cupCakePresent = false;
					cupcakesEaten[threadNum]++;
					print(getTimeString(currentTime) + ": Guest #" + threadNum + " has taken a cupcake!");
				} else {
					print(getTimeString(currentTime) + ": Guest #" + threadNum + " has left the cupcake present!");
				}
			}

			// Simulating the 0th thread going in after each thread
			if (threadNum != 0) {
				// Runnable threadZero = new MinotaursBirthdayParty(0);
				// new Thread(threadZero).start();
				zerothThread(0);
			}

		} finally {
			lock.unlock();

			int goBackIn = rand.nextInt(10);
			if (goBackIn > 5) {
				Runnable thisGuest = new MinotaursBirthdayParty(threadNum);
				new Thread(thisGuest).start();
			}
		}
		
	}

	public static void main(String[] args) throws InterruptedException, IOException {
		Scanner sc = new Scanner(System.in);
		print("Please enter your desired number of guests:");
		numGuests = sc.nextInt();

		while (numGuests < 1) {	
			print("Please enter your desired number of guests (greater than 0):");
			numGuests = sc.nextInt();
		}

		print("HH:mm:ss");
		
		Runnable[] guests = new MinotaursBirthdayParty[numGuests];
		cupcakesEaten = new int[numGuests];
		numEmptyPlatesFoundByZeroThread = 0;

		for (int i = 0; i < numGuests; i++) {
			guests[i] = new MinotaursBirthdayParty(i);
			new Thread(guests[i]).start();
		}
	}

	// Returns formatted time
	private static String getTimeString(LocalDateTime time) {
		return time.format(format);
	}

	// Returns a random amount of time (360ms to 1080ms) for a guest to complete a maze
	private static int getMazeCompletionTime() {
		// Generate random int in range 0 to 2*mazeLength
		// Thus, guests can finish either in minimum time or 3 times that
		int amountSecondsDelay = rand.nextInt(2*mazeLength);

		return mazeLength + amountSecondsDelay;
	}

	// Just a shortcut for System.out.println()
	private static void print(String s) {
		System.out.println(s);
	}
}