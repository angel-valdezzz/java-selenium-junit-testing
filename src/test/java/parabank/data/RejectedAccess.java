package parabank.data;

public record RejectedAccess(String scenario, Credentials credentials, String expectedMessage) {
  @Override
  public String toString() {
    return scenario;
  }
}
