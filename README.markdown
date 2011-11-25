

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

* `min` (int) - minimal execution time in milliseconds, should be zero or more
* `max` (int) - maximal execution time in milliseconds, should be more than `min`

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
