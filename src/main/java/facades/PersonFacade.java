package facades;

import dtos.*;
import entities.Address;
import entities.Cityinfo;
import entities.Person;
import entities.Phone;
import entities.*;
import errorhandling.PersonException;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;

public class PersonFacade implements IPersonFacade
{

    private static PersonFacade instance;
    private static EntityManagerFactory emf;

    private PersonFacade()
    {
    }

    public static PersonFacade getPersonFacadeMethods(EntityManagerFactory _emf)
    {
        if (instance == null)
        {
            emf = _emf;
            instance = new PersonFacade();
        }
        return instance;
    }

    private EntityManager getEntityManager()
    {
        return emf.createEntityManager();
    }


    @Override
    public PersonDTO addPerson(PersonDTO p) throws Exception
    {
        EntityManager em = emf.createEntityManager();

        if (p.getDto_fName().length() == 0 || p.getDto_lName().length() == 0)
        {
            throw new PersonException(404, "Fejl i brugerinfo. Check navn");
        }
        if (!p.getDto_email().contains("@"))
        {
            throw new PersonException(404, "Ikke gyldig email");
        }


//        try {
//            TypedQuery<Person> query = em.createQuery("SELECT p FROM Person p WHERE p.email =:email", Person.class);
//            query.setParameter("email",p.getDto_email());
//            if (query.getSingleResult() != null){
//                throw new PersonException(404,"Emailen eksistere allerede");
//            }
//        } catch (PersonException e) {
//            throw new PersonException(404,"Emailen eksistere allerede");
//        }


        Person person = new Person();
        person.setfName(p.getDto_fName());
        person.setlName(p.getDto_lName());
        person.setEmail(p.getDto_email());

        if (em.find(Cityinfo.class, p.getDto_zipCode()) == null)
        {
            throw new PersonException(404, "Fejl i post nr. Indtast et gyldigt post nr");
        }
        Cityinfo city = new Cityinfo(p.getDto_zipCode(), p.getDto_city());
        Address address = new Address(p.getDto_street());
        address.setCityinfo(city);
        person.setAddress(address);
        if (p.getDto_phones() != null)
        {
            for (int i = 0; i < p.getDto_phones().size(); i++)
            {
                if (em.find(Phone.class, p.getDto_phones().get(i).getDto_number()) != null)
                {
                    throw new PersonException(404, "Telefon nr eksistere allerede");
                } else
                {
                    List<Phone> phoneList = new ArrayList<>();
                    for (int j = 0; j < p.getDto_phones().size(); j++)
                    {
                        Phone phone = new Phone(p.getDto_phones().get(j).getDto_number(), person);
                        phoneList.add(phone);
                    }
                    person.setPhones(phoneList);
                }
            }
        } else
        {
            throw new PersonException(404, "Indtast venligst et tlf nr");
        }
        List<Hobby> hobbies = new ArrayList<>();
        if (p.getDto_hobbies().size() != 0)
        {
            for (int i = 0; i < p.getDto_hobbies().size(); i++)
            {
                if (em.find(Hobby.class, p.getDto_hobbies().get(i).getDto_name()) != null)
                {
                    Hobby h = (em.find(Hobby.class, p.getDto_hobbies().get(i).getDto_name()));
                    hobbies.add(h);
                }
            }
            person.setHobbies(hobbies);
        } else
        {
            throw new PersonException(404, "Vælg venlist en hobby");
        }
        try
        {
            em.getTransaction().begin();
            em.persist(person);
            em.getTransaction().commit();
        } finally
        {
            em.close();
        }
        return new PersonDTO(person);
    }

    public long getPersonCount()
    {
        EntityManager em = emf.createEntityManager();
        try
        {
            long personCount = (long) em.createQuery("SELECT COUNT(p) FROM Person p").getSingleResult();
            return personCount;
        } finally
        {
            em.close();
        }
    }

    @Override
    public PersonDTO deletePerson(int id) throws PersonException
    {
        EntityManager em = getEntityManager();
        Person p = em.find(Person.class, id);

        if (p == null)
            throw new PersonException(404, "Could not delete person with id: " + id + " bacause the person does not exist");
        try
        {
            em.getTransaction().begin();
            for (int i = 0; i < p.getPhones().size(); i++)
            {
                em.remove(p.getPhones().remove(i));
            }
            em.remove(p.getAddress());
            em.remove(p);
            em.getTransaction().commit();
        } finally
        {
            em.clear();
            em.close();
        }
        return new PersonDTO(p);
    }

    @Override
    public PersonDTO getPerson(int id) throws PersonException
    {

        EntityManager em = emf.createEntityManager();
        if (em.find(Person.class, id) == null)
        {
            throw new PersonException(404, "Could not find person with id: " + id + " because the person does not exist");
        }
        return new PersonDTO(em.find(Person.class, id));
    }

