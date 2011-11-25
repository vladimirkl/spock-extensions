package com.goldin.spock.extensions.tempdir

import com.goldin.spock.extensions.BaseMethodInterceptor
import org.spockframework.runtime.model.FieldInfo
import org.spockframework.runtime.model.SpecInfo


abstract class TempDirManagingInterceptor extends BaseMethodInterceptor
{

	private final FieldInfo field
	private final File directory

	protected TempDirManagingInterceptor (FieldInfo field, File directory)
    {
		this.field = field
		this.directory = directory
	}

	protected void setupDirectory(target)
    {
		directory.mkdirs()
		target[field.name] = directory
	}

	protected void destroyDirectory()
    {
		directory.deleteDir()
	}

	abstract void install(SpecInfo spec)
}
