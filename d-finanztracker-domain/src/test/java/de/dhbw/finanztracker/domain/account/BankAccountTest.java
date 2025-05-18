package de.dhbw.finanztracker.domain.account;

import de.dhbw.finanztracker.domain.account.transaction.ITransaction;
import de.dhbw.finanztracker.domain.account.transaction.reaccuringTransactions.IReaccuring;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

class BankAccountTest {

    private BankAccount account;

    @BeforeEach
    void setUp() {
        account = new BankAccount(100.0, "TestAccount", "TestBank");
    }

    @Test
    void constructorShouldSetFieldsCorrectly() {
        Assertions.assertEquals("TestAccount", account.getAccountName());
        Assertions.assertEquals("TestBank", account.getBankName());
        Assertions.assertTrue(account.getBalance() > 0);
        Assertions.assertNotNull(account.getAccountId());
        Assertions.assertEquals(0, account.getCounter());
        Assertions.assertNotNull(account.getTransactionHistory());
        Assertions.assertNotNull(account.getReaccuringTransactions());
    }

    @Test
    void constructorShouldThrowOnInvalidArgs() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> new BankAccount(-1, "A", "B"));
        Assertions.assertThrows(NullPointerException.class, () -> new BankAccount(10, null, "B"));
        Assertions.assertThrows(NullPointerException.class, () -> new BankAccount(10, "A", null));
        Assertions.assertThrows(IllegalArgumentException.class, () -> new BankAccount(10, "", "B"));
        Assertions.assertThrows(IllegalArgumentException.class, () -> new BankAccount(10, "A", ""));
    }

    @Test
    void fullConstructorShouldSetFieldsCorrectly() {
        UUID id = UUID.randomUUID();
        ArrayList<ITransaction> transactions = new ArrayList<>();
        ArrayList<IReaccuring> reaccurings = new ArrayList<>();
        BankAccount acc = new BankAccount(id, 50.0, "A", "B", 2, transactions, reaccurings);
        Assertions.assertEquals(id, acc.getAccountId());
        Assertions.assertEquals("A", acc.getAccountName());
        Assertions.assertEquals("B", acc.getBankName());
        Assertions.assertEquals(50.0, acc.getBalance());
        Assertions.assertEquals(2, acc.getCounter());
        Assertions.assertSame(transactions, acc.getTransactionHistory());
        Assertions.assertSame(reaccurings, acc.getReaccuringTransactions());
    }

    @Test
    void setAccountNameShouldWorkAndValidate() {
        account.setAccountName("NewName");
        Assertions.assertEquals("NewName", account.getAccountName());
        Assertions.assertThrows(IllegalArgumentException.class, () -> account.setAccountName(""));
        Assertions.assertThrows(NullPointerException.class, () -> account.setAccountName(null));
    }

    @Test
    void setBankNameShouldWorkAndValidate() {
        account.setBankName("NewBank");
        Assertions.assertEquals("NewBank", account.getBankName());
        Assertions.assertThrows(IllegalArgumentException.class, () -> account.setBankName(""));
        Assertions.assertThrows(NullPointerException.class, () -> account.setBankName(null));
    }

    @Test
    void setBalanceShouldWorkAndValidate() {
        account.setBalance(200.0);
        Assertions.assertEquals(200.0, account.getBalance());
        Assertions.assertThrows(IllegalArgumentException.class, () -> account.setBalance(-1));
    }

    @Test
    void addTransactionShouldIncreaseBalanceAndCounter() {
        ITransaction t = Mockito.mock(ITransaction.class);
        Mockito.when(t.getAmount()).thenReturn(50.0);
        boolean result = account.addTransaction(t);
        Assertions.assertTrue(result);
        Assertions.assertEquals(150.0, account.getBalance());
        Assertions.assertEquals(1, account.getCounter());
        Assertions.assertTrue(account.getTransactionHistory().contains(t));
    }

    @Test
    void addTransactionShouldFailIfBalanceNegative() {
        ITransaction t = Mockito.mock(ITransaction.class);
        Mockito.when(t.getAmount()).thenReturn(-200.0);
        boolean result = account.addTransaction(t);
        Assertions.assertFalse(result);
        Assertions.assertEquals(100.0, account.getBalance());
        Assertions.assertEquals(0, account.getCounter());
        Assertions.assertFalse(account.getTransactionHistory().contains(t));
    }

    @Test
    void removeTransactionShouldDecreaseBalanceAndCounter() {
        ITransaction t = Mockito.mock(ITransaction.class);
        Mockito.when(t.getAmount()).thenReturn(50.0);
        account.addTransaction(t);
        boolean result = account.removeTransaction(t);
        Assertions.assertTrue(result);
        Assertions.assertEquals(100.0, account.getBalance());
        Assertions.assertEquals(0, account.getCounter());
        Assertions.assertFalse(account.getTransactionHistory().contains(t));
    }

    @Test
    void removeTransactionShouldFailIfBalanceNegative() {
        ITransaction t = Mockito.mock(ITransaction.class);
        Mockito.when(t.getAmount()).thenReturn(200.0);
        boolean result = account.removeTransaction(t);
        Assertions.assertFalse(result);
    }

    @Test
    void addAndRemoveReaccuringTransaction() {
        IReaccuring r = Mockito.mock(IReaccuring.class);
        Assertions.assertTrue(account.addReaccuringTransaction(r));
        Assertions.assertTrue(account.getReaccuringTransactions().contains(r));
        Assertions.assertTrue(account.removeReaccuringTransaction(r));
        Assertions.assertFalse(account.getReaccuringTransactions().contains(r));
    }

    @Test
    void setTransactionsAndReaccurings() {
        List<ITransaction> txs = new ArrayList<>();
        List<IReaccuring> reaccs = new ArrayList<>();
        account.setTransactions(txs);
        account.setReaccuring(reaccs);
        Assertions.assertSame(txs, account.getTransactionHistory());
        Assertions.assertSame(reaccs, account.getReaccuringTransactions());
    }

    @Test
    void toTerminalStringShouldReturnCorrectFormat() {
        String s = account.toTerminalString();
        Assertions.assertTrue(s.contains(account.getAccountName()));
        Assertions.assertTrue(s.contains(account.getBankName()));
        Assertions.assertTrue(s.contains("€"));
    }
}