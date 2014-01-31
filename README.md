Robot Kata
==========

A [web application](http://robot-kata.herokuapp.com) to explore a simple robot
programming problem.

### The Problem ##

This simple robot (the gray circle with the black outline) needs to get back to
its dock (the black and blue blob) to charge.  The dock emits infrared lobes
(the green, yellow, and red blobs) to guide the robot in.  The robot has a
single sensor (the small black circle at its front) that can sense the color of
the infrared beams.  Each test starts the robot at a different orientation in
the environment.

Program the robot to navigate to the blue part of its dock without crossing the
black part.

### How to program the robot ###

To program the robot, you can overwrite two functions in the JavaScript console:
`robot_kata.robot.init_robot_state` and
`robot_kata.robot.get_new_robot_velocity`.  (Best to edit in an external editor
then copy and paste in.)  `init_robot_state` is called at the beginning of each
test run to initialize any state you want to maintain.  `get_new_robot_velocity`
is called at each 20Hz tick of the simulation to determine the new commanded
velocity of the robot.  For example, here is how the robot is programmed
initially (just drive straight and print the sensed color at 2Hz):

    robot_kata.robot.init_robot_state = function (s) {
        s.cmds = robot_kata.robot.commands; // Just to require less typing later
        s.count = 0;
        return null;
    }
    
    robot_kata.robot.get_new_robot_velocity = function (s, sensed_color) {
        s.count += 1;
        if (s.count % 10 == 0) {
            console.log(sensed_color);
        }
    
        if (sensed_color == "blue") {
            return s.cmds.STOP;
        } else {
            return s.cmds.STRAIGHT;
        }
    }

The value of the second argument to `get_new_robot_velocity` (i.e.
`sensed_color`) is the string name of the color.  Possible values are `black`,
`blue`, `green`, `red`, `white`, and `yellow`.  `white` indicates no IR
detected.  `black` means failure, and `blue` means success, so you probably
don't need to account for those two.

`get_new_robot_velocity` must return a property from the
`robot_kata.robot.commands` object.  That object contains the following
properties:

  * `ARC_LEFT`
  * `ARC_RIGHT`
  * `STOP`
  * `STRAIGHT`
  * `TURN_IN_PLACE_LEFT`
  * `TURN_IN_PLACE_RIGHT`

That should be it.  Enjoy (hopefully)!

Kata Development
----------------

### Requirements ###

[Leiningen](http://leiningen.org/) and a JDK (6 or newer?)

### Usage ###

Build ClojureScript code

    lein cljsbuild once

Serve locally

    lein run -m robot-kata.server <PORT>

To deploy, push to Heroku.

License
-------

Copyright © 2013-2014 Dan Kee

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
