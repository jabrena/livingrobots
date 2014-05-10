#! /bin/sh

#NOTE: Copy this script to /etc/rc0.d and reboot

#Enable syslog
syslogd -l 8

#Kill telnet service
start-stop-daemon -K -n telnetd

#Run the http server
#httpd -v -h /home/lejos/www/

exit 1;