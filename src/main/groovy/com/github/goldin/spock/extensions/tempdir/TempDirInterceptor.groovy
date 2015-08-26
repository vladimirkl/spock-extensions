package com.github.goldin.spock.extensions.tempdir

import groovy.transform.InheritConstructors
import org.spockframework.runtime.extension.IMethodInvocation
import org.spockframework.runtime.model.SpecInfo


@InheritConstructors
class TempDirInterceptor extends TempDirManagingInterceptor {

    @Override
    void interceptSetupMethod(IMethodInvocation invocation) {
        setupDirectory(invocation.instance)
        invocation.proceed()
    }

    @Override
    void interceptCleanupMethod(IMethodInvocation invocation) {
        destroyDirectory()
        invocation.proceed()
    }

    @Override
    void install(SpecInfo spec) {
        spec.addSetupInterceptor this
        spec.addCleanupInterceptor this
    }
}
