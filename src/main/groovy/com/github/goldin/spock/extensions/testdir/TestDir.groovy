package com.github.goldin.spock.extensions.testdir

import java.lang.annotation.ElementType
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy
import java.lang.annotation.Target
import org.spockframework.runtime.extension.ExtensionAnnotation


/**
 * Extension creating a separate test directory for each test method.
 */
@Retention( RetentionPolicy.RUNTIME )
@Target( ElementType.FIELD )
@ExtensionAnnotation( TestDirExtension )
@interface TestDir {
    String  baseDir() default 'build/test'
    boolean clean()   default true
}