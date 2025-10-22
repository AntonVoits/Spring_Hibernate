package hiber.dao;

import hiber.model.Car;
import hiber.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.TypedQuery;
import java.util.List;

@Repository
@Transactional
public class UserDaoImp implements UserDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void add(User user) {
        sessionFactory.getCurrentSession().save(user);
    }

    @Override
    public List<User> listUsers() {
        TypedQuery<User> query = sessionFactory.getCurrentSession().createQuery("FROM User user LEFT JOIN FETCH user.car", User.class);
        return query.getResultList();
    }

    @Override
    public User getUserByCarModelAndSeries(Car car) {
        Query<User> query = sessionFactory.getCurrentSession().createQuery("FROM User user LEFT JOIN FETCH user.car " +
                "WHERE user.car.model = :model " +
                "AND user.car.series = :series", User.class);
        query.setParameter("model", car.getModel()).setParameter("series", car.getSeries());
        User user = query.getSingleResult();
        return user;
    }
}