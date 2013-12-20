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

