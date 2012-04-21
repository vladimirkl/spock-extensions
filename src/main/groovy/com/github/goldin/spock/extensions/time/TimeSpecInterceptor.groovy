package com.github.goldin.spock.extensions.time

import groovy.transform.InheritConstructors
import org.spockframework.runtime.extension.IMethodInvocation


/**
 * {@link Time} extension interceptor for Spec.
 */
@InheritConstructors
class TimeSpecInterceptor extends TimeBaseInterceptor
{
    @Override
    void interceptSpecExecution ( IMethodInvocation invocation )
    {
        intercept( invocation, "Spec [${ invocation.spec.name }]" )
    }
}
