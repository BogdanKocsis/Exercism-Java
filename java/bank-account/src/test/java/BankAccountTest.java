import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.assertj.core.api.Assertions.*;

public class BankAccountTest {
    private BankAccount bankAccount;

    @BeforeEach
    public void setUp() {
        bankAccount = new BankAccount();
    }

    @Test
    @DisplayName("Newly opened account has zero balance")
    public void newlyOpenedAccountHasEmptyBalance() throws BankAccountActionInvalidException {
        bankAccount.open();

        assertThat(bankAccount.getBalance()).isEqualTo(0);
    }

    @Test
    @DisplayName("Single deposit")
    public void singleDeposit() throws BankAccountActionInvalidException {
        bankAccount.open();
        bankAccount.deposit(100);

        assertThat(bankAccount.getBalance()).isEqualTo(100);
    }

    @Test
    @DisplayName("Multiple deposits")
    public void multipleDeposits() throws BankAccountActionInvalidException {
        bankAccount.open();
        bankAccount.deposit(100);
        bankAccount.deposit(50);

        assertThat(bankAccount.getBalance()).isEqualTo(150);
    }

    @Test
    @DisplayName("Withdraw once")
    public void withdrawOnce() throws BankAccountActionInvalidException {
        bankAccount.open();
        bankAccount.deposit(100);
        bankAccount.withdraw(75);

        assertThat(bankAccount.getBalance()).isEqualTo(25);
    }

    @Test
    @DisplayName("Withdraw twice")
    public void withdrawTwice() throws BankAccountActionInvalidException {
        bankAccount.open();
        bankAccount.deposit(100);
        bankAccount.withdraw(80);
        bankAccount.withdraw(20);

        assertThat(bankAccount.getBalance()).isEqualTo(0);
    }

    @Test
    @DisplayName("Can do multiple operations sequentially")
    public void canDoMultipleOperationsSequentially() throws BankAccountActionInvalidException {
        bankAccount.open();
        bankAccount.deposit(100);
        bankAccount.deposit(110);
        bankAccount.withdraw(200);
        bankAccount.deposit(60);
        bankAccount.withdraw(50);

        assertThat(bankAccount.getBalance()).isEqualTo(20);
    }

    @Test
    @DisplayName("Cannot check balance of closed account")
    public void cannotCheckBalanceOfClosedAccount() throws BankAccountActionInvalidException {
        bankAccount.open();
        bankAccount.close();

        assertThatExceptionOfType(BankAccountActionInvalidException.class)
                .isThrownBy(bankAccount::getBalance)
                .withMessage("Account closed");
    }

    @Test
    @DisplayName("Cannot deposit into closed account")
    public void cannotDepositIntoClosedAccount() throws BankAccountActionInvalidException {
        bankAccount.open();
        bankAccount.close();

        assertThatExceptionOfType(BankAccountActionInvalidException.class)
                .isThrownBy(() -> bankAccount.deposit(50))
                .withMessage("Account closed");
    }

    @Test
    @DisplayName("Cannot deposit into unopened account")
    public void cannotDepositIntoUnopenedAccount() {
        assertThatExceptionOfType(BankAccountActionInvalidException.class)
                .isThrownBy(() -> bankAccount.deposit(50))
                .withMessage("Account closed");
    }

    @Test
    @DisplayName("Cannot withdraw from closed account")
    public void cannotWithdrawFromClosedAccount() throws BankAccountActionInvalidException {
        bankAccount.open();
        bankAccount.close();

        assertThatExceptionOfType(BankAccountActionInvalidException.class)
                .isThrownBy(() -> bankAccount.withdraw(50))
                .withMessage("Account closed");
    }

    @Test
    @DisplayName("Cannot close an account that was not opened")
    public void cannotCloseAnAccountThatWasNotOpened() {
        assertThatExceptionOfType(BankAccountActionInvalidException.class)
                .isThrownBy(bankAccount::close)
                .withMessage("Account not open");
    }

    @Test
    @DisplayName("Cannot open an already opened account")
    public void cannotOpenAnAlreadyOpenedAccount() throws BankAccountActionInvalidException {
        bankAccount.open();

        assertThatExceptionOfType(BankAccountActionInvalidException.class)
                .isThrownBy(bankAccount::open)
                .withMessage("Account already open");
    }

    @Test
    @DisplayName("Reopened account does not retain balance")
    public void reopenedAccountDoesNotRetainBalance() throws BankAccountActionInvalidException {
        bankAccount.open();
        bankAccount.deposit(50);
        bankAccount.close();
        bankAccount.open();

        assertThat(bankAccount.getBalance()).isEqualTo(0);
    }

    @Test
    @DisplayName("Cannot withdraw more than deposited")
    public void cannotWithdrawMoreThanDeposited() throws BankAccountActionInvalidException {
        bankAccount.open();
        bankAccount.deposit(25);

        assertThatExceptionOfType(BankAccountActionInvalidException.class)
                .isThrownBy(() -> bankAccount.withdraw(50))
                .withMessage("Cannot withdraw more money than is currently in the account");
    }

    @Test
    @DisplayName("Cannot withdraw negative")
    public void cannotWithdrawNegativeAmount() throws BankAccountActionInvalidException {
        bankAccount.open();
        bankAccount.deposit(100);

        assertThatExceptionOfType(BankAccountActionInvalidException.class)
                .isThrownBy(() -> bankAccount.withdraw(-50))
                .withMessage("Cannot deposit or withdraw negative amount");
    }

    @Test
    @DisplayName("Cannot deposit negative")
    public void cannotDepositNegativeAmount() throws BankAccountActionInvalidException {
        bankAccount.open();

        assertThatExceptionOfType(BankAccountActionInvalidException.class)
                .isThrownBy(() -> bankAccount.deposit(-50))
                .withMessage("Cannot deposit or withdraw negative amount");
    }

    @Test
    @DisplayName("Can handle concurrent transactions")
    public void canHandleConcurrentTransactions() throws BankAccountActionInvalidException, InterruptedException {
        bankAccount.open();
        bankAccount.deposit(1000);

        for (int i = 0; i < 10; i++) {
            adjustBalanceConcurrently();
            assertThat(bankAccount.getBalance()).isEqualTo(1000);
        }
    }

    private void adjustBalanceConcurrently() throws InterruptedException {
        Random random = new Random();

        Thread[] threads = new Thread[1000];
        for (int i = 0; i < 1000; i++) {
            threads[i] = new Thread(() -> {
                try {
                    bankAccount.deposit(5);
                    Thread.sleep(random.nextInt(10));
                    bankAccount.withdraw(5);
                } catch (BankAccountActionInvalidException e) {
                    fail("Exception should not be thrown: " + e.getMessage());
                } catch (InterruptedException ignored) {
                }
            });
            threads[i].start();
        }

        for (Thread thread : threads) {
            thread.join();
        }
    }
}
