package com.busbooking.controllers;

import com.busbooking.dao.AdminDAO;
import com.busbooking.models.Admin;
import com.busbooking.utils.PasswordUtil;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/admin")
public class AdminServlet extends HttpServlet {
    private AdminDAO adminDAO;

    @Override
    public void init() {
        adminDAO = new AdminDAO();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("register".equals(action)) {
            registerAdmin(request, response);
        } else if ("login".equals(action)) {
            loginAdmin(request, response);
        }
    }

    private void registerAdmin(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        String firstName = request.getParameter("first_name");
        String lastName = request.getParameter("last_name");
        String email = request.getParameter("email");
        String phoneNumber = request.getParameter("phone_number");
        String password = request.getParameter("password");

        String hashedPassword = PasswordUtil.hashPassword(password);
        Admin admin = new Admin(0, firstName, lastName, email, phoneNumber, hashedPassword, "Admin");

        boolean isRegistered = adminDAO.registerAdmin(admin);

        if (isRegistered) {
            response.sendRedirect("admin-dashboard.jsp?message=Admin registered successfully");
        } else {
            response.sendRedirect("register.jsp?error=Registration failed");
        }
    }

    private void loginAdmin(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        Admin admin = adminDAO.getAdminByEmail(email);
        if (admin != null && PasswordUtil.verifyPassword(password, admin.getPasswordHash())) {
            HttpSession session = request.getSession();
            session.setAttribute("admin", admin);
            response.sendRedirect("admin-dashboard.jsp");
        } else {
            response.sendRedirect("login.jsp?error=Invalid email or password");
        }
    }
}
