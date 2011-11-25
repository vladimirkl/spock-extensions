

# Spock Extensions

## @TempDirectory

**Forked from `http://github.com/robfletcher/spock-extensions`.**

Used on a `File` property of a spec class this annotation will cause
a temporary directory to be created and injected before each feature
method is run and destroyed afterwards. If the field is `@Shared` the
directory is only destroyed after all feature methods have run. You
can have as many such fields as you like in a single spec, each will
be generated with a unique name.

Temporary directories are created inside `java.io.tmpdir`.

This is useful when testing a class that reads from or writes to a
location on disk.


### Example

	class MySpec extends Specification {

		@TempDirectory File myTempDir

		def diskStore = new DiskStore()

		def "disk store writes bytes to a file"() {
			given:
			diskStore.baseDir = myTempDir
			diskStore.targetFilename = "foo"

			when:
			diskStore << "some text"

			then:
			new File(myTempDir, "foo").text == "some text"
		}

	}


## @Time


`@Time` annotation is used per-Specification or per-feature (test method). It allows to time-limit execution of the whole Spec or specific test method with two attributes:

* `min` (int) - minimal execution time in milliseconds, should be zero or more.
* `max` (int) - maximal execution time in milliseconds, should be more than `min`.

Note, that both attributes are `int` (covering 24-days execution) and not `long` although they deal with milliseconds. This is to avoid warnings around `0L` constants and avoid appending `L` to numbers as well.

### Example (taken from [this file](https://github.com/evgeny-goldin/gcommons/blob/a4abda41f5977c742b202d6d22a326699e6da7bf/src/test/groovy/com/goldin/gcommons/specs/GeneralBeanSpec.groovy))


    @Time( min = 500, max = 2000 )
    class GeneralBeanSpec extends BaseSpec
    {
        @Time( min = 500, max = 2000 )
        def 'gc-87: GeneralBean.executeWithResult()'()
        {
            ...
        }


        @Time( min = 0, max = 100 )
        def 'check match()'()
        {
            ...
        }
    }


## @TestDir


`@TestDir` annotation creates empty test directory for each Spock feature (test method).

It has two attributes

* `baseDir` (String) - `"build/test"` by default.
   Directory where all test directories are created.
* `clean`  (boolean) - `true` by default.
   If test directory already exists whether it should be cleaned up (`true` value) or another one should be created next to it (`false` value).

For each feature test directory created at `"<baseDir>/<spec FQCN>/<feature name>"` where all non-alphabetic characters in feature name are replaced by `"-"`.

### Example (taken from [this file](https://github.com/evgeny-goldin/gcommons/blob/87484d54f0065f7e73008d4eabf1ea507b0922e4/src/test/groovy/com/goldin/gcommons/specs/FileBeanSpec.groovy))


    class FileBeanSpec extends BaseSpec
    {
        @TestDir File testDir

        def 'Check pack() and unpack() operations' ()
        {
            // build/test/com.goldin.gcommons.specs.FileBeanSpec/Check-pack-and-unpack-operations/
            assert testDir.directory && ( ! testDir.listFiles())

            given:
            def zipUnpack1 = new File( testDir, 'zip-1' )
            def zipUnpack2 = new File( testDir, 'zip-2' )
            ...
        }


        def 'Check "fullpath" and "prefix" pack() options'()
        {
            // build/test/com.goldin.gcommons.specs.FileBeanSpec/Check-fullpath-and-prefix-pack-options/
            assert testDir.directory && ( ! testDir.listFiles())

            given:
            def zipFile    = testResource(      "${testArchive.key}.zip" )
            def zipUnpack  = new File( testDir, 'zip' )
            def extFile1   = new File( testDir, "${testArchive.key}-1.$extension" )
            def extFile2   = new File( testDir, "${testArchive.key}-2.$extension" )
            ...
        }

        ...
    }