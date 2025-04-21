import java.util.Arrays;
public class LOOK {
    public static void main(String[] args) {
        int[] requests = {98, 183, 37, 122, 14, 124, 65, 67};
        int head = 53;
        int totalSeek = 0;

        Arrays.sort(requests); // sort the requests

        // Find the first request greater than or equal to head
        int split = 0;
        for (int i = 0; i < requests.length; i++) {
            if (requests[i] >= head) {
                split = i;
                break;
            }
        }

        System.out.println("LOOK Order:");
        // Move right
        for (int i = split; i < requests.length; i++) {
            System.out.print(requests[i] + " ");
            totalSeek += Math.abs(requests[i] - head);
            head = requests[i];
        }

        // Then move left
        for (int i = split - 1; i >= 0; i--) {
            System.out.print(requests[i] + " ");
            totalSeek += Math.abs(requests[i] - head);
            head = requests[i];
        }

        System.out.println("\nTotal seek time: " + totalSeek);
    }
}

/*
 * ALGORITHM : LOOK 
            1 The Request array holds track indexes in ascending order; head is the starting position.
            2 The head moves in a given direction, servicing requests in that direction.
            3 Service all requests in the current direction.
            4 Continue in the same direction until all requests are serviced.
            5 Calculate the distance from the head to each track.
            6 Add the distance to the total seek time.
            7 The serviced track becomes the new head position.
            8 Repeat until the last request in the current direction.
            9 If no more requests in that direction, reverse the direction and repeat until all requests are serviced.
 
 * EXPLANATION:
            LOOK is a disk scheduling algorithm that moves the disk head in one direction until it has serviced all requests in that direction, then reverses to process the remaining requests. This minimizes unnecessary movement and reduces seek time. In the provided code, requests are sorted, and the head moves from its starting position to the right, then reverses once all requests in that direction are handled. The total seek time is calculated by summing the distances between the head and each request.
            The algorithm uses a simple array to store requests and basic variables to track the head's position and seek time. By sorting the requests, the code efficiently handles the requests in order. LOOK is effective in optimizing disk access time, especially when requests are clustered.
            
*/