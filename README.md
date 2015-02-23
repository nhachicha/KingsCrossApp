***This document is under construction***

King's Cross App
===============

What is this?
------------

This is a sandbox app that allows us to experiment with new API & libraries, the purpose of the app is to show reviewed Point Of Interests, around King's Cross (our new office at The App Business)

Build & Install 
---------------

```bash
./gradlew installDebug
````

Libraries 
---------
The project uses `RxJava` & `Retrofit` for network/REST calls , `Dagger` for *Dependency Injection* & `SnappyDB` for the *NoSQL* persistence
 
    - 'com.etsy.android.grid:library:1.0.5'
    - 'com.squareup.picasso:picasso:2.3.4'
    - 'com.squareup.retrofit:retrofit:1.8.0'
    - 'com.squareup.okhttp:okhttp:2.0.0'
    - 'io.reactivex:rxandroid:0.22.0'
    - 'com.snappydb:snappydb-lib:0.5.0'
    - 'com.esotericsoftware.kryo:kryo:2.24.0'
    - 'com.squareup.dagger:dagger:1.2.2'
    - 'com.crashlytics.sdk.android:crashlytics:2.1.0

Icon & Design
-------------
Logo & design by [TAB](https://dribbble.com/theappbusiness)
Under [CC attribution](https://creativecommons.org/licenses/by/3.0/)


License
=======

    Copyright 2015 The App Business

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.

