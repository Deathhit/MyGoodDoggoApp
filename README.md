# MyGoodDoggoApp
Android application architecture sample.
# Goal
Create a code structure which has the benefits described in [Guide to app architecture](https://developer.android.com/topic/architecture) and [Guide to Android app modularization](https://developer.android.com/topic/modularization).
# The Dog Api
This project requires an api key from [The Dog Api](https://www.thedogapi.com/) to make paging work.  
Replace the value of the metadata with your api key.
```
        <!--Dog Api Meta-->
        <meta-data
            android:name="@string/data_source_dog_api_api_key"
            android:value="@string/app_api_key_dog_api" /> <!--todo provide your own api key!-->
```
# License
```
#Note
For demonstration purpose, the optional parts like the use cases are not skipped in the project. For simple scenarios, repository can be used directly in view model.
Copyright 2021 Deathhit

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
