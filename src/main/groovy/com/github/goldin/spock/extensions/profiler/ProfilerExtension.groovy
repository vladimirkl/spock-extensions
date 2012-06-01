package com.github.goldin.spock.extensions.profiler

import org.gcontracts.annotations.Requires
import org.spockframework.runtime.IRunListener
import org.spockframework.runtime.extension.IGlobalExtension
import org.spockframework.runtime.model.SpecInfo


/**
 * Global extension profiling tests execution.
 */
class ProfilerExtension implements IGlobalExtension
{
    private final Set<ProfilerData> data     = []
    private final IRunListener      listener = new ProfilerListener( data )


    @Requires({ spec })
    void visitSpec ( SpecInfo spec )
    {
        spec.addListener( listener )
    }
}
