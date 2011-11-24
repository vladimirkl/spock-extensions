package com.goldin.spock.extensions.time

import org.gcontracts.annotations.Ensures
import org.gcontracts.annotations.Requires
import org.spockframework.runtime.extension.IMethodInvocation

class FeatureTimeInterceptor extends BaseTimeInterceptor
{
    private final Map<String, Time> annotations


    @Requires({ annotations })
    @Ensures({ this.annotations == annotations })
    FeatureTimeInterceptor( Map<String, Time> annotations )
    {
        this.annotations = annotations.asImmutable()
    }


    @Override
    @Requires({ invocation })
    void interceptFeatureExecution ( IMethodInvocation invocation )
    {
        def name      = invocation.feature.name
        def startTime = -1

        if ( annotations[ name ] )
        {
            startTime = now()
        }

        invocation.proceed()

        if ( annotations[ name ] )
        {
            assert startTime > 0
            checkTime( now() - startTime, annotations[ name ], "Feature [$name]" )
        }
    }
}
