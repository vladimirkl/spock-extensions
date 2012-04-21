package com.github.goldin.spock.extensions.time

import com.github.goldin.spock.extensions.BaseMethodInterceptor
import groovy.util.logging.Slf4j
import org.gcontracts.annotations.Ensures
import org.gcontracts.annotations.Requires
import org.spockframework.runtime.extension.IMethodInvocation


/**
 * Base class for all {@code @Time} interceptors.
 */
@Slf4j
abstract class TimeBaseInterceptor extends BaseMethodInterceptor
{
    protected final long min
    protected final long max


    @Requires({ ( min >= 0 ) && ( max > min ) })
    @Ensures({ ( this.min == min ) && ( this.max == max ) })
    protected TimeBaseInterceptor ( int min, int max )
    {
        this.min = ( long ) min
        this.max = ( long ) max
    }


    /**
     * {@code System.currentTimeMillis()} wrapper.
     * @return current system time
     */
    protected final long now() { System.currentTimeMillis() }


    /**
     * Verifies execution time specified doesn't violate limits imposed by {@code @Time} annotation.
     *
     * @param executionTime execution time in milliseconds
     * @param title         execution entity title
     */
    @Requires({ ( executionTime > -1 ) && title })
    protected final void checkTime( long executionTime, String title )
    {
        final message = "$title execution time is $executionTime ms"

        if ( executionTime < min )
        {
            throw new RuntimeException( "$message, it is less than $min ms specified as 'min'" )
        }

        if ( executionTime > max )
        {
            throw new RuntimeException( "$message, it is more than $max ms specified as 'max'" )
        }

        log.info( message )
    }


    /**
     * Intercepts invocation and applies time limits on it.
     *
     * @param invocation invocation to intercept
     * @param title      entity title (spec or feature)
     */
    @Requires({ invocation && title })
    protected final void intercept( IMethodInvocation invocation, String title )
    {
        final startTime = now()
        invocation.proceed()
        checkTime( now() - startTime, title )
    }
}
