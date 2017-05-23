package dk.plpa.scheme;

import gnu.mapping.Environment;
import org.junit.After;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class SchemeConfigurerTest {

    @Test
    public void testConfigureSchemeEnvironment() throws Exception {
        assertNull(Environment.getCurrent());
        SchemeConfigurer schemeConfigurer = new SchemeConfigurer();
        schemeConfigurer.configureSchemeEnvironment();
        assertNotNull(Environment.getCurrent());
    }

    @After
    public void cleanUp(){
        Environment.setCurrent(null);
    }

}