//package com.example.demo.user;
//
//import org.hibernate.query.Query;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Example;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.domain.Sort;
//import org.springframework.stereotype.Repository;
//import javax.persistence.EntityManager;
//import java.util.List;
//import java.util.Optional;
//@Repository
//public class UserRepositoryImpl implements UserRepository{
//
//	    private EntityManager entityManager;
//
//	    @Autowired
//	    public UserRepositoryImpl(EntityManager entityManager){
//	        this.entityManager = entityManager;
//
//	    }
//	    
//	   //get all the transactions from the database
//	    @Override
//	    public List<User> getAllUsers() {
//	        Query theQuery= (Query) entityManager.createQuery("from User");
//	        List<User> users = theQuery.getResultList();
//
//	        return users;
//	    }
//
//	    //return the transaction by giving id as input
//	    @Override
//	    public User findUserById(Integer id) {
//	        User user = entityManager.find(User.class,id);
//	        return user;
//	    }
//
////////add the transaction to the database
//	    @Override
//	    public User saveUser(User user) {
//	        User dbUser = entityManager.merge(user);
//	        user.setId(dbUser.getId());
//	        return user;
//	    }
//
//	    //delete the transaction from the database using transaction id
//	    @Override
//	    public void deleteUserById(Integer id) {
//	        Query theQuery = (Query) entityManager.createQuery("delete from Transaction where id=:transactionId");
//	        theQuery.setParameter("id", id);
//	        theQuery.executeUpdate();
//	    }
//
//		@Override
//		public User findByEmail(String email) {
//			// TODO Auto-generated method stub
//			return null;
//		}
//
//		@Override
//		public User getUserByEmail(String email) {
//			// TODO Auto-generated method stub
//			return null;
//		}
//
//		@Override
//		public Optional<User> findById(Integer id) {
//			// TODO Auto-generated method stub
//			return null;
//		}
//
//		
//	}
