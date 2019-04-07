package project.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import project.entity.UserDetails;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Repository
@Transactional
public class UserDetailsDaoImpl implements UserDetailsDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void save(UserDetails userDetails) {
        entityManager.persist(userDetails);
    }

    @Override
    public UserDetails findById(int id) {
        return entityManager.find(UserDetails.class, id);
    }



    @Override
    public UserDetails findByLogin(String login) {
        Query query = entityManager.createQuery("SELECT u from UserDetails u where u.login = :login");
        query.setParameter("login", login);
        UserDetails result;
        try {
            result = (UserDetails) query.getSingleResult();
        }catch (NoResultException e) {
            result = null;
        }
        return result;
    }

    @Override
    public void update(UserDetails userDetails) {
        entityManager.merge(userDetails);
    }
}
