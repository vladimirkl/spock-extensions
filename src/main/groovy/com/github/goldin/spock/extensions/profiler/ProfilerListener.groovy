package com.github.goldin.spock.extensions.profiler

import org.gcontracts.annotations.Requires
import org.spockframework.runtime.AbstractRunListener
import org.spockframework.runtime.model.FeatureInfo
import org.spockframework.runtime.model.SpecInfo


/**
 * {@link ProfilerExtension} listener.
 */
class ProfilerListener extends AbstractRunListener
{
    private final Set<ProfilerData> data

    @Requires({ data != null })
    ProfilerListener ( Set<ProfilerData> data )
    {
        this.data = data
    }

    @Override
    void beforeFeature ( FeatureInfo feature )
    {
        assert data.add( new ProfilerData( feature ))
    }


    @Override
    void afterFeature ( FeatureInfo feature )
    {
        final profilerData = new ProfilerData( feature )
        data.find { it == profilerData }.executionEnded()
    }


    @Override
    void afterSpec ( SpecInfo spec )
    {
        final list = data.toList()
        Collections.sort( list )

        final longestNameSize = list*.key*.size().max()
        new File( 'profiler.txt' ).write(
            list.collect { "${ it.key.padRight( longestNameSize ) } : ${ it.executionTime } ms" }.
                 join( '\n' ))
    }
}
