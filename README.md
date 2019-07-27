# PhoneSaver

This app turns off a device after every 5 minutes IF the device is not in a CALL. It starts itselft on BOOT and keeps running itself in the backround as a Service.
The app works in relation with Tasker automation app w/ AutoInput plugin. It is necessary to create a Tasker Task named "phonesaver" which will do the actual phone shut down based on Power Dialogue selection.

Starting from Android O it is required to take in App permission from user to listed to a CALL broadcast. 
Additionally following permission is needed for Tasker App external Access : 

<uses-permission android:name="net.dinglisch.android.tasker.PERMISSION_RUN_TASKS" />

To invoke Takser Tasks, we need to follow the procedures described here : 
https://tasker.joaoapps.com/invoketasks.html

A simple sharedPreferences varialbe is used across Android Services and Broadcast Receivers to check if the user is in CALL or not. 

To keep running a neverending service following example is used: 
https://fabcirablog.weebly.com/blog/creating-a-never-ending-background-service-in-android#
