kafka-demo
==========
A simple project intended to demo Kafka and get developers up and running quickly

>*Note*:
>   This project uses [Gradle](http://www.gradle.org). You must install [Gradle(2.5)](http://www.gradle.org/downloads).
>   If you would rather not install Gradle locally you can use the [Gradle Wrapper](http://www.gradle.org/docs/current/userguide/gradle_wrapper.html) by replacing all refernces to ```gradle``` with ```gradlew```.

How To Build:
-------------
1. Execute ```gradle build```
2. Find the artifact jars in './build/libs/'

Intellij Project Setup:
-----------------------
1. Execute ```gradle idea```
2. Open project folder in Intellij or open the generated .ipr file

>*Note*:
>   If you have any issues in Intellij a good first troubleshooting step is to execute ```gradle cleanIdea idea```

Eclipse Project Setup:
----------------------
1. Execute ```gradle eclipse```
2. Open the project folder in Eclipse

>*Note*:
>   If you have any issues in Eclipse a good first troubleshooting step is to execute ```gradle cleanEclipse eclipse```

Key Kafka Links:
----------------
- [Downloads](http://kafka.apache.org/downloads.html)
- [Configuration](http://kafka.apache.org/documentation.html)

Using The Project:
------------------

>*Note*:
>  This guide has only been tested on Mac OS X and may assume tools that are specific to it. 
>  If working in another OS substitutes may need to be used but should be available.

### Step 1 - Build the Project: ###
1. Run ```gradle build```

...TODO...