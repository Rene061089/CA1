package entities;

import javax.persistence.*;
import java.util.Objects;

@Entity
@NamedQuery(name = "Phone.deleteAllRows", query = "DELETE from Phone")
public class Phone {
    @Id
    @Column(name = "number", nullable = false)
    private int number;
//    private int phone_id;


    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    private Person person;


    public Phone() {
    }

    public Phone(int number, Person person) {
        this.number = number;
        this.person = person;
    }

    public Person getPerson() {
        return person;
    }

//    public int getPhone_id() {
//        return phone_id;
//    }
//
//    public void setPhone_id(int phone_id) {
//        this.phone_id = phone_id;
//    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    @Override
    public String toString() {
        return "Phone{" +
//                "phone_id=" + phone_id +
                ", number=" + number +
                '}';
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Phone phone = (Phone) o;
        return Objects.equals(number, phone.number);
    }

    @Override
    public int hashCode()
    {
        return 0;
    }
}
