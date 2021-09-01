package br.com.esparda.${serviceLowerCase};

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class ${service}AppTest extends TestCase {

	public ${service}AppTest ( String testName ) {
        super( testName );
    }

    public static Test suite() {
        return new TestSuite( ${service}AppTest.class );
    }

    public void test() {
        assertTrue( true );
    }
}
