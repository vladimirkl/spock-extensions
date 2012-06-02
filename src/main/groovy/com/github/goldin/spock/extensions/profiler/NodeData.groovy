package com.github.goldin.spock.extensions.profiler

import org.spockframework.runtime.model.NodeInfo

/**
 *
 */
class NodeData implements Comparable<NodeData>
{
    NodeInfo method
    long     executionTime

    String description (){ "${ method.parent.name }.${ method.name }" }

    @Override
    String toString (){ "${ description() } - [${ this.executionTime }] ms" }

    @Override
    int compareTo ( NodeData o ){ o.executionTime <=> this.executionTime }
}