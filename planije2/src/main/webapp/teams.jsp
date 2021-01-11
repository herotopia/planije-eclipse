<%@page import="org.hibernate.Transaction"%>
<%@page import="web.utility.HibernateUtil"%>
<%@page import="org.hibernate.Session"%>
<%@page import="java.util.Collection"%>
<%@page import="web.dao.UserDao"%>
<%@page import="web.beans.User"%>
<%@page import="web.security.UserDetails"%>
<%@page import="web.beans.Team"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Planije - Teams</title>
    <link rel="stylesheet"
          href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T"
          crossorigin="anonymous">
</head>
<body>
<a href="doing.jsp">
   <button class="btn btn-primary">Doing</button>
</a>
<a href="addTeam.jsp">
   <button class="btn btn-primary">Create new Team</button>
</a>
<form action="app/joinTeam" method="post">
            <label for="teamCode">Team Code:</label>
            <input  type="text"
                    class="form-control" placeholder="Enter Team code to join"
                    name="teamCode" required>
        <input type="submit" class="btn btn-primary" value="Join">
</form>
<%= (String) request.getAttribute("joinTeamResponse") %>
<h1>Owned Teams</h1>
<%
	Session session3 = HibernateUtil.getSessionFactory().openSession();
	Transaction transaction = session3.beginTransaction();
	
	UserDao userDao = new UserDao();

	HttpSession session2 = request.getSession();
	UserDetails userDetails = (UserDetails) session2.getAttribute("userDetails");
	
	User user = userDao.getUserByUsername(userDetails.getUsername());
	
	Collection<Team> ownedTeams = userDao.getOwnedTeams(user);
	Collection<Team> teams = userDao.getTeams(user);
	
	transaction.commit();
	
	if (!ownedTeams.isEmpty()) {
		out.println(ownedTeams);
	}
	else {
		out.println("You still haven't created any teams");
	}


%>
<h1>Joined Teams</h1>
<%
if (!teams.isEmpty()) {
	out.println(teams);
}
else {
	out.println("You still haven't joined any teams");
}

%>
</body>
</html>