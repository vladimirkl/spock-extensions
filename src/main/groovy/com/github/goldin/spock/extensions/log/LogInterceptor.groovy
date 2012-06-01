package com.github.goldin.spock.extensions.log

import org.spockframework.runtime.extension.IMethodInterceptor
import org.spockframework.runtime.extension.IMethodInvocation


/**
 * Interceptor logging invocation intercepted.
 */
class LogInterceptor implements IMethodInterceptor
{
    Logger logger

    void intercept ( IMethodInvocation invocation )
    {
        logger.log( "${ this.class.simpleName }.intercept - before: [${ invocation.method.description }]" )
        invocation.proceed()
        logger.log( "${ this.class.simpleName }.intercept - after : [${ invocation.method.description }]" )
    }
}
