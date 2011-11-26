package com.goldin.spock.extensions.with

import org.gcontracts.annotations.Requires
import org.spockframework.runtime.extension.AbstractAnnotationDrivenExtension
import org.spockframework.runtime.model.FeatureInfo
import org.spockframework.runtime.model.SpecInfo


/**
 * {@code @With} extension.
 */
class WithExtension extends AbstractAnnotationDrivenExtension<With>
{
    private final Map<String, List<Object>> featureObjects = [:]

    @Override
    @Requires({ annotation && feature })
    void visitFeatureAnnotation ( With annotation, FeatureInfo feature )
    {
        Closure closure = ( Closure ) annotation.value().newInstance( '1', '1' ) // Dummy values, see http://goo.gl/mR3r4
        Object  objects = closure()

        if ( objects )
        {
            featureObjects[ feature.name ] = ( objects instanceof List ? objects : [ objects ] )
        }
    }


    @Override
    @Requires({ spec })
    void visitSpec ( SpecInfo spec )
    {
        featureObjects.each {
            String featureName, List<Object> objects ->
            spec.features.find { it.name == featureName }?.addInterceptor( new WithInterceptor( objects ))
        }
    }
}
