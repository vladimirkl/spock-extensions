package com.github.goldin.spock.extensions.log


import org.spockframework.runtime.IRunListener
import org.spockframework.runtime.model.ErrorInfo
import org.spockframework.runtime.model.FeatureInfo
import org.spockframework.runtime.model.IterationInfo
import org.spockframework.runtime.model.SpecInfo


/**
 * Listener logging all events.
 */
class LogListener implements IRunListener
{
    Logger logger

    private void log ( String message, Object o )
    {
        logger.log( "${ this.class.simpleName }.${ message }: [${ o }]" )
    }

    void beforeSpec      ( SpecInfo      spec      ){ log( 'beforeSpec',      spec.name       )}
    void beforeFeature   ( FeatureInfo   feature   ){ log( 'beforeFeature',   feature.name    )}
    void beforeIteration ( IterationInfo iteration ){ log( 'beforeIteration', iteration.name  )}
    void afterIteration  ( IterationInfo iteration ){ log( 'afterIteration',  iteration.name  )}
    void afterFeature    ( FeatureInfo   feature   ){ log( 'afterFeature',    feature.name    )}
    void afterSpec       ( SpecInfo      spec      ){ log( 'afterSpec',       spec.name       )}
    void error           ( ErrorInfo     error     ){ log( 'error',           error.exception )}
    void specSkipped     ( SpecInfo      spec      ){ log( 'specSkipped',     spec.name       )}
    void featureSkipped  ( FeatureInfo   feature   ){ log( 'featureSkipped',  feature.name    )}
}
