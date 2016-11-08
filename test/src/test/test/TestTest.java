package test.test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import org.junit.Assert;
import java.lang.reflect.Method;

public class TestTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testExpression() {
		try {
	        test.Test a = new test.Test();
	        Method b = a.getClass().getDeclaredMethod("expression" ,String.class);
	        b.setAccessible(true);
	        Object c=b.invoke(a,"a+3%b");
	        Assert.assertEquals(c,"Error");
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}

	@Test
	public void testPutin() {
		
	}
	@Test
	public void testSimplify() {
		
	}

	@Test
	public void testDer() {
		
	}

	@Test
	public void testMain() {
		
	}

}
