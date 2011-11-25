package com.goldin.spock.extensions

import com.goldin.spock.extensions.testdir.TestDir
import spock.lang.Specification

/**
 * {@code @TestDir} extension test spec.
 */
class TestDirSpec extends Specification
{
    @SuppressWarnings( 'StatelessClass' )
    @TestDir File testDir


    def 'test method()'() {

        expect:
        true
        testDir.directory
        ! testDir.listFiles()
        ! ( testDir.path.matches( /^.+_\d+$/ ))
    }


    def 'data-driven test method()'( int x, int y, int z ) {

        expect:
        x + y == z
        testDir.directory
        ! testDir.listFiles()
        ! ( testDir.path.matches( /^.+_\d+$/ ))

        where:
        x | y | z
        0 | 1 | 1
        1 | 1 | 2
        2 | 2 | 4
        3 | 6 | 9
        4 | 7 | 11
    }


    def 'data-driven test method using testDir'( int x, int y, int z ) {

        expect:
        x + y == z
        testDir.directory
        ! testDir.listFiles()
        ! ( testDir.path.matches( /^.+_\d+$/ ))

        new File( testDir, '1' ).mkdirs()
        new File( testDir, '1/2' ).mkdirs()
        new File( testDir, '1/2/3' ).mkdirs()
        new File( testDir, '1/2/3/4' ).mkdirs()
        new File( testDir, "1/2/3/$x-$y.$z" ).write( "$x-$y-$z" )

        where:
        x | y | z
        0 | 1 | 1
        1 | 1 | 2
        2 | 2 | 4
        3 | 6 | 9
        4 | 7 | 11
    }
}
