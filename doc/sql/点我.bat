
@echo off & title Love & setlocal enabledelayedexpansion
mode con: cols=36 lines=19
:loop
set colors=12345689abcde 
set /a rand=%random%%%13+1
set color=!colors:~%rand%,1! 
color 0%color% 
echo.  
echo. 
echo          ****          ****
echo        *******        *******
echo      ***********    ***********
echo     *************  *************
echo     ****************************
echo     ****************************
echo     ****************************
echo      **************************
echo        ***********************
echo          *******************
echo            ***************
echo              ***********
echo                *******
echo                  ***
echo                   *
ping 127.1 -n 2 >nul
cls
goto loop
echo. & pause > nul
