package parabank.tests;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import parabank.config.BrowserSession;
import parabank.data.RejectedAccess;
import parabank.usecases.CustomerAccess;

@Tag("e2e")
@ExtendWith(BrowserSession.class)
class RejectedAccessTest {
    @ParameterizedTest(name = "{0}")
    @MethodSource("parabank.config.ScenarioData#rejectedAccess")
    void incompleteCredentialsAreRejected(RejectedAccess scenario, CustomerAccess access) {
        access.reject(scenario);
    }
}
