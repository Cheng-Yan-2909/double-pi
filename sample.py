#!/usr/bin/python

from gpiozero import *
from time import *
import time

t1 = time.localtime(time.time())

f = LED("GPIO22")
b = LED("GPIO23")

print "running\n"

while time.localtime(time.time()).tm_min < t1.tm_min + 1:
    current_sec = time.localtime(time.time()).tm_sec
    sec_tenth = float(float(current_sec) / float(10))
    print "sec: ", current_sec, " / ", sec_tenth, "\n"
    if round(sec_tenth) >  (sec_tenth):
        f.off()
        b.on()
    else:
        b.off()
        f.on()
    sleep(1)

print "done\n"