# LineGraph
Android自定义折线图

![](http://upload-images.jianshu.io/upload_images/2746415-44f9776af1fe3427.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)



## Installing

Users of your library will need add the jitpack.io repository:

```gradle
allprojects {
 repositories {
    jcenter()
    maven { url "https://jitpack.io" }
 }
}
```

and:

```gradle
dependencies {
    compile 'com.github.zhouchupen:LineGraph:v1.0'
}
```

Note: do not add the jitpack.io repository under `buildscript` 

## Adding a sample app 

If you add a sample app to the same repo then your app needs to depend on the library. To do this in your app/build.gradle add a dependency in the form:

```gradle
dependencies {
    compile project(':library')
}
```

where 'library' is the name of your library module.

## Using

You may need this to use the calendar.  Put this into your xml file:
```xml
<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:layout_width="match_parent"
    android:layout_height="match_parent">

    <com.scnu.zhou.widget.LineGraph
        android:id="@+id/lineGraph"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_centerInParent="true"
        app:lineColor="#2680ef"/>
</RelativeLayout>
```
And put this into your activity file:
```java
LineGraph lineGraph = (LineGraph) findViewById(R.id.lineGraph);

LineGraph.GraphData data1 = new LineGraph.GraphData("2010", 50);
LineGraph.GraphData data2 = new LineGraph.GraphData("2011", 120);
LineGraph.GraphData data3 = new LineGraph.GraphData("2012", 100);
LineGraph.GraphData data4 = new LineGraph.GraphData("2013", 110);
LineGraph.GraphData data5 = new LineGraph.GraphData("2014", 150);
LineGraph.GraphData data6 = new LineGraph.GraphData("2015", 80);
LineGraph.GraphData data7 = new LineGraph.GraphData("2016", 100);
LineGraph.GraphData data8 = new LineGraph.GraphData("2017", 170);
List<LineGraph.GraphData> datas = new ArrayList<>();
datas.add(data1);
datas.add(data2);
datas.add(data3);
datas.add(data4);
datas.add(data5);
datas.add(data6);
datas.add(data7);
datas.add(data8);

lineGraph.setGraphData(datas);
```
