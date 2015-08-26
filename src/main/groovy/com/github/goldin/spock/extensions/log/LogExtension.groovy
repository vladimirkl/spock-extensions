package com.github.goldin.spock.extensions.log
import org.spockframework.runtime.extension.AbstractGlobalExtension
import org.spockframework.runtime.model.SpecInfo
/**
 * Global extension logging all testing events.
 */
class LogExtension extends AbstractGlobalExtension
{
    void visitSpec ( SpecInfo spec )
    {
        final logger = new Logger()
        spec.addListener   ( new LogListener   ( logger: logger ))
        spec.addInterceptor( new LogInterceptor( logger: logger ))
    }
}
