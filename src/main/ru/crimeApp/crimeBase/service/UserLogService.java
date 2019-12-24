package crimeApp.crimeBase.service;

import crimeApp.crimeBase.model.UserLog;

import java.util.List;

public interface UserLogService {
    List<UserLog> usersActions();
    void add(UserLog userLog);
}
