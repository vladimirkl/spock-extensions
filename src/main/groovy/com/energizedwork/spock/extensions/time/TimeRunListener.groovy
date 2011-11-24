package com.energizedwork.spock.extensions.time

import org.gcontracts.annotations.Ensures
import org.gcontracts.annotations.Requires
import org.spockframework.runtime.AbstractRunListener
import org.spockframework.runtime.model.FeatureInfo
import org.spockframework.runtime.model.SpecInfo
import groovy.util.logging.Slf4j


/**
 * {@link Time} extension listener.
 */
@Slf4j
class TimeRunListener extends AbstractRunListener
{
    @SuppressWarnings( 'StatelessClass' )
    private        long              specStartTime
    private final  Time              specAnnotation
    private final  Map<String, Time> featureAnnotations
    private final  Map<String, Long> featureStartTimes = [:].withDefault { null }

    private long now() { System.currentTimeMillis() }

    @Requires({ featureAnnotations     != null })
    @Ensures({ this.featureAnnotations != null })
    TimeRunListener( Time specAnnotation, Map<String, Time> featureAnnotations )
    {
        this.specAnnotation     = specAnnotation // Allowed to be null
        this.featureAnnotations = featureAnnotations
    }


    /**
     * Checks execution time specified doesn't violate limits imposed by {@code @Time} annotation.
     *
     * @param executionTime execution time in milliseconds
     * @param annotation    annotation instance
     * @param title         execution entity title
     */
    @Requires({ ( executionTime > 0 ) && annotation })
    private void checkTime( long executionTime, Time annotation, String title )
    {
        String message        = "$title execution time ($executionTime ms)"
        assert executionTime >= annotation.min(), "$message is less than 'min' (${ annotation.min() } ms)"
        assert executionTime <= annotation.max(), "$message is more than 'max' (${ annotation.max() } ms)"

        log.info( message )
    }


    @Override
    @Requires({ spec })
    void beforeSpec ( SpecInfo spec )
    {
        if ( specAnnotation )
        {
            specStartTime = now()
        }
    }


    @Override
    @Requires({ spec })
    void afterSpec ( SpecInfo spec )
    {
        if ( specAnnotation )
        {
            checkTime( now() - specStartTime, specAnnotation, "Spec [$spec.name]" )
        }
    }


    @Override
    @Requires({ feature })
    void beforeFeature ( FeatureInfo feature )
    {
        if ( featureAnnotations[ feature.name ] )
        {
            featureStartTimes[ feature.name ] = now()
        }
    }


    @Override
    @Requires({ feature })
    void afterFeature ( FeatureInfo feature )
    {
        if ( featureAnnotations[ feature.name ] )
        {
            checkTime( now() - featureStartTimes[ feature.name ], featureAnnotations[ feature.name ], "Feature [$feature.name]" )
        }
    }
}
