@echo off  
start cmd /k "taskkill /f /t /im redis-server.exe && taskkill /f /t /im cmd.exe"