package com.github.goldin.spock.extensions.profiler

import org.gcontracts.annotations.Requires
import org.spockframework.runtime.AbstractRunListener
import org.spockframework.runtime.extension.AbstractGlobalExtension
import org.spockframework.runtime.extension.IGlobalExtension
import org.spockframework.runtime.extension.IMethodInterceptor
import org.spockframework.runtime.extension.IMethodInvocation
import org.spockframework.runtime.model.SpecInfo

import java.util.concurrent.ConcurrentLinkedQueue
import org.spockframework.runtime.model.ErrorInfo


/**
 * Global extension profiling features execution time.
 */
class ProfilerExtension extends AbstractGlobalExtension
{
    private final Collection<NodeData> data = new ConcurrentLinkedQueue<NodeData>()

    /**
     * Writes {@link #data} to the file.
     */
    private void writeFile ()
    {
        final padSize = data*.description()*.size().max()

        new File( 'profiler.txt' ).write(
            data.sort().
                 collect { "${ it.description().padRight( padSize ) } : ${ it.executionTime } ms" }.
                 join( '\n' ), 'UTF-8' )
    }


    @Requires({ specInfo })
    void visitSpec ( SpecInfo specInfo )
    {
        /**
         * Feature interceptors updating {@link #data} with execution time.
         */
        specInfo.features*.addInterceptor({
            IMethodInvocation invocation ->

            final long t = System.currentTimeMillis()

            invocation.proceed()

            data.add( new NodeData( method        : invocation.feature,
                                    executionTime : System.currentTimeMillis() - t ))
        } as IMethodInterceptor )

        /**
         * Spec listener writing the {@link #data} to the file.
         */
        specInfo.addListener( new AbstractRunListener(){

            @Override
            void afterSpec ( SpecInfo  spec  ){ writeFile() }

            @Override
            void error     ( ErrorInfo error ){ writeFile() }
        })
    }
}
