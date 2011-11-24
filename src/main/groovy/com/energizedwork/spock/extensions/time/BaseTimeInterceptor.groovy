package com.energizedwork.spock.extensions.time

import groovy.util.logging.Slf4j
import org.gcontracts.annotations.Requires
import org.spockframework.runtime.extension.AbstractMethodInterceptor


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
    @Requires({ ( executionTime > 0 ) && annotation && title })
    protected final void checkTime( long executionTime, Time annotation, String title )
    {
        String message = "$title execution time is [$executionTime] ms"

        if ( executionTime < annotation.min())
        {
            throw new RuntimeException( "$message, it is less than 'min' (${ annotation.min() } ms)" )
        }

        if ( executionTime > annotation.max())
        {
            throw new RuntimeException( "$message, it is more than 'max' (${ annotation.max() } ms)" )
        }

        log.info( message )
    }
}
