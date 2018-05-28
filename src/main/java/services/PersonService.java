package services;

import entities.Person;

import javax.xml.rpc.ServiceException;
import java.util.List;

/**
 * @author omerbatmaz@gmail.com May 2018
 */
public interface PersonService {

    public List<Person> getPersons();
    public Person clearCache(Long id) throws ServiceException;
    public Person getById(Long id);
    public Person delete(Long id) throws ServiceException;
    public Person update(Person person);
    public Person add(Person person) throws ServiceException;
}
