package hiber.dao;

import hiber.model.User;
import org.hibernate.NonUniqueResultException;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Repository
public class UserDaoImp implements UserDao {

   @Autowired
   private SessionFactory sessionFactory;

   @Override
   public void add(User user) {
      sessionFactory.getCurrentSession().save(user);
   }

   @Override
   @SuppressWarnings("unchecked")
   public List<User> listUsers() {
      TypedQuery<User> query=sessionFactory.getCurrentSession().createQuery("from User");
      return query.getResultList();
   }
   @Override
   public Optional<User> getUserByCarModelAndSeries(String model, int series) {
      try {
         TypedQuery<User> query = sessionFactory.getCurrentSession()
                 .createQuery("FROM User u WHERE u.car.model = :model AND u.car.series = :series", User.class);
         query.setParameter("model", model);
         query.setParameter("series", series);
         return Optional.of(query.getSingleResult());
      } catch (NoResultException | NonUniqueResultException e) {
         return Optional.empty();
      }
   }

}
