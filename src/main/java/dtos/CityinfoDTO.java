package dtos;

import entities.Cityinfo;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.ArrayList;
import java.util.List;

@Schema(name = "CityinfoDTO")
public class CityinfoDTO {

    private int dto_zipCode;
    private String dto_city;

    public CityinfoDTO() {
    }


        public static List<CityinfoDTO> getDtos(List<Cityinfo> ct) {
        List<CityinfoDTO> ctDTO = new ArrayList();
        ct.forEach(c -> ctDTO.add(new CityinfoDTO(c)));
        return ctDTO;
    }
    public CityinfoDTO(Cityinfo c) {
        if (c.getCity() != null){
            this.dto_zipCode = c.getZipCode();
            this.dto_city= c.getCity();
        }
    }

    public int getDto_zipCode() {
        return dto_zipCode;
    }

    public void setDto_zipCode(int dto_zipCode) {
        this.dto_zipCode = dto_zipCode;
    }

    public String getDto_city() {
        return dto_city;
    }

    public void setDto_city(String dto_city) {
        this.dto_city = dto_city;
    }
}
