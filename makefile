all : mvn pkg

mvn :
	mvn clean install

dps :
	jdeps --module-path build/libJarbes/ --list-deps build/Jarbes.jar build/libJarbes/* | grep -E "java\.|javax\.|jdk\."

dpsAll :
	jdeps --module-path build/libJarbes/ --list-deps build/Jarbes.jar build/libJarbes/*

bldJre :
	rm -rf jre
	jlink --output jre --add-modules java.base,java.desktop,java.net.http,java.xml,jdk.jfr,jdk.jsobject,jdk.unsupported,jdk.xml.dom --strip-debug --strip-native-commands --compress 2 --no-header-files --no-man-pages

bldJreAll :
	rm -rf jre
	jlink --output jre --add-modules ALL-MODULE-PATH --strip-debug --strip-native-commands --compress 2 --no-header-files --no-man-pages

pkg :
	jpackage --runtime-image jre --input build --dest dist --java-options '--enable-preview' --main-jar Jarbes.jar --name "Jarbes" --app-version 0.1.0 --icon assets/pin/jarbes/jarbes.ico --win-dir-chooser --win-menu --win-menu-group "Pointel"