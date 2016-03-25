package fr.xebia.task;

import org.junit.gen5.api.extension.*;

import java.lang.reflect.Method;

import static org.junit.gen5.api.extension.ExtensionPointRegistry.Position.INNERMOST;

/**
 * Simple extension that <em>times</em> the execution of test methods and
 * prints the results to {@link System#out}.
 *
 * @since 5.0
 */
public class TimedExtension implements ExtensionRegistrar {

    @Override
    public void registerExtensions(ExtensionPointRegistry registry) {
        registry.register(new TestMethodInvocationWrapper(), INNERMOST);
    }

    private static class TestMethodInvocationWrapper implements BeforeEachExtensionPoint, AfterEachExtensionPoint {

        private final ExtensionContext.Namespace namespace = ExtensionContext.Namespace.of(getClass());

        @Override
        public void beforeEach(TestExtensionContext context) throws Exception {
            ExtensionContext.Store times = context.getStore(getNamespace(context));
            times.put(context.getTestMethod(), System.currentTimeMillis());
        }

        private ExtensionContext.Namespace getNamespace(TestExtensionContext context) {
            return ExtensionContext.Namespace.of(getClass(), context);
        }

        @Override
        public void afterEach(TestExtensionContext context) throws Exception {
            ExtensionContext.Store times = context.getStore(getNamespace(context));
            Method testMethod = context.getTestMethod();
            long start = (long) times.remove(testMethod);
            long duration = System.currentTimeMillis() - start;

            System.out.println(String.format("Method [%s] took %s ms.", testMethod, duration));
        }

    }

}
