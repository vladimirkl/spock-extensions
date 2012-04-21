package com.github.goldin.spock.extensions

import com.github.goldin.spock.extensions.time.Time
import spock.lang.Specification

/**
 * {@code @Time} extension test spec.
 */
@Time( min = 100, max = 300 )
class TimeSpec extends Specification
{

    @Time( min = 100, max = 200 )
    def 'simple test method' () {

        def time = System.currentTimeMillis()

        when:
        sleep( 100 )

        then:
        ( System.currentTimeMillis() - time ) >= 100
    }


    @Time( min = 0, max = 10 )
    def 'another test method' () {
        expect:
        true
    }
}
