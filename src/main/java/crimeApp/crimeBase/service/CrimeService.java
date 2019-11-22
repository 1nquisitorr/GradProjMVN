package crimeApp.crimeBase.service;

import crimeApp.crimeBase.model.Crime;

import java.util.List;

public interface CrimeService {

    Crime getByID(int id);

    List<Crime> getAllCrimes();
}
