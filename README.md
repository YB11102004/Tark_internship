# Train Booking System

This project is a Java-based train booking system that allows users to search for trains, check seat availability, and book tickets on specified routes. It is designed using Object-Oriented Programming (OOP) concepts in Java, with a focus on modularity and reusability.

## Features

- *Train Creation*: Initialize multiple trains with different routes, distances, and coach types.
- *Route Search*: Search for available trains between source and destination cities with the specified seat class.
- *Seat Availability Check*: Check if seats are available in a train for the desired class and route.
- *Ticket Booking*: Book tickets with seat allocation and manage waiting lists for fully booked trains.
- *Fare Calculation*: Calculate ticket fare based on distance and seat class.
- *PNR Management*: Generate unique PNR numbers for each booking and retrieve ticket details by PNR.
- *Report Generation*: Generate a report of all bookings with details such as train number, date, route, fare, and seat status.

## Project Structure

### Classes

1. *Tark (Main Class)*: Handles user input, booking requests, ticket retrieval by PNR, and report generation.
2. *Train*: Represents a train with cities on its route, coaches, and functionalities for booking seats and managing waiting lists.
3. *City*: Represents a city with a name and distance from the starting point of the train route.
4. *CoachType (Enum)*: Defines types of coaches (Sleeper, TIER_3, TIER_2, TIER_1) with corresponding fare per kilometer.
5. *Coach*: Represents a coach in a train, containing a list of seats.
6. *Seat*: Represents a seat with a seat number and reservation dates.
7. *ReservationDate*: Represents reservation details including date, source, and destination.
8. *final_repo*: Represents ticket information for reports and PNR-based ticket retrieval.
9. *RouteWaitingList*: Manages waiting lists for each route if seats are unavailable.

### How It Works

- Users enter a train search request specifying the source, destination, date, seat class, and number of passengers.
- The program checks if there are any trains available for the route and seat class.
- If available, the user selects a train and the system checks for seat availability.
- Tickets are booked, and fare is calculated based on the class and distance.
- A unique PNR is generated, and the ticket details are stored for future reference.
- If seats are unavailable, the system adds the passengers to a waiting list.

## Usage

### Prerequisites

- Java Development Kit (JDK) installed (version 8 or above).
- A terminal or command prompt for executing the Java program.

### Compiling and Running the Program

1. *Clone the Repository*:
   ```bash
   git clone <repository-url>
   cd train-booking-system
