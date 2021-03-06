Country phone code chooser
==========================

Component given ability to choose phone code from list.

<img src="https://raw.githubusercontent.com/dmitrikudrenko/CountryPhoneCodeChooser/master/media/screenshot_1.png" alt="screenshot 1" width="320">
<img src="https://raw.githubusercontent.com/dmitrikudrenko/CountryPhoneCodeChooser/master/media/screenshot_2.png" alt="screenshot 2" width="320">
<img src="https://raw.githubusercontent.com/dmitrikudrenko/CountryPhoneCodeChooser/master/media/screenshot_3.png" alt="screenshot 3" width="320">
<img src="https://raw.githubusercontent.com/dmitrikudrenko/CountryPhoneCodeChooser/master/media/screenshot_4.png" alt="screenshot 4" width="320">

# Usage
To open country selector screen, call
```Java
CountryCodeChooserActivity.start(this, country, REQUEST_CODE);
```
where ```this``` is activity or fragment. ```country``` can be nullable.

Also override ```onActivityResult```
```Java
CountryCode code = CountryCodeIntent.get(data);
```
where ```data``` is output intent.

Download
--------
[![Download](https://api.bintray.com/packages/dmitrikudrenko/maven/Countryphonecodechooser/images/download.svg)](https://bintray.com/dmitrikudrenko/maven/Countryphonecodechooser/_latestVersion)
[![Build Status](https://travis-ci.org/dmitrikudrenko/CountryPhoneCodeChooser.svg?branch=master)](https://travis-ci.org/dmitrikudrenko/Countryphonecodechooser)

Maven
```xml
<dependency>
  <groupId>io.github.dmitrikudrenko</groupId>
  <artifactId>countryphonecodechooser</artifactId>
  <version>$latestVersion</version>
  <type>pom</type>
</dependency>
```

Gradle
```groovy
compile 'io.github.dmitrikudrenko:countryphonecodechooser:$latestVersion'
```

License
=======

    Copyright 2016 Dmitri Kudrenko.

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
