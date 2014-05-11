#! /bin/sh

#NOTE: Copy this script to /etc/rc0.d and reboot

#Enable syslog
syslogd -l 8 -O /var/backups/messages -s 0

#Kill telnet service
start-stop-daemon -K -n telnetd

#Kill telnet bluetooth (Crash the LeJOS menu)
#start-stop-daemon -K -n bluetoothd

#Run the http server
#httpd -v -h /home/lejos/www/

exit 1;