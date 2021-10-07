package dtos;

import entities.Address;
import entities.Hobby;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Schema(name = "HobbyDTO")
public class HobbyDTO {

    private String dto_name;
    private String dto_wikiLink;
    private String dto_category;
    private String dto_type;

    public HobbyDTO() {
    }

    public HobbyDTO(HobbyDTO h) {
    }

    public static List<HobbyDTO> getDtos(List<Hobby> hb) {
        List<HobbyDTO> hDTO = new ArrayList();
        hb.forEach(h -> hDTO.add(new HobbyDTO(h)));
        return hDTO;
    }

    public HobbyDTO(Hobby hb) {
        this.dto_name = hb.getName();
        this.dto_wikiLink = hb.getWikiLink();
        this.dto_category = hb.getCategory();
        this.dto_type = hb.getType();
    }

    public String getDto_name() {
        return dto_name;
    }

    public void setDto_name(String dto_name) {
        this.dto_name = dto_name;
    }

    public String getDto_wikiLink() {
        return dto_wikiLink;
    }

    public void setDto_wikiLink(String dto_wikiLink) {
        this.dto_wikiLink = dto_wikiLink;
    }

    public String getDto_category() {
        return dto_category;
    }

    public void setDto_category(String dto_category) {
        this.dto_category = dto_category;
    }

    public String getDto_type() {
        return dto_type;
    }

    public void setDto_type(String dto_type) {
        this.dto_type = dto_type;
    }


    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HobbyDTO hobbyDTO = (HobbyDTO) o;
        return Objects.equals(dto_name, hobbyDTO.dto_name) && Objects.equals(dto_wikiLink, hobbyDTO.dto_wikiLink) && Objects.equals(dto_category, hobbyDTO.dto_category) && Objects.equals(dto_type, hobbyDTO.dto_type);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(dto_name, dto_wikiLink, dto_category, dto_type);
    }
}
