import java.io.*;
import java.util.*;
import java.lang.Math;
import java.lang.Thread;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReentrantLock;

public class MinotaursCrystalVase implements Runnable{
	public static final String ANSI_RESET = "\u001B[0m";
	public static final String ANSI_RED = "\u001B[31m";
	public static final String ANSI_GREEN = "\u001B[32m";

	private static final Random rand = new Random();
	private static ReentrantLock lock = new ReentrantLock();
	private static int numGuests;
	private static int amountGuestsNotVased;
	private static int[] vaseVisits;
	private static boolean visitsEnded = false;
	
	private int threadNum;

	public MinotaursCrystalVase(int threadNum) {
		this.threadNum = threadNum;
	}

	// Simulate guests viewing the vase and making the sign say "BUSY"
	public void run() {
		if (amountGuestsNotVased == 0) {
			if (visitsEnded == false) {
				visitsEnded = true;
				print("The amount of guests that have not visited the vase is " + amountGuestsNotVased
				+ ", thus all guests have had time in the showroom!");
			}
			return;
		}

		lock.lock();
		try {
			if (vaseVisits[threadNum] > 0) {
				print("Guest #" + threadNum + " has made an additional visit to the showroom!");
			} else {
				print("Guest #" + threadNum + " has just visited the showroom!");
			}
			print("***SHOWROOM STATUS: " + ANSI_RED + "BUSY" + ANSI_RESET);

			print("Guest #" + threadNum + " has finished viewing the vase and left the showroom!");

			print("***SHOWROOM STATUS: " + ANSI_GREEN + "AVAILABLE" + ANSI_RESET);
			if (vaseVisits[threadNum] == 0) {
				amountGuestsNotVased--;
			}
			vaseVisits[threadNum]++;

		} finally {
			lock.unlock();
			if (amountGuestsNotVased == 0) {
				if (visitsEnded == false) {
					visitsEnded = true;
					print("The amount of guests that have not visited the vase is " + amountGuestsNotVased
					+ ", thus all guests have had time in the showroom!");
				}
				return;
			}
			int goBackIn = rand.nextInt(10);
			if (goBackIn > 5) {
				Runnable thisGuest = new MinotaursCrystalVase(threadNum);
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
		
		Runnable[] guests = new MinotaursCrystalVase[numGuests];
		vaseVisits = new int[numGuests];
		amountGuestsNotVased = numGuests;

		for (int i = 0; i < numGuests; i++) {
			guests[i] = new MinotaursCrystalVase(i);
			new Thread(guests[i]).start();
		}
	}

	// Just a shortcut for System.out.println()
	private static void print(String s) {
		System.out.println(s);
	}
}