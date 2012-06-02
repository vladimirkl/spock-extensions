package com.github.goldin.spock.extensions.profiler

import org.gcontracts.annotations.Requires
import org.spockframework.runtime.AbstractRunListener
import org.spockframework.runtime.extension.IGlobalExtension
import org.spockframework.runtime.extension.IMethodInterceptor
import org.spockframework.runtime.extension.IMethodInvocation
import org.spockframework.runtime.model.SpecInfo

import java.util.concurrent.ConcurrentLinkedQueue
import org.spockframework.runtime.model.ErrorInfo

/**
 * Global extension profiling features execution time.
 */
class ProfilerExtension implements IGlobalExtension
{
    private final Collection<NodeData> data = new ConcurrentLinkedQueue<NodeData>()

    @Requires({ specInfo })
    void visitSpec ( SpecInfo specInfo )
    {
        specInfo.features*.addInterceptor({
            IMethodInvocation invocation ->

            final long t = System.currentTimeMillis()
            invocation.proceed()

            data.add( new NodeData( method        : invocation.feature,
                                    executionTime : System.currentTimeMillis() - t ))
        } as IMethodInterceptor )

        final writeFile = {
            final padSize = data*.description()*.size().max()

            new File( 'profiler.txt' ).write(
                    data.sort().
                         collect { "${ it.description().padRight( padSize ) } : ${ it.executionTime } ms" }.
                         join( '\n' ), 'UTF-8' )
        }

        specInfo.addListener( new AbstractRunListener(){

            @Override
            void afterSpec ( SpecInfo  spec  ){ writeFile() }

            @Override
            void error     ( ErrorInfo error ){ writeFile() }
        })
    }
}
