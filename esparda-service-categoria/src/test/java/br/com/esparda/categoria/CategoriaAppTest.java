package br.com.esparda.categoria;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class CategoriaAppTest extends TestCase {

	public CategoriaAppTest ( String testName ) {
        super( testName );
    }

    public static Test suite() {
        return new TestSuite( CategoriaAppTest.class );
    }

    public void test() {
        assertTrue( true );
    }
}
