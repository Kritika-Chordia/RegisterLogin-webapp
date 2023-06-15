package com.registration;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.mysql.cj.Session;
@WebServlet("/login")
public class Login extends HttpServlet{
    protected void doPost ( HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        String uname=request.getParameter("username");
        String upwd=request.getParameter("password");
        HttpSession session=request.getSession();
        RequestDispatcher dispatcher=null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/RegisterLogin?useSSL=false","root","kritika");
            PreparedStatement pst=con.prepareStatement("select * from users where uname = ? and upwd = ?");

            pst.setString(1, uname);
            pst.setString(2, upwd);

            ResultSet rs=pst.executeQuery();
            if(rs.next()){
                session.setAttribute("name", rs.getString("uname"));
                dispatcher=request.getRequestDispatcher("index.jsp");
            }
            else{
                request.setAttribute("status", "failed");
                dispatcher=request.getRequestDispatcher("login.jsp");
            }
            dispatcher.forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    
}
