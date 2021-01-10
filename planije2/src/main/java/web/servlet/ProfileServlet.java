package web.servlet;

import web.dao.UserDao;
import web.beans.User;
import web.security.UserDetails;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(displayName = "Profile Servlet", name = "ProfileServlet", value = "/app/profile")
public class ProfileServlet extends HttpServlet {
    private UserDao userDao;
    @Override
    public void init() throws ServletException {
        userDao = new UserDao();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        UserDetails userDetails = (UserDetails) session.getAttribute("userDetails");
        User user = userDao.getUserById(userDetails.getUserId());
        request.setAttribute("User",user);
    }

    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        UserDetails userDetails = (UserDetails) session.getAttribute("userDetails");
        User user = userDao.getUserById(userDetails.getUserId());
        request.setAttribute("User",user);
    }


}
