package web.security;

import web.dao.UserDao;
import web.beans.User;
import web.utility.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class UserDetailsDao implements UserDetailsServices{

    private UserDao userDao;

    @Override
    public UserDetails loadUserDetailsByUsername(String username) {
        UserDetails userDetails;
        User user = userDao.getUserByUsername(username);
        userDetails = new UserDetails(user.getUserId(), user.getUsername(), user.getRoles());
        return userDetails;
    }

    @Override
    public boolean validate(String username, String password) {

        Transaction transaction = null;
        User user = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // start a transaction
            transaction = session.beginTransaction();
            // get an user object
            user = (User) session.createQuery("FROM User U WHERE U.username = :username").setParameter("username", username)
                    .uniqueResult();
            System.out.println(user);
            if (user != null && user.getPassword().equals(password)) {
                return true;
            }
            // commit transaction
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return false;
    }

}