    @Override
    public PersonsDTO getAllPersons()
    {
        EntityManager em = getEntityManager();
        TypedQuery<Person> query = em.createQuery("SELECT p FROM Person p", Person.class);
        List<Person> persons = query.getResultList();
        return new PersonsDTO(persons);
    }

    @Override
    public PersonsDTO getAllPersonsWithHobby(String name) throws PersonException
    {
        EntityManager em = getEntityManager();

        if (em.find(Hobby.class, name) == null)
        {
            throw new PersonException(404, "Hobbien fantes ikke");
        }

        TypedQuery<Person> query = em.createQuery("SELECT p FROM Person p JOIN p.hobbies h WHERE h.name = :name", Person.class);
        query.setParameter("name", name);
        List<Person> result = query.getResultList();
        return new PersonsDTO(result);

    }

    public PersonsDTO getAllPersonsByAddress(String street) throws PersonException
    {
        EntityManager em = getEntityManager();


        TypedQuery<Person> query = em.createQuery("SELECT p FROM Person p JOIN p.address a WHERE a.street = :street", Person.class);
        query.setParameter("street", street);
        List<Person> result = query.getResultList();
        return new PersonsDTO(result);
    }


    @Override
    public PersonsDTO getAllPersonsLivingInCity(int id) throws PersonException
    {
        EntityManager em = getEntityManager();

        if (em.find(Cityinfo.class, id) == null)
        {
            throw new PersonException(404, "Byen fandtes ikke, tjek post nr");
        }

        TypedQuery<Person> query = em.createQuery("SELECT p FROM Person p JOIN p.address.cityinfo a WHERE a.zipCode = :id ", Person.class);
        query.setParameter("id", id);
        List<Person> result = query.getResultList();
        return new PersonsDTO(result);

    }

    @Override
    public PersonsDTO getAllPhonesFromPersonWithHobby(int id)
    {
        return null;
    }

    @Override
    public PersonDTO updatePerson(PersonDTO p) throws PersonException
    {
        EntityManager em = getEntityManager();

        Person person = em.find(Person.class, p.getDto_id());

        if (person == null)
        {
            throw new PersonException(404, "Personen med dette id fandtes ikke");
        }

        if (p.getDto_fName().length() == 0 || p.getDto_lName().length() == 0)
        {
            throw new PersonException(404, "Fejl i brugerinfo. Check navn");
        }
        if (!p.getDto_email().contains("@"))
        {
            throw new PersonException(404, "Ikke gyldig email");
        }

        person.setfName(p.getDto_fName());
        person.setlName(p.getDto_lName());
        person.setEmail(p.getDto_email());

        Address newadr = em.find(Address.class, person.getAddress().getA_id());
        newadr.setStreet(p.getDto_street());

        if (em.find(Cityinfo.class, p.getDto_zipCode()) == null)
        {
            throw new PersonException(404, "Byen fandtes ikke, tjek post nr");
        }

        Cityinfo cty = new Cityinfo(p.getDto_zipCode(), p.getDto_city());
        newadr.setCityinfo(cty);
        person.setAddress(newadr);


        List<Phone> phoneList = new ArrayList<>();

//TODO lav personexception på tlf & hobby
        for (int i = 0; i < p.getDto_phones().size(); i++)
        {
            int nr = p.getDto_phones().get(i).getDto_number();
            Phone phone = new Phone(nr, person);
            phoneList.add(phone);
        }
        person.setPhones(phoneList);


        List<Hobby> hobbyList = new ArrayList<>();
        for (int i = 0; i < p.getDto_hobbies().size(); i++)
        {
            Hobby hobby = em.find(Hobby.class, p.getDto_hobbies().get(i).getDto_name());
            hobby.setName(p.getDto_hobbies().get(i).getDto_name());
            hobbyList.add(hobby);
        }
        person.setHobbies(hobbyList);

        try
        {
            em.getTransaction().begin();
            em.merge(person);
            em.getTransaction().commit();
        } finally
        {

            em.close();
        }
        return new PersonDTO(p);
    }


    public PersonDTO getPersonByPhone(int id) throws PersonException
    {
        EntityManager em = getEntityManager();

        if (em.find(Phone.class, id) == null)
        {
            throw new PersonException(404, "Ingen bruger med dette nr fandtes");
        }

        TypedQuery<Person> query = em.createQuery("SELECT p FROM Person p JOIN p.phone h WHERE h.number = :name", Person.class);
        query.setParameter("name", id);
        Person person = query.getSingleResult();

        return new PersonDTO(person);

    }


}
