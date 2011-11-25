

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

* `min` - minimal execution time in milliseconds, should be zero or more
* `max` - maximal execution time in milliseconds, should be more than `min`


### Example (taken from [this file](https://github.com/evgeny-goldin/gcommons/blob/cb42537388b5d04fabe18a18f235b5c763df8010/src/test/groovy/com/goldin/gcommons/specs/GeneralBeanSpec.groovy))


    @Time( min = 500L, max = 2000L )
    class GeneralBeanSpec extends BaseSpec
    {
        @Time( min = 500L, max = 2000L )
        def 'gc-87: GeneralBean.executeWithResult()'()
        {
            ...
        }


        @Time( min = 0L, max = 100L )
        def 'check match()'()
        {
            ...
        }
    }
