package com.github.goldin.spock.extensions.log

import org.slf4j.LoggerFactory


/**
 * Logger class used by extensions.
 */
class Logger
{
    private static final LOG = LoggerFactory.getLogger( Logger )

    void log( String message )
    {
        LOG.info( message )
    }
}
