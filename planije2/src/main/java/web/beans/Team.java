package web.beans;

import java.sql.Date;
import java.util.Collection;

import javax.persistence.*;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.NaturalId;

@Entity
@Table(name = "Team")
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int teamId;

    private String title;
    private String description;

    @CreationTimestamp
    private Date creationDate;

    @NaturalId
    private String code;

    ///////////////////////////////////// Relationships ///////////////////////////////////////////////

    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name = "ownedTeams")
    private User teamOwner;

    @ManyToMany(mappedBy = "teams", fetch= FetchType.LAZY)
    private Collection<User> members;

    @OneToMany(mappedBy = "sourceTeam", fetch= FetchType.LAZY)
    private Collection<Task> tasks;

    ///////////////////////////////// Getters Setters Constructors //////////////////////////////////////////



    public Team(int teamId, String title, String description, Date creationDate, String code, User teamOwner,
                Collection<User> members, Collection<Task> tasks) {
        super();
        this.teamId = teamId;
        this.title = title;
        this.description = description;
        this.creationDate = creationDate;
        this.code = code;
        this.teamOwner = teamOwner;
        this.members = members;
        this.tasks = tasks;
    }

    public Team() {
    }

    public int getTeamId() {
        return teamId;
    }

    public void setTeamId(int teamId) {
        this.teamId = teamId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public String getCode() {
        return code;
    }

    public void setCode() {
        this.code = Integer.toString(title.hashCode() + Integer.toString(teamId).hashCode());
    }

    public User getTeamOwner() {
        return teamOwner;
    }

    public void setTeamOwner(User teamOwner) {
        this.teamOwner = teamOwner;
    }

    public Collection<User> getMembers() {
        return members;
    }

    public void setMembers(Collection<User> members) {
        this.members = members;
    }

    public Collection<Task> getTasks() {
        return tasks;
    }

    public void setTasks(Collection<Task> tasks) {
        this.tasks = tasks;
    }


}
