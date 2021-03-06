package com.github.goldin.spock.extensions.time

import org.gcontracts.annotations.Requires
import org.spockframework.runtime.extension.AbstractAnnotationDrivenExtension
import org.spockframework.runtime.model.FeatureInfo
import org.spockframework.runtime.model.SpecInfo


/**
 * {@link Time} extension.
 */
class TimeExtension extends AbstractAnnotationDrivenExtension<Time>
{
    @SuppressWarnings( 'StatelessClass' )
    private       Time              specAnnotation     = null // Spec @Time annotation instance
    private final Map<String, Time> featureAnnotations = [:]  // Mapping of features annotated to their @Time annotation

    @Requires({ annotation })
    private Time check( Time annotation )
    {
        assert ( annotation.min() >= 0 ) && ( annotation.max() > annotation.min())
        annotation
    }


    @Override
    @Requires({ annotation })
    void visitSpecAnnotation ( Time annotation, SpecInfo spec )
    {
        specAnnotation = check( annotation )
    }


    @Override
    @Requires({ annotation && feature })
    void visitFeatureAnnotation ( Time annotation, FeatureInfo feature )
    {
        featureAnnotations[ feature.name ] = check( annotation )
    }


    @Override
    void visitSpec ( SpecInfo spec )
    {
        if ( specAnnotation )
        {
            spec.addInterceptor( new TimeSpecInterceptor( specAnnotation.min(), specAnnotation.max()))
        }

        for ( featureInfo in spec.features )
        {
            final annotation = featureAnnotations[ featureInfo.name ]
            if  ( annotation )
            {
                featureInfo.addInterceptor( new TimeFeatureInterceptor( annotation.min(), annotation.max()))
            }
        }
    }
}
