#!/usr/bin/sh
export JAVA_HOME=/usr/lib/jvm/jdk-19
export JRE_HOME=$JAVA_HOME
export JDK_HOME=$JAVA_HOME
export PATH_TO_FX=/opt/javafx-sdk-21/lib
export PATH=$JAVA_HOME/bin:$PATH

# полный путь до скрипта
export ABSOLUTE_FILENAME=`readlink -e "$0"`
# каталог, в котором лежит скрипт
export DIRECTORY=`dirname "$ABSOLUTE_FILENAME"`

cd $DIRECTORY

java -classpath $PATH_TO_FX --module-path $PATH_TO_FX --add-modules javafx.controls,javafx.fxml,javafx.media --add-exports java.base/jdk.internal.misc=ALL-UNNAMED -jar ./SimpleMP3Player.jar
