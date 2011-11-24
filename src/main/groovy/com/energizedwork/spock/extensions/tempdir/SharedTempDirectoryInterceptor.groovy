package com.energizedwork.spock.extensions.tempdir

import groovy.transform.InheritConstructors
import org.spockframework.runtime.extension.IMethodInvocation
import org.spockframework.runtime.model.SpecInfo


@InheritConstructors
class SharedTempDirectoryInterceptor extends DirectoryManagingInterceptor {

	@Override
	void interceptSetupSpecMethod(IMethodInvocation invocation) {
		setupDirectory(invocation.target)
		invocation.proceed()
	}

	@Override
	void interceptCleanupSpecMethod(IMethodInvocation invocation) {
		destroyDirectory()
		invocation.proceed()
	}

	@Override
	void install(SpecInfo spec) {
		spec.setupSpecMethod.addInterceptor this
		spec.cleanupSpecMethod.addInterceptor this
	}

}
