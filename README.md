
Ticket Service Application
--------------------------

Assumptions:
-----------
1. Java and Maven are installed on the host and available to build the ticket service application. 
   NOTE: Application is tested with jdk 1.8 and maven 3.3

Instructions to build ticket service application:
------------------------------------------------
1. Checkout source from git repository https://github.com/gjamakayala/TicketService.git
2. Open command line tool and go to the folder where TicketService source is checked out (application home directory).
3. In a text editor open src/main/resources/ticket-service-config.properties file to edit the below settings:

venue.rowslimit=10
venue.row.seatslimit=15
venue.reserveseat.timelimit=30

WHERE venue.rowslimit indicates number of rows at a venue
      venue.row.seatslimit indicates number of seats per row
      venue.reserveseat.timelimit indicates timelimit in seconds before which customer would need to reserve seats.
      
3. Build Ticket Service application by executing the below maven command from application home directory:
   mvn clean install
   
   Build compiles, runs unit tests and creates tktsvc-1.0.jar file in target folder.
   
4. Run the ticket service application by issuing the below command:
   java -jar target/tktsvc-1.0.jar 
   
5. Follow application instructions.  When prompted enter number of seats and customer email to hold seats and then confirm to reserve seats.

Future Enhancements:
-------------------
1. Enhance to provide the ability to manage multiple ticket venues.
2. Enhance TicketService interface to accept venueId and hold/reserve seats against a specific venue
3. Possibly enhance with other types of ticket services. Ex: sports, movie, concert tickets etc.
4. Enhance and scale the application with multi-thread support.
5. Improve messaging by adjusting the log levels and using application specific TicketServiceException.

