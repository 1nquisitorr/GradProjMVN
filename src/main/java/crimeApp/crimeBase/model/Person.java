package crimeApp.crimeBase.model;

import javax.persistence.*;
import java.sql.Date;

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
    private String LastName;


    @Column(name = "BirthDate")
    private Date birthDate;


    @Column(name = "Crimes")
    private String Crimes;


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

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getCrimes() {
        return Crimes;
    }

    public void setCrimes(String crimes) {
        Crimes = crimes;
    }

}

