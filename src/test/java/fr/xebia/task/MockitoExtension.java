package fr.xebia.task;

import org.junit.gen5.api.extension.*;
import org.junit.gen5.api.extension.ExtensionContext.Namespace;
import org.junit.gen5.api.extension.ExtensionContext.Store;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.lang.reflect.Parameter;

import static org.mockito.Mockito.mock;

/**
 * {@code MockitoExtension} showcases the {@link InstancePostProcessor}
 * and {@link MethodParameterResolver} extension points of JUnit 5 by
 * providing dependency injection support at the field level via Mockito's
 * {@link Mock @Mock} annotation and at the method level via our demo
 * {@link InjectMock @InjectMock} annotation.
 *
 * @since 5.0
 */
public class MockitoExtension implements InstancePostProcessor, MethodParameterResolver {

    private static final Namespace namespace = Namespace.of(MockitoExtension.class);

    @Override
    public void postProcessTestInstance(TestExtensionContext context) {
        MockitoAnnotations.initMocks(context.getTestInstance());
    }

    @Override
    public boolean supports(Parameter parameter, MethodInvocationContext methodInvocationContext,
                            ExtensionContext extensionContext) {

        return parameter.isAnnotationPresent(InjectMock.class);
    }

    @Override
    public Object resolve(Parameter parameter, MethodInvocationContext methodInvocationContext,
                          ExtensionContext extensionContext) throws ParameterResolutionException {
        Store mocks = extensionContext.getStore(namespace);
        return getMock(parameter.getType(), mocks);
    }

    private Object getMock(Class<?> mockType, Store mocks) {
        return mocks.getOrComputeIfAbsent(mockType, type -> mock(mockType));
    }

}