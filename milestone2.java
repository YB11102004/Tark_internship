import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Tark1 {
    public static void main(String[] args) {
        // Create multiple trains
        List<Train> trains = new ArrayList<>();

        String[] citiesArray1 = new String[]{"Rajkot", "Ahmedabad", "Vadodara", "Surat", "Mumbai"};
        String[] citiesArray2 = new String[]{"Ahmedabad", "Anand", "Vadodara", "Bharuch", "Surat"};
        String[] citiesArray3 = new String[]{"Vadodara", "Dahod", "Indore"};
        int[] distance1 = new int[]{0, 200, 300, 500, 700};
        int[] distance2 = new int[]{0, 50, 100, 200, 300};
        int[] distance3 = new int[]{0, 150, 350};
        String[] coachesArray1 = new String[]{"S1", "S2", "B1", "A1", "H1"};
        String[] coachesArray2 = new String[]{"S1", "S2", "S3", "B1", "B2"};
        String[] coachesArray3 = new String[]{"S1", "S2", "B1", "A1"};
        int[] seatsPerCoach1 = new int[]{72, 72, 72, 48, 24};
        int[] seatsPerCoach2 = new int[]{15, 20, 50, 36, 48};
        int[] seatsPerCoach3 = new int[]{72, 72, 72, 48};


        Train t1 = createTrain("17726", citiesArray1, distance1, coachesArray1, seatsPerCoach1);
        trains.add(t1);

        Train t2 = createTrain("37392", citiesArray2, distance2, coachesArray2, seatsPerCoach2);
        trains.add(t2);

        Train t3 = createTrain("29772", citiesArray3, distance3, coachesArray3, seatsPerCoach3);
        trains.add(t3);

        Scanner sc = new Scanner(System.in);
        int tktnum = 100000001;
        List<final_repo> report = new ArrayList<>();

        while (true) {
            System.out.println("Enter train search request (type 'exit' to quit)");
            try {
                String d = sc.nextLine();
                if (d.equals("exit")) {
                    break;
                } else if(d.equals("REPORT")){
                    System.out.println("PNR, DATE, TRAIN, FROM, TO, FARE, SEATS");
                    for (final_repo final_repo_instances : report) {
                        final_repo_instances.display();
                    }
                    break;
                }else if(d.length()==9){
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
                }else {
                    String[] arr = d.split(" ");
                    String src = arr[0];
                    String dest = arr[1];
                    String dateString = arr[2];
                    String seatClass = arr[3];
                    int numPassengers = Integer.parseInt(arr[4]);

                    List<String> alltrains = getTrainByRouteAndSeatClass(trains, src, dest, seatClass);

                    System.out.println("Available Trains for particular route are: ");
                    for(String i : alltrains){
                        System.out.println(i);
                    }


                    System.out.println("Enter the train number to book:");
                    String selectedTrainnumber=sc.nextLine();

                    Train selectedTrain = findTrainByNumber(trains, selectedTrainnumber);
                    
                    if (selectedTrain == null) {
                        System.out.println("Train not found.");
                        continue;
                    }

                    CoachType coachType = getCoachTypeFromString(seatClass);

                    LocalDate travelDate = LocalDate.parse(dateString, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                    int dist = selectedTrain.distancereturn(src, dest);

                    if (selectedTrain.areCitiesAvailable(src, dest)) {
                        List<String> availableSeats = selectedTrain.checkSeatAvailability(seatClass, travelDate, src, dest, numPassengers);
                        if (availableSeats != null) {
                            int Fare=coachType.calculateFare(dist, numPassengers);
                            System.out.println(tktnum + " " + Fare);
                            final_repo x = new final_repo(tktnum, travelDate, selectedTrainnumber, src, dest, Fare,seatClass,availableSeats);
                            report.add(x);
                            tktnum++;
                        } else {
                            System.out.println("Not enough seats available.");
                        }
                    } else {
                        System.out.println("Cities not available on this train.");
                    }

                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid number format: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("An unexpected error occurred: " + e.getMessage());
            }
        }
    }

    static Train findTrainByNumber(List<Train> trains, String trainNumber) {
        for (Train train : trains) {
            if (train.trainno.equals(trainNumber)) {
                return train;
            }
        }
        return null;
    }

    static Train createTrain(String trainNumber, String[] citiesArray, int[] distance, String[] coachesArray, int[] seatsPerCoach) {
        List<City> citiesList = new ArrayList<>();
        List<Coach> coachesList = new ArrayList<>();

        // Adding cities to the list
        for (int i = 0; i < citiesArray.length; i++) {
            City city = new City();
            city.name = citiesArray[i];
            city.dist = distance[i];
            citiesList.add(city);
        }

        // Adding coaches to the list
        for (int i = 0; i < coachesArray.length; i++) {
            Coach coach = new Coach();
            String coachName = coachesArray[i];

            coach.coachName = coachName;
            if (coachName.startsWith("S")) {
                coach.coachType = CoachType.Sleeper;
            } else if (coachName.startsWith("B")) {
                coach.coachType = CoachType.TIER_3;
            } else if (coachName.startsWith("A")) {
                coach.coachType = CoachType.TIER_2;
            } else if (coachName.startsWith("H")) {
                coach.coachType = CoachType.TIER_1;
            }

            coach.seats = new ArrayList<>();
            for (int j = 0; j < seatsPerCoach[i]; j++) {
                Seat seat = new Seat();
                seat.seatNo = coach.coachName + "-" + (j + 1);
                seat.ReservedDates = new ArrayList<>();
                coach.seats.add(seat);
            }
            coachesList.add(coach);
        }

        return new Train(trainNumber, citiesList, coachesList);
    }

    static List<String> getTrainByRouteAndSeatClass(List<Train> trains, String source, String destination, String seatClass) {
        List<String> l1=new ArrayList<>();
        for (Train train : trains) {
            if (train.areCitiesAvailable(source, destination) && train.isSeatClassAvailable(seatClass)) {
                l1.add(train.trainno);
            }
        }
        return l1;
    }

    static CoachType getCoachTypeFromString(String seatClass) {
        switch (seatClass) {
            case "SL":
                return CoachType.Sleeper;
            case "1A":
                return CoachType.TIER_1;
            case "2A":
                return CoachType.TIER_2;
            case "3A":
                return CoachType.TIER_3;
            default:
                throw new IllegalArgumentException("Invalid seat class: " + seatClass);
        }
    }
}

class final_repo {
    int tktnum;
    LocalDate date;
    String TrainNo;
    String src;
    String dest;
    int Fare;
    String seatclass;
    List<String> removedseats;

    final_repo(int tktnum, LocalDate date, String TrainNo, String src, String dest, int Fare,String seatclass,List<String> removedseats) {
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

class City {
    String name;
    int dist;
}

enum CoachType {
    Sleeper(1),
    TIER_3(2),
    TIER_2(3),
    TIER_1(4);

    int fareperkm;

    CoachType(int fare) {
        this.fareperkm = fare;
    }

    int calculateFare(int distance, int passenger) {
        return this.fareperkm * distance * passenger;
    }
}

class Coach {
    String coachName;
    CoachType coachType;
    List<Seat> seats;
}

class Train {
    String trainno;
    List<City> cities;
    List<Coach> coaches;

    Train(String trainno, List<City> cities, List<Coach> coaches) {
        this.trainno = trainno;
        this.cities = cities;
        this.coaches = coaches;
    }

    public int distancereturn(String sourceName, String destinationName){
        int srcdist=0,destdist=0;
        boolean sourceAvailable = false;
        boolean destinationAvailable = false;
        for (City city : cities) {
            if (city.name.equals(sourceName)) {
                sourceAvailable = true;
                srcdist=city.dist;
            }
            if (city.name.equals(destinationName)) {
                destinationAvailable = true;
                destdist=city.dist;
            }
            if (sourceAvailable && destinationAvailable) {
                return (destdist-srcdist);
            }
        }
        return 0;
    }

    public boolean areCitiesAvailable(String sourceName, String destinationName) {
        boolean sourceAvailable = false;
        boolean destinationAvailable = false;

        for (City city : cities) {
            if (city.name.equals(sourceName)) {
                sourceAvailable = true;
            }
            if (city.name.equals(destinationName)) {
                destinationAvailable = true;
            }
            if (sourceAvailable && destinationAvailable) {
                return true;
            }
        }
        return false;
    }

    public boolean isSeatClassAvailable(String seatClass) {
        String seatClassPrefix;
        switch (seatClass) {
            case "SL":
                seatClassPrefix = "S";
                break;
            case "1A":
                seatClassPrefix = "H";
                break;
            case "2A":
                seatClassPrefix = "A";
                break;
            case "3A":
                seatClassPrefix = "B";
                break;
            default:
                return false;
        }

        for (Coach coach : coaches) {
            if (coach.coachName.startsWith(seatClassPrefix)) {
                return true;
            }
        }
        return false;
    }

    public List<String> checkSeatAvailability(String seatClass, LocalDate travelDate, String src, String dest, int numPassengers) {
        List<String> reservedSeats = new ArrayList<>();
        int availableSeatCount = 0;
        String seatClassPrefix = "";
        switch (seatClass) {
            case "SL":
                seatClassPrefix = "S";
                break;
            case "1A":
                seatClassPrefix = "H";
                break;
            case "2A":
                seatClassPrefix = "A";
                break;
            case "3A":
                seatClassPrefix = "B";
                break;
            default:
                return null;
        }

        for (Coach coach : coaches) {
            if (coach.coachName.startsWith(seatClassPrefix)) {
                for (Seat seat : coach.seats) {
                    boolean isAvailable = true;
                    for (ReservationDate reservedDate : seat.ReservedDates) {
                        if (reservedDate.date.equals(travelDate) &&
                            isOverlapping(reservedDate.source, reservedDate.destination, src, dest)) {
                            isAvailable = false;
                            break;
                        }
                    }
                    if (isAvailable) {
                        availableSeatCount++;
                    }
                }
            }
        }

        if (availableSeatCount < numPassengers) {
            return null;
        }

        int count = 0;
        for (Coach coach : coaches) {
            if (coach.coachName.startsWith(seatClassPrefix)) {
                for (Seat seat : coach.seats) {
                    boolean isAvailable = true;

                    for (ReservationDate reservedDate : seat.ReservedDates) {
                        if (reservedDate.date.equals(travelDate) &&
                            isOverlapping(reservedDate.source, reservedDate.destination, src, dest)) {
                            isAvailable = false;
                            break;
                        }
                    }
                    if (isAvailable) {
                        ReservationDate newReservation = new ReservationDate(travelDate, getCityByName(src), getCityByName(dest));
                        seat.ReservedDates.add(newReservation);
                        reservedSeats.add(seat.seatNo);
                        count++;
                        if (count == numPassengers) {
                            return reservedSeats;
                        }
                    }
                }
            }
        }
        return reservedSeats.size() >= numPassengers ? reservedSeats : null;
    }
    
    boolean isOverlapping(City reservedSrc, City reservedDest, String src, String dest) {
        int reservedSrcIndex = getCityIndex(reservedSrc.name);
        int reservedDestIndex = getCityIndex(reservedDest.name);
        int srcIndex = getCityIndex(src);
        int destIndex = getCityIndex(dest);

        return !(destIndex <= reservedSrcIndex || srcIndex >= reservedDestIndex);
    }
    int getCityIndex(String cityName) {
        for (int i = 0; i < cities.size(); i++) {
            if (cities.get(i).name.equals(cityName)) {
                return i;
            }
        }
        return -1;
    }

    City getCityByName(String cityName) {
        for (City city : cities) {
            if (city.name.equals(cityName)) {
                return city;
            }
        }
        return null;
    }
}

class Seat {
    String seatNo;
    List<ReservationDate> ReservedDates = new ArrayList<>();
}

class ReservationDate {
    LocalDate date;
    City source;
    City destination;

    ReservationDate(LocalDate date, City source, City destination) {
        this.date = date;
        this.source = source;
        this.destination = destination;
    }
}
