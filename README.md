Living Robots
=============

[![Build Status](https://secure.travis-ci.org/jabrena/livingrobots.png)](http://travis-ci.org/jabrena/livingrobots)
 
Living Robots, is the Github repository for the new Ebook about LeJOS "Living Robots with EV3".

"Living Robots with EV3" is a ebook designed to learn about development of Robots with modern techniques and technologies. The book put the focus in the platform Lego Mindstorms EV3, Linux and the programming language Java.

If you enjoyed the previous ebook (http://juanantonio.info/lejos-ebook/) then book your copy now: http://juanantonio.info/about.htm

    05/04/2014: First commit
    05/25/2014: RPLIDAR Support for LeJOS


## Chapter 0:

    Last release: 1/6/2014

Download: https://github.com/jabrena/livingrobots/raw/master/chapter0/docs/LRWE_Chapter0.pdf

## Chapter 1: Introduction

### Getting Started

Once you have a brick with a WIFI Dongle (For exampe a Netgear WNA 1100), turn on the brick and connect with your computer using a USB wire. Open a shell window and type:

    ssh lejos@10.0.1.1
    lejos@10.0.1.1's password: 

To configurate your WIFI connection edit the file wpa_supplicant.conf

path: /home/root/lejos/bin/utils

ctrl_interface=/var/run/wpa_supplicant

    network={
        ssid="YOUR SSID"
        key_mgmt=WPA-PSK
        psk=YOUR PSK
    }

if your computer is not able to create a psk, use the following online tool:
https://www.wireshark.org/tools/wpa-psk.html

later, execute the following command:
/home/root/lejos/startwlan

### Living Robots

#### Brity V2

Specifications:

    1x Lego Mindstorms EV3 Brick + EV3 Rechargable battery + USB Hub + Netgear WNA 1100
    1x Hitechnic Compass sensor
    1x EV3 IR Sensor
    1x Arduino One + Arduino Proto shield + Dexter breadboard adapter
    1x Robopeak RPLIDAR
    1x Smartphone + Chrome Web browser

![ScreenShot](https://raw.githubusercontent.com/jabrena/livingrobots/master/LDD/Base1/Base1_5.png)

## Chapter 2: Fundamentals of Robotics

Pending

## Chapter 3: Sensors

In this chapter, we will cover the most popular and useful sensors compatible with EV3 Brick.

### Exteroceptors sensors

Exteroceptors are sensors that measure the positional or force-type interaction of the robot with its environment. 

Sensors tested:

    Robopeak RPLIDAR
    EV3 IRSensor
    Hitechnic Compass Sensor
    Dexter GPS

#### RPLIDAR

RPLIDAR is a low cost 360 degree 2D laser scanner (LIDAR) solution developed by RoboPeak. The system can perform 360 degree scan within 6 meter range. The produced 2D point cloud data can be used in mapping, localization and object/environment modeling.

#### Sensor support

RPLIDAR Arduino Library: https://github.com/jabrena/livingrobots/raw/master/chapter3/arduino/RPLIDAR/Driver/RPLidarDriver_r1.0.zip

Arduino Sketch: https://github.com/jabrena/livingrobots/blob/master/chapter3/arduino/RPLIDAR/Sketches/simple_connect6/simple_connect6.ino

LeJOS Sensor Class: https://github.com/jabrena/livingrobots/blob/master/chapter3/ev3/RPLIDARTest/src/lejos/hardware/sensor/RPLIDARSensor.java

#### Data Visualization

I have added a simple example to generate a JSON file to view  LIDAR data in a polar chart in any web browser.

![ScreenShot](https://raw.githubusercontent.com/jabrena/livingrobots/master/chapter5/docs/RPLIDAR_livingRoom2.png)

    http://192.168.0.101/Radar/amcharts/polarChart.htm

### Proprioceptors sensors

Proprioception in robotics means sensing the internal state of the robot or a part of it . For example the posture of a mechanical manipulator, leg or other jointed mechanism or the battery level.

Sensors tested:

    EV3 Battery

### Examples

    Example: IRSensor to detect a Beacon
    Example: The magic 8 ball using a EV3GyroSensor
    Example: How to use the sensor RPLIDAR
    Example: How to generate JSON data to view LIDAR data

## Chapter 4: Actuators

Pending

## Chapter 5: Navigation

In this chapter, user will learn basic concepts about Local & Global navigation

### Local navigation

####  Occupancy grid maps

I am working in a better support for Occupancy grid map. Current leJOS support is poor and the implementation doesn't cover Bayes theory.

http://www.lejos.org/ev3/docs/lejos/robotics/mapping/OccupancyGridMap.html

To solve this Gap, I am trying to develop a better support to upgrade the class and add Bayes support using some papers as:

http://www.cs.ox.ac.uk/people/michael.wooldridge/teaching/robotics/lect05.pdf

Current solution start showing a real map about a real space using a EV3 Robot with a 2D LIDAR in few minutes.

![ScreenShot](https://raw.githubusercontent.com/jabrena/livingrobots/master/chapter5/docs/OGM_WebView.png)

    http://192.168.0.101/ogm/index2.htm

I am thinking in some kind of solution to export the map to use later in next session. User could choose the map using some kind of web tool.

#### Local path planning

##### Virtual Potential Fields

![ScreenShot](http://www.cs.mcgill.ca/~hsafad/robotics/report_files/image010.jpg)

http://www.cs.mcgill.ca/~hsafad/robotics/

## Chapter 6: Cognition

### Reactive Control

Pending

### Behavior-Based Control

Example: http://sourceforge.net/p/lejos/ev3/ci/master/tree/EV3BumperCar/src/EV3BumperCar.java

### Deliberative Control

#### FSM

The behaviours are modelled with a HFSM using Apache Commons SCXML.

Examples: https://github.com/jabrena/liverobots

### Examples

    Example: Simple bumper car

## Chapter 7: Computer vision

Pending

## Chapter 8: Internet, web services & web interfaces

In this Chapter, the reader will learn many stuff about Web Servers and  Web interfaces for robots with EV3 Brick.

### Web Servers

In this section, user will learn the different among httpd and other developments in Java to run a Web Server as NanoWeb server.

    root@(none):~# start-stop-daemon -K -n httpd
    stopped httpd (pid 1823)
    root@(none):~# httpd -v -h /home/lejos/www/

An example when the httpd is pretty busy:

    Mem: 49048K used, 11812K free, 0K shrd, 516K buff, 25556K cached
    CPU:   8% usr  35% sys   0% nic   0% idle   0% io  15% irq  39% sirq
    Load average: 11.89 12.35 10.50 17/101 9146
      PID  PPID USER     STAT   VSZ %MEM %CPU COMMAND
     1551  1197 root     S     178m 300%  16% /home/root/lejos/ejre1.7.0_55/bin/java
     1735     1 root     R     2880   5%  11% httpd -v -h /home/lejos/www/ 

### Web Interfaces

In this section, user will learn basic stuff about Modern web development.

![ScreenShot](https://raw.githubusercontent.com/jabrena/livingrobots/master/chapter8/docs/remoteControl.jpg)

### Examples

    Example: Web remote control with Websockets
    Example: Data visualization for RPLIDAR (Polar Chart)
    Example: Occupancy grid map viewer
    Example: JSON generation
    Example: Node.JS for JVM example

## Chapter 9: Integrations with third parties

This chapter covers the technologies used to integrate your robot with third parties as Arduino, New Sensors, Internet, etc...

### Examples

    Example: How to integrate Arduino with EV3
    Example: RMI Client & Server example

## Chapter 15: Hacking robots

It is very important to ensure that your robot operates in a secure environment. Learn some stuff about security for Robots and avoid future robot's hacks.

![ScreenShot](https://raw.githubusercontent.com/jabrena/livingrobots/master/chapter15/docs/Tachikoma.png)

In this chapter you will learn some concepts about security for Unix. Remember that your EV3 Brick run over a Busybox distro.

Nano Web server running on EV3 and suffering a DOS Attack:

    Mem: 59800K used, 1060K free, 0K shrd, 48K buff, 4288K cached
    CPU:  23% usr  26% sys   0% nic   0% idle  23% io   9% irq  17% sirq
    Load average: 3.60 1.78 0.77 1/373 2030
    PID  PPID USER     STAT   VSZ %MEM %CPU COMMAND
    1577  1538 root     S     266m 447%  43% /home/root/lejos/ejre1.7.0_55/bin/java -classpath /ho
        4     2 root     SW       0   0%  11% [events/0]
      430     2 root     DW       0   0%   8% [mmcqd]
     1538  1184 root     S     179m 301%   5% /home/root/lejos/ejre1.7.0_55/bin/java -classpath /ho
      180     2 root     DW       0   0%   4% [kswapd0]
     1594  1567 root     R     3072   5%   2% top 

### Examples

The examples included in this chapter are:

    Example: Discover ports opened in your EV3 Brick
    Example: Hack your SSH root account using Brute force
    Example: Read /var/volatile/log/ to discover ssh attacks
    Example: A mini example about a DOS Attack to the service httpd included Busybox
    Example: Shell scripts to remove some no necessary processes
    Example: Scheduling a task with Cron4J
    Example: NTP Client (Using classes from project EV3Menu)

### Exercises

    Exercise 1: How to add a program in the startup
    Exercise 2: Create a Virus for EV3 brick

## Annexes

## Annex 1: Getting started with LeJOS

### References

http://sourceforge.net/p/lejos/wiki/Getting%20started%20with%20leJOS%20EV3/

## Annex 2: Busybox

LeJOS project uses Busybox as the way to offer a Linux experience. It was specifically created for embedded operating systems with very limited resources. BusyBox provides several stripped-down Unix tools in a single executable file. The authors dubbed it "The Swiss Army Knife of Embedded Linux", as the single executable replaces basic functions of more than 300 common commands

In this Annex, the reader will learn how to use Busybox in a EV3 environment.

Download preview: https://github.com/jabrena/livingrobots/raw/master/annex2/docs/LRWE_Annex2_Busybox_Preview.pdf
Exercises: https://github.com/jabrena/livingrobots/raw/master/annex2/docs/LRWE_Annex2_Busybox_Exercises.pdf


