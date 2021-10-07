package facades;

import dtos.PersonDTO;
import entities.*;
import errorhandling.PersonException;
import utils.EMF_Creator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

//Uncomment the line below, to temporarily disable this test
//@Disabled
public class FacadeExampleTest
{

    private static EntityManagerFactory emf;
    private static PersonFacade facade;
    Person p1;
    Person p2;
    Person p3;

    Cityinfo city1;
    Cityinfo city2;
    Address address1;
    Address address2;

    Hobby h1;
    Hobby h2;

    Phone phone1;
    Phone phone2;
    Phone phone3;



    public FacadeExampleTest()
    {
    }


    // Setup the DataBase in a known state BEFORE EACH TEST


    @AfterEach
    public void tearDown()
    {
//        Remove any data after each test was run
    }

    @BeforeAll
    public static void setUpClass()
    {
        emf = EMF_Creator.createEntityManagerFactoryForTest();
        facade = PersonFacade.getPersonFacadeMethods(emf);
    }

    @AfterAll
    public static void tearDownClass()
    {
//        Clean up database after test is done or use a persistence unit with drop-and-create to start up clean on every test
    }

    // Setup the DataBase in a known state BEFORE EACH TEST
    @BeforeEach
    public void setUp()
    {
        EntityManager em = emf.createEntityManager();
        p1 = new Person("Førstenavn1", "Sidstenavn1", "Ema@il1");
        Cityinfo city1 = new Cityinfo(3700, "Rønne");
        Address address1 = new Address("Kaldbakgade 8");
        address1.setCityinfo(city1);
        p1.setAddress(address1);

        p2 = new Person("Førstenavn2", "Sidstenavn2", "Email2");
        Cityinfo city2 = new Cityinfo(180, "Kaldbak");
        Address address2 = new Address("SortePerVej 8");
        address2.setCityinfo(city2);
        p2.setAddress(address2);

        List<Hobby> testhobby1 = new ArrayList<>();
        Hobby h1 = (em.find(Hobby.class, "Action figur"));
        testhobby1.add(h1);
        p1.setHobbies(testhobby1);

        List<Hobby> testhobby2 = new ArrayList<>();
        Hobby h2 = (em.find(Hobby.class, "Stand-up"));
        testhobby2.add(h2);
        p2.setHobbies(testhobby2);

        List<Phone> testPhone1 = new ArrayList<>();
        phone1 = new Phone(1111, p1);
        testPhone1.add(phone1);
        p1.setPhones(testPhone1);

        List<Phone> testPhone2 = new ArrayList<>();
        phone2 = new Phone(2222, p2);
        testPhone2.add(phone2);
        p2.setPhones(testPhone2);

        try
        {
            em.getTransaction().begin();
            em.createNativeQuery("DELETE from PERSON_PHONE").executeUpdate();
            em.createNativeQuery("DELETE from PERSON_HOBBY").executeUpdate();
            em.createNamedQuery("Phone.deleteAllRows").executeUpdate();
            em.createNamedQuery("Person.deleteAllRows").executeUpdate();
            em.createNamedQuery("Address.deleteAllRows").executeUpdate();
            em.persist(p1);
            em.persist(p2);
            em.getTransaction().commit();
        } finally
        {
            em.close();
        }
    }

//    @AfterEach
//    public void tearDown() {
////        Remove any data after each test was run
//    }

    @Test
    public void testAFacadeMethod()
    {
        assertEquals(2, facade.getPersonCount(), "Expects two rows in the database");
    }

    @Test
    public void getPerson() throws PersonException
    {
        int expected = p1.getPerson_id();
        int actual = facade.getPerson(p1.getPerson_id()).getDto_id();
        assertEquals(expected, actual);
    }

    @Test
    public void deletePerson() throws PersonException
    {
        int expected = p2.getPerson_id();
        int actual = facade.deletePerson(p2.getPerson_id()).getDto_id();
        assertEquals(expected, actual);
    }

    @Test
    public void getAllPersons()
    {
        int expected = 2;
        int actual = facade.getAllPersons().getSize();
        assertEquals(expected, actual);
    }

    @Test
    public void getAllPIC() throws PersonException
    {
        int expected = 1;
        int actual = facade.getAllPersonsLivingInCity(p2.getAddress().getCityinfo().getZipCode()).getSize();
        System.out.println(actual);
        assertEquals(expected, actual);
    }

    @Test
    public void getAllpwh() throws PersonException
    {
        int expected = 1;
        int actual = facade.getAllPersonsWithHobby(p2.getHobbies().get(0).getName()).getSize();
        System.out.println(actual);
        assertEquals(expected, actual);
    }

    @Test
    public void updatePerson() throws PersonException
    {
        p1.setfName("René");
        PersonDTO expected = new PersonDTO(p1);
        PersonDTO actual = facade.updatePerson(new PersonDTO(p1));
        assertEquals(expected, actual);
    }


    @Test
    public void FailInUpdateNameException()
    {
        Exception exception = assertThrows(PersonException.class, () ->
        {
            p1.setfName("");
            PersonDTO expected = new PersonDTO(p1);
            PersonDTO actual = facade.updatePerson(new PersonDTO(p1));
            assertEquals(expected,actual);

        });
        String expectedMessage = "Fejl i brugerinfo. Check navn";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }


    @Test
    public void getPersonException()
    {
        Exception exception = assertThrows(PersonException.class, () ->
        {
            facade.getPerson(3);
        });
        String expectedMessage = "Could not find person with id: 3 because the person does not exist";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }
    @Test
    public void getAllPersonsByAddress() throws PersonException
    {
        int expected = 1;
        int actual = facade.getAllPersonsByAddress("Kaldbakgade 8").getSize();
        assertEquals(expected,actual);
    }
}

