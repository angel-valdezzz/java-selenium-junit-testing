# Automation architecture

Use robotframework-selenium-testing as the web architecture reference.
Tests compose business use cases and configuration; they never access Selenium or Pages.
Pages contain declarative locators only. Use cases own browser interactions and business assertions,
receive typed data, and never read files or construct browser instances.
Config owns browser lifecycle, composition and loading data/tables. Preserve leading zeroes.
Keep native Maven/JUnit execution, pinned dependencies and the verified Maven Wrapper.
Run the quality profile and all eight real ParaBank cases before declaring the pilot validated.
Never infer architectural acceptance just because execution passed.
