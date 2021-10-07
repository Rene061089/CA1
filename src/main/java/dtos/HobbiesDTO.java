package dtos;

import entities.Hobby;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.ArrayList;
import java.util.List;

@Schema(name = "HobbiesDTO")
public class HobbiesDTO {

    List<HobbyDTO> all = new ArrayList();

    public HobbiesDTO(List<Hobby> hobbyEntity)
    {
        for (Hobby h : hobbyEntity)
        {
            all.add(new HobbyDTO(h));
        }
    }

    @Override
    public String toString() {
        return "HobbiesDTO{" +
                "all=" + all +
                '}';
    }

    // Test only
    public int getSize() {
        return all.size() ;
    }

}
