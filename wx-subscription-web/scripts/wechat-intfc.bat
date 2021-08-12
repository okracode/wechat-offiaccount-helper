@echo off
cd /d %~dp0..
echo  wx-subscription-web(Java)
@title wx-subscription-web
setLocal EnableDelayedExpansion
set CLASSPATH="conf
for /f "tokens=* delims=" %%a in ('dir "*.jar" /b') do (
   set CLASSPATH=!CLASSPATH!;%%a
)
set CLASSPATH=!CLASSPATH!"
echo %CLASSPATH%
java -Xmx512m -Xmn256m -Djava.ext.dirs=lib -cp %CLASSPATH% -jar wx-subscription-web-1.0.0.war
