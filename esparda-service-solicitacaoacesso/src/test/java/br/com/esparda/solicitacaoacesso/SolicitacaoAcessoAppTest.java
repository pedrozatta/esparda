package br.com.esparda.solicitacaoacesso;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class SolicitacaoAcessoAppTest extends TestCase {

	public SolicitacaoAcessoAppTest ( String testName ) {
        super( testName );
    }

    public static Test suite() {
        return new TestSuite( SolicitacaoAcessoAppTest.class );
    }

    public void test() {
        assertTrue( true );
    }
}
