import java.util.*;

class Trains {
    String TrainNo;
    String source;
    String dest;
    int dist;

    Trains(String TrainNo, String source, String dest, int dist) {
        this.TrainNo = TrainNo;
        this.source = source;
        this.dest = dest;
        this.dist = dist;
    }

    public void display() {
        System.out.println(TrainNo + " " + source + " " + dest + " " + dist);
    }
}

class Dates {
    String date;
    int[][] seatCounts;

    Dates(String date, int[][] initialSeatCounts) {
        this.date = date;
        this.seatCounts = new int[initialSeatCounts.length][initialSeatCounts[0].length];
        for (int i = 0; i < initialSeatCounts.length; i++) {
            for (int j = 0; j < initialSeatCounts[i].length; j++) {
                this.seatCounts[i][j] = initialSeatCounts[i][j];
            }
        }
    }
}

public class TarkHackathon {
    public static void main(String[] args) {
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
        int[][] initialSeatCounts = new int[seats.length][4];
        for (int i = 0; i < seats.length; i++) {
            String[] arr2 = seats[i].split("[ -]");
            for (int j = 1; j < arr2.length; j += 2) {
                if (arr2[j].startsWith("S")) {
                    initialSeatCounts[i][0] += Integer.parseInt(arr2[j+1]);
                } else if (arr2[j].startsWith("B")) {
                    initialSeatCounts[i][1] += Integer.parseInt(arr2[j+1]);
                } else if (arr2[j].startsWith("A")) {
                    initialSeatCounts[i][2] += Integer.parseInt(arr2[j+1]);
                } else if (arr2[j].startsWith("H")) {
                    initialSeatCounts[i][3] += Integer.parseInt(arr2[j+1]);
                }
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
        List<Dates> datesList = new ArrayList<>();

        while (true) {
            try {
                String d = sc.nextLine();
                if (d.equals("exit")) {
                    break;
                }

                String[] arr = d.split(" ");
                if (arr.length != 5) {
                    System.out.println("Invalid input format.");
                    continue;
                }

                String src = arr[0];
                String dest = arr[1];
                String travelDate = arr[2];
                String seatClass = arr[3];
                int numPassengers = Integer.parseInt(arr[4]);

                Dates currentDate = null;
                for (Dates dateInstance : datesList) {
                    if (dateInstance.date.equals(travelDate)) {
                        currentDate = dateInstance;
                        break;
                    }
                }

                if (currentDate == null) {
                    currentDate = new Dates(travelDate, initialSeatCounts);
                    datesList.add(currentDate);
                }

                boolean trainFound = false;
                for (int i = 0; i < trains.length; i++) {
                    if (trains[i].source.equals(src) && trains[i].dest.equals(dest)) {
                        trainFound = true;
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

                        if (seatClassIndex == -1) {
                            System.out.println("Invalid seat class.");
                            continue;
                        }

                        if (currentDate.seatCounts[i][seatClassIndex] >= numPassengers) {
                            int price = 0;
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

                            currentDate.seatCounts[i][seatClassIndex] -= numPassengers;

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
            } catch (NumberFormatException e) {
                System.out.println("Invalid number format: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("An unexpected error occurred: " + e.getMessage());
            }
        }
    }
}
