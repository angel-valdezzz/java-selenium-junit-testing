package parabank.data;

/** An execution row: kept separate from selectors, browser state and source CSV files. */
public record Customer(
    String scenario,
    String firstName,
    String lastName,
    String street,
    String city,
    String state,
    String zipCode,
    String phone,
    String ssn,
    Credentials credentials) {
  public String fullName() {
    return firstName + " " + lastName;
  }

  @Override
  public String toString() {
    return scenario;
  }
}
