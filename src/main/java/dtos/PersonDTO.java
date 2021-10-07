package dtos;

import entities.Person;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@Schema(name = "PersonDTO")
public class PersonDTO{
    private int dto_id;
    private String dto_fName;
    private String dto_lName;
    private String dto_email;
    private List <PhoneDTO> dto_phones;
    private int dto_zipCode;
    private String dto_street;
    private String dto_city;
    private List<HobbyDTO> dto_hobbies;
    private int dto_address_id;

    public static List<PersonDTO> getDtos(List<Person> lp) {
        List<PersonDTO> pdtos = new ArrayList();
        lp.forEach(p -> pdtos.add(new PersonDTO(p)));
        return pdtos;
    }

    public PersonDTO(Person p) {
        if (p.getPerson_id() != 0){
            this.dto_id = p.getPerson_id();
        }
        this.dto_fName = p.getfName();
        this.dto_lName = p.getlName();
        this.dto_email = p.getEmail();
        this.dto_phones = PhoneDTO.getDtos(p.getPhones());
        this.dto_street = p.getAddress().getStreet();
        this.dto_city = p.getAddress().getCityinfo().getCity();
        this.dto_zipCode = p.getAddress().getCityinfo().getZipCode();
        this.dto_hobbies = HobbyDTO.getDtos(p.getHobbies());
        this.dto_address_id = p.getAddress().getA_id();
    }

    public PersonDTO (PersonDTO pDTO) {
        if (pDTO.getDto_id() != 0){
            this.dto_id = pDTO.getDto_id();
        }
        this.dto_fName = pDTO.dto_fName;
        this.dto_lName = pDTO.dto_lName;
        this.dto_email = pDTO.dto_email;
        this.dto_phones = pDTO.dto_phones;
        this.dto_street = pDTO.dto_street;
        this.dto_city = pDTO.dto_city;
        this.dto_zipCode = pDTO.dto_zipCode;
        this.dto_hobbies = pDTO.getDto_hobbies();
        this.dto_address_id = pDTO.dto_address_id;
    }


    public int getDto_address_id()
    {
        return dto_address_id;
    }

    public void setDto_address_id(int dto_address_id)
    {
        this.dto_address_id = dto_address_id;
    }

    public List<PhoneDTO> getDto_phones() {
        return dto_phones;
    }

    public void setDto_phones(List<PhoneDTO> dto_phones) {
        this.dto_phones = dto_phones;
    }

    public int getDto_zipCode() {
        return dto_zipCode;
    }

    public void setDto_zipCode(int dto_zipCode) {
        this.dto_zipCode = dto_zipCode;
    }

    public String getDto_street() {
        return dto_street;
    }

    public void setDto_street(String dto_street) {
        this.dto_street = dto_street;
    }

    public String getDto_city() {
        return dto_city;
    }

    public void setDto_city(String dto_city) {
        this.dto_city = dto_city;
    }

    public List<HobbyDTO> getDto_hobbies() {
        return dto_hobbies;
    }

    public void setDto_hobbies(List<HobbyDTO> dto_hobbies) {
        this.dto_hobbies = dto_hobbies;
    }

    public int getDto_id() {
        return dto_id;
    }

    public void setDto_id(int dto_id) {
        this.dto_id = dto_id;
    }

    public String getDto_fName() {
        return dto_fName;
    }

    public void setDto_fName(String dto_fName) {
        this.dto_fName = dto_fName;
    }

    public String getDto_lName() {
        return dto_lName;
    }

    public void setDto_lName(String dto_lName) {
        this.dto_lName = dto_lName;
    }

    public String getDto_email() {
        return dto_email;
    }

    public void setDto_email(String dto_email) {
        this.dto_email = dto_email;
    }

    @Override
    public String toString() {
        return "PersonDTO{" +
                "dto_id=" + dto_id +
                ", dto_fName='" + dto_fName + '\'' +
                ", dto_lName='" + dto_lName + '\'' +
                ", dto_email='" + dto_email + '\'' +
                ", dto_phone=" + dto_phones +
                ", dto_zipCode=" + dto_zipCode +
                ", dto_street='" + dto_street + '\'' +
                ", dto_city='" + dto_city + '\'' +
                ", dto_hobbies=" + dto_hobbies +
                '}';
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PersonDTO personDTO = (PersonDTO) o;
        return dto_id == personDTO.dto_id && dto_zipCode == personDTO.dto_zipCode && dto_address_id == personDTO.dto_address_id && Objects.equals(dto_fName, personDTO.dto_fName) && Objects.equals(dto_lName, personDTO.dto_lName) && Objects.equals(dto_email, personDTO.dto_email) && Objects.equals(dto_phones, personDTO.dto_phones) && Objects.equals(dto_street, personDTO.dto_street) && Objects.equals(dto_city, personDTO.dto_city) && Objects.equals(dto_hobbies, personDTO.dto_hobbies);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(dto_id, dto_fName, dto_lName, dto_email, dto_phones, dto_zipCode, dto_street, dto_city, dto_hobbies, dto_address_id);
    }
}

