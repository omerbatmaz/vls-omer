package rest;

import entities.Person;
import services.PersonService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.xml.rpc.ServiceException;
import java.util.List;

/**
 * @author omerbatmaz@gmail.com May 2018
 */
@Path("person")
public class PersonRestImpl {

    @Inject
    private PersonService personService;

    @GET
    public Person[] getPersons() {

        List<Person> persons = personService.getPersons();

        return persons.toArray(new Person[persons.size()]);
    }

    @GET
    @Path("/clearCache/{id}")
    public Person clearCache(@PathParam("id") Long id) throws ServiceException {
        return personService.clearCache(id);
    }

    @GET()
    @Path("findById/{id}")
    public Person getById(@PathParam("id") Long id)
    {
        return personService.getById(id);
    }

    @DELETE
    @Path("deleteById/{id}")
    public Person delete(@PathParam("id") Long id) throws ServiceException {
        return personService.delete(id);
    }

    @POST
    public Person add(Person person) throws ServiceException {
        return personService.add(person);
    }

    @PUT
    public Person update(Person person)
    {
        return personService.update(person);
    }
}
