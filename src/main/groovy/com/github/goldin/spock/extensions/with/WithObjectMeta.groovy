package com.github.goldin.spock.extensions.with

import org.gcontracts.annotations.Ensures
import org.gcontracts.annotations.Requires


/**
 * {@code @With} annotation object meta storage.
 */
class WithObjectMeta
{
    private final Object       object
    private final List<String> methodNames
    private final List<String> propertyNames


    @Requires({ object != null })
    @Ensures({ this.object == object })
    @SuppressWarnings([ 'UnnecessaryGetter', 'GroovyGetterCallCanBePropertyAccess' ])
    WithObjectMeta( Object object )
    {
        this.object        = object
        this.methodNames   = ( object.getMetaClass().methods + object.getMetaClass().metaMethods )*.name
        this.propertyNames = object.getMetaClass().properties*.name
    }


    @Ensures({ result != null })
    Object getObject(){ this.object }


    @Requires({ methodName })
    boolean respondsToMethod( String methodName )
    {
        methodNames.grep( methodName ).size() > 0
    }


    @Requires({ propertyName })
    @SuppressWarnings([ 'UnnecessaryGetter', 'GroovyGetterCallCanBePropertyAccess' ])
    boolean hasProperty( String propertyName )
    {
        propertyNames.grep( propertyName ) || object.getMetaClass().getMetaProperty( propertyName )
    }
}
