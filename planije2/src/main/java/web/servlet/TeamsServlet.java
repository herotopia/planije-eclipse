package web.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import web.beans.User;
import web.dao.UserDao;
import web.security.UserDetails;

/**
 * Servlet implementation class TeamsServlet
 */
public class TeamsServlet extends HttpServlet {
       
	UserDao userDao;

    public void init() {
    	userDao = new UserDao();
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		UserDetails userDetails = (UserDetails) session.getAttribute("userDetails");
		User user = userDao.getUserByUsername(userDetails.getUsername());
		if (!user.getOwnedTeams().isEmpty()) {
			request.setAttribute("ownedTeams", user.getOwnedTeams());
		}
		else {
			request.setAttribute("ownedTeams", null);
		}
		if (!user.getTeams().isEmpty()) {
			request.setAttribute("Teams", user.getTeams());
		}
		else {
			request.setAttribute("Teams", null);
		}
		response.sendRedirect("app/teams.jsp");
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
