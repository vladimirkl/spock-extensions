package com.energizedwork.spock.extensions.temp

import org.spockframework.runtime.extension.*
import org.spockframework.runtime.model.*


class TempDirectoryExtension extends AbstractAnnotationDrivenExtension<TempDirectory> {

	private static final File TEMP_DIR = new File(System.getProperty( 'java.io.tmpdir' ))

	@Override
    @SuppressWarnings( 'UnnecessaryGetter' )
	void visitFieldAnnotation(TempDirectory annotation, FieldInfo field) {
		def tempDirectory = new File(TEMP_DIR, generateFilename(field.name))

		def interceptor
		if (field.isShared()) {
			interceptor = new SharedTempDirectoryInterceptor(field, tempDirectory)
		} else {
			interceptor = new TempDirectoryInterceptor(field, tempDirectory)
		}

		interceptor.install(field.parent.getTopSpec())
	}

	private String generateFilename(String baseName) {
		"$baseName-${Long.toHexString(System.currentTimeMillis())}"
	}

}

