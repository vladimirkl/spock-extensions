package com.goldin.spock.extensions.with

import com.goldin.spock.extensions.BaseMethodInterceptor
import org.gcontracts.annotations.Ensures
import org.gcontracts.annotations.Requires
import org.spockframework.runtime.extension.IMethodInvocation


/**
 * {@code @With} extension interceptor.
 */
class WithInterceptor extends BaseMethodInterceptor
{
    private final List<WithObjectMeta> metaObjects


    @Requires({ objects })
    @Ensures({ this.metaObjects.size() <= objects.size() })
    WithInterceptor ( List<Object> objects )
    {
        this.metaObjects = objects.findAll{ it != null }.collect { new WithObjectMeta( it ) }
    }


    @Override
    @SuppressWarnings([ 'UnnecessaryReturnKeyword', 'GroovyReturnFromClosureCanBeImplicit' ])
    void interceptFeatureExecution ( IMethodInvocation invocation )
    {
        def specMeta = getSpec( invocation ).class.metaClass

        /**
         * Before every feature invocation both "methodMissing" and "propertyMissing" are redefined
         * to work with objects specific to this feature.
         */

        specMeta.methodMissing = {
            String methodName, Object args ->

            assert methodName

            /**
             * Postponing null validation to execution phase so that this failure can be checked with @FailsWith
             */
            assert this.metaObjects, 'Only null objects specified to @With'

            Object  result    = null
            boolean hasResult = false

            metaObjects.find { it.respondsToMethod( methodName )}?.object?.with {
                result    = ( args ? delegate."$methodName"( *args ) : delegate."$methodName"())
                hasResult = true
            }

            assert hasResult, "No @With object responds to method [$methodName]"
            result
        }

        specMeta.propertyMissing = {
            String propertyName ->

            assert propertyName

            /**
             * Postponing null validation to execution phase so that this failure can be checked with @FailsWith
             */
            assert this.metaObjects, 'Only null objects specified to @With'

            Object  result    = null
            boolean hasResult = false

            metaObjects.find { it.hasProperty( propertyName ) }?.object?.with {
                result    = delegate."$propertyName"
                hasResult = true
            }

            assert hasResult, "No @With object has property [$propertyName]"
            result
        }

        invocation.proceed()
    }
}
