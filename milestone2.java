import java.util.*;

class Trains {
    String Trainno;
    String[][] sdestdist;

    Trains(String Trainno, String[][] sdestdist) {
        this.Trainno = Trainno;
        this.sdestdist = sdestdist;
    }

    boolean containsSourceAndDestination(String source, String destination) {
        boolean sourceFound = false, destinationFound = false;
        for (String[] stop : sdestdist) {
            if (stop[0].equals(source)) {
                sourceFound = true;
            }
            if (stop[0].equals(destination)) {
                destinationFound = true;
            }
        }
        return sourceFound && destinationFound;
    }

    int getDistance(String source, String destination) {
        int srcIndex = -1, destIndex = -1;
        for (int i = 0; i < sdestdist.length; i++) {
            if (sdestdist[i][0].equals(source)) {
                srcIndex = i;
            }
            if (sdestdist[i][0].equals(destination)) {
                destIndex = i;
            }
        }
        if (srcIndex != -1 && destIndex != -1 && srcIndex < destIndex) {
            return Integer.parseInt(sdestdist[destIndex][1]) - Integer.parseInt(sdestdist[srcIndex][1]);
        }
        return -1; 
    }

    void display() {
        System.out.println(Trainno);
    }
}

class Seats
{
    String Trainno;
    String seats;
    List<String> seatlist;
    Seats(String Trainno,String seats)
    {
        this.Trainno=Trainno;
        this.seats=seats;
    }

    boolean isSeatClassAvailable(String seatClass) {
        boolean seatAvailable=false;
        switch (seatClass) {
            case "SL":
            if(seats.contains("S"))
            {
                seatAvailable=true;
            }
            break;
            case "1A":
            if(seats.contains("H"))
            {
                seatAvailable=true;
            }
            break;
            case "2A":
            if(seats.contains("B"))
            {
                seatAvailable=true;
            }
            break;
            case "3A":
            if(seats.contains("A"))
            {
                seatAvailable=true;
            }
            break;
        }
        return seatAvailable;
    }

    List<String> createSeatnumbers()
    {
        this.seatlist=new ArrayList<>();
        String[] arr = seats.split("[ -]");
        for (int j = 0; j < arr.length; j +=2){
            String seatclass=arr[j];
            int totalSeat=Integer.parseInt(arr[j+1]);
            for (int i = 1; i <= totalSeat; i++) {
                this.seatlist.add(seatclass + "-" + i);
            }
        }
        return seatlist;
    }

}

class Dates {
    String date;
    int[][] seatCounts;
    List<List<String>> allGeneratedLists = new ArrayList<>();
    
    Dates(String date, int[][] initialSeatCounts,List<List<String>> allGeneratedLists) {
        this.date = date;
        this.allGeneratedLists=allGeneratedLists;
        this.seatCounts = new int[initialSeatCounts.length][initialSeatCounts[0].length];
        for (int i = 0; i < initialSeatCounts.length; i++) {
            for (int j = 0; j < initialSeatCounts[i].length; j++) {
                this.seatCounts[i][j] = initialSeatCounts[i][j];
            }
        }
    }
    List<String> removeseats(int numPassengers, String seatClass,int trainIndex){
        List<String> removedSeats = new ArrayList<>();
        String seatPrefix = "";
    
        switch (seatClass) {
            case "SL":
                seatPrefix = "S";
                break;
            case "1A":
                seatPrefix = "H";
                break;
            case "2A":
                seatPrefix = "B";
                break;
            case "3A":
                seatPrefix = "A";
                break;
            default:
                return removedSeats;
        }
    
        List<String> seatNumbers = allGeneratedLists.get(trainIndex);

        Iterator<String> iterator = seatNumbers.iterator();
        while (iterator.hasNext() && removedSeats.size() < numPassengers) {
            String seat = iterator.next();
            if (seat.startsWith(seatPrefix)) {
                removedSeats.add(seat);
                iterator.remove();
            }
        }
        return removedSeats;
    }
}

