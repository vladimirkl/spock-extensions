package com.goldin.spock.extensions

import org.gcontracts.annotations.Requires
import org.spockframework.runtime.extension.AbstractMethodInterceptor
import org.spockframework.runtime.extension.IMethodInvocation
import spock.lang.Specification

/**
 * Helper base class for classes extending {@link AbstractMethodInterceptor}.
 */
abstract class BaseMethodInterceptor extends AbstractMethodInterceptor
{
   /**
    * Retrieves invocation target, i.e., {@link Specification} instance under the test.
    * @param invocation invocation intercepted
    * @return invocation target
    */
    @Requires({ invocation })
    protected final Specification getSpec( IMethodInvocation invocation )
    {
        final spec = invocation.target.with { delegate instanceof Specification ? delegate : invocation.sharedInstance }
        assert spec instanceof Specification
        spec
    }
}
