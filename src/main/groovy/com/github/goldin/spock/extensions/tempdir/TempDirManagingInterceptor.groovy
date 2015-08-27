package com.github.goldin.spock.extensions.tempdir

import org.spockframework.runtime.extension.AbstractMethodInterceptor
import org.spockframework.runtime.model.FieldInfo
import org.spockframework.runtime.model.SpecInfo

abstract class TempDirManagingInterceptor extends AbstractMethodInterceptor
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
