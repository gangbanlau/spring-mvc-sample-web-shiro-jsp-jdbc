package my.mycompany.myapp.web;


import my.mycompany.myapp.service.IUsersService;

import org.junit.Before;
import org.junit.Ignore;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ThreadContext;
import org.apache.shiro.mgt.SecurityManager;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import static org.easymock.EasyMock.createNiceMock;
import static org.easymock.EasyMock.expect;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.DelegatingFilterProxy;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = { "classpath:root-context.xml",
		"classpath:servlet-context.xml" })
@Transactional
public class LoginControllerTest {

	@Autowired
	WebApplicationContext wac;	// cached
	
	@Autowired 
	MockServletContext servletContext; // cached
			
	@Autowired
	SecurityManager securityManager;

	private MockMvc mockMvc;

	@Before
	public void setup() {
		this.mockMvc = webAppContextSetup(this.wac).alwaysExpect(status().isOk()).build(); 
		
		//setSecurityManager(securityManager);
		//createNiceMock(WebSecurityManager.class);
		//ThreadContext.bind(createNiceMock(WebSecurityManager.class));   
	     //Subject subjectUnderTest = createNiceMock(Subject.class);
	     //expect(subjectUnderTest.isAuthenticated()).andReturn(true);
	}

	@After
	public void teardown() {
	}
	
	@Test
	public void test() throws Exception {
		this.mockMvc.perform(get("/login"))
				.andExpect(status().isOk())
				.andExpect(forwardedUrl("/WEB-INF/views/login.jsp"));
	}
	
	@Test
	@Ignore			// TODO fix it
	public void verifyPassphase() throws Exception
	{				
		this.mockMvc.perform(post("/login").param("user", "admin").param("password", "TestUserPassword"))
			.andExpect(status().isOk())
			.andExpect(redirectedUrl("/inventory"));
	}	
}
