package crimeApp.crimeBase.service;

import crimeApp.crimeBase.dao.CrimeDAO;
import crimeApp.crimeBase.model.Crime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CrimeServiceImpl implements CrimeService {

    private CrimeDAO crimeDAO;

    @Autowired
    public void setCrimeDAO(CrimeDAO crimeDAO) {
        this.crimeDAO = crimeDAO;
    }

    @Override
    @Transactional
    public Crime getByID(int id) {

        return crimeDAO.getByID(id);
    }

    @Override
    public List<Crime> getAllCrimes() {
        return crimeDAO.getAllCrimes();
    }
}
