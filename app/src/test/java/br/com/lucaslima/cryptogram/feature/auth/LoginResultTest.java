package br.com.lucaslima.cryptogram.feature.auth;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import br.com.lucaslima.cryptogram.feature.auth.domain.LoginResult;

public class LoginResultTest {

    @Test
    public void success_holdsUsername() {
        LoginResult.Success result = new LoginResult.Success("player1");
        assertEquals("player1", result.username());
    }

    @Test
    public void success_equality_sameUsername() {
        assertEquals(new LoginResult.Success("x"), new LoginResult.Success("x"));
    }

    @Test
    public void success_inequality_differentUsername() {
        assertNotEquals(new LoginResult.Success("x"), new LoginResult.Success("y"));
    }

    @Test
    public void success_hashCode_consistentWithEquals() {
        LoginResult.Success a = new LoginResult.Success("u");
        LoginResult.Success b = new LoginResult.Success("u");
        assertEquals(a.hashCode(), b.hashCode());
    }

    @Test
    public void invalidCredentials_isCorrectType() {
        LoginResult result = new LoginResult.InvalidCredentials();
        assertTrue(result instanceof LoginResult.InvalidCredentials);
    }

    @Test
    public void invalidCredentials_equality() {
        assertEquals(new LoginResult.InvalidCredentials(), new LoginResult.InvalidCredentials());
    }

    @Test
    public void error_holdsMessage() {
        LoginResult.Error result = new LoginResult.Error("boom");
        assertEquals("boom", result.message());
    }

    @Test
    public void error_equality_sameMessage() {
        assertEquals(new LoginResult.Error("msg"), new LoginResult.Error("msg"));
    }

    @Test
    public void error_inequality_differentMessage() {
        assertNotEquals(new LoginResult.Error("a"), new LoginResult.Error("b"));
    }

    @Test
    public void subtypes_areDistinctFromEachOther() {
        assertNotEquals(new LoginResult.Success("u"), new LoginResult.InvalidCredentials());
        assertNotEquals(new LoginResult.Success("u"), new LoginResult.Error("e"));
        assertNotEquals(new LoginResult.InvalidCredentials(), new LoginResult.Error("e"));
    }
}
