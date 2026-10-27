# Java · Selenium · JUnit — ParaBank

[Español](README.es.md)

Independent browser automation following the established
[Robot/SeleniumLibrary architecture](https://github.com/angel-valdezzz/robotframework-selenium-testing/blob/main/docs/architecture.md):
**business scenarios → use cases → declarative page locators**, with configuration and tabular data kept separate.

## Install and run

JDK 17+ (CI: JDK 21), Chrome and network access for Maven/Selenium Manager.
Maven Wrapper pins and checks the Maven distribution; no Python launcher is required.

```powershell
.\mvnw.cmd -B verify
.\mvnw.cmd -B -Pquality spotless:check verify
.\mvnw.cmd -B -Dparabank.url=http://127.0.0.1:8080/parabank/ verify
```

Linux/macOS: use `./mvnw` for the same commands.
Public ParaBank is the default: https://parabank.parasoft.com/parabank/.
For visible Chrome add `-Dbrowser.headless=false`. Timeout: `-Dbrowser.timeout=25`.
Formatting: `./mvnw spotless:apply`.

## Structure

- `src/test/java/parabank/tests`: complete business journeys.
- `usecases`: customer registration, access, accounts and banking.
- `pages`: locators only; no browser or business operations.
- `config`: per-case browser lifecycle, dependency composition and CSV loading.
- `data` package: typed inputs; `data/tables`: explicit scenario inputs and expected results.
- `architecture`: executable layer restrictions.

Eight E2E cases cover registration, identity/account access, rejected incomplete credentials,
savings and transfers with exact balance changes and visible movements.
Fresh browser and unique fictional customer data per case. The source CSV is never rewritten.
JUnit results: `target/surefire-reports`; failure screenshots: `target/evidence`.

See [architecture](docs/architecture.md), [scenario matrix](docs/scenarios.md)
and [validation status](VALIDATION.md). Native test runners and Maven own execution.
No claim of architectural acceptance is inferred merely from an earlier green CI run.
