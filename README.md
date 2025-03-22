# 🚍 Bus Booking System

## Overview
The **Bus Booking System** is a JavaFX-based desktop application that allows users to search for bus routes, select seats, make payments, and receive booking confirmations. The system follows the MVC architecture and integrates a structured DAO pattern for efficient database interaction.

## ✨Features
- **User Authentication** (Login & Registration)
- **Bus Search & Selection**
- **Seat Booking with Interactive UI**
- **Passenger Details Input**
- **Payment Processing**
- **Receipt Generation**

## 🛠️Technologies Used
- **Frontend:** JavaFX, FXML
- **Backend:** Java (DAO, Services, Controllers)
- **Database:** MySQL
  

## 📂Project Structure
```
- config/          # Database connection & configuration
- controllers/     # Handles UI logic
- dao/            # Database interaction (Data Access Objects)
- models/         # POJOs representing database tables
- services/       # Business logic layer
- utils/          # Helper utilities
- ui/             # JavaFX main entry points (AdminMain.java, PassengerMain.java, Main.java)
- views/          # FXML UI files (admin_dashboard.fxml, login.fxml, etc.)
- assets/         # Images, CSS, and other static files
```

## 🚀Installation & Setup
Prerequisites
JDK 17+

JavaFX SDK

MySQL/SQLite
1. **Clone the Repository:**
   ```sh
   git clone https://github.com/yourusername/bus-booking-system.git
   cd bus-booking-system
   ```
2. **Configure Database:**
   - Import `bus_booking.sql` into MySQL.
   - Update database credentials in `config/DatabaseConfig.java`.
3. **Run the Application:**
   - Open the project in an IDE (IntelliJ IDEA, Eclipse, or NetBeans).
   - Build and run `Main.java`.

## How to Use
1. **Register/Login** as a passenger.
2. **Search for a Bus** by selecting departure and destination.
3. **Choose a Bus & Seat** from the available options.
4. **Enter Passenger Details** and proceed to payment.
5. **Complete Payment** and receive a booking confirmation.


## Contributors
- **Eugine Onyango** - Developer
- **Team Members (N/A YET)**

## 📜License
This project is not licensed.

## 📌 TODO or Future Improvements
- Implement a **mobile app** for Android/iOS.
- Enhance **real-time seat booking updates**.
- Improve **payment gateway integration** with more options.

---
Feel free to fork and contribute! 🚀

## 📂File structure

YM1587/Busbooking_java-project
This is my file structure:
📂 BusBookingSystem
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
├── README.md (Project documentation)

🤝 Contributing
Contributions are welcome! Feel free to fork this repo and submit a pull request.
