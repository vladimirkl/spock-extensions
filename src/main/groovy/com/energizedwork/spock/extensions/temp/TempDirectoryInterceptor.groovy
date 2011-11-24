package com.energizedwork.spock.extensions.temp

import groovy.transform.InheritConstructors
import org.spockframework.runtime.extension.IMethodInvocation
import org.spockframework.runtime.model.SpecInfo


@InheritConstructors
class TempDirectoryInterceptor extends DirectoryManagingInterceptor {

	@Override
	void interceptSetupMethod(IMethodInvocation invocation) {
		setupDirectory(invocation.target)
		invocation.proceed()
	}

	@Override
	void interceptCleanupMethod(IMethodInvocation invocation) {
		destroyDirectory()
		invocation.proceed()
	}

	@Override
	void install(SpecInfo spec) {
		spec.setupMethod.addInterceptor this
		spec.cleanupMethod.addInterceptor this
	}

}
