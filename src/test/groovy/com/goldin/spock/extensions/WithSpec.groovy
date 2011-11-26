package com.goldin.spock.extensions

import com.goldin.spock.extensions.with.With
import spock.lang.Specification
import com.goldin.spock.extensions.testdir.TestDir
import com.goldin.spock.extensions.time.Time
import spock.lang.FailsWith

/**
 * {@code @With} extension test spec.
 */
@Time( min = 0, max = 5000 )
class WithSpec extends Specification
{
    @SuppressWarnings( 'StatelessClass' )
    @TestDir File testDir


    @Time( min = 10, max = 300 )
    @With({ [ 'string', [ '1' : 2 ], [ true ] ] })
    def 'regular test method' () {

        expect:
        true
        size() == 6
        containsKey( '1' )
        first()
    }


    @Time( min = 10, max = 300 )
    @With({ [ 'string', [ '1' : 3 ], [ true ] ] })
    @FailsWith( value = AssertionError, reason = 'No @With object responds to method [aaaa]' )
    def 'failing test method' () {

        expect:
        true
        size() == 6
        containsKey( '1' )
        first()
        aaaa()
    }


    @Time( min = 500, max = 5000 )
    @With({ 'http://gradle.org/'.toURL() })
    def 'URL test method' () {

        when:
        new File( testDir, 'data.txt' ).write( text )

        then:
        new File( testDir, 'data.txt' ).text.size() > ( 9 * 1024 )
    }


    @Time( min = 0, max = 100 )
    @With({ [ 'http://groovy.codehaus.org/', null ] })
    def 'null test method' () {

        expect:
        bytes
        chars
        size()
    }
}
