## Busbooking_java-project
A complete JavaFX-based bus booking management application with real-time seat reservation, MPESA-style payment simulation, and printable receipts. Built for efficient travel planning and user-friendly experiences.

## ✨ Features
🧑 Passenger Information Management

🪑 Interactive Seat Selection per Bus

🔍 Real-time Route & Fare Display

💸 Payment Integration (simple mock up)

📄 Receipt Generation with Print Support

📚 Booking History Management

🗃️ MySQL Database Integration


## ⚙️ Tech Stack
```
Layer | Technology
Frontend | JavaFX (FXML, SceneBuilder)
Backend | Java (OOP + DAO Pattern)
Database | MySQL
Styling | CSS (JavaFX inline)
```

## This is my file structure:
```
📁 Project Structure
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
```

## 🛠️ Setup Instructions

## 1. Clone the Repo
```
bash
git clone https://github.com/yourusername/bus-booking-system.git
cd bus-booking-system
```
## 2. Setup Database
Create a MySQL database, e.g., bus_booking

Run the provided SQL file:
```
sql
SOURCE path/to/database.sql;
```
## 3. Update DB Credentials
In your DatabaseConnection.java:
```
java
private static final String URL = "jdbc:mysql://localhost:3306/bus_booking";
private static final String USER = "root";
private static final String PASSWORD = "yourpassword";
```
## 4. Run the Application
Using your IDE (IntelliJ/VSCode/NetBeans):

Run Main.java

Or build with javac and run via terminal

## 🧾 Receipt Format
After successful payment, a printable receipt is generated including:

Passenger name

Route info

Seat No.

Fare

Booking status

Transaction reference

Travel date and time

## 📌 To Do / Improvements
Email/SMS ticket delivery

Admin dashboard for bus/staff management

Live seat updates

Real MPESA API integration

QR code ticket validation
