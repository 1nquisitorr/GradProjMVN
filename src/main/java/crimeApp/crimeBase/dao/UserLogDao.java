package crimeApp.crimeBase.dao;

import crimeApp.crimeBase.model.UserLog;

import java.util.List;

public interface UserLogDao {

    List<UserLog> usersActions();

    void add(UserLog userLog);
}
