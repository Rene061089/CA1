package dtos;

import entities.Address;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.ArrayList;
import java.util.List;

@Schema(name = "AddressDTO")
public class AddressDTO {

    private int dto_a_id;
    private String dto_street;
    private List<PersonDTO> dto_persons;
    private String dto_city;
    private int dto_cityzip;

    public AddressDTO() {
    }


    public static List<AddressDTO> getDtos(List<Address> ad) {
        List<AddressDTO> adDTO = new ArrayList();
        ad.forEach(a -> adDTO.add(new AddressDTO(a)));
        return adDTO;
    }

    public AddressDTO(Address a) {
        if (a.getA_id() != 0) {
            this.dto_a_id = a.getA_id();
        }
        this.dto_street = a.getStreet();
        this.dto_city = a.getCityinfo().getCity();
        this.dto_cityzip = a.getCityinfo().getZipCode();
    }

//    public AddressDTO(PersonDTO aDTO) {
//        if (aDTO.getDto_id() != 0) {
//            this.dto_a_id = aDTO.getDto_id();
//        }
//        this.dto_street = aDTO.dto_street;
//        this.dto_city = aDTO.dto_city;
//        this.dto_cityzip = aDTO.dto_cityzip;
//    }



    public List<PersonDTO> getDto_persons() {
        return dto_persons;
    }

    public void setDto_persons(List<PersonDTO> dto_persons) {
        this.dto_persons = dto_persons;
    }

    public String getDto_city() {
        return dto_city;
    }

    public void setDto_city(String dto_city) {
        this.dto_city = dto_city;
    }

    public int getDto_cityzip() {
        return dto_cityzip;
    }

    public void setDto_cityzip(int dto_cityzip) {
        this.dto_cityzip = dto_cityzip;
    }

    public int getDto_a_id() {
        return dto_a_id;
    }

    public void setDto_a_id(int dto_a_id) {
        this.dto_a_id = dto_a_id;
    }

    public String getDto_street() {
        return dto_street;
    }

    public void setDto_street(String dto_street) {
        this.dto_street = dto_street;
    }


}