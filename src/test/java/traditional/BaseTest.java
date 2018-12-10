package traditional;

import core.ExtensionContextParameterResolver;
import core.Inv;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.ExtensionContext;

import java.lang.reflect.Method;
import java.time.LocalTime;

@ExtendWith(ExtensionContextParameterResolver.class)
public class BaseTest {
    protected Inv inv;

    @BeforeEach
    public void setup() {
        inv = new Inv();
        inv.startBrowser("chrome");
    }

    @AfterEach
    public void tearDown(ExtensionContext extensionContext) {
        if (true) {
            Method testMethod = extensionContext.getRequiredTestMethod();
            Boolean testFailed = extensionContext.getExecutionException().isPresent();
            if (testFailed) {
                inv.takeScreenshot(testMethod.getDeclaringClass().getSimpleName(), testMethod.getName(), LocalTime.now());
            }
        }
        inv.quit();
    }

}
