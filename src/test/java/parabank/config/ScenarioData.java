package parabank.config;

import java.io.IOException;
import java.io.Reader;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import parabank.data.Credentials;
import parabank.data.Customer;
import parabank.data.RejectedAccess;

public final class ScenarioData {
  private ScenarioData() {}

  private static List<CSVRecord> rows(String table) {
    try (Reader input =
        Files.newBufferedReader(Path.of("data", "tables", table), StandardCharsets.UTF_8)) {
      return CSVFormat.DEFAULT
          .builder()
          .setHeader()
          .setSkipHeaderRecord(true)
          .build()
          .parse(input)
          .getRecords();
    } catch (IOException error) {
      throw new IllegalStateException("Cannot load scenario data: " + table, error);
    }
  }

  public static Stream<Customer> customerJourneys() {
    return rows("customers.csv").stream().map(ScenarioData::customer);
  }

  public static Customer bankingCustomer() {
    return customer(rows("customers.csv").get(0));
  }

  public static Stream<RejectedAccess> rejectedAccess() {
    return rows("access_cases.csv").stream()
        .map(
            row ->
                new RejectedAccess(
                    row.get("scenario"),
                    new Credentials(row.get("username"), row.get("password")),
                    row.get("expected_message")));
  }

  public static BigDecimal transferAmount() {
    return new BigDecimal(rows("transfers.csv").get(0).get("amount"));
  }

  private static Customer customer(CSVRecord row) {
    String username = "qa" + UUID.randomUUID().toString().replace("-", "").substring(0, 18);
    return new Customer(
        row.get("scenario"),
        row.get("first_name"),
        row.get("last_name"),
        row.get("street"),
        row.get("city"),
        row.get("state"),
        row.get("zip_code"),
        row.get("phone"),
        row.get("ssn"),
        new Credentials(username, row.get("password")));
  }
}
