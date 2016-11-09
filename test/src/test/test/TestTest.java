package test.test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import org.junit.Assert;
import java.lang.reflect.Method;

import static com.sun.xml.internal.ws.dump.LoggingDumpTube.Position.Before;


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
		try {
	        test.Test a = new test.Test();
	        Class[] arg = new Class[2];
	        arg[0] = String.class;
	        arg[1] = String.class;
	        Method b = a.getClass().getDeclaredMethod("der", arg);
	        b.setAccessible(true);
	        Object[] sArg = new Object[2];
	        sArg[0] = "!d/dx";
	        sArg[1] = "";
	        Object c = b.invoke(a, sArg);
	        Assert.assertEquals(c, "0");
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
		
	}

	@Test
	public void testMain() {
		
	}

}
