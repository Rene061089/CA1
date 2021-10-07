package facades;

import entities.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import utils.EMF_Creator;


public class Populator {
//    public static void populate() {
//        EntityManagerFactory emf = EMF_Creator.createEntityManagerFactory();
//
//
//        Person ps1 = new Person("Nick1", "Nick2", "Nick3");
//        Person ps2 = new Person("Sven1", "Sven2", "Sven3");
//        Person ps3 = new Person("Konge1", "Konge2", "Konge3");
//
//        Hobby h1 = new Hobby("svømning", "Svømme","2","5");
//        Hobby h2 = new Hobby("Fiskning", "Fiskeri","3","6");
//        Hobby h3 = new Hobby("Fiskning2", "Fiskeri2","3","62");
//        Hobby h4 = new Hobby("Fiskning23", "Fiskeri22","33","622");
//
//        Phone p1 = new Phone(55);
//        Phone p2 = new Phone(66);
//        Phone p3 = new Phone(77);
//
//        Address a1 = new Address("Nordlyst 8 ");
//        Address a2 = new Address("add");
//        Address a3 = new Address("Svaadddneke");
//
//        Cityinfo c1 = new Cityinfo(3700, "Rønne");
//        Cityinfo c2 = new Cityinfo(3780, "Nexø");
//        Cityinfo c3 = new Cityinfo(3740, "Svaneke");
//
//        ps1.getHobbies().add(h1);
//        ps2.getHobbies().add(h2);
//        ps3.getHobbies().add(h3);
//        ps3.getHobbies().add(h4);
//        ps1.setPhone(p1);
//        ps2.setPhone(p2);
//        ps3.setPhone(p3);
//
//        a1.setCityinfo(c1);
//        a2.setCityinfo(c2);
//        a3.setCityinfo(c3);
//
//
//        ps1.setAddress(a1);
//        ps2.setAddress(a2);
//        ps3.setAddress(a3);
//
//        EntityManager em = emf.createEntityManager();
//        try {
//            em.getTransaction().begin();
//            em.persist(ps1);
//            em.persist(ps2);
//            em.persist(ps3);
//            em.getTransaction().commit();
//        } finally {
//            em.close();
//        }
//    }

//    public static void main(String[] args) {
//        populate();
//    }
}
