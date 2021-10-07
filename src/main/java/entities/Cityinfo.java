package entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
public class Cityinfo implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(length = 4)
    private int zipCode;
    @Column(length=35)
    private String city;

    @OneToMany(mappedBy = "cityinfo")
    private List<Address> addresses;

    public Cityinfo() {
    }

    public Cityinfo(int zipCode, String city) {
        this.zipCode = zipCode;
        this.city = city;
    }


    public List<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<Address> addresses) {
        this.addresses = addresses;
    }

    public int getZipCode() {
        return zipCode;
    }

    public void setZipCode(int zipCode) {
        this.zipCode = zipCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public String toString() {
        return "Cityinfo{" +
                "zipCode=" + zipCode +
                ", city='" + city + '\'' +
                ", addresses=" + addresses +
                '}';
    }
}
