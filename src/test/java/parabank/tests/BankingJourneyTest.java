package parabank.tests;

import java.math.BigDecimal;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import parabank.config.BrowserSession;
import parabank.config.ScenarioData;
import parabank.data.Customer;
import parabank.data.SavingsAccounts;
import parabank.usecases.CustomerAccess;
import parabank.usecases.CustomerAccounts;
import parabank.usecases.CustomerBanking;
import parabank.usecases.CustomerRegistration;

@Tag("e2e")
@ExtendWith(BrowserSession.class)
class BankingJourneyTest {
    @Test
    void customerRegistersAndEndsTheirSession(
            CustomerRegistration registration, CustomerAccess access) {
        registration.register(ScenarioData.bankingCustomer());
        access.logout();
    }

    @Test
    void customerOpensAndConsultsSavings(
            CustomerRegistration registration, CustomerAccess access,
            CustomerBanking banking, CustomerAccounts accounts) {
        prepareCustomer(registration, access);
        SavingsAccounts opened = banking.openSavingsAccount();
        accounts.verifySavings(opened.savingsId());
        access.logout();
    }

    @Test
    void transferUpdatesBothBalancesAndRecordsMovements(
            CustomerRegistration registration, CustomerAccess access,
            CustomerBanking banking, CustomerAccounts accounts) {
        prepareCustomer(registration, access);
        SavingsAccounts opened = banking.openSavingsAccount();
        BigDecimal amount = ScenarioData.transferAmount();
        BigDecimal sourceBefore = accounts.balance(opened.sourceId());
        BigDecimal savingsBefore = accounts.balance(opened.savingsId());

        banking.transfer(opened, amount);

        accounts.verifyBalanceAndMovement(opened.sourceId(), sourceBefore.subtract(amount), amount);
        accounts.verifyBalanceAndMovement(opened.savingsId(), savingsBefore.add(amount), amount);
        access.logout();
    }

    private void prepareCustomer(CustomerRegistration registration, CustomerAccess access) {
        Customer customer = ScenarioData.bankingCustomer();
        registration.register(customer);
        access.logout();
        access.login(customer.credentials());
    }
}
