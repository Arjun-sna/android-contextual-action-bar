# Contextual ActionBar [![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-Contextual%20ActionBar-brightgreen.svg?style=flat)](http://android-arsenal.com/details/1/4115)
A contextual actionbar library with many customisation option.

## Demo
<img src="https://rawgit.com/Arjun-sna/Arjun-sna.github.io/master/raw/cab.gif" width="200" />

## Installation

Add dependency in build.gradle

```
repositories {
        jcenter()
}

dependencies {
    compile 'in.arjsna:cablib:0.0.1'
}
```

## Usage

Include a `ViewStub` in the layout file where the `Contextual Actionbar` need to be shown

```
<ViewStub
    android:id="@+id/cab_stub"
    android:layout_width="match_parent"
    android:layout_height="?actionBarSize" />
```

Then customise the `Contextual Actionbar` and show it whenever needed

```
ContextualActionBar customCab = new ContextualActionBar(MainActivity.this, R.id.cab_stub)
            .setMenu(R.menu.sample_menu) //The menu to inflated
            .setCloseDrawableRes(R.drawable.up_button_white) //Home button drawable
            .setTitle(getResources().getString(R.string.cab_text)) //Title string
            .setLayoutAnim(R.anim.layout_anim) //Enter Animation
            .setTitleTypeFace(Typeface.createFromAsset(MainActivity.this.getAssets(), "fonts/Avenir-Medium.ttf"))
            .start(cabCallback);

private CabCallback cabCallback = new CabCallback() {
        @Override
        public boolean onCreateCab(ContextualActionBar cab, Menu menu) {
            return true;
        }

        @Override
        public boolean onCabItemClicked(MenuItem item) {
            switch (item.getItemId()){
                case R.id.delete_chat_heads:
                    Toast.makeText(MainActivity.this, "Deleted", Toast.LENGTH_SHORT).show();
                    customCab.finish();
                    return true;
                case R.id.select_all:
                    Toast.makeText(MainActivity.this, "Selected all", Toast.LENGTH_SHORT).show();
                    return true;
                case R.id.select_none:
                    Toast.makeText(MainActivity.this, "Deselected all", Toast.LENGTH_SHORT).show();
                    return true;
                default:
                    return false;
            }
        }

        @Override
        public boolean onDestroyCab(ContextualActionBar cab) {
            return true;
        }
    };

```

The above initialisation needs to be done only once for a view. You can hide and restore later the same by

```
customCab.restore();
```

##### Note
Forked and enhanced the code from this project
https://github.com/afollestad/material-cab

License
=======

Licensed under the Apache License, Version 2.0 (the "License"); you may not use this work except in compliance with the License.
You may obtain a copy of the License in the LICENSE file, or at:

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.

