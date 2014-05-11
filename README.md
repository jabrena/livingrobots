Living Robots
=============
 
Living Robots, is the Github repository for the new Ebook about LeJOS "Living Robots with EV3".
http://juanantonio.info/lejos-ebook/

## Project status ##

    2014/05/04: First commit

## Chapter 0:

Downloads: https://github.com/jabrena/livingrobots/raw/master/chapter0/docs/LRWE_Chapter0.pdf

## Chapter 1: Introduction

Pending

https://www.youtube.com/watch?v=kN50ENE_HUU

http://en.wikipedia.org/wiki/Tachikoma

## Chapter 2: Fundamentals of Robotics

Pending

## Chapter 3: Sensors

In this chapter, we will cover the most popular sensors compatible with EV3 Brick.

![ScreenShot](https://raw.githubusercontent.com/jabrena/livingrobots/master/chapter3/ev3/GyroSensor/models/Magic8Ball.png)

### Examples

Examples included in the chapter:

    Example: IRSensor to detect a Beacon
    Example: The magic 8 ball using a EV3GyroSensor

## Chapter 4: Actuators

Pending

## Chapter 5: Navigation

In this chapter, user will learn basic concepts about Local & Global navigation

### Occupancy grid maps

![ScreenShot](https://raw.githubusercontent.com/jabrena/livingrobots/master/chapter8/docs/mapping.png)

### References

http://www.cs.ox.ac.uk/people/michael.wooldridge/teaching/robotics/lect05.pdf

https://github.com/edgemaster/robotics/tree/master/lejos/tut5Probability/src/navigation

## Chapter 6: Cognition

Pending

## Chapter 7: Computer vision

Pending

## Chapter 8: Web interfaces

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

### Web Interfaces

In this section, user will learn basic stuff about Modern web development.

### Websockets

In this section, user will learn to develop a distributed application using Websockets technology.

![ScreenShot](https://raw.githubusercontent.com/jabrena/livingrobots/master/chapter8/docs/remoteControl.jpg)

### References

https://jcp.org/en/jsr/detail?id=356

https://tools.ietf.org/html/rfc6455

https://github.com/TooTallNate/Java-WebSocket

https://github.com/NanoHttpd/nanohttpd

## Chapter 9: Communications

Pending

## Chapter 15: Hacking robots

It is very important to ensure that your robot operates in a secure environment. Learn some stuff about security for Robots and avoid future robot's hacks.

![ScreenShot](https://raw.githubusercontent.com/jabrena/livingrobots/master/chapter15/docs/Tachikoma.png)

In this chapter you will learn some concepts about security for Unix. Remember that your EV3 Brick run over a Busybox distro.

### Examples

The examples included in this chapter are:

    Example: Discover ports opened in your EV3 Brick
    Example: Hack your SSH root account using Brute force
    Example: Read /var/volatile/log/ to discover ssh attacks
    Example: A mini example about a DOS Attack to the service httpd included Busybox
    Example: Shell scripts to remove some no necessary processes

### Concepts

    What is a Zombie robot?

### Practices

    Exercise 1: How to add a program in the startup
    Exercise 2: Create a Virus for EV3 brick

### References

https://www.youtube.com/watch?v=wRSYYPhqaKg

https://www.youtube.com/watch?v=ufuUr2F5FkU

https://www.youtube.com/watch?v=Q8uwwGtfm6U

http://freeware.the-meiers.org/

http://www.openwall.com/john/

http://www.jcraft.com/jsch/

https://www.thc.org/thc-hydra/

http://nmap.org/

http://en.wikipedia.org/wiki/Syslog

http://www.ietf.org/rfc/rfc3164.txt


## Annexes

## Annex 1: Getting started with LeJOS

Pending. 

### References

http://sourceforge.net/p/lejos/wiki/Getting%20started%20with%20leJOS%20EV3/

## Annex 2: Busybox

    lejos@(none):~$ nc -l -p 10000
    nc: bind: Address already in use
    lejos@(none):~$ nc localhost 10000
    asdf
    asdf
    hello

    root@(none):~# nc -l -p 10000
    asdf
    asdf
    hello
