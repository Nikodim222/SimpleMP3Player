@Echo Off
chcp 1251

Set JAVA_HOME="%ProgramFiles%\Java\jdk-18.0.1.1"
Set JRE_HOME=%JAVA_HOME%
Set JDK_HOME=%JAVA_HOME%
Set PATH_TO_FX="%USERPROFILE%\Downloads\Program Files\javafx-sdk-21\lib"
Set PATH=%JAVA_HOME%\bin;%PATH%

cd /D "%~dp0"

java -classpath %PATH_TO_FX% --module-path %PATH_TO_FX% --add-modules javafx.controls,javafx.fxml,javafx.media --add-exports java.base/jdk.internal.misc=ALL-UNNAMED -jar ./SimpleMP3Player.jar