class final_repo {
    int tktnum;
    String date;
    String TrainNo;
    String src;
    String dest;
    int Fare;
    String seatclass;
    List<String> removedseats;

    final_repo(int tktnum, String date, String TrainNo, String src, String dest, int Fare,String seatclass,List<String> removedseats) {
        this.tktnum = tktnum;
        this.date = date;
        this.TrainNo = TrainNo;
        this.src = src;
        this.dest = dest;
        this.Fare = Fare;
        this.seatclass=seatclass;
        this.removedseats=removedseats;
    }
    void display() {
        System.out.print(tktnum + ", " + date + ", " + TrainNo + ", " + src + ", " + dest + ", " + Fare+", ");
        for(String s:removedseats)
        {
            System.out.print(s+" ");
        }
        System.out.println();
    }

    void display1(){
        System.out.print(TrainNo+" "+src+" "+dest+" "+date+" "+Fare+" ");
        for(String s:removedseats)
        {
            System.out.print(s+" ");
        }
        System.out.println();
    }
}

public class TarkHackathon {
    public static void main(String[] args) {
        Trains[] trains = {
            new Trains("17726", new String[][]{
                {"Rajkot", "0"},
                {"Ahmedabad", "200"},
                {"Vadodara", "300"},
                {"Surat", "500"},
                {"Mumbai", "750"}
            }),
            new Trains("37392", new String[][]{
                {"Ahmedabad", "0"},
                {"Anand", "50"},
                {"Vadodara", "100"},
                {"Bharuch", "200"},
                {"Surat", "500"}
            }),
            new Trains("29772", new String[][]{
                {"Vadodara", "0"},
                {"Dahod", "150"},
                {"Indore", "350"}
            })
        };
    
        Seats[] seats = {
            new Seats("17726","S1-72 S2-72 B1-72 A1-48 H1-24"),
            new Seats("37392", "S1-15 S2-20 S3-50 B1-36 B2-48"),
            new Seats("29772","S1-72 S2-72 B1-72 A1-48")
        };
    
        
        // Initialize seat counts
        int[][] initialSeatCounts = new int[seats.length][4];
        for (int i = 0; i < seats.length; i++) {
            String[] arr2 = seats[i].seats.split("[ -]");
            for (int j = 0; j < arr2.length; j += 2) {
                if (arr2[j].startsWith("S")) {
                    initialSeatCounts[i][0] += Integer.parseInt(arr2[j + 1]);
                } else if (arr2[j].startsWith("B")) {
                    initialSeatCounts[i][1] += Integer.parseInt(arr2[j + 1]);
                } else if (arr2[j].startsWith("A")) {
                    initialSeatCounts[i][2] += Integer.parseInt(arr2[j + 1]);
                } else if (arr2[j].startsWith("H")) {
                    initialSeatCounts[i][3] += Integer.parseInt(arr2[j + 1]);
                }
            }
        }
    
        System.out.println("Welcome to Railways");
    
        // User input
        Scanner sc = new Scanner(System.in);
    
        int tktnum = 100000001;
        List<Dates> datesList = new ArrayList<>();
        List<final_repo> report = new ArrayList<>();

            
        
        
        while (true) {
            System.out.println("Enter train search request (type 'exit' to quit)");
            try {
                String d = sc.nextLine();
                if (d.equals("exit")) {
                    break;
                }
                if (d.equals("REPORT")) {
                    System.out.println("PNR, DATE, TRAIN, FROM, TO, FARE");
                    for (final_repo final_repo_instances : report) {
                        final_repo_instances.display();
                    }
                    break;
                }
                if(d.length()==9)
                {
                    boolean found=false;
                    for (final_repo final_repo_instances1 : report) {
                        if(d.equals(String.valueOf(final_repo_instances1.tktnum))){
                            final_repo_instances1.display1();
                            found = true;
                            break;
                        }
                    }
                    if(!found)
                    {
                        System.out.println("Invalid PNR");
                    }
                }
                else{
                    String[] arr = d.split(" ");
                    String src = arr[0];
                    String dest = arr[1];
                    String travelDate = arr[2];
                    String seatClass = arr[3];
                    int numPassengers = Integer.parseInt(arr[4]);
                    
                    // Display available trains for the source and destination
                    System.out.println("Available trains for " + src + " to " + dest + ":");
                    List<Trains> availableTrains = new ArrayList<>();
                    for (Trains train : trains) {
                        if (train.containsSourceAndDestination(src, dest)) {
                            boolean seatAvailable = false;
                            for (Seats seat : seats) {
                                if (seat.Trainno.equals(train.Trainno) && seat.isSeatClassAvailable(seatClass)) {
                                    seatAvailable = true;
                                    break;
                                }
                            }
                            if (seatAvailable) {
                                train.display();
                                availableTrains.add(train);
                            }
                        }
                    }
                    
                    if (availableTrains.isEmpty()) {
                        System.out.println("No trains available for the specified route.");
                        continue;
                    }
                    
                    System.out.println("Enter the train number to select for booking:");
                    String selectedTrainNo = sc.nextLine();
                    Trains selectedTrain = null;
                    
                    for (int i = 0; i < availableTrains.size(); i++) {
                        if (availableTrains.get(i).Trainno.equals(selectedTrainNo)) {
                            selectedTrain = availableTrains.get(i);
                            break;
                        }
                    }
                    
                    if (selectedTrain == null) {
                        System.out.println("Invalid train number.");
                        continue;
                    }
                    
                    Dates currentDate = null;
                    for (Dates dateInstance : datesList) {
                        if (dateInstance.date.equals(travelDate)) {
                            currentDate = dateInstance;
                            break;
                        }
                    }
                    List<List<String>> allGeneratedLists = new ArrayList<>();

                    if (currentDate == null) {
                        for (int i = 0; i < trains.length; i++)
                        {
                            List<String> s=seats[i].createSeatnumbers();
                            allGeneratedLists.add(s);
                        }
                        currentDate = new Dates(travelDate, initialSeatCounts,allGeneratedLists);
                        datesList.add(currentDate);
                    }
        
                    boolean seatClassAvailable = false;
                    int trainIndex = -1;
                    for (int i = 0; i < trains.length; i++) {
                        if (trains[i].Trainno.equals(selectedTrainNo)) {
                            trainIndex = i;
                            seatClassAvailable = seats[i].isSeatClassAvailable(seatClass);
                            break;
                        }
                    }
        

                    if (!seatClassAvailable) {
                        System.out.println("Requested seat class not available on the selected train.");
                        continue;
                    }
        
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
                    if (currentDate.seatCounts[trainIndex][seatClassIndex] >= numPassengers) {
                        int distance = selectedTrain.getDistance(src, dest);
                        if (distance == -1) {
                            System.out.println("Invalid source or destination.");
                            continue;
                        }
                        int price = 0;
                        switch (seatClass) {
                            case "SL":
                                price = numPassengers * 1 * distance;
                                break;
                            case "3A":
                                price = numPassengers * 2 * distance;
                                break;
                            case "2A":
                                price = numPassengers * 3 * distance;
                                break;
                            case "1A":
                                price = numPassengers * 4 * distance;
                                break;
                        }
        
                        currentDate.seatCounts[trainIndex][seatClassIndex] -= numPassengers;
    
                        List<String> removedSeats = currentDate.removeseats(numPassengers,seatClass,trainIndex);

                        System.out.println("Ticket Number: " + tktnum);
                        System.out.println("Total Price: " + price);
                        final_repo x = new final_repo(tktnum, travelDate, selectedTrain.Trainno, src, dest, price,seatClass,removedSeats);
                        report.add(x);
                        tktnum++;
                    } else {
                        System.out.println("Seats not available for booking.");
                    }
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid number format: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("An unexpected error occurred: " + e.getMessage());
            }
            
        }
        sc.close();
    }    
}
