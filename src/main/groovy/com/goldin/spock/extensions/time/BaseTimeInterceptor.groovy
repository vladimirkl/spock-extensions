package com.goldin.spock.extensions.time

import groovy.util.logging.Slf4j
import org.gcontracts.annotations.Requires
import org.spockframework.runtime.extension.AbstractMethodInterceptor
import org.spockframework.runtime.extension.IMethodInvocation

/**
 * Base class for all {@code @Time} interceptors.
 */
@Slf4j
abstract class BaseTimeInterceptor extends AbstractMethodInterceptor
{
    /**
     * {@code System.currentTimeMillis()} wrapper.
     * @return current system time
     */
    protected final long now() { System.currentTimeMillis() }


    /**
     * Verifies execution time specified doesn't violate limits imposed by {@code @Time} annotation.
     *
     * @param executionTime execution time in milliseconds
     * @param annotation    annotation instance
     * @param title         execution entity title
     */
    @Requires({ ( executionTime > -1 ) && annotation && title })
    protected final void checkTime( long executionTime, Time annotation, String title )
    {
        String message = "$title execution time is $executionTime ms"

        if ( executionTime < ( long ) annotation.min())
        {
            throw new RuntimeException( "$message, it is less than 'min' specified (${ annotation.min() } ms)" )
        }

        if ( executionTime > ( long ) annotation.max())
        {
            throw new RuntimeException( "$message, it is more than 'max' specified (${ annotation.max() } ms)" )
        }

        log.info( message )
    }


    /**
     * Intercepts invocation and applies annotation execution time validations.
     *
     * @param invocation invocation to intercept
     * @param annotation time annotation
     * @param title      entity title (spec or feature)
     */
    @Requires({ invocation && annotation && title })
    protected final void intercept( IMethodInvocation invocation, Time annotation, String title )
    {
        def startTime = now()
        invocation.proceed()
        checkTime( now() - startTime, annotation, title )
    }
}
