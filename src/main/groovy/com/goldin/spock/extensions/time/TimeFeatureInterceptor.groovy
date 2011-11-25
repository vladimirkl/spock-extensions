package com.goldin.spock.extensions.time

import groovy.transform.InheritConstructors
import org.spockframework.runtime.extension.IMethodInvocation


/**
 * {@link Time} extension interceptor for feature (test method).
 */
@InheritConstructors
class TimeFeatureInterceptor extends TimeBaseInterceptor
{
    @Override
    void interceptFeatureExecution ( IMethodInvocation invocation )
    {
        intercept( invocation, annotation, "Feature [${ invocation.feature.name }]" )
    }
}
