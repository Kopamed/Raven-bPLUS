echo off
cls
echo A Java 8 installation is required to build.
echo If you have a Java 8 installation, but your JAVA_HOME is set to something else,
echo please set it to your Java 8 installation.
echo.
echo Current JAVA_HOME version:
java -version
echo.
echo Please only continue if your Java version is 8.
echo.
pause
cls
echo You can find the jar in /build/libs once the build is done.
echo.
pause
cls
cls
gradlew.bat clean build
echo.
pause