package br.com.esparda.sistema;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class SistemaAppTest extends TestCase {

	public SistemaAppTest ( String testName ) {
        super( testName );
    }

    public static Test suite() {
        return new TestSuite( SistemaAppTest.class );
    }

    public void test() {
        assertTrue( true );
    }
}
