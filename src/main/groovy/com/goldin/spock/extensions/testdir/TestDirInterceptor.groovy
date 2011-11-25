package com.goldin.spock.extensions.testdir

import com.goldin.spock.extensions.BaseMethodInterceptor
import org.gcontracts.annotations.Ensures
import org.gcontracts.annotations.Requires
import org.spockframework.runtime.extension.IMethodInvocation
import groovy.io.FileType

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


    @Override
    @Requires({ invocation && baseDir && fieldName })
    void interceptSetupMethod ( IMethodInvocation invocation )
    {
        final testDirName  = "${ invocation.sharedInstance.class.name }/${ invocation.feature.name.replaceAll( /\W+/, '-' ) }"
        final specInstance = getSpec( invocation )
        File  testDir      = new File( baseDir, testDirName ).canonicalFile

        if ( clean )
        {   /**
             * Cleaning up directory if it already exists
             */
            if ( testDir.directory )
            {
                testDir.eachFileRecurse( FileType.FILES ){ File f -> assert f.file && f.delete() }
                assert testDir.delete()
            }
        }
        else
        {   /**
             * Creating new directory if it already exists
             */
            for ( int counter = 1; testDir.directory; counter++ )
            {
                testDir = new File( baseDir, testDirName + "_$counter" ).canonicalFile
            }
        }

        assert testDir.with { ( ! directory ) && mkdirs() }, "Failed to create test directory [$testDir]"
        specInstance."$fieldName"         = testDir
        assert specInstance."$fieldName" == testDir
        invocation.proceed()
    }
}
