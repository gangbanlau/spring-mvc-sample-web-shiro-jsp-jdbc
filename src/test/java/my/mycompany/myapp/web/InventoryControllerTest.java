package my.mycompany.myapp.web;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import org.apache.shiro.subject.Subject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import static org.easymock.EasyMock.*;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = { "classpath:root-context.xml",
		"classpath:servlet-context.xml" })
public class InventoryControllerTest extends AbstractShiroTest {
	@Autowired
	WebApplicationContext wac;	// cached
	
	@Autowired 
	MockServletContext servletContext; // cached
				
	private MockMvc mockMvc;

	@Before
	public void setup() {
		this.mockMvc = webAppContextSetup(this.wac).alwaysExpect(status().isOk()).build();
		
		//1.  Create a mock authenticated Subject instance for the test to run:
        Subject subjectUnderTest = createNiceMock(Subject.class);
        expect(subjectUnderTest.isAuthenticated()).andReturn(true);

        //2. Bind the subject to the current thread:
        setSubject(subjectUnderTest);
        
        //perform test logic here.  Any call to 
        //SecurityUtils.getSubject() directly (or nested in the 
        //call stack) will work properly.        
	}

	@After
	public void teardown() {
		//3. Unbind the subject from the current thread:
        clearSubject();
	}
	
	@Test
	public void testInventory() throws Exception {
		this.mockMvc.perform(get("/inventory"))
				.andExpect(status().isOk())
				.andExpect(forwardedUrl("/WEB-INF/views/inventory/products.jsp"));
	}
}
