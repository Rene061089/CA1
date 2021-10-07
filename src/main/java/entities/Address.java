package entities;

import dtos.CityinfoDTO;

import javax.persistence.*;
import java.util.List;

@Entity
@NamedQuery(name = "Address.deleteAllRows", query = "DELETE from Address ")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "a_id", nullable = false)
    private int a_id;
    private String street;

    @OneToMany(mappedBy = "address")
    private List<Person> persons;

    @JoinColumn
    @ManyToOne
    private Cityinfo cityinfo;

    public Address() {
    }



    public Address(String street) {
        this.street = street;
    }

    public List<Person> getPersons() {
        return persons;
    }

    public void setPersons(List<Person> persons) {
        this.persons = persons;
    }

    public Cityinfo getCityinfo() {
        return cityinfo;
    }

    public void setCityinfo(Cityinfo cityinfo) {
        this.cityinfo = cityinfo;
    }

    public int getA_id() {
        return a_id;
    }

    public void setA_id(int a_id) {
        this.a_id = a_id;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }
}
