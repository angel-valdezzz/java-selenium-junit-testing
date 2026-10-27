package parabank.tests;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import parabank.config.BrowserSession;
import parabank.data.Customer;
import parabank.usecases.CustomerAccess;
import parabank.usecases.CustomerAccounts;
import parabank.usecases.CustomerRegistration;

@Tag("e2e")
@ExtendWith(BrowserSession.class)
class CustomerJourneyTest {
    @ParameterizedTest(name = "{0}")
    @MethodSource("parabank.config.ScenarioData#customerJourneys")
    void registeredCustomerCanAccessTheirAccounts(
            Customer customer, CustomerRegistration registration,
            CustomerAccess access, CustomerAccounts accounts) {
        registration.register(customer);
        access.logout();
        access.login(customer.credentials());
        accounts.verifyOverviewFor(customer);
        access.logout();
    }
}
