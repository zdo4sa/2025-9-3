package com.example.inquiry;
 
import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession; 
 
@WebServlet("/UserUserAccountServlet") 
public class UserAccountServlet extends HttpServlet { 
    protected void service(HttpServletRequest request, 
                           HttpServletResponse response) 
            throws ServletException, IOException { 
 
        request.setCharacterEncoding("UTF-8"); 
        response.setContentType("text/html; charset=UTF-8"); 
        PrintWriter out = response.getWriter(); 
 
        String action = request.getParameter("action"); 
 
        if ("login".equals(action)) { 
            String userName = request.getParameter("userName"); 
            String userPass = request.getParameter("userPass"); 
 
            if ("admin".equals(userName) && "secret".equals(userPass)) { 
                HttpSession session = request.getSession(); 
                session.setAttribute("isLoggedIn", Boolean.TRUE); 
                out.println("<h2>ログイン成功！</h2>"); 
                out.println("<a href=\"/jdynamic/shopItems.html\">商品を見る</a>"); 
            } else { 
                out.println("<h2>ログイン失敗</h2>"); 
            } 
        } else if ("logout".equals(action)) { 
            HttpSession session = request.getSession(false); 
            if (session != null) { 
                session.invalidate(); 
            } 
            out.println("<h2>ログアウトしました</h2>"); 
        } else { 
            out.println("<h2>不正なアクセス</h2>"); 
        } 
    } 
}