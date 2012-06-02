package com.github.goldin.spock.extensions.log

import groovy.util.logging.Slf4j


/**
 * Logger class used by extensions.
 */
@Slf4j
class Logger
{
    void log( String message )
    {
        log.info( message )
    }
}
