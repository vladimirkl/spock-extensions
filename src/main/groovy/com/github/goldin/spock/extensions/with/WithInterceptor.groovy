package com.github.goldin.spock.extensions.with

import com.github.goldin.spock.extensions.BaseMethodInterceptor
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

            for ( object in metaObjects.findAll { it.respondsToMethod( methodName )}*.object )
            {   // noinspection GroovyEmptyCatchBlock
                try   { return ( args ? object."$methodName"( *args ) : object."$methodName"()) }
                catch ( ignored ) {}
            }

            throw new RuntimeException( "No @With object responds to method [$methodName]" )
        }

        specMeta.propertyMissing = {
            String propertyName ->

            assert propertyName

            /**
             * Postponing null validation to execution phase so that this failure can be checked with @FailsWith
             */
            assert this.metaObjects, 'Only null objects specified to @With'

            for ( object in metaObjects.findAll { it.hasProperty( propertyName ) }*.object )
            {   // noinspection GroovyEmptyCatchBlock
                try   { return object."$propertyName" }
                catch ( ignored ){}
            }

            throw new RuntimeException( "No @With object has property [$propertyName]" )
        }

        invocation.proceed()
    }
}
