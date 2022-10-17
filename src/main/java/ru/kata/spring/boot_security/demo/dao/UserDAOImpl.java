package ru.kata.spring.boot_security.demo.dao;

import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.Status;
import ru.kata.spring.boot_security.demo.model.User;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.HashSet;
import java.util.List;

@Repository
@Transactional
public class UserDAOImpl implements UserDAO {


    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<User> getAllUsers() {
        return entityManager.createQuery("select u from User u", User.class).getResultList();
    }

    @Override
    public User getUserById(long userId) {
        return entityManager.find(User.class, userId);
    }

    @Override
    public void addUser(User user) {
        entityManager.persist(user);
    }


    @Override
    public void removeUser(long id) {
        Query q = entityManager.createQuery("delete from User User where user_id = :id");
        q.setParameter("id",id);
        q.executeUpdate();
    }

    @Override
    public void updateUser(User user) {
        entityManager.merge(user);
    }

    @Override
    public User getUserByUsername(String username) {
        TypedQuery<User> q = (entityManager.createQuery("select u from User u " +
                "join fetch u.roles where u.username = :username",User.class));
        q.setParameter("username",username);
        return q.getResultList().stream().findFirst().orElse(null);
    }

    public void addDefaultUser() {
        HashSet<Role> roles = new HashSet<Role>();
        roles.add(new Role("ROLE_USER"));
        User user1 = new User("Ga1rry", "Pot1ter", (byte) 27, "user12@mail.ru", "root", "user21", Status.ACTIVE, roles);
        addUser(user1);
    }
}