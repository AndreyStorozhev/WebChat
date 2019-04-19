package project.dao.chating;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import project.entity.Conversation;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Repository
@Transactional
public class ConversationDaoImpl implements ConversationDao {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Conversation getConversationById(int id) {
        return entityManager.find(Conversation.class, id);
    }

    @Override
    public Conversation getConversationByUIDConversation(int UIDConversation) {
        Query query = entityManager.createQuery("SELECT c from Conversation c where c.UIDConversation = :UIDConversation");
        query.setParameter("UIDConversation", UIDConversation);
        Conversation result;
        try {
            result = (Conversation) query.getSingleResult();
        }catch (NoResultException e) {
            result = null;
        }
        return result;
    }

    @Override
    public void save(Conversation conversation) {
        entityManager.persist(conversation);
    }
}
