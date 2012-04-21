package com.github.goldin.spock.extensions.testdir

import com.github.goldin.spock.extensions.BaseMethodInterceptor
import org.gcontracts.annotations.Ensures
import org.gcontracts.annotations.Requires
import org.spockframework.runtime.extension.IMethodInvocation

/**
 * {@link @TestDir} listener.
 */
class TestDirInterceptor extends BaseMethodInterceptor
{
    private final String  baseDir
    private final boolean clean
    private final String  fieldName


    @Requires({ baseDir && fieldName })
    @Ensures({ ( this.baseDir == baseDir ) && ( this.clean == clean ) && ( this.fieldName == fieldName ) })
    TestDirInterceptor ( String baseDir, boolean clean, String fieldName )
    {
        this.baseDir   = baseDir
        this.clean     = clean
        this.fieldName = fieldName
    }


    /**
     * Deletes directory specified recursively.
     *
     * @param directory directory to delete
     * @return directory deleted
     */
    @Requires({ directory.directory })
    private File delete ( File directory )
    {
        for ( f in directory.listFiles())
        {
            if ( f.file )
            {
                assert f.delete()
            }
            else if ( f.directory )
            {
                delete( f )
            }
            else
            {
                throw new RuntimeException( "Unknwon File instance [$f]" )
            }
        }

        assert (( ! directory.listFiles()) && ( directory.delete()) && ( ! directory.directory ))
        directory
    }


    @Override
    @Requires({ invocation && baseDir && fieldName })
    void interceptSetupMethod ( IMethodInvocation invocation )
    {
        final specInstance = getSpec( invocation )
        final testDirName  = "${ specInstance.class.name }/${ invocation.feature.name.replaceAll( /\W+/, '-' ) }"
        File  testDir      = new File( baseDir, testDirName ).canonicalFile

        if ( testDir.directory )
        {
            // Cleaning directory
            if ( clean )
            {
                delete( testDir )
            }
            else
            {
                // Creating new directory next to existing one
                for ( int counter = 1; testDir.directory; counter++ )
                {
                    testDir = new File( baseDir, testDirName + "_$counter" ).canonicalFile
                }
            }
        }

        assert testDir.with { ( ! directory ) && mkdirs() }, "Failed to create test directory [$testDir]"
        specInstance."$fieldName"         = testDir
        assert specInstance."$fieldName" == testDir
        invocation.proceed()
    }
}
