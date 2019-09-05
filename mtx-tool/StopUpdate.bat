cd /d %~dp0
%1 start "" mshta vbscript:createobject("shell.application").shellexecute("""%~0""","::",,"runas",1)(window.close)&exit

@echo off

net stop wuauserv
sc config wuauserv start= disabled

pause