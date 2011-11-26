package com.goldin.spock.extensions.with

import java.lang.annotation.ElementType
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy
import java.lang.annotation.Target
import org.spockframework.runtime.extension.ExtensionAnnotation


/**
 * Extension wrapping test method by a number of with{ .. } blocks using objects specified.
 */
@Retention( RetentionPolicy.RUNTIME )
@Target( ElementType.METHOD )
@ExtensionAnnotation( WithExtension )
@interface With {
    Class value()
}