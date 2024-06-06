import java.util.Scanner;
import java.io.*;

class tark_hackathon {
    public static void writeIntegerToFile(int number, String filename) {
    try (FileWriter fw = new FileWriter(filename)) {
        fw.write(Integer.toString(number));
        System.out.println("Successfully wrote number " + number + " to file " + filename);
    } catch (IOException e) {
        System.err.println("Error writing number " + number + " to file " + filename);
        e.printStackTrace();
    }
    }
    
    public static int readIntegerFromFile(String filename) {
        int readNumber = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line = br.readLine();
            readNumber = Integer.parseInt(line);
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }
        return readNumber;
    }

    public static void main(String args[]) {
        int a = 2;
        String b = "17726 Rajkot-0 Mumbai-750";
        String b1 = "17226 S1-72 S2-72 B1-72 A1-48 H1-24";
        String c = "37392 Ahmedabad-0 Surat-300";
        String c1 = "37392 S1-15 S2-20 S3-50 B1-36 B2-48";
        String[] trains = {b, c};
        String[] seats = {b1, c1};

        System.out.println("Welcome to Railways");
        System.out.println("The number of available trains is: " + a);
        System.out.println(b);
        System.out.println(b1);
        System.out.println(c);
        System.out.println(c1);

        // User input
        Scanner sc = new Scanner(System.in);
        try {
            String d = sc.nextLine();
            String[] arr = d.split(" ");
            String s = arr[3];
            int ss = Integer.parseInt(arr[4]); // no of passengers.
            int tktnum = readIntegerFromFile("number1.txt");
            for (int i = 0; i < trains.length; i++) {
                int seatSL = 0, seatB = 0, seatA = 0, seatH = 0;
                int flags = 0, flagd = 0;
                String[] arr1 = trains[i].split("[ -]");
                String[] arr2 = seats[i].split("[ -]");
                int price = 0; // Initialize price here

                for (int j = 0; j < arr1.length; j++) {
                    if (arr1[j].equals(arr[0])) {
                        flags = 1;
                    }
                    if (arr1[j].equals(arr[1])) {
                        flagd = 1;
                    }
                }
                for (int j = 0; j < arr2.length; j++) {
                    if (arr2[j].contains("S")) {
                        seatSL += Integer.parseInt(arr2[j + 1]);
                        j++;
                    } else if (arr2[j].contains("B")) {
                        seatB += Integer.parseInt(arr2[j + 1]);
                        j++;
                    } else if (arr2[j].contains("A")) {
                        seatA += Integer.parseInt(arr2[j + 1]);
                        j++;
                    } else if (arr2[j].contains("H")) {
                        seatH += Integer.parseInt(arr2[j + 1]);
                        j++;
                    }
                }

                if (flags == 1 && flagd == 1) {
                    System.out.print("The Train is found and the PNR is: " + arr1[0]);
                    if (s.equals("SL")) {
                        System.out.println(" and available seats as per the class requested: " + seatSL);
                        if (seatSL >= ss) {
                            price = ss * 1 * Integer.parseInt(arr1[4]);
                        } else {
                            System.out.println("Seats not available for booking.");
                            return; // Exit the program if seats are not available
                        }
                    } else if (s.equals("3A")) {
                        System.out.println(" and available seats as per the class requested: " + seatB);
                        if (seatB >= ss) {
                            price = ss * 2 * Integer.parseInt(arr1[4]);
                        } else {
                            System.out.println("Seats not available for booking.");
                            return; // Exit the program if seats are not available
                        }
                    } else if (s.equals("2A")) {
                        System.out.println(" and available seats as per the class requested: " + seatA);
                        if (seatA >= ss) {
                            price = ss * 3 * Integer.parseInt(arr1[4]);
                        } else {
                            System.out.println("Seats not available for booking.");
                            return; // Exit the program if seats are not available
                        }
                    } else if (s.equals("1A")) {
                        System.out.println(" and available seats as per the class requested: " + seatH);
                        if (seatH >= ss) {
                            price = ss * 4 * Integer.parseInt(arr1[4]);
                        } else {
                            System.out.println("Seats not available for booking.");
                            return; // Exit the program if seats are not available
                        }
                    } else {
                        System.out.println("Invalid seat class.");
                        return; // Exit the program if seat class is invalid
                    }
                    System.out.println("Ticket Number: " + tktnum);
                    System.out.println("Total Price: " + price);
                    tktnum++;
                    writeIntegerToFile(tktnum, "number1.txt");
                }
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Error processing input. Please check the format of the input string.");
        } catch (NumberFormatException e) {
            System.out.println("Invalid seat count.");
        } catch (Exception e) {
            System.out.println("An unexpected error occurred: " + e.getMessage());
        }
    }
}
