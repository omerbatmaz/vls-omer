package repositories;

import entities.Person;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;
import javax.transaction.Transactional;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.xml.rpc.ServiceException;
import java.util.List;
import java.util.Set;

/**
 * @author omerbatmaz@gmail.com May 2018
 */
@Stateless
public class PersonRepository {

    @PersistenceUnit(name = "persistence-unit")
    EntityManagerFactory emf;

    @PersistenceContext
    private EntityManager entityManager;

    public List<Person> getPersons()
    {
        return entityManager
                .createQuery(
                        "SELECT person FROM Person person",
                        Person.class
                )
                .getResultList();
    }

    public Person clearCache(Long id)
    {
        Person person = entityManager.getReference(Person.class, id);
        if(person != null) {
            entityManager.refresh(person);
            return person;
        }
        return null;
    }

    public Person getById(Long id)
    {
        return entityManager.find(Person.class, id);
    }

    public Person delete(Long id)
    {
        Person person = this.getById(id);
        if(person != null) {
            entityManager.remove(person);
            return person;
        }
        return null;
    }

    public Person merge(Person person) {
        return entityManager.merge(person);
    }

    @Transactional
    public Person persist(Person person) throws ServiceException {

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Person>> constraintViolations = validator.validateProperty(
                person,
                "name"
        );

        entityManager.persist(person);

        return person;
    }
}
