package com.energizedwork.spock.extensions.temp

import org.spockframework.runtime.extension.ExtensionAnnotation
import java.lang.annotation.*

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@ExtensionAnnotation(TempDirectoryExtension)
@interface TempDirectory {}
