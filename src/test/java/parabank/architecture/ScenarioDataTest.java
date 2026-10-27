package parabank.architecture;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.Test;
import parabank.config.ScenarioData;
import parabank.data.Customer;

class ScenarioDataTest {
  @Test
  void customerRowsPreserveZipCodesAndDoNotRewriteTheSource() throws Exception {
    Path source = Path.of("data", "tables", "customers.csv");
    String before = Files.readString(source);

    List<Customer> customers = ScenarioData.customerJourneys().toList();

    assertEquals(2, customers.size());
    assertEquals("01000", customers.get(0).zipCode());
    assertEquals(before, Files.readString(source));
  }

  @Test
  void preparingTheSameRowTwiceCreatesIndependentCredentials() {
    Customer first = ScenarioData.bankingCustomer();
    Customer second = ScenarioData.bankingCustomer();

    assertNotEquals(first.credentials().username(), second.credentials().username());
    assertEquals(20, first.credentials().username().length());
    assertFalse(first.credentials().toString().contains(first.credentials().password()));
  }

  @Test
  void rejectedAccessRowsRetainBlankCredentials() {
    var cases = ScenarioData.rejectedAccess().toList();

    assertEquals(3, cases.size());
    assertEquals("", cases.get(0).credentials().username());
    assertEquals("", cases.get(1).credentials().password());
    assertEquals("", cases.get(2).credentials().username());
    assertEquals("", cases.get(2).credentials().password());
  }
}
