#
DrawComplexView

View will open slowly and lead to ANR when app UI design a very complex layout.

But we can't change the design in most situation.

We can draw a custom view replace the complex layout.

##
Demo

![Image](/Android/DrawComplexView/_001.jpg)

this layout is complex with android provide view,so
we can draw a custom view replace it.

##
Use

in xml
```xml
<com.yourPackageName.BarChars
    android:id="@+id/bc_match"
    android:layout_width="match_parent"
    android:layout_height="280dp"/>
```

in Activity
```java
BarChars barChars = (BarChars)view.findViewById(R.id.bc_match);
addTestData();
barChars.setData(topList,leftList,rightList);
```

```java
private void addTestData() {
    topList = new ArrayList<>();
    topList.add("欧冠");
    topList.add("英超");
    topList.add("法甲");
    topList.add("德甲");
    topList.add("意甲");

    leftList = new ArrayList<>();
    leftList.add(29);
    leftList.add(16);
    leftList.add(11);
    leftList.add(9);
    leftList.add(13);

    rightList = new ArrayList<>();
    rightList.add(23);
    rightList.add(8);
    rightList.add(8);
    rightList.add(3);
    rightList.add(6);
}
```

##
Result

![Image](/Android/DrawComplexView/_002.jpg)

Now, the first and second graph have one view only.

##
License

```
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

