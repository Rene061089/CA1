package facades;

import dtos.PersonDTO;
import dtos.PersonsDTO;
import errorhandling.PersonException;

public interface IPersonFacade
{

    PersonDTO addPerson(PersonDTO p) throws Exception;

    PersonDTO deletePerson(int id) throws PersonException;

    PersonDTO getPerson(int id) throws PersonException;

    PersonDTO updatePerson(PersonDTO p) throws PersonException;

    PersonsDTO getAllPersons();

    PersonsDTO getAllPersonsWithHobby(String name) throws PersonException;

    PersonsDTO getAllPersonsLivingInCity(int id) throws PersonException;

    PersonsDTO getAllPhonesFromPersonWithHobby(int id);


}
