package br.com.esparda.orcamento;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class OrcamentoAppTest extends TestCase {

	public OrcamentoAppTest ( String testName ) {
        super( testName );
    }

    public static Test suite() {
        return new TestSuite( OrcamentoAppTest.class );
    }

    public void test() {
        assertTrue( true );
    }
}
