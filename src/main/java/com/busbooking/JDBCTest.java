package com.busbooking;

class JDBCTest {
    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("JDBC Driver is installed!");
        } catch (ClassNotFoundException e) {
            System.out.println("JDBC Driver is missing!");
            e.printStackTrace();
        }
    }
}
