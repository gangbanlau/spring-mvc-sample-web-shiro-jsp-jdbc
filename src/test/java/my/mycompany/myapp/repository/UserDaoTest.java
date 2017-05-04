package my.mycompany.myapp.repository;

import org.junit.runner.RunWith;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import my.mycompany.myapp.domain.User;
import my.mycompany.myapp.repository.IUserDao;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:root-context.xml" })
@Transactional
public class UserDaoTest {
	private static final Logger logger = LoggerFactory.getLogger(UserDaoTest.class);
	
	@Autowired
	IUserDao userDao;
	
	@Test
	public void testInsert() {
		User newUser = new User();
		newUser.setCreatedDate(new java.util.Date());
		newUser.setPassphrase("passphase");
		newUser.setSalt("salt");
		newUser.setUserId("userid");
		
		userDao.insert(newUser);
	}
}
