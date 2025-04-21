public class SCAN {
    public static void main(String[] args) {
        int[] requests = {98, 183, 37, 122, 14, 124, 65, 67};  // Disk requests
        int head = 53;   // Initial head position
        int diskSize = 199;  // Assume disk size (0–199)
        int totalSeek = 0;
        // Sort the request array
        for (int i = 0; i < requests.length - 1; i++) {
            for (int j = i + 1; j < requests.length; j++) {
                if (requests[i] > requests[j]) {
                    int temp = requests[i];
                    requests[i] = requests[j];
                    requests[j] = temp;
                }
            }
        }
        // Find the split index
        int i;
        for (i = 0; i < requests.length; i++) {
            if (requests[i] >= head)
                break;
        }
        // Move right (to end), then left
        System.out.println("SCAN Order:");
        for (int j = i; j < requests.length; j++) {
            System.out.print(requests[j] + " ");
            totalSeek += Math.abs(requests[j] - head);
            head = requests[j];
        }
        // Go to end
        System.out.print((diskSize - 1) + " ");
        totalSeek += Math.abs((diskSize - 1) - head);
        head = diskSize - 1;
        // Move left
        for (int j = i - 1; j >= 0; j--) {
            System.out.print(requests[j] + " ");
            totalSeek += Math.abs(requests[j] - head);
            head = requests[j];
        }
        System.out.println("\nTotal seek time: " + totalSeek);
    }
}

/*
 * ALGORITHM : SCAN 
            1. Let the Request array represents an array storing indexes of tracks that have been requested in ascending order of their time of arrival. 'head' is the position of the disk head.
            2. Let direction represents whether the head is moving towards left or right.
            3. In the direction in which the head is moving, service all tracks one by one.
            4. Calculate the absolute distance of the track from the head.
            5. Increment the total seek count with this distance.
            6. Currently serviced track position now becomes the new head position.
            7. Go to step 3 until we reach one of the ends of the disk.
            8. If we reach the end of the disk reverse the direction and go to step 2 until all tracks in the request array have not been serviced.

 * EXPLANATION:
       The SCAN (Elevator) disk scheduling algorithm works like an elevator: the disk arm (or read/write head) moves in one direction across the disk, servicing all the requests in its path until it reaches the end, then reverses direction and services the remaining requests. This reduces overall seek time compared to FCFS by avoiding random jumps. 
       In the code, the array of disk requests is sorted to enable sequential access, and the current head position is set. The program first finds the position where the head should begin servicing requests, then moves in the increasing order of track numbers (right direction), reaches the disk’s maximum boundary (track 198), and then moves back to service the remaining lower-numbered requests (left direction). The total seek time is calculated by summing the absolute differences between the head’s consecutive positions. This approach improves efficiency and reduces latency in disk operations by mimicking real-world hard disk arm movement.    
 */


