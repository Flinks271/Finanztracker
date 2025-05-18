package de.dhbw.finanztracker.domain.user;


import de.dhbw.finanztracker.domain.account.IAccount;

import org.junit.Before;
import org.junit.Test;
import java.util.*;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;


public class UserTest {

    private IAccount mockAccount1;
    private IAccount mockAccount2;
    private List<IAccount> accountList;

    @Before
    public void setUp() {
        mockAccount1 = mock(IAccount.class);
        mockAccount2 = mock(IAccount.class);
        accountList = new ArrayList<>();
        accountList.add(mockAccount1);
        accountList.add(mockAccount2);
    }

    @Test
    public void testConstructorWithUsername() {
        User user = new User("testuser");
        assertNotNull(user.getUserId());
        assertEquals("testuser", user.getUsername());
        assertNotNull(user.getAccounts());
        assertTrue(user.getAccounts().isEmpty());
    }

    @Test
    public void testConstructorWithUUIDAndUsername() {
        UUID uuid = UUID.randomUUID();
        User user = new User(uuid, "testuser");
        assertEquals(uuid, user.getUserId());
        assertEquals("testuser", user.getUsername());
        assertNotNull(user.getAccounts());
        assertTrue(user.getAccounts().isEmpty());
    }

    @Test
    public void testConstructorWithUUIDUsernameAndAccounts() {
        UUID uuid = UUID.randomUUID();
        User user = new User(uuid, "testuser", accountList);
        assertEquals(uuid, user.getUserId());
        assertEquals("testuser", user.getUsername());
        assertEquals(accountList, user.getAccounts());
    }

    @Test(expected = NullPointerException.class)
    public void testConstructorWithNullUsername() {
        new User((String) null);
    }

    @Test(expected = NullPointerException.class)
    public void testConstructorWithNullUUID() {
        new User(null, "testuser");
    }

    @Test(expected = NullPointerException.class)
    public void testConstructorWithNullUsernameAndUUID() {
        new User(null, null);
    }

    @Test(expected = NullPointerException.class)
    public void testConstructorWithNullAccounts() {
        new User(UUID.randomUUID(), "testuser", null);
    }

    @Test
    public void testGetAccounts() {
        User user = new User(UUID.randomUUID(), "testuser", accountList);
        assertEquals(accountList, user.getAccounts());
    }

    @Test
    public void testGetAccount() {
        User user = new User(UUID.randomUUID(), "testuser", accountList);
        assertEquals(mockAccount1, user.getAccount(0));
        assertEquals(mockAccount2, user.getAccount(1));
    }

    @Test
    public void testSetAccounts() {
        User user = new User("testuser");
        user.setAccounts(accountList);
        assertEquals(accountList, user.getAccounts());
    }

    @Test(expected = NullPointerException.class)
    public void testSetAccountsNull() {
        User user = new User("testuser");
        user.setAccounts(null);
    }

    @Test
    public void testAddAccount() {
        User user = new User("testuser");
        boolean result = user.addAccount(mockAccount1);
        assertTrue(result);
        assertEquals(1, user.getAccounts().size());
        assertEquals(mockAccount1, user.getAccounts().get(0));
    }

    @Test(expected = NullPointerException.class)
    public void testAddAccountNull() {
        User user = new User("testuser");
        user.addAccount(null);
    }

    @Test
    public void testAddAccounts() {
        User user = new User("testuser");
        boolean result = user.addAccounts(accountList);
        assertTrue(result);
        assertEquals(2, user.getAccounts().size());
        assertEquals(mockAccount1, user.getAccounts().get(0));
        assertEquals(mockAccount2, user.getAccounts().get(1));
    }

    @Test(expected = NullPointerException.class)
    public void testAddAccountsNull() {
        User user = new User("testuser");
        user.addAccounts(null);
    }

    @Test
    public void testRemoveAccount() {
        User user = new User(UUID.randomUUID(), "testuser", new ArrayList<>(accountList));
        boolean result = user.removeAccount(mockAccount1);
        assertTrue(result);
        assertEquals(1, user.getAccounts().size());
        assertEquals(mockAccount2, user.getAccounts().get(0));
    }

    @Test(expected = NullPointerException.class)
    public void testRemoveAccountNull() {
        User user = new User("testuser");
        user.removeAccount(null);
    }
}