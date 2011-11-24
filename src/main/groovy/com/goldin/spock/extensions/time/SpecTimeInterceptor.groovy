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
    @Requires({ invocation })
    void interceptSpecExecution ( IMethodInvocation invocation )
    {
        def name      = invocation.spec.name
        def startTime = now()
        invocation.proceed()
        checkTime( now() - startTime, annotation, "Spec [$name]" )
    }
}
