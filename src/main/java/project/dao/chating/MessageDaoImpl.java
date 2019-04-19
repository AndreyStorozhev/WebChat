package project.dao.chating;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import project.entity.Message;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
@Transactional
public class MessageDaoImpl implements MessageDao {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void saveMessage(Message message) {
        entityManager.persist(message);
    }
}
