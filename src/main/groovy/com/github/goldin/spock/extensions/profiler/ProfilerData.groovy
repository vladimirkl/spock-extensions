package com.github.goldin.spock.extensions.profiler

import org.gcontracts.annotations.Ensures
import org.gcontracts.annotations.Requires
import org.spockframework.runtime.model.FeatureInfo


/**
 * {@link ProfilerExtension} data measured.
 */
class ProfilerData implements Comparable<ProfilerData>
{
    final String key
    final long   executionStarted = System.currentTimeMillis()
          long   executionTime    = -1


    @Requires({ feature })
    @Ensures({ key })
    ProfilerData ( FeatureInfo feature )
    {
        this.key = "${feature.description.className}.${feature.description.methodName}"
    }

    void executionEnded() { executionTime = System.currentTimeMillis() - executionStarted }

    @Override
    int hashCode (){ key.hashCode() }

    @Override
    boolean equals ( Object o ){ ( o == this ) || (( o instanceof ProfilerData ) && ( o.key.equals( this.key )))}

    @Override
    String toString (){ "[${ this.key }]-[${ this.executionTime }]" }

    @Override
    int compareTo ( ProfilerData o ){ o.executionTime <=> this.executionTime }
}
