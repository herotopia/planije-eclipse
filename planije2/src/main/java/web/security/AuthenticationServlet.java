package web.security;

import web.dao.TaskDao;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/login")
public class AuthenticationServlet extends HttpServlet {
    private UserDetailsDao loginDao;
    private HttpSession session;
    private TaskDao taskDao;

    public void init() {
        loginDao = new UserDetailsDao();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    private void authenticate(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        session = request.getSession();
        if (loginDao.validate(username, password)) {
            //set session
            session.setAttribute("userDetails",loginDao.loadUserDetailsByUsername(username));
            response.sendRedirect("home.jsp");
        } else {
            //reset session
            session.setAttribute("userDetails",null);
            response.sendRedirect("login.jsp");
        }
    }
}
