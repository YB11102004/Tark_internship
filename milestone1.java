import java.util.*;
class Trains {
    String PNR;
    String source;
    String dest;
    int dist;

    Trains(String PNR, String source, String dest, int dist) {
        this.PNR = PNR;
        this.source = source;
        this.dest = dest;
        this.dist = dist;
    }

    public void display() {
        System.out.println(PNR + " " + source + " " + dest + " " + dist);
    }
}

class tark_hackathon {
    public static void main(String args[]) {
        int a = 2;
        Trains[] trains = {
            new Trains("17726", "Rajkot", "Mumbai", 750),
            new Trains("37392", "Ahmedabad", "Surat", 300)
        };

        String[] seats = {
            "17726 S1-72 S2-72 B1-72 A1-48 H1-24",
            "37392 S1-15 S2-20 S3-50 B1-36 B2-48"
        };

        // Initialize seat counts
        int[][] seatCounts = new int[seats.length][4];
        for (int i = 0; i < seats.length; i++) {
            String[] arr2 = seats[i].split("[ -]");
            for (int j = 1; j < arr2.length; j += 2) {
                if (arr2[j].startsWith("S")) {
                    seatCounts[i][0] += Integer.parseInt(arr2[j+1]);
                } else if (arr2[j].startsWith("B")) {
                    seatCounts[i][1] += Integer.parseInt(arr2[j+1]);
                } else if (arr2[j].startsWith("A")) {
                    seatCounts[i][2] += Integer.parseInt(arr2[j+1]);
                } else if (arr2[j].startsWith("H")) {
                    seatCounts[i][3] += Integer.parseInt(arr2[j+1]);
                }
            }
        }

        //copy of seatcount array
        int[][] seatCounts1 = new int[seatCounts.length][seatCounts[0].length];
        for (int i = 0; i < seatCounts.length; i++) {
            for (int j = 0; j < seatCounts[i].length; j++) {
                seatCounts1[i][j] = seatCounts[i][j];
            }
        }

        System.out.println("Welcome to Railways");
        System.out.println("The number of available trains are: " + a);
        for (Trains train : trains) {
            train.display();
        }

        // User input
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter train search request (type 'exit' to quit)");

        int tktnum = 100000001;
        Set<String> date = new HashSet<>();

        while (true) {
            try {
                String d = sc.nextLine();
                if (d.equals("exit")) {
                    break;
                }

                String[] arr = d.split(" ");
                String src = arr[0];
                String dest = arr[1];
                String seatClass = arr[3];
                String traveldate=arr[2];
                int numPassengers = Integer.parseInt(arr[4]); // no of passengers
                
                if(!date.contains(traveldate))
                {
                    for (int i = 0; i < seatCounts1.length; i++) {
                        for (int j = 0; j < seatCounts1[i].length; j++) {
                            seatCounts[i][j] = seatCounts1[i][j];
                        }
                    }
                    date.add(traveldate);
                }

                boolean trainFound = false;
                for (int i = 0; i < trains.length; i++) {
                    if (trains[i].source.equals(src) && trains[i].dest.equals(dest)) {
                        trainFound = true;
                        int price = 0;
                        System.out.println("The Train is found and the train number is: " + trains[i].PNR);

                        int seatClassIndex = -1;
                        switch (seatClass) {
                            case "SL":
                                seatClassIndex = 0;
                                break;
                            case "3A":
                                seatClassIndex = 1;
                                break;
                            case "2A":
                                seatClassIndex = 2;
                                break;
                            case "1A":
                                seatClassIndex = 3;
                                break;
                            default:
                                System.out.println("Invalid seat class.");
                                continue;
                        }

                        if (seatCounts[i][seatClassIndex] >= numPassengers) {
                            switch (seatClass) {
                                case "SL":
                                    price = numPassengers * 1 * trains[i].dist;
                                    break;
                                case "3A":
                                    price = numPassengers * 2 * trains[i].dist;
                                    break;
                                case "2A":
                                    price = numPassengers * 3 * trains[i].dist;
                                    break;
                                case "1A":
                                    price = numPassengers * 4 * trains[i].dist;
                                    break;
                            }

                            seatCounts[i][seatClassIndex] -= numPassengers;

                            System.out.println("Ticket Number: " + tktnum);
                            System.out.println("Total Price: " + price);
                            tktnum++;
                        } else {
                            System.out.println("Seats not available for booking.");
                        }
                        break;
                    }
                }
                if (!trainFound) {
                    System.out.println("Train not found.");
                }
            } catch (Exception e) {
                System.out.println("An unexpected error occurred: " + e.getMessage());
            }
        }
    }
}



// OUTPUT according to queries
/* Welcome to Railways
The number of available trains are: 2
17726 Rajkot Mumbai 750
37392 Ahmedabad Surat 300
Enter train search request (type 'exit' to quit)
Rajkot Mumbai 2023-03-15 SL 6
The Train is found and the train number is: 17726
Ticket Number: 100000001
Total Price: 4500
Rajkot Mumbai 2023-03-15 1A 24
The Train is found and the train number is: 17726
Ticket Number: 100000002
Total Price: 72000
Rajkot Mumbai 2023-03-15 1A 1
The Train is found and the train number is: 17726
Seats not available for booking.    
Rajkot Mumbai 2023-03-16 1A 10
The Train is found and the train number is: 17726
Ticket Number: 100000003
Total Price: 30000
Rajkot Chennai 2023-03-16 1A 10
Train not found.
exit */
