package parabank.architecture;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import java.lang.reflect.Modifier;
import java.util.List;
import org.junit.jupiter.api.Test;
import parabank.pages.AccountsPage;
import parabank.pages.BankingPage;
import parabank.pages.LoginPage;
import parabank.pages.RegistrationPage;

class LayerRulesTest {
    private final JavaClasses classes = new ClassFileImporter().importPackages("parabank");

    @Test
    void scenariosDependOnBusinessOperationsInsteadOfTheBrowserSdk() {
        noClasses().that().resideInAPackage("..tests..")
                .should().dependOnClassesThat()
                .resideInAnyPackage("org.openqa.selenium..", "..pages..").check(classes);
    }

    @Test
    void businessOperationsDoNotLoadDataOrOwnBrowserLifecycle() {
        noClasses().that().resideInAPackage("..usecases..")
                .should().dependOnClassesThat()
                .resideInAnyPackage("..config..", "..tests..", "java.nio.file..",
                        "org.openqa.selenium.chrome..").check(classes);
    }

    @Test
    void pagesOnlyDeclareLocators() {
        for (Class<?> page : List.of(AccountsPage.class, BankingPage.class,
                LoginPage.class, RegistrationPage.class)) {
            assertTrue(page.getDeclaredMethods().length == 0, page + " contains operations");
            for (var field : page.getDeclaredFields()) {
                assertTrue(Modifier.isStatic(field.getModifiers())
                        && Modifier.isFinal(field.getModifiers()), page + " has mutable state");
            }
        }
        noClasses().that().resideInAPackage("..pages..")
                .should().dependOnClassesThat()
                .resideInAnyPackage("..usecases..", "..config..", "..tests..",
                        "org.openqa.selenium.support..").check(classes);
    }
}
