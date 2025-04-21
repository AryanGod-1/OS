import java.util.*;

public class PageReplacement {

    // FIFO Page Replacement
    public static int fifoPageFaults(int capacity, int[] pages) {
        Set<Integer> set = new HashSet<>();
        Queue<Integer> queue = new LinkedList<>();
        int faults = 0;

        for (int page : pages) {
            if (!set.contains(page)) {
                if (set.size() == capacity) {
                    int removed = queue.poll();
                    set.remove(removed);
                }
                set.add(page);
                queue.add(page);
                faults++;
            }
        }
        return faults;
    }

    // LRU Page Replacement
    public static int lruPageFaults(int capacity, int[] pages) {
        Set<Integer> set = new HashSet<>();
        Map<Integer, Integer> recentUse = new HashMap<>();
        int faults = 0;

        for (int i = 0; i < pages.length; i++) {
            int page = pages[i];
            if (!set.contains(page)) {
                if (set.size() == capacity) {
                    int lru = Collections.min(recentUse.entrySet(), Map.Entry.comparingByValue()).getKey();
                    set.remove(lru);
                    recentUse.remove(lru);
                }
                set.add(page);
                faults++;
            }
            recentUse.put(page, i); // update recent use
        }
        return faults;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Input capacity
        System.out.print("Enter number of page frames: ");
        int capacity = sc.nextInt();
        sc.nextLine(); // consume newline

        // Input reference string
        System.out.print("Enter page reference string (space-separated): ");
        String[] input = sc.nextLine().split(" ");
        int[] pages = Arrays.stream(input).mapToInt(Integer::parseInt).toArray();

        int fifoFaults = fifoPageFaults(capacity, pages);
        int lruFaults = lruPageFaults(capacity, pages);

        // Output faults
        System.out.println("FIFO Page Faults: " + fifoFaults);
        System.out.println("LRU Page Faults: " + lruFaults);
    }
}


/*
 * INPUT FOR OUTPUT : 
                 3, 7 0 1 2 0 3 0 4 2 3
 * ALGORITHM
                FIFO:
                Start traversing the pages.
                Now declare the size w.r.t length of the Page.
                Check need of the replacement from the page to memory.
                Similarly, Check the need of the replacement from the old page to new page in memory.
                Now form the queue to hold all pages.
                Insert Require page memory into the queue.
                Check bad replacements and page faults.
                Get no of processes to be inserted.
                Show the values.

                LRU:
                Step 1. Start the process
                Step 2. Declare the page size
                Step 3. Determine the number of pages to be inserted.
                Step 4. Get the value.
                Step 5. Declare the counter and stack value.
                Step 6. Choose the least recently used page by the counter value.
                Step 7. Stack them as per the selection.
                Step 8. Display the values.
                Step 9. Terminate the process.      
                Stop
                
 * EXPLANATION :
This program demonstrates two memory management algorithms—FIFO and LRU—used by operating systems to minimize page faults. FIFO removes the oldest page using a queue-based approach, while LRU evicts the least recently accessed page by tracking usage timestamps. Both methods use a HashSet for fast page lookups, with FIFO employing a Queue for order tracking and LRU using a HashMap to record access times.  
The user inputs memory capacity and a page reference string. For each page request, FIFO checks if the page exists in memory, replacing the oldest entry when full. LRU does the same but replaces the page with the oldest access timestamp. The program outputs fault counts for both methods.  
FIFO is simpler but can exhibit Belady's anomaly, while LRU is more efficient but requires extra overhead. The implementation correctly shows how systems balance memory constraints and performance, with FIFO prioritizing simplicity and LRU favoring optimal page retention. 
 */