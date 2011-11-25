package com.goldin.spock.extensions.time

import org.gcontracts.annotations.Ensures
import org.gcontracts.annotations.Requires
import org.spockframework.runtime.extension.IMethodInvocation


/**
 * {@link Time} extension interceptor for feature (test method).
 */
class FeatureTimeInterceptor extends BaseTimeInterceptor
{
    private final Time annotation


    @Requires({ annotation })
    @Ensures({ this.annotation == annotation })
    FeatureTimeInterceptor( Time annotation )
    {
        this.annotation = annotation
    }


    @Override
    void interceptFeatureExecution ( IMethodInvocation invocation )
    {
        intercept( invocation, annotation, "Feature [${ invocation.feature.name }]" )
    }
}
