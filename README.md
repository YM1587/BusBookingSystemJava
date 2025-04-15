YM1587/Busbooking_java-project
This is my file structure:

<details> <summary><strong>📁 Project Structure📂 BusBookingSystem
├── 📂 src
│   ├── 📂 main
│   │   ├── 📂 java
│   │   │   ├── 📂 com.busbooking
│   │   │   │   ├── 📂 app                    # Main application package
│   │   │   │   │   ├── Main.java             # JavaFX entry point
│   │   │   │   ├── 📂 config                 # Database & app configurations
│   │   │   │   │   ├── DatabaseConnection.java
│   │   │   │   ├── 📂 controllers            # Handles UI logic & navigation
│   │   │   │   │   ├── LoginController.java
│   │   │   │   │   ├── RegisterController.java
│   │   │   │   │   ├── DashboardController.java
│   │   │   │   │   ├── BookingController.java
│   │   │   │   │   ├── PaymentController.java
│   │   │   │   │   ├── SeatSelectionController.java
│   │   │   │   ├── 📂 dao                     # Data Access Objects (DB operations)
│   │   │   │   │   ├── GenericDAO.java       # Generic CRUD interface
│   │   │   │   │   ├── PassengerDAO.java
│   │   │   │   │   ├── AdminDAO.java
│   │   │   │   │   ├── BookingDAO.java
│   │   │   │   │   ├── SeatDAO.java
│   │   │   │   │   ├── PaymentDAO.java
│   │   │   │   │   ├── NotificationDAO.java
│   │   │   │   │   ├── BusDAO.java
│   │   │   │   │   ├── RouteDAO.java
│   │   │   │   ├── 📂 models                  # POJOs representing database tables
│   │   │   │   │   ├── Passenger.java
│   │   │   │   │   ├── Admin.java
│   │   │   │   │   ├── Booking.java
│   │   │   │   │   ├── Seat.java
│   │   │   │   │   ├── Payment.java
│   │   │   │   │   ├── Notification.java
│   │   │   │   │   ├── Bus.java
│   │   │   │   │   ├── Route.java
│   │   │   │   ├── 📂 services                # Business logic & interactions
│   │   │   │   │   ├── PassengerService.java
│   │   │   │   │   ├── AdminService.java
│   │   │   │   │   ├── BookingService.java
│   │   │   │   │   ├── PaymentService.java
│   │   │   │   │   ├── NotificationService.java
│   │   │   │   │   ├── BusService.java
│   │   │   │   │   ├── RouteService.java
│   │   │   │   ├── 📂 utils                    # Helper utilities (hashing, email, SMS)
│   │   │   │   │   ├── JwtUtil.java           # Authentication (optional)
│   │   │   │   │   ├── PasswordUtil.java      # Password hashing & validation
│   │   │   │   │   ├── EmailUtil.java         # Sending emails
│   │   │   │   │   ├── SmsUtil.java           # Sending SMS
│   │   │   │   │   ├── NotificationUtil.java  # Push notifications handling
│   │   ├── 📂 resources
│   │   │   ├── 📂 fxml                        # JavaFX UI screens
│   │   │   │   ├── login.fxml
│   │   │   │   ├── register.fxml
│   │   │   │   ├── dashboard.fxml
│   │   │   │   ├── booking.fxml
│   │   │   │   ├── payment.fxml
│   │   │   │   ├── seat_selection.fxml
│   │   │   │   ├── booking_confirmation.fxml
│   │   │   ├── 📂 css                         # Styling for JavaFX
│   │   │   │   ├── main.css
│   │   │   │   ├── dark_theme.css
│   │   │   ├── 📂 images                      # Icons, logos, and assets
│   │   │   │   ├── logo.png
├── 📂 sql                                     # Database-related files
│   ├── database_schema.sql                    # SQL file for table creation
├── pom.xml (If using Maven)
├── build.gradle (If using Gradle)
├── README.md (Project documentation) </strong> (click to expand)</summary>
