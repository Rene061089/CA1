package dtos;

import entities.Phone;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.ArrayList;
import java.util.List;

@Schema(name = "PhonesDTO")
public class PhonesDTO {


    List<PhoneDTO> all = new ArrayList();

    public PhonesDTO(List<Phone> phoneEntity)
    {
        for (Phone p : phoneEntity)
        {
            all.add(new PhoneDTO(p));
        }
    }

    @Override
    public String toString() {
        return "Phones{" +
                "all=" + all +
                '}';
    }

    // Test only
    public int getSize() {
        return all.size() ;
    }


}
