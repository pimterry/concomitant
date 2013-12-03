Concomitant
===========

Concomitant is a framework and set of utilities for conveniently testing concurrent code in Java, integrated with JUnit and Mockito.

Current Status [![Build Status](https://travis-ci.org/pimterry/concomitant.png)](https://travis-ci.org/pimterry/concomitant)
--------------
Concomitant is currently in very very early pre-release development, and is not yet practically usable. Feel free to jump in and help though!

We're actively aiming for a first v0.1.0 release at the moment, providing proof of concept usable implementations of the features for the core Concomitant use cases:

* Simply unit testing code with potential race conditions to deterministically verify thread safety
* Enabling integration testing of fundamentally concurrent systems deterministically

The specific steps to enable this are visible under the [v0.1.0 milestone](https://github.com/pimterry/concomitant/issues?milestone=1&state=open). Take a look at the [system test suite](https://github.com/pimterry/concomitant/tree/master/concomitant-test/src/test/java/org/concomitant/systemtests) for examples of how this works so far.

Inspirations
------------
This is modelled around the design and principles of previous frameworks like [MultithreadedTC](http://www.cs.umd.edu/projects/PL/multithreadedtc/overview.html) and [IMUnit](http://crest.cs.ucl.ac.uk/cow/17/slides/COW17_Marinov.pdf), also pulling in elements from other investigations in this field such as [ConcJunit](http://www.cs.rice.edu/~mgricken/research/concutest/concjunit/) and [ThreadControl](http://code.google.com/p/threadcontrol/).

Concomitant is an attempt to pull these together into a practical framework for testing concurrent mechanisms, primarily for use with Mockito and JUnit 4, with a fluent and intuitive API in the same style as Mockito's.


[![Bitdeli Badge](https://d2weczhvl823v0.cloudfront.net/pimterry/concomitant/trend.png)](https://bitdeli.com/free "Bitdeli Badge")

