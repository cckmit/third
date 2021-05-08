#!/bin/bash
A=`ps -C nginx --no-header |wc -l`        
if [ $A -eq 0 ];then                            
    /usr/local/webserver/nginx/sbin/nginx                #÷ÿ∆Ùnginx
    if [ `ps -C nginx --no-header |wc -l` -eq 0 ];then    #nginx÷ÿ∆Ù ß∞‹
        exit 1
    else
        exit 0
    fi
else
    exit 0
fi