package crimeApp.crimeBase.service;

import crimeApp.crimeBase.dao.PersonDAO;
import crimeApp.crimeBase.dao.UserLogDao;
import crimeApp.crimeBase.model.UserLog;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserLogServiceImpl implements UserLogService {

    private UserLogDao userLogDao;
    public UserLogServiceImpl(UserLogDao userLogDao) {
        this.userLogDao = userLogDao;
    }

    @Override
    @Transactional
    public List<UserLog> usersActions() {
        return userLogDao.usersActions();
    }

    @Override
    @Transactional
    public void add(UserLog userLog) {
        userLogDao.add(userLog);
    }
}
