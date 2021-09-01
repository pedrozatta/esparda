package br.com.esparda.vendedor;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class VendedorAppTest extends TestCase {

	public VendedorAppTest ( String testName ) {
        super( testName );
    }

    public static Test suite() {
        return new TestSuite( VendedorAppTest.class );
    }

    public void test() {
        assertTrue( true );
    }
}
