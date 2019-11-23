package crimeApp.crimeBase.model;

import javax.persistence.*;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

@Entity
@Table(name = "personList")
public class Person {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "Name")
    private String name;


    @Column(name = "LastName")
    private String surname;


    @Column(name = "BirthDate")
    private Date birthDate;


    @Column(name = "Crimes")
    private String Crimes;


    public Person(String name, String surname, Date birthDate, String crimes) {
        this.name = name;
        this.surname = surname;
        this.birthDate = birthDate;
        Crimes = crimes;
    }

    public Person() {
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) throws ParseException {
        if (!birthDate.equals("")) {
            SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
            java.util.Date parsed = format.parse(birthDate);
            Date sql = new java.sql.Date(parsed.getTime());
            Calendar c = Calendar.getInstance();
            c.setTime(sql);
            c.add(Calendar.DATE, 1);
            this.birthDate = new Date(c.getTimeInMillis());
        } else
            this.birthDate = new Date(1);
    }

    public String getCrimes() {
        return Crimes;
    }

    public void setCrimes(String crimes) {
        Crimes = crimes;
    }


}

