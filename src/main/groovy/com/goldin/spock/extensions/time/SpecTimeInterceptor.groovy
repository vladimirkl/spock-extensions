package com.goldin.spock.extensions.time

import org.gcontracts.annotations.Ensures
import org.gcontracts.annotations.Requires
import org.spockframework.runtime.extension.IMethodInvocation


/**
 * {@link Time} extension interceptor for Spec.
 */
class SpecTimeInterceptor extends BaseTimeInterceptor
{
    private final Time annotation


    @Requires({ annotation })
    @Ensures({ this.annotation == annotation })
    SpecTimeInterceptor ( Time annotation )
    {
        this.annotation = annotation
    }


    @Override
    void interceptSpecExecution ( IMethodInvocation invocation )
    {
        intercept( invocation, annotation, "Spec [${ invocation.spec.name }]" )
    }
}
