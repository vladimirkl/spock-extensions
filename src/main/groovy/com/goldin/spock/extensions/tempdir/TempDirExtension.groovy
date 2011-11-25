package com.goldin.spock.extensions.tempdir

import org.spockframework.runtime.extension.*
import org.spockframework.runtime.model.*


class TempDirExtension extends AbstractAnnotationDrivenExtension<TempDir>
{

	private static final File TEMP_DIR = new File(System.getProperty( 'java.io.tmpdir' ))

	@Override
    @SuppressWarnings([ 'UnnecessaryGetter', 'GroovyGetterCallCanBePropertyAccess' ])
	void visitFieldAnnotation(TempDir annotation, FieldInfo field)
    {
		def tempDirectory = new File(TEMP_DIR, generateFilename(field.name))

		def interceptor
		if ( field.shared ) {
			interceptor = new TempDirSharedInterceptor(field, tempDirectory)
		} else {
			interceptor = new TempDirInterceptor(field, tempDirectory)
		}

		interceptor.install(field.parent.getTopSpec())
	}


	private String generateFilename(String baseName)
    {
		"$baseName-${Long.toHexString(System.currentTimeMillis())}"
	}
}

