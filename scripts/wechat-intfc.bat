@echo off
cd /d %~dp0..
echo  wechat-intfc(Java)
@title wechat-intfc
setLocal EnableDelayedExpansion
set CLASSPATH="conf
for /f "tokens=* delims=" %%a in ('dir "*.jar" /b') do (
   set CLASSPATH=!CLASSPATH!;%%a
)
set CLASSPATH=!CLASSPATH!"
echo %CLASSPATH%
java -Xmx512m -Xmn256m -Djava.ext.dirs=lib -cp %CLASSPATH% -jar wechat-intfc.war
