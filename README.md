Living Robots
=============
 
Living Robots, is the Github repository for the new Ebook about LeJOS "Living Robots with EV3".
http://juanantonio.info/lejos-ebook/

## Project status ##

    2014/05/04: First commit

## Chapter 1: Introduction

Pending

https://www.youtube.com/watch?v=kN50ENE_HUU

http://en.wikipedia.org/wiki/Tachikoma

## Chapter 2: Fundamentals of Robotics

Pending

## Chapter 3: Sensors

In this chapter, we will cover the most popular sensors compatible with EV3 Brick.

![ScreenShot](https://raw.githubusercontent.com/jabrena/livingrobots/master/chapter3/ev3/GyroSensor/models/Magic8Ball.png)

    Example: IRSensor to detect a Beacon
    Example: The magic 8 ball using a EV3GyroSensor

## Chapter 4: Actuators

Pending

## Chapter 5: Navigation

In this chapter, user will learn basic concepts about Local & Global navigation

### Occupancy grid maps

![ScreenShot](https://raw.githubusercontent.com/jabrena/livingrobots/master/chapter8/docs/mapping.png)

http://www.cs.ox.ac.uk/people/michael.wooldridge/teaching/robotics/lect05.pdf
https://github.com/edgemaster/robotics/tree/master/lejos/tut5Probability/src/navigation

## Chapter 6: Cognition

Pending

## Chapter 7: Computer vision

Pending

## Chapter 8: Web UI for EV3

In this Chapter, the reader will learn to develop Web UI for EV3. The user will learn to use Websockets, RFC 6455 & JSR 356.

![ScreenShot](https://raw.githubusercontent.com/jabrena/livingrobots/master/chapter8/docs/remoteControl.jpg)

https://jcp.org/en/jsr/detail?id=356

https://tools.ietf.org/html/rfc6455

https://github.com/TooTallNate/Java-WebSocket

Besides, the reader will learn to use the Webserver included in the Busybox distro.

    root@(none):~# start-stop-daemon -K -n httpd
    stopped httpd (pid 1823)
    root@(none):~# httpd -v -h /home/lejos/www/

## Chapter 9: Communications

Pending

## Chapter 15: Hacking robots

It is very important to ensure that your robot operates in a secure environment. Learn some stuff about security for Robots and avoid future robot's hacks.

![ScreenShot](https://raw.githubusercontent.com/jabrena/livingrobots/master/chapter15/docs/Tachikoma.png)

https://www.youtube.com/watch?v=wRSYYPhqaKg

https://www.youtube.com/watch?v=ufuUr2F5FkU

https://www.youtube.com/watch?v=Q8uwwGtfm6U

    Example: Discover ports opened in your EV3 Brick
    Example: Hack your SSH root account using Brute force

syslogd -l 6

root@(none):/var/volatile/log# syslogd ?   
BusyBox v1.13.2 (2010-12-21 19:28:47 CST) multi-call binary

Usage: syslogd [OPTION]...

System logging utility.
Note that this version of syslogd ignores /etc/syslog.conf.

Options:
	-n		Run in foreground
	-O FILE		Log to given file (default=/var/log/messages)
	-l n		Set local log level
	-S		Smaller logging output
	-s SIZE		Max size (KB) before rotate (default=200KB, 0=off)
	-b NUM		Number of rotated logs to keep (default=1, max=99, 0=purge)
	-R HOST[:PORT]	Log to IP or hostname on PORT (default PORT=514/UDP)
	-L		Log locally and via network (default is network only if -R)
	-D		Drop duplicates
	-C[size(KiB)]	Log to shared mem buffer (read it using logread)

cd /var/volatile/log/

cat messages

scp lejos@10.0.1.1:/home/lejos/messages.txt /Users/jabrena/

http://freeware.the-meiers.org/

http://www.openwall.com/john/

http://www.jcraft.com/jsch/

https://www.thc.org/thc-hydra/

http://nmap.org/

http://www.netfilter.org/

http://wiki.openwrt.org/doc/howto/log.messages

http://wiki.openwrt.org/doc/howto/log.essentials

http://en.wikipedia.org/wiki/Syslog

http://www.ietf.org/rfc/rfc3164.txt

http://stackoverflow.com/questions/16980779/java-program-for-ddos-attack



## Appendices

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
