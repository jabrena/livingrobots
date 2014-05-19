Living Robots
=============
 
Living Robots, is the Github repository for the new Ebook about LeJOS "Living Robots with EV3".

"Living Robots with EV3" is a ebook designed to learn about development of Robots with modern techniques and technologies. The book put the focus in the platform Lego Mindstorms EV3, Linux and the programming language Java. The ebook cover modern topics as "Internet of things" (IOT) and the integration with other technologies as Web, Raspberry PI & Arduino.

If you enjoyed the previous ebook (http://juanantonio.info/lejos-ebook/) then book your copy now: http://juanantonio.info/about.htm

    2014/05/04: First commit

## Chapter 0:

Downloads: https://github.com/jabrena/livingrobots/raw/master/chapter0/docs/LRWE_Chapter0.pdf

## Chapter 1: Introduction

### Lego Mindstorms

Pending

### LeJOS Project

Pending

### Alternatives to Lego Mindstorms

Pending

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

### Pedadogy

Pending

### References

http://www.cs.berkeley.edu/~jfc/hcc/courseF99/projects/magno.pdf

https://creative-computing.appspot.com/assets/lib/Papert-1993.pdf

http://facweb.cs.depaul.edu/elulis/ricca.pdf

## Chapter 2: Fundamentals of Robotics

### Wheeled mobile robots

Pending

### References

http://www.probabilistic-robotics.org/

http://probabilistic-robotics.informatik.uni-freiburg.de/ppt/

http://www.eecs.yorku.ca/course_archive/2011-12/W/4421/lectures.html

http://www.ohio.edu/people/williar4/html/PDF/IntroRob.pdf

http://www-ee.ccny.cuny.edu/www/web/jxiao/I5501-introduction.pdf

http://robotics.usc.edu/~aatrash/cs445/lec01.pdf

http://www.cs.cmu.edu/~me/811/

http://er.jsc.nasa.gov/seh/robot_pdf_files/robotics_in_the_classroom.pdf

## Chapter 3: Sensors

In this chapter, we will cover the most popular and useful sensors compatible with EV3 Brick.

### Exteroceptors sensors

Exteroceptors are sensors that measure the positional or force-type interaction of the robot with its environment. 

#### Arduino

Arduino is a single-board microcontroller used in this ebook as a Bridge to connect complex sensors as RPILIDAR with I2C or Bluetooth.

#### RPLIDAR

RPLIDAR is a low cost 360 degree 2D laser scanner (LIDAR) solution developed by RoboPeak. The system can perform 360 degree scan within 6 meter range. The produced 2D point cloud data can be used in mapping, localization and object/environment modeling.

![ScreenShot](https://github.com/jabrena/livingrobots/raw/master/chapter3/docs/RPLIDAR.png)

##### Status

EV3 read values the distance from the angle 1 to 255 (1 byte)

    lejos@(none):~/programs$ jrun -jar ArduinoTest.0.1.jar 
    Arduino Connection Test2
    1 0
    2 0
    3 0
    4 0
    5 0
    6 0
    7 0
    8 0
    9 0
    10 0
    11 0
    12 0
    13 0
    14 0
    15 0
    16 0
    17 0
    18 0
    19 0
    20 0
    21 0
    22 0
    23 0
    24 0
    25 0
    26 0
    27 0
    28 0
    29 0
    30 247
    31 46
    32 117
    33 208
    34 209
    35 157
    36 111
    37 111
    38 106
    39 158
    40 152
    41 110
    42 69
    43 57
    44 48
    45 50
    46 62
    47 25
    48 18
    49 13
    50 4
    51 251
    52 218
    53 184
    54 179
    55 177
    56 176
    57 172
    58 167
    59 9
    60 74
    61 9
    62 4
    63 11
    64 31
    65 12
    66 35
    67 11
    68 13
    69 169
    70 172
    71 175
    72 18
    73 38
    74 42
    75 44
    76 75
    77 78
    78 79
    79 85
    80 88
    81 120
    82 125
    83 131
    84 131
    85 124
    86 141
    87 128
    88 158
    89 0
    90 0
    91 49
    92 43
    93 43
    94 40
    95 37
    96 37
    97 36
    98 36
    99 37
    100 38
    101 39
    102 41
    103 45
    104 48
    105 53
    106 56
    107 61
    108 69
    109 98
    110 95
    111 91
    112 90
    113 89
    114 89
    115 90
    116 91
    117 93
    118 95
    119 103
    120 112
    121 123
    122 124
    123 97
    124 92
    125 109
    126 137
    127 151
    128 167
    129 189
    130 225
    131 251
    132 237
    133 218
    134 203
    135 189
    136 180
    137 170
    138 164
    139 159
    140 158
    141 154
    142 151
    143 149
    144 147
    145 146
    146 146
    147 146
    148 148
    149 147
    150 147
    151 147
    152 147
    153 147
    154 147
    155 148
    156 150
    157 151
    158 153
    159 157
    160 163
    161 168
    162 57
    163 59
    164 65
    165 65
    166 65
    167 61
    168 62
    169 60
    170 61
    171 61
    172 64
    173 63
    174 66
    175 68
    176 72
    177 75
    178 78
    179 81
    180 86
    181 91
    182 97
    183 104
    184 110
    185 119
    186 131
    187 132
    188 58
    189 57
    190 49
    191 44
    192 33
    193 34
    194 29
    195 26
    196 23
    197 19
    198 18
    199 16
    200 15
    201 14
    202 14
    203 14
    204 18
    205 18
    206 21
    207 26
    208 33
    209 40
    210 42
    211 0
    212 0
    213 0
    214 0
    215 0
    216 0
    217 0
    218 238
    219 234
    220 227
    221 44
    222 112
    223 104
    224 80
    225 64
    226 153
    227 19
    228 139
    229 86
    230 192
    231 180
    232 0
    233 0
    234 0
    235 0
    236 0
    237 0
    238 0
    239 0
    240 0
    241 188
    242 188
    243 185
    244 188
    245 0
    246 188
    247 188
    248 0
    249 154
    250 134
    251 0
    252 0
    253 0
    254 0
    255 0
    lejos@(none):~/programs$ 

Issues:

How to send by I2C An array of bytes?
How to represent a distance in 2 bytes?

### Proprioceptors sensors

Proprioception in robotics means sensing the internal state of the robot or a part of it . For example the posture of a mechanical manipulator, leg or other jointed mechanism or the battery level.

### Examples

    Example: IRSensor to detect a Beacon
    Example: The magic 8 ball using a EV3GyroSensor
    Example: How to integrate Arduino with EV3

### References

http://en.wikipedia.org/wiki/Arduino

http://arduino.cc/en/Main/ArduinoProtoShield

http://www.dexterindustries.com/NXTBreadBoard.html

http://rplidar.robopeak.com/index.html

## Chapter 4: Actuators

### Examples

    Example: Simple bumper car

## Chapter 5: Navigation

In this chapter, user will learn basic concepts about Local & Global navigation

### Occupancy grid maps

![ScreenShot](https://raw.githubusercontent.com/jabrena/livingrobots/master/chapter8/docs/mapping.png)

### References

http://www.cs.ox.ac.uk/people/michael.wooldridge/teaching/robotics/lect05.pdf

http://cdn.intechopen.com/pdfs-wm/5368.pdf

http://www.sci.brooklyn.cuny.edu/~parsons/courses/3415-fall-2011/papers/elfes.pdf

http://www.cs.utexas.edu/~kuipers/slides/L13-occupancy-grids.pdf

http://ais.informatik.uni-freiburg.de/teaching/ss11/robotics/slides/08-occupancy-mapping.ppt.pdf

http://dai.fmph.uniba.sk/~petrovic/probrob/ch9.pdf

http://ijcai.org/papers07/Papers/IJCAI07-350.pdf

https://github.com/edgemaster/robotics/tree/master/lejos/tut5Probability/src/navigation

### Exercises

http://ais.informatik.uni-freiburg.de/teaching/ss11/robotics/exercises/sheet07.pdf

http://www.ra.cs.uni-tuebingen.de/lehre/uebungen/ss13/robotik2/assignment07.pdf

http://www.sci.brooklyn.cuny.edu/~parsons/courses/3415-fall-2011/projects/project6.pdf

http://www.asl.ethz.ch/research/asl/cogniron/ShrihariVasudevan_CWSHRI08.pdf


## Chapter 6: Cognition

### Reactive Control

Pending

### Behavior-Based Control

Example: http://sourceforge.net/p/lejos/ev3/ci/master/tree/EV3BumperCar/src/EV3BumperCar.java

### Deliberative Control

#### FSM

The behaviours are modelled with a HFSM using Apache Commons SCXML.

Examples: https://github.com/jabrena/liverobots

### References

http://robotics.usc.edu/~aatrash/cs445/lec12.pdf

http://robotics.usc.edu/~aatrash/cs445/lec13.pdf

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

http://git.busybox.net/busybox/tree/networking/httpd.c

https://github.com/TooTallNate/Java-WebSocket

https://github.com/NanoHttpd/nanohttpd

## Chapter 9: Communications

This chapter covers the following technologies: Websockets, Http, RMI, D-Bus & I2C.

### References

http://en.wikipedia.org/wiki/I2C

http://arduino.cc/en/Reference/Wire

http://playground.arduino.cc/Main/WireLibraryDetailedReference

https://github.com/DexterInd/EV3_Dexter_Industries_Sensors/blob/master/EV3_arduino/

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
    Example: Scheduling a task with Cron4J
    Example: NTP Client (Using classes from project EV3Menu)

### Exercises

    Exercise 1: How to add a program in the startup
    Exercise 2: Create a Virus for EV3 brick

### References

http://freeware.the-meiers.org/

http://www.openwall.com/john/

http://www.jcraft.com/jsch/

https://www.thc.org/thc-hydra/

http://nmap.org/

## Annexes

## Annex 1: Getting started with LeJOS

### References

http://sourceforge.net/p/lejos/wiki/Getting%20started%20with%20leJOS%20EV3/

## Annex 2: Busybox

LeJOS project uses Busybox as the way to offer a Linux experience. It was specifically created for embedded operating systems with very limited resources. BusyBox provides several stripped-down Unix tools in a single executable file. The authors dubbed it "The Swiss Army Knife of Embedded Linux", as the single executable replaces basic functions of more than 300 common commands

In this Annex, the reader will learn how to use Busybox in a EV3 environment.

Download preview: https://github.com/jabrena/livingrobots/raw/master/annex2/docs/LRWE_Annex2_Busybox_Preview.pdf
Exercises: https://github.com/jabrena/livingrobots/raw/master/annex2/docs/LRWE_Annex2_Busybox_Exercises.pdf


