package crimeApp.crimeBase.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "UserActions")
public class UserLog {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int UserId;
    @Column(name = "UserName")
    String UserName;
    @Column(name = "VisitedPage")
    String visitedPage;
    @Column(name = "Action")
    String action;
    @Column(name = "Date")
    Date date;

    public UserLog(String userName, String visitedPage, String action, Date date) {
        UserName = userName;
        this.visitedPage = visitedPage;
        this.action = action;
        this.date = date;
    }

    public UserLog() {}

    public int getUserId() {
        return UserId;
    }

    public void setUserId(int userId) {
        UserId = userId;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getVisitedPage() {
        return visitedPage;
    }

    public void setVisitedPage(String visitedPage) {
        this.visitedPage = visitedPage;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
