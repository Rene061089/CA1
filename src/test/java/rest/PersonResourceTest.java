package rest;

import entities.*;
import utils.EMF_Creator;
import io.restassured.RestAssured;

import static io.restassured.RestAssured.given;

import io.restassured.parsing.Parser;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.util.HttpStatus;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
//Uncomment the line below, to temporarily disable this test
//@Disabled

public class PersonResourceTest
{

    private static final int SERVER_PORT = 7777;
    private static final String SERVER_URL = "http://localhost/api";

    static final URI BASE_URI = UriBuilder.fromUri(SERVER_URL).port(SERVER_PORT).build();
    private static HttpServer httpServer;
    private static EntityManagerFactory emf;
    Person p1;
    Person p2;
    Cityinfo city1;
    Cityinfo city2;
    Address address1;
    Address address2;

    Hobby h1;
    Hobby h2;

    Phone phone1;
    Phone phone2;

    static HttpServer startServer()
    {
        ResourceConfig rc = ResourceConfig.forApplication(new ApplicationConfig());
        return GrizzlyHttpServerFactory.createHttpServer(BASE_URI, rc);
    }

    @BeforeAll
    public static void setUpClass()
    {
        //This method must be called before you request the EntityManagerFactory
        EMF_Creator.startREST_TestWithDB();
        emf = EMF_Creator.createEntityManagerFactoryForTest();

        httpServer = startServer();
        //Setup RestAssured
        RestAssured.baseURI = SERVER_URL;
        RestAssured.port = SERVER_PORT;
        RestAssured.defaultParser = Parser.JSON;
    }

    @AfterAll
    public static void closeTestServer()
    {
        //System.in.read();

        //Don't forget this, if you called its counterpart in @BeforeAll
        EMF_Creator.endREST_TestWithDB();
        httpServer.shutdownNow();
    }

    // Setup the DataBase (used by the test-server and this test) in a known state BEFORE EACH TEST
    //TODO -- Make sure to change the EntityClass used below to use YOUR OWN (renamed) Entity class
    @BeforeEach
    public void setUp()
    {
        EntityManager em = emf.createEntityManager();
        p1 = new Person("Førstenavn1", "Sidstenavn1", "Email1");
        city1 = new Cityinfo(3700, "Rønne");
        address1 = new Address("Kaldbakgade 8");
        address1.setCityinfo(city1);
        p1.setAddress(address1);

        p2 = new Person("Førstenavn2", "Sidstenavn2", "Email2");
        city2 = new Cityinfo(180, "Kaldbak");
        address2 = new Address("SortePerVej 8");
        address2.setCityinfo(city2);
        p2.setAddress(address2);

        List<Hobby> testhobby1 = new ArrayList<>();
        h1 = (em.find(Hobby.class, "Action figur"));
        testhobby1.add(h1);
        p1.setHobbies(testhobby1);

        List<Hobby> testhobby2 = new ArrayList<>();
        h2 = (em.find(Hobby.class, "Stand-up"));
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

    @Test
    public void testServerIsUp()
    {
        System.out.println("Testing is server UP");
        given().when().get("/person").then().statusCode(200);
    }

    //This test assumes the database contains two rows
    @Test
    public void testDummyMsg() throws Exception
    {
        given()
                .contentType(MediaType.APPLICATION_JSON)
                .get("/person/").then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("msg", equalTo("Hello World"));
    }

    @Test
    public void testPersonCount() throws Exception
    {
        given()
                .contentType(MediaType.APPLICATION_JSON)
                .get("/person/count").then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("count", equalTo(2));
    }


    @Test
    public void getAll()
    {
        given()
                .contentType(MediaType.APPLICATION_JSON)
                .get("/person/all")
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("all", hasSize(2));
    }

    @Test
    public void getAllPIC()
    {
        given()
                .contentType(MediaType.APPLICATION_JSON)
                .get("/person/allpic/" + address1.getCityinfo().getZipCode()).then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("all", hasSize(1));
    }


    @Test
    public void getAllPWH()
    {
        given().contentType(MediaType.APPLICATION_JSON)
                .get("/person/allpwh/" + h1.getName())
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("all", hasSize(1));
    }


    @Test
    public void getPerson()
    {
        given().contentType(MediaType.APPLICATION_JSON)
                .get("/person/" + p1.getPerson_id())
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("dto_fName", equalTo("Førstenavn1"))
                .body("dto_lName", equalTo("Sidstenavn1"))
                .body("dto_email", equalTo("Email1"))
//                .body("dto_phones",   equalTo("[{dto_number=1111, dto_person=1}]"))
                .body("dto_zipCode", equalTo(3700))
                .body("dto_street", equalTo("Kaldbakgade 8"))
                .body("dto_city", equalTo("Rønne"));
//                .body("dto_name", equalTo("Action figur"));
    }

    @Test
    public void getPersonByPhone()
    {
        given()
                .contentType(MediaType.APPLICATION_JSON)
                .get("person/phone/" + phone2.getNumber())
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("dto_id", equalTo(p2.getPerson_id()));
    }

    @Test
    public void getAllHobbiesCount()
    {
        given()
                .contentType(MediaType.APPLICATION_JSON)
                .get("/hobby/count")
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("count", equalTo(450));
    }

    @Test
    public void testEndPoints() throws Exception
    {
        given().when().get("/person").then().statusCode(200);
        given().when().get("/person/count").then().statusCode(200);
        given().when().get("/person/all").then().statusCode(200);
        given().when().get("/person/phone/1111").then().statusCode(200);
        given().when().get("/person/allpwh/Action figur").then().statusCode(200);
        given().when().get("/person/allpic/3700").then().statusCode(200);
        given().when().get("/hobby/").then().statusCode(200);
        given().when().get("/hobby/all").then().statusCode(200);

    }
}



