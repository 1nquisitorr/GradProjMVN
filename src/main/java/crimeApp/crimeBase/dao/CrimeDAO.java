package crimeApp.crimeBase.dao;

import crimeApp.crimeBase.model.Crime;

import java.util.List;

public interface CrimeDAO {

    Crime getByID(int id);
    List<Crime> getAllCrimes();
}
