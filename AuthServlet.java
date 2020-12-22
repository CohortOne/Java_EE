/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.func;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.sql.DataSource;
import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author chand
 */
public class AuthServlet extends HttpServlet {

    @Resource(name = "com.ubs.DS1")
    DataSource ds;
    Connection con;

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            HttpSession sess = request.getSession();
            String action = request.getParameter("action");
            if (action.equals("Login")) {
                String userid = request.getParameter("usrn");
                String pwd = request.getParameter("pwd");
                if (checkIfLoggedIn(userid)) {
                    request.setAttribute("status", userid);
                    request.getRequestDispatcher("login.jsp").forward(request, response);
                } else {
                    String username = loginNow(userid, pwd);
                    if (username.equals("invalid")) {
                        con.close();
                        request.setAttribute("error", "invalid");
                        request.getRequestDispatcher("login.jsp").forward(request, response);
                    } else {
                        setLoggedInStatus(userid);
                        sess.setAttribute("user", username);
                        request.getRequestDispatcher("home.jsp").forward(request, response);
                    }
                }
            } else if (action.equals("Logout")) {
                String username = sess.getAttribute("user").toString();
                setLoggedOutStatus(username);
                sess.invalidate();
                request.getRequestDispatcher("login.jsp").forward(request, response);
            }
        } catch (Exception e) {
            System.out.println(" Exception in AuthN Servlet " + e.getMessage());
            e.printStackTrace();
        } 
    }

    protected boolean checkIfLoggedIn(String userid) throws SQLException {
        Connection con = ds.getConnection();
        String stmt = "SELECT INUSE from hr.AUTHENTICATIONTBL where USERID='" + userid + "'";
        System.out.println(" CheckIf Logged In :: " + stmt);
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery(stmt);
        if (rs.next()) {
            if (rs.getInt(1) == 1) {
                System.out.println(" User already logged in.. so exiting :: " + rs.getInt(1));
                con.close();
                return true;
            }
        }
        con.close();
        return false;
    }

    protected void setLoggedInStatus(String userid) throws SQLException {
        Connection con = ds.getConnection();
        String stmt = "update HR.AUTHENTICATIONTBL SET INUSE=1 where USERID='" + userid + "'";
        Statement st = con.createStatement();
        st.executeUpdate(stmt);
        con.close();
    }

    protected void setLoggedOutStatus(String userid) throws SQLException {
        Connection con = ds.getConnection();
        String stmt = "update HR.AUTHENTICATIONTBL SET INUSE=0 where USERNAME='" + userid + "'";
        System.out.println(" Logout Stmt " + stmt);
        Statement st = con.createStatement();
        st.executeUpdate(stmt);
        con.close();
    }

    protected String loginNow(String userid, String pwd) throws SQLException {
        Connection con = ds.getConnection();
        String stmt = "select USERNAME from hr.AUTHENTICATIONTBL where USERID='" + userid + "' and PASSWORD='" + pwd + "' ";
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery(stmt);
        System.out.println(stmt + " STMT Executed here    " + userid + "   ::  " + pwd);
        if (rs.next()) {
            String usr = rs.getString(1);
            System.out.println(" Username = " + usr);
            con.close();
            return usr;
        } else {
            System.out.println(" Invalid Credentials ");
            return "invalid";
        }

    }
// <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">

    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
