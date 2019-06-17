# showcase
<img src="https://raw.githubusercontent.com/bilgehankalkan/showcase/master/screenshots/1.png" width="170"/>
<img src="https://raw.githubusercontent.com/bilgehankalkan/showcase/master/screenshots/2.png" width="170"/>
<img src="https://raw.githubusercontent.com/bilgehankalkan/showcase/master/screenshots/3.png" width="170"/>

With **showcase**, you can easily show tooltips. **showcase** will highlight the view and show tooltip on it. You can customize title and description text fields, backgrounds and arrow positions. Also you can have callback when user quits from **showcase**.

# installation
 - To implement **showcase** to your Android project via Gradle, you need to add Jitpack repository to your root build.gradle.
```
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}
```
 - After adding Jitpack repository, you can add **showcase** dependency to your app level build.gradle.
```
dependencies {
    implementation "com.trendyol.showcase:showcase:$showcase_version"
}
```

# usage
You can easily use ShowcaseManager.Builder to create **showcase**.
```
val showcaseManager = ShowcaseManager.Builder()  
    .view(myView)  
    .titleText("Title about myView")  
    .descriptionText("Little bit info for my lovely myView")  
    .titleTextSize(22F)  
    .titleTextColor(ContextCompat.getColor(this, R.color.blue))  
    .backgroundColor(Color.WHITE)  
    .windowBackgroundAlpha(127)  
    .arrowPosition(ArrowPosition.DOWN)
    .cancelListener(object : CancelListener {  
	    override fun onCancel() {  
			//User has exitted from tooltip
	    }  
	})
    .build()  
  
showcaseManager.show(context)
```

# builder configuration
| Usage         | Description | Optional | Default Value |
| ------------- |-------------| ------------- |------------- |
| `builder.view(View)` | view to be focused on | no | |
| `builder.titleText(String)` | text to be showed on top of the tooltip | yes | "" |
| `builder.descriptionText(String)` | description text will be displayed on tooltip | yes | "" |
| `builder.titleTextColor(Int)` | titleText's color | yes | Color.BLACK |
| `builder.descriptionTextColor(Int)` | descriptionText's color | yes | Color.BLACK |
| `builder.titleTextSize(Int)` | titleText's text size in SP | yes | 18 SP |
| `builder.descriptionTextSize(Int)` | descriptionText's text size in SP | yes | 14 SP |
| `builder.backgroundColor(Int)` | background color of tooltip | yes | Color.WHITE |
| `builder.closeButtonColor(Int)` | closeButton's color | yes | Color.BLACK |
| `builder.showCloseButton(Boolean)` | show close button on tooltip | yes | true |
| `builder.ArrowPosition(ArrowPosition)` | arrow can be placed under or over the tooltip | yes | ArrowPosition.AUTO |
| `builder.highlightType(HighlightType)` | view can be highlighted with a circle shape or rectangle | yes | HighlightType.RECTANGLE |
| `builder.cancelListener(CancelListener)` | will be called after user quit from tooltip | yes | null |
| `builder.arrowPercentage(Int)` | arrow position percentage can be decided | yes | null |
| `builder.windowBackgroundColor(Int)` | background of the window's color can be decided | yes | Color.BLACK |
| `builder.windowBackgroundTint(Int)` | alpha value of window's background color | yes | 204 |
| `builder.titleTextSize(Int)` | titleText's text size in SP | yes | 18 |
| `builder.build()` | will return ShowcaseManager instance | no |  |
| `showcaseManager.show(Context)` | show the tooltip with set attributes on  | no |  |
