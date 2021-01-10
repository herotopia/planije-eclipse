package web.dao;

import web.dao.repository.TeamRepository;
import web.beans.Team;
import web.beans.User;
import web.utility.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;

public class TeamDao implements TeamRepository {

    private UserDao userDao;

    public void addTeam(Team team) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.save(team);
        transaction.commit();
        session.close();
    }

    @Override
    public void updateTeam(int teamId, String title, String description) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        Team team = (Team) session.load(Team.class, teamId);

        // Update user properties
        team.setTitle(title);
        team.setDescription(description);
        team.setCode();

        session.update(team);
        transaction.commit();
        session.close();
    }

    public void deleteTeam(Team team) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.delete(team);
        transaction.commit();
        session.close();

    }

    @Override
    public Team getTeamById(int teamId) {
        Transaction transaction = null;
        Team team = null;
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            transaction = session.beginTransaction();
            team = (Team) session.load(Team.class, teamId);
            // commit transaction
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return team;
    }

    public Team getTeamByCode(String code) {
        Transaction transaction = null;
        Team team = null;
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            transaction = session.beginTransaction();
            team = (Team) session.get(Team.class, code);
            // commit transaction
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return team;
    }

    @Override
    public boolean joinTeam(int userId, String code) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Team team = getTeamByCode(code);
        User user = userDao.getUserById(userId);
        if (team != null){
            user.addToTeams(team);
            session.update(team);
            return true;
        }
        return false;
    }

    @Override
    public void leaveTeam(int userId, Team team) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        ArrayList<User> members;
        User user;
        //if userId is owner of team then the first member to join the team would become the owner
        if (team.getTeamOwner().getUserId()==userId){
            members = (ArrayList<User>) team.getMembers();
            team.setTeamOwner(members.get(0));
        }
        //if userId is just a member then he leaves the team
        else{
            user = userDao.getUserById(userId);
            user.removeFromTeams(team);
        }
        session.update(team);
    }
}
