
# Showcase
<img src="https://raw.githubusercontent.com/Trendyol/showcase/master/screenshots/1.png" width="170"/>	<img src="https://raw.githubusercontent.com/Trendyol/showcase/master/screenshots/2.png" width="170"/>	<img src="https://raw.githubusercontent.com/Trendyol/showcase/master/screenshots/3.png" width="170"/>	<img src="https://raw.githubusercontent.com/Trendyol/showcase/master/screenshots/4.png" width="170"/>	<img src="https://raw.githubusercontent.com/Trendyol/showcase/master/screenshots/5.png" width="170"/>

With **Showcase**, you can easily show tooltips. **Showcase** will highlight the view and show tooltip on it. You can customize title and description text fields, backgrounds and arrow positions. You can also find out how the user closed the showcase and in multi focus situations you can find out which view was clicked.

# Installation
 - To implement **Showcase** to your Android project via Gradle, you need to add Jitpack repository to your root build.gradle.
```
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}
```
 - After adding Jitpack repository, you can add **Showcase** dependency to your app level build.gradle.
```
dependencies {
    implementation "com.trendyol.showcase:showcase:0.9.5"
}
```

# Usage
You can easily use ShowcaseManager.Builder to create **Showcase**.
```
val showcaseManager = ShowcaseManager.Builder()  
    .focus(myView)  
    .titleText("Title about myView")  
    .descriptionText("Little bit info for my lovely myView")  
    .titleTextSize(22F)  
    .titleTextColor(ContextCompat.getColor(this, R.color.blue)) 
    .windowBackgroundAlpha(127)  
    .arrowPosition(ArrowPosition.DOWN)
    .textPosition(TextPosition.START)
    .resId(R.style.Showcase_Theme)
    .build()  

showcaseManager.show(context)
```

You can also provide layout resource id for more complex **Showcase** needs with `customContent`.

```
val showcaseManager = ShowcaseManager.Builder()
    .focus(myView)
    .customContent(R.layout.view_custom_content)
    .arrowPosition(ArrowPosition.UP)
    .build()

showcaseManager.show(context)
```

# Builder Configuration
| Usage                                          | Description                                              | Optional | Default Value                | StyleRes |
|:-----------------------------------------------|:---------------------------------------------------------|:---------|:-----------------------------|:---------|
| `builder.focus(View)`                          | view to be focused on                                    | no       | null                         | no       |
| `builder.focus(Array<View>)`                   | view array to be focused on                              | no       | null                         | no       |
| `builder.resId(Int)`                           | Showcase.Theme style                                     | yes      | null                         | yes      |
| `builder.titleText(String)`                    | text to be showed on top of the tooltip                  | yes      | ""                           | no       |
| `builder.descriptionText(String)`              | description text will be displayed on tooltip            | yes      | ""                           | no       |
| `builder.titleTextColor(Int)`                  | titleText's color                                        | yes      | Color.BLACK                  | yes      |
| `builder.descriptionTextColor(Int)`            | descriptionText's color                                  | yes      | Color.BLACK                  | yes      |
| `builder.titleTextSize(Int)`                   | titleText's text size in SP                              | yes      | 18 SP                        | no       |
| `builder.descriptionTextSize(Int)`             | descriptionText's text size in SP                        | yes      | 14 SP                        | no       |
| `builder.backgroundColor(Int)`                 | background color of tooltip                              | yes      | Color.WHITE                  | yes      |
| `builder.closeButtonColor(Int)`                | closeButton's color                                      | yes      | Color.BLACK                  | yes      |
| `builder.showCloseButton(Boolean)`             | show close button on tooltip                             | yes      | true                         | yes      |
| `builder.arrowResource(Int)`                   | custom icon resource for arrow.                          | yes      | ic_arrow_down or ic_arrow_up | no       |
| `builder.arrowPosition(ArrowPosition)`         | arrow can be placed under or over the tooltip            | yes      | ArrowPosition.AUTO           | no       |
| `builder.arrowPercentage(Int)`                 | arrow position percentage can be decided                 | yes      | null                         | no       |
| `builder.highlightType(HighlightType)`         | view can be highlighted with a circle shape or rectangle | yes      | HighlightType.RECTANGLE      | no       |
| `builder.cancelListener(CancelListener)`       | will be called after user quit from tooltip              | yes      | null                         | no       |
| `builder.windowBackgroundColor(Int)`           | background of the window's color can be decided          | yes      | Color.BLACK                  | yes      |
| `builder.windowBackgroundTint(Int)`            | alpha value of window's background color                 | yes      | 204                          | no       |
| `builder.titleTextSize(Int)`                   | titleText's text size in SP                              | yes      | 18                           | no       |
| `builder.cancellableFromOutsideTouch(Boolean)` | outside touch from tooltip will act as close click       | yes      | false                        | yes      |
| `builder.showcaseViewClickable(Boolean)`       | makes the showcase view clickable or not                 | yes      | false                        | yes      |
| `builder.isDebugMode(Boolean)`                 | tooltip won't be presented                               | yes      | false                        | no       |
| `builder.textPosition(TextPosition)`           | text can be positioning center, end and start            | yes      | TextPosition.START           | no       |
| `builder.imageUrl(String)`                     | show image on tooltip                                    | yes      | null                         | no       |
| `builder.customContent(Int)`                   | show given layout                                        | yes      | null                         | no       |
| `builder.statusBarVisible(Boolean)`            | statusBar visibility of window                           | yes      | true                         | no       |
| `builder.build()`                              | will return ShowcaseManager instance                     | no       |                              |          |
| `showcaseManager.show(Context)`                | show the tooltip with set attributes on                  | no       |                              |          |

# Action Result

By overriding `onActivityResult` you can get feedback based on the types in the ActionType class.

If the actionType is `HIGHLIGHT_CLICKED`, the `KEY_SELECTED_VIEW_INDEX` parameter returns the index of the clicked view. If no view is clicked, the index will be -1.

```
override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (Activity.RESULT_OK == resultCode && REQUEST_CODE_SHOWCASE_CLICKED == requestCode && data != null) {
            val actionType = data.getSerializableExtra(ShowcaseView.KEY_ACTION_TYPE) as ActionType
            val selectedViewIndex = data.getIntExtra(ShowcaseView.KEY_SELECTED_VIEW_INDEX, -1)
            Log.i("MainActivity", "onActivityResult: actionType=${actionType.name} and selected view index=${selectedViewIndex}")
        }
    }
```

License
--------


    Copyright 2021 Trendyol.com

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
