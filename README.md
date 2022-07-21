# SmartSitter

SmartSitter is a client-server solution which will allow a student to see available sits in the lab using a graphical interactive UI. Choose a time and place and reserve it in the DB.

## Running the Server
+ The server was developed in python 3.8
+ Before running the server, you must receive from the developer a configuration file with the details required to connect to the DB that was set up for this project. You must also get a specific permission to access the DB. 
+ To run the server use mainServer.py
+ Once the server is listening, it prints the address the client will need to connect to. 

## Running the Client
+ The code is in kotlin.
+ I run it using Android studio in IntelliJ version 2019.3 (using the Gradle build).
+ For best user experience use the emulator of Android 10.0 (Q) - API 29, "Pixel 3 XL API 29"
+ Before running the client, you must change the field "serverURL" in the file "SmartSitterClient/app/src/main/java/com/example/smartsitterclient/SimpleDataClasses.kt" to the address of the server (see "**Running the server**").

## Demo video
The link to the video: https://github.com/LilachGr/SmartSitterProject/blob/master/SmartSitterDemo.mp4

