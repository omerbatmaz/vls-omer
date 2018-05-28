package rest;

import entities.Person;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import repositories.PersonRepository;
import services.PersonService;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.rpc.ServiceException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


/**
 * @author omerbatmaz@gmail.com May 2018
 */
@RunWith(Arquillian.class)
public class PersonTest {

    @ArquillianResource
    private URL base;

    private WebTarget target;

    @Before
    public void setUp() throws MalformedURLException
    {
        Client client = ClientBuilder.newClient();
        System.out.println("Base URL: " + base.toString());
        target = client.target(URI.create(new URL(base, "/fourth/person").toExternalForm()));
        target.register(PersonRestImpl.class);
    }

    @Deployment(testable = false)
    public static WebArchive createDeployment()
    {

        return ShrinkWrap
                .create(WebArchive.class)//, "test.war"
                .addPackage(PersonRestImpl.class.getPackage())
                .addPackage(PersonService.class.getPackage())
                .addPackage(PersonRepository.class.getPackage())
                .addClass(PersonRestImpl.class)
                .addClass(PersonService.class)
                .addClass(PersonRepository.class)
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
    }

    private Person[] getPersonList()
    {
        return target.request().get(Person[].class);
    }

    private void removePerson(Long id)
    {
        target.path("{id}")
                .resolveTemplate("id", id)
                .request()
                .delete();
    }

    private void addPerson(Person person)
    {
        Response post = target.request().post(
                Entity.entity(person, MediaType.APPLICATION_JSON_TYPE)
        );
    }

    private void updatePerson(Person person)
    {
        Response post = target.request().put(
                Entity.entity(person, MediaType.APPLICATION_JSON_TYPE)
        );
    }

    private String getRandomName()
    {
        return "ABC" + UUID.randomUUID().toString().substring(0,16);
    }

    private Person getPersonByUniqueName(String name)
    {
        Person[] personList = this.getPersonList();
        for (Person p : personList)
        {
            if ( name.equals(p.getName()) )
            {
                return p;
            }
        }

        return null;
    }

    @Test
    public void testPostAndGet() throws ServiceException
    {
        String randomName = this.getRandomName();
        Person person = new Person();
        person.setName(randomName);
        this.addPerson(person);

        Person personByUniqueName = this.getPersonByUniqueName(randomName);

        assertNotNull(personByUniqueName);
    }

    @Test
    public void testPut() {

        String randomName = this.getRandomName();
        Person person = new Person();
        person.setName(randomName);
        this.addPerson(person);

        Person personByUniqueName = this.getPersonByUniqueName(randomName);
        String randomUpdatedName = this.getRandomName();
        personByUniqueName.setName(randomUpdatedName);
        this.updatePerson(personByUniqueName);

        Person updatedPersonByUniqueName = this.getPersonByUniqueName(randomName);

        assertNotNull(updatedPersonByUniqueName);
    }

    @Test
    public void testRemoveAll()
    {
        Person[] list = getPersonList();

        for (Person p : list)
        {
            removePerson(p.getID());
        }

        list = target.request().get(Person[].class);

        assertEquals(0, list.length);
    }
}
