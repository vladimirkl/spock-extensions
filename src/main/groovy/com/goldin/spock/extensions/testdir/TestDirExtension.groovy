package com.goldin.spock.extensions.testdir

import org.gcontracts.annotations.Requires
import org.spockframework.runtime.extension.AbstractAnnotationDrivenExtension
import org.spockframework.runtime.model.FieldInfo


/**
 * {@link @TestDir} extension.
 */
class TestDirExtension extends AbstractAnnotationDrivenExtension<TestDir>
{
    @Override
    @Requires({ annotation && field })
    @SuppressWarnings([ 'UnnecessaryGetter', 'GroovyGetterCallCanBePropertyAccess' ])
    void visitFieldAnnotation ( TestDir annotation, FieldInfo field )
    {
        final interceptor = new TestDirInterceptor( annotation.baseDir(), annotation.clean(), field.name )
        field.parent.getTopSpec().setupMethod.addInterceptor( interceptor )
    }
}
