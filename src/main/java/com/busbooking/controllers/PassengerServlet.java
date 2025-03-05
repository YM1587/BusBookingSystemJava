package com.busbooking.controllers;

import com.busbooking.dao.PassengerDAO;
import com.busbooking.models.Passenger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/passenger") // Handles requests to /passenger
public class PassengerServlet extends HttpServlet {
    private final PassengerDAO passengerDAO = new PassengerDAO();

    /**
     * Handles GET requests for retrieving passenger(s).
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idParam = request.getParameter("id");

        if (idParam != null) {
            // Get single passenger by ID
            int id = Integer.parseInt(idParam);
            Passenger passenger = passengerDAO.getPassengerById(id);
            request.setAttribute("passenger", passenger);
            request.getRequestDispatcher("/passengerDetails.jsp").forward(request, response);
        } else {
            // Get all passengers
            List<Passenger> passengers = passengerDAO.getAllPassengers();
            request.setAttribute("passengers", passengers);
            request.getRequestDispatcher("/passengerList.jsp").forward(request, response);
        }
    }

    /**
     * Handles POST requests for registering a new passenger.
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("register".equals(action)) {
            String firstName = request.getParameter("firstName");
            String lastName = request.getParameter("lastName");
            String email = request.getParameter("email");
            String phone = request.getParameter("phone");
            String password = request.getParameter("password");

            // Default role: "PASSENGER"
            Passenger passenger = new Passenger(0, firstName, lastName, email, phone, password, "PASSENGER", null);

            // Check if email already exists
            if (passengerDAO.isEmailExists(email)) {
                request.setAttribute("error", "Email already exists!");
                request.getRequestDispatcher("/register.jsp").forward(request, response);
                return;
            }

            passengerDAO.registerPassenger(passenger);
            response.sendRedirect("login.jsp"); // Redirect to login after registration
        } else if ("login".equals(action)) {
            String email = request.getParameter("email");
            String password = request.getParameter("password");

            Passenger passenger = passengerDAO.authenticatePassenger(email, password);

            if (passenger != null) {
                request.getSession().setAttribute("loggedPassenger", passenger);
                response.sendRedirect("dashboard.jsp"); // Redirect to dashboard
            } else {
                request.setAttribute("error", "Invalid email or password!");
                request.getRequestDispatcher("/login.jsp").forward(request, response);
            }
        }
    }

    /**
     * Handles PUT requests for updating a passenger's details.
     */
    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String password = request.getParameter("password");
        String role = request.getParameter("role");

        Passenger passenger = new Passenger(id, firstName, lastName, email, phone, password, role, null);
        passengerDAO.updatePassenger(passenger);
        response.getWriter().write("Passenger updated successfully!");
    }

    /**
     * Handles DELETE requests for deleting a passenger.
     */
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        passengerDAO.deletePassenger(id);
        response.getWriter().write("Passenger deleted successfully!");
    }
}
