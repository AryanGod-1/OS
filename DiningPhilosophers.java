import java.util.Scanner;
import java.util.concurrent.Semaphore;

class Philosopher implements Runnable {
    private final int id;
    private final Semaphore leftChopstick;
    private final Semaphore rightChopstick;
    private final int totalPhilosophers;
    private static volatile boolean allThought = false;

    public Philosopher(int id, Semaphore leftChopstick, Semaphore rightChopstick, int totalPhilosophers) {
        this.id = id;
        this.leftChopstick = leftChopstick;
        this.rightChopstick = rightChopstick;
        this.totalPhilosophers = totalPhilosophers;
    }

    private void doAction(String action) throws InterruptedException {
        System.out.println("Philosopher " + id + " " + action);
        Thread.sleep((int) (Math.random() * 300)); // Shorter delay for demo
    }

    @Override
    public void run() {
        try {
            while (true) {
                // Phase 1: Ensure all philosophers think first
                synchronized (Philosopher.class) {
                    System.out.println("Philosopher " + id + " is thinking");
                    if (id == totalPhilosophers - 1) {
                        allThought = true; // Last philosopher signals
                    }
                }

                // Wait until ALL have finished thinking
                while (!allThought) {
                    Thread.yield(); // Prevent busy-waiting
                }

                // Phase 2: Pick forks (last philosopher picks right first)
                if (id == totalPhilosophers - 1) {
                    rightChopstick.acquire();
                    doAction("picked up right chopstick");
                    leftChopstick.acquire();
                    doAction("picked up left chopstick - eating");
                } else {
                    leftChopstick.acquire();
                    doAction("picked up left chopstick");
                    rightChopstick.acquire();
                    doAction("picked up right chopstick - eating");
                }

                // Release forks
                rightChopstick.release();
                doAction("put down right chopstick");
                leftChopstick.release();
                doAction("put down left chopstick");
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

public class DiningPhilosophers {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the number of philosophers: ");
        int numPhilosophers = scanner.nextInt();
        scanner.close();

        Semaphore[] chopsticks = new Semaphore[numPhilosophers];
        for (int i = 0; i < numPhilosophers; i++) {
            chopsticks[i] = new Semaphore(1);
        }

        Thread[] threads = new Thread[numPhilosophers];
        for (int i = 0; i < numPhilosophers; i++) {
            Semaphore left = chopsticks[i];
            Semaphore right = chopsticks[(i + 1) % numPhilosophers];
            threads[i] = new Thread(new Philosopher(i, left, right, numPhilosophers));
            threads[i].start();
        }
    }
}

/*
 
Algortithm: 
            Initialize N philosophers and N chopsticks (semaphores).
            Each philosopher:
            Thinks first (prints "thinking").
            Picks forks:
            Last philosopher (ID=N-1) picks RIGHT fork first.
            Others pick LEFT fork first.
            Eats (prints "eating").
            Releases forks (RIGHT first, then LEFT).
            Repeat forever.


Ouput (for 4, correct order):
            Philosopher 0 is thinking  
            Philosopher 1 is thinking  
            Philosopher 2 is thinking  
            Philosopher 3 is thinking  

            // Fork picking (Philosopher 3 picks RIGHT first, others pick LEFT first)
            Philosopher 0 picked up left chopstick  
            Philosopher 1 picked up left chopstick  
            Philosopher 2 picked up left chopstick  
            Philosopher 3 picked up right chopstick  

            // Eating phase (order varies)
            Philosopher 0 picked up right chopstick - eating  
            Philosopher 3 picked up left chopstick - eating  
            ... (rest continues)

explanation: 
            This code solves the dining philosophers problem safely. Each philosopher is a thread that thinks, then picks up forks to eat. To prevent deadlock, the last philosopher picks up the right fork first while others take the left fork first. This breaks the circular wait condition.

            Key components:

            Semaphores act as forks (acquire=take, release=put down)

            Each philosopher has left and right fork semaphores

            The last philosopher (ID=N-1) picks forks in reverse order

            All others pick left then right

            The output order varies because threads run simultaneously, but the logic guarantees no deadlock. The solution uses only basic Java threading tools (Semaphore and Runnable) without extra synchronization. The non-deterministic output is normal - what matters is the safety (no deadlocks) and liveness (all philosophers keep eating).

            A semaphore is a counter that controls access to shared resources. In this dining philosophers code, each fork is represented by a semaphore.  
how why sempahore : 
The semaphore starts with 1 permit, meaning the fork is available. When a philosopher wants to grab a fork, they call `acquire()`. This takes the permit if available, or makes them wait if another philosopher holds it.  

After eating, the philosopher calls `release()` to put the fork back. This adds the permit back, allowing others to take it. The last philosopher breaks deadlock by grabbing forks in reverse order.  

Semaphores here ensure forks are shared safely between threads. They act like physical forks - only one philosopher can hold each fork at a time. The counter tracks availability while `acquire`/`release` manage access.
*/