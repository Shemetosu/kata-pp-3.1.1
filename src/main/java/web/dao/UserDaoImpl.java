package web.dao;

import org.springframework.stereotype.Repository;
import web.model.User;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.PersistenceException;
import java.util.List;

@Repository
public class UserDaoImpl implements UserDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<User> getAllUsers() {
        return em
                .createQuery("select u from User u", User.class)
                .getResultList();
    }

    @Override
    public User getUser(int id) {
        User user = em.find(User.class, id);
        if (user == null) {
            throw new EntityNotFoundException(
                    "User with id: " + id + " not found"
            );
        }
        return user;
    }

    @Override
    public void saveUser(User user) {
        try {
            em.persist(user);
        } catch (PersistenceException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateUser(User user) {
        getUser(user.getId());
        try {
            em.merge(user);
        } catch (PersistenceException  e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteUser(int id) {
        User user = getUser(id);
        try {
            em.remove(user);
        } catch (PersistenceException  e) {
            e.printStackTrace();
        }
    }
}
