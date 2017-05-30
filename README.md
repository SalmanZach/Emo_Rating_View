## Emo_Rating_View
an implementation for custom rating view with multiple expressions to express your users feedback.
![ic_launcher](https://cloud.githubusercontent.com/assets/11782272/26569035/b02c497c-4525-11e7-9687-d5dde7c72a06.png)

### API 15+
### Support Vector Assets.

## Example.
![emo](https://cloud.githubusercontent.com/assets/11782272/26559532/14a0eeec-4465-11e7-8da6-ef120b6341ea.gif)

set Default1,Default2 as Unselected Views and set Rate1,Rate2 as Selected Views 

           <com.salman.zach.emorater.EmoRatingView
            android:id="@+id/rateBar"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_centerInParent="true"
            app:horizontalSpace="12dp"
            android:layout_margin="20dp"
            android:layout_marginBottom="40dp"
            app:Default1="@drawable/default_rate1"
            app:Default2="@drawable/default_rate2"
            app:Default3="@drawable/default_rate3"
            app:Default4="@drawable/default_rate4"
            app:Default5="@drawable/default_rate5"
            app:Rate1="@drawable/rate1"
            app:Rate2="@drawable/rate2"
            app:Rate3="@drawable/rate3"
            app:Rate4="@drawable/rate4"
            app:Rate5="@drawable/rate5"
            app:rating="3" />           

           
set Selection at compile time as well as run time

        app:rating="3"
        emoRatingView.setRating(5);
        
        
### Add Listner

    emoRatingView.setOnRatingSliderChangeListener(new IEmoRatingListener() {
            @Override
            public void onRatingFinal(int rating) {
              emoRatingView.getRating()
            }

            @Override
            public void onRatingCancel() {

            }

            @Override
            public void onRatingPending(int rating) {

            }
        });

License
-------

    Copyright 2017 Salman Zach

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
