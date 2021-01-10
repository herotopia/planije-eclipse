package web.dao;

import web.dao.repository.TaskRepository;
import web.beans.Task;
import web.beans.User;
import web.utility.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;

public class TaskDao implements TaskRepository {
    @Override
    public void addTask(Task task) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.save(task);
        transaction.commit();
        session.close();
    }

    @Override
    public void updateTask(int taskId, String title, String description, Date startDate, Date endDate, String state, String category) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        Task task = (Task) session.load(Task.class, taskId);

        // Update task properties
        task.setTitle(title); //Update HttpSession
        task.setDescription(description);
        task.setStartDate(startDate);
        task.setEndDate(endDate);
        task.setState(state);
        task.setCategory(category);

        session.update(task);
        transaction.commit();
        session.close();
    }

    @Override
    public void deleteTask(Task task) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.delete(task);
        transaction.commit();
        session.close();
    }

    @Override
    public Task getTaskById(int taskId) {
        Transaction transaction = null;
        Task task = null;
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            transaction = session.beginTransaction();
            task = (Task) session.load(Task.class, taskId);
            // commit transaction
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return task;
    }

    @Override
    public Collection<Task> getPersonalTasks(User user) {
        Collection<Task> filteredTasks = new ArrayList<>();
        for (Task task:user.getOwnedTasks()) {
            if (task.getSourceTeam().equals(null)){
                filteredTasks.add(task);
            }
        }
        return filteredTasks;
    }

    @Override
    public Collection<Task> filterTasksByState(ArrayList<Task> tasks, String state) {
        Collection<Task> filteredTasks = new ArrayList<>();
        for (Task task:tasks) {
            if (task.getState().equals(state)){
                filteredTasks.add(task);
            }
        }
        return filteredTasks;
    }

    @Override
    public Collection<Task> filterTasksByCategory(ArrayList<Task> tasks, String category) {
        Collection<Task> filteredTasks = new ArrayList<>();
        for (Task task:tasks) {
            if (task.getCategory().equals(category)){
                filteredTasks.add(task);
            }
        }
        return filteredTasks;
    }

    @Override
    public Collection<Task> filterTasksDueIn(ArrayList<Task> tasks, int numberOfDays) {
        //initialize result list
        Collection<Task> filteredTasks = new ArrayList<>();

        //get Current date
        long millis=System.currentTimeMillis();
        Date currentDate=new java.sql.Date(millis);

        //Add numberOfDays into currentDate to get resultDate
        Calendar c = Calendar.getInstance();
        c.setTime(currentDate);
        c.add(Calendar.DATE, numberOfDays);
        Date resultDate = new Date(c.getTimeInMillis());

        //Compare endDate with resultDate
        for (Task task:tasks) {
            if (task.getEndDate().compareTo(resultDate)<=0){
                filteredTasks.add(task);
            }
        }
        return filteredTasks;
    }

    //tells you how many days are left for the deadline
    public int daysLeft(Task task){
        //get Current date
        long millis=System.currentTimeMillis();
        Date currentDate=new java.sql.Date(millis);
        int days = task.getEndDate().compareTo(currentDate);
        return days;
    }
}
