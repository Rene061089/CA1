package dtos;

import entities.Person;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.ArrayList;
import java.util.List;

@Schema(name = "PersonsDTO")
public class PersonsDTO {

    List<PersonDTO> all = new ArrayList();

    public PersonsDTO(List<Person> personEntities)
    {
        for (Person p : personEntities)
        {
            all.add(new PersonDTO(p));
        }
    }

    @Override
    public String toString() {
        return "PersonsDTO{" +
                "all=" + all +
                '}';
    }

    // Test only
    public int getSize() {
        return all.size() ;
    }


}
