package services;

import entities.Person;
import repositories.PersonRepository;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.xml.rpc.ServiceException;
import java.util.List;

/**
 * @author omerbatmaz@gmail.com May 2018
 */
@Stateless
public class PersonServiceImpl implements PersonService{

    @Inject
    private PersonRepository personRepository;

    @Override
    public List<Person> getPersons() {
        return personRepository.getPersons();
    }

    public Person clearCache(Long id) throws ServiceException {
        Person person = personRepository.clearCache(id);

        if(person == null)
            throw new ServiceException("Kayit bulunamadi.");

        return person;
    }

    public Person getById(Long id)
    {
        return personRepository.getById(id);
    }

    public Person delete(Long id) throws ServiceException {
        Person person = personRepository.delete(id);

        if(person == null)
            throw new ServiceException("Kayit bulunamadi.");

        return person;
    }

    public Person update(Person person)
    {
        return personRepository.merge(person);
    }

    public Person add(Person person) throws ServiceException {
        return personRepository.persist(person);
    }
}
