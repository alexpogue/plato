import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

public class DatabaseSupport implements IDatabaseSupport {
	private static final String PERSISTENCE_UNIT_NAME = "EclipseLink-JPA-Installation";
	private static EntityManagerFactory entityManagerFactory;

	public DatabaseSupport(String url, String user, String password) {
		Map<String, String> properties = new HashMap<String, String>();
		properties.put("javax.persistence.jdbc.url", url);
		properties.put("javax.persistence.jdbc.user", user);
		properties.put("javax.persistence.jdbc.password", password);

		entityManagerFactory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME, properties);
	}
	
	public boolean putMedia(Media m) {
		if(m.getId() < 0) {
			return putNewMedia(m);
		}
		else {
			return updateOldMedia(m);
		}
	}

	private boolean putNewMedia(Media m) {
		boolean success;
		Media.MediaType type = m.getType();
		switch(type) {
		case Book:
			success = putBook((Book) m);
			break;
		default:
			success = false;
			break;
		}
		return success;
	}

	private boolean putBook(Book b) {
		try {
			EntityManager em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();
			em.persist(b);
			em.getTransaction().commit();
			em.close();
		}
		catch(Exception e) {
			return false;
		}
		return true;
	}

	private boolean updateOldMedia(Media m) {
		boolean success;
		Media.MediaType type = m.getType();
		switch(type) {
		case Book:
			success = updateBook((Book) m);
			break;
		default:
			success = false;
			break;
		}
		return success;
	}

	private boolean updateBook(Book b) {
		EntityManager em = entityManagerFactory.createEntityManager();
		Book bookInDb = em.find(Book.class, b.getId());
		if(bookInDb == null) {
			return false;
		}
		em.getTransaction().begin();
		bookInDb.setTitle(b.getTitle());
		bookInDb.setAuthor(b.getAuthor());
		bookInDb.setPublisher(b.getPublisher());
		bookInDb.setIsbn(b.getIsbn());
		bookInDb.setType(b.getType());
		em.getTransaction().commit();
		return true;
	}

	private Book getBook(long bid) {
		EntityManager em = entityManagerFactory.createEntityManager();
		return em.find(Book.class, bid);
	}

	@Override
	public Media getMedia(long mid) {
		// for now we assume media can only be a book
		return getBook(mid);
	}

	@Override
	public boolean removeMedia(long mid) {
		return removeBook(mid);
	}

	private boolean removeBook(long bid) {
		try {
			EntityManager em = entityManagerFactory.createEntityManager();
			Book b = em.find(Book.class, bid);
			em.getTransaction().begin();
			em.remove(b);
			em.getTransaction().commit();
			return true;
		}
		catch(Exception e) {
			return false;
		}
	}
	
	@Override
	public boolean putCustomer(Customer c) {
		if(c.getId() < 0) {
			return putNewCustomer(c);
		}
		else {
			return updateOldCustomer(c);
		}
	}

	private boolean putNewCustomer(Customer c) {
		try {
			EntityManager em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();
			em.persist(c);
			em.getTransaction().commit();
			em.close();
		}
		catch(Exception e) {
			return false;
		}
		return true;
	}

	private boolean updateOldCustomer(Customer c) {
		EntityManager em = entityManagerFactory.createEntityManager();
		Customer customerInDb = em.find(Customer.class, c.getId());
		if(customerInDb == null) {
			return false;
		}
		em.getTransaction().begin();
		customerInDb.setName(c.getName());
		em.getTransaction().commit();
		return true;
	}

	@Override
	public Customer getCustomer(long cid) {
		EntityManager em = entityManagerFactory.createEntityManager();
		return em.find(Customer.class, cid);
	}

	@Override
	public boolean removeCustomer(long cid) {
		try {
			EntityManager em = entityManagerFactory.createEntityManager();
			Customer c = em.find(Customer.class, cid);
			em.getTransaction().begin();
			em.remove(c);
			em.getTransaction().commit();
			return true;
		}
		catch(Exception e) {
			return false;
		}
	}

	@Override
	public boolean putCheckoutCard(CheckoutCard cc) {
		long ccId = cc.getId();
		boolean success;
		if(ccId < 0) {
			success = putNewCheckoutCard(cc);
		}
		else {
			success = updateOldCheckoutCard(cc);
		}
		return success;
	}

	private boolean putNewCheckoutCard(CheckoutCard cc) {
		try {
			// Note: We do it this backward-seeming way because when we use the
			// media and customer held in cc directly, media and customer will
			// have duplicated rows in the database. Consider revisiting.

			long mid = cc.getMedia().getId();
			long cid = cc.getCustomer().getId();

			EntityManager em = entityManagerFactory.createEntityManager();

			Media media = em.find(Media.class, mid);
			Customer customer = em.find(Customer.class, cid);

			em.getTransaction().begin();
			CheckoutCard card = new CheckoutCard();
			card.setMedia(media);
			media.addCheckoutCard(card);
			card.setCustomer(customer);
			card.setCheckOutDate(cc.getCheckOutDate());
			card.setCheckInDate(cc.getCheckInDate());
			customer.addCheckoutCard(card);
			em.persist(card);
			em.getTransaction().commit();
			em.close();
		}
		catch(Exception e) {
			return false;
		}
		return true;
	}
	
	private boolean updateOldCheckoutCard(CheckoutCard cc) {
		// TODO
		long mid = cc.getMedia().getId();
		long cid = cc.getCustomer().getId();

		EntityManager em = entityManagerFactory.createEntityManager();

		Media media = em.find(Media.class, mid);
		Customer customer = em.find(Customer.class, cid);

		CheckoutCard ccInDb = em.find(CheckoutCard.class, cc.getId());
		if(ccInDb == null) {
			return false;
		}
		em.getTransaction().begin();
		ccInDb.setMedia(media);
		ccInDb.setCustomer(customer);
		ccInDb.setCheckOutDate(cc.getCheckOutDate());
		ccInDb.setCheckInDate(cc.getCheckInDate());
		em.getTransaction().commit();
		return true;
	}

	@Override
	public List<CheckoutCard> getCheckoutCardsForMedia(long mid) {
		// TODO
		return null;
	}

	@Override
	public LatePolicy getLatePolicy() {
		// TODO
		return null;
	}

	@Override
	public boolean putLatePolicy(LatePolicy lp) {
		// TODO Auto-generated method stub
		return false;
	}
	
	// Iteration 2 starts here!

	@Override
	public Media.MediaType getMediaType(long mid) {
		// TODO
		return Media.MediaType.Error;
	}

	@Override
	public User getUser(String username) {
		// TODO: enforce one user per username.
		EntityManager em = entityManagerFactory.createEntityManager();
		TypedQuery<User> q = em.createQuery("select u from User u where u.username = :username", User.class);
		q.setParameter("username", username);
		List<User> userList = q.getResultList();

		// hopefully there's only one!
		return userList.get(0);
	}

	@Override
	public boolean putUser(User user) {
		try {
			EntityManager em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();
			em.persist(user);
			em.getTransaction().commit();
			em.close();
		}
		catch(Exception e) {
			return false;
		}
		return true;
	}

	@Override
	public CheckoutCard getMostRecentCheckoutCardForMedia(long mid) {
		EntityManager em = entityManagerFactory.createEntityManager();
		Media m = em.find(Media.class, mid);
		Set<CheckoutCard> cards = m.getCheckoutCards();
		if(cards.size() == 0)
			return null;
		CheckoutCard recentCard = cards.iterator().next();
		for(CheckoutCard card : cards) {
			if(card.getCheckOutDate().after(recentCard.getCheckOutDate()))
				recentCard = card;
		}

		return recentCard;
	}

	@Override
	public List<Book> searchBooks(Book.BookField field, String searchString) {
		EntityManager em = entityManagerFactory.createEntityManager();

		String fieldName;
		switch(field)
		{
		case Title:
			fieldName = "title";
			break;

		case Author:
			fieldName = "author";
			break;

		case Publisher:
			fieldName = "publisher";
			break;

		case ISBN:
			fieldName = "isbn";
			break;

		default:
			return null;
		}
		TypedQuery<Book> q = em.createQuery("select b from Book b where b." + fieldName + "=:value", Book.class);
		q.setParameter("value", searchString);
		List<Book> results = q.getResultList();

		return results;
	}
}