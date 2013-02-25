Concomitant
===========

Concomitant is a framework and set of utilities for conveniently testing concurrent code in Java, integrated with JUnit and Mockito.

Current Status [![Build Status](https://travis-ci.org/pimterry/concomitant.png)](https://travis-ci.org/pimterry/concomitant)
--------------
Concomitant is currently in very very early pre-release development, aiming for a first semi-usable 0.1 release by mid-March.

Inspirations
------------
This is modelled around the design and principles of previous frameworks like [MultithreadedTC](http://www.cs.umd.edu/projects/PL/multithreadedtc/overview.html) and [IMUnit](http://crest.cs.ucl.ac.uk/cow/17/slides/COW17_Marinov.pdf), also pulling in elements from other investigations in this field such as [ConcJunit](http://www.cs.rice.edu/~mgricken/research/concutest/concjunit/) and [ThreadControl](http://code.google.com/p/threadcontrol/). 

Concomitant is an attempt to pull these together into a practical framework for testing concurrent mechanisms, primarily for use with Mockito and JUnit 4, with a fluent and intuitive API in the same sort of style as Mockito's.
