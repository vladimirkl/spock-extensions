package com.github.goldin.spock.extensions.tempdir

import org.spockframework.runtime.extension.ExtensionAnnotation
import java.lang.annotation.*

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@ExtensionAnnotation(TempDirExtension)
@interface TempDir {}
