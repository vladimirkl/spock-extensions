package com.goldin.spock.extensions

import com.goldin.spock.extensions.testdir.TestDir
import spock.lang.Specification


/**
 * {@code @TestDir} extension test spec.
 */
class TestDirSpec2 extends Specification
{
    @SuppressWarnings( 'StatelessClass' )
    @TestDir( clean = false ) File testDir


    def 'test method()'() {

        expect:
        true
        testDir.directory
        testDir.listFiles().size() == 0
    }


    def 'data-driven test method()'( int x, int y, int z ) {

        expect:
        x + y == z
        testDir.directory
        testDir.listFiles().size() == 0
        ( x == 0 ) || ( testDir.path.matches( /^.+_\d+$/ ))

        where:
        x | y | z
        0 | 1 | 1
        1 | 1 | 2
        2 | 2 | 4
        3 | 6 | 9
        4 | 7 | 11
    }
}
