package com.goldin.spock.extensions.testdir

import org.gcontracts.annotations.Requires
import org.spockframework.runtime.extension.AbstractAnnotationDrivenExtension
import org.spockframework.runtime.model.FieldInfo
import org.spockframework.runtime.model.SpecInfo

/**
 * {@link @TestDir} extension.
 */
class TestDirExtension extends AbstractAnnotationDrivenExtension<TestDir>
{
    @Override
    @Requires({ annotation })
    void visitFieldAnnotation ( TestDir annotation, FieldInfo field )
    {
    }


    @Override
    @Requires({ spec })
    void visitSpec ( SpecInfo spec )
    {
    }
}
