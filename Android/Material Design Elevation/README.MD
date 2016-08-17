##Material Design Elevatio介绍(译)

为了避免不同的Material Object在用户做点击,按压等操作时,Material Object动态的改变它的Elevation而发生彼此穿过对方,
谷歌推荐不同的Material Objec要有不同的Elevation


###具体表格
Elevation (dp)|Component
---|---
24|Dialog<br>Picker
23|
22|
21|
20|
19|
18|
17|
16|Nav drawer<br>Right drawer<br>Modal bottom Sheet
15|
14|
13|
12|Floating action button (FAB - pressed)
11|
10|
9|Sub menu (+1dp for each sub menu)
8|Menu<br>Card (picked up state)<br>Raised button (pressed state)
7|
6|Floating action button (FAB - resting elevation)<br>Snackbar
5|
4|App Bar
3|Refresh indicator<br>Quick entry / Search bar (scrolled state)
2|Card (resting elevation)<br>Raised button (resting elevation)<br>Quick entry / Search bar (resting elevation)
1|Switch

###Z轴立体图

![Image](/Android/Material Design Elevation/_001.png)

###eg

####App bar 4dp

![Image](/Android/Material Design Elevation/_002.png)

####Raised button

Resting state: 2dp

Pressed state: 8dp

![Image](/Android/Material Design Elevation/_003.png)

####Floating action button (FAB)

Resting state: 6dp

Pressed state: 12dp

![Image](/Android/Material Design Elevation/_004.png)

####Card

Resting state: 2dp

Picked-up state: 8dp

![Image](/Android/Material Design Elevation/_005.png)

####Menus and sub menus

Menus: 8dp

Sub menus: 9dp (+1 dp for each sub menu)

![Image](/Android/Material Design Elevation/_006.png)

####Dialogs 24dp

![Image](/Android/Material Design Elevation/_007.png)

####Nav Drawer & Right drawer 16dp

![Image](/Android/Material Design Elevation/_008.png)

####Modal bottom sheet 16dp

![Image](/Android/Material Design Elevation/_009.png)

####Refresh indicator 3dp

![Image](/Android/Material Design Elevation/_010.png)

####Quick entry/Search bar

Resting state: 2dp

Scrolled state: 3dp

![Image](/Android/Material Design Elevation/_011.png)

####Snackbar 6dp

![Image](/Android/Material Design Elevation/_012.png)

####Switch 1dp

![Image](/Android/Material Design Elevation/_013.png)