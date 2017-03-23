##


Android ��Դ�ļ��н���(��)


###


����

����������ҪҪ̽�ֵ�������:Android ϵͳ��������ò��������Դ?

Ҫ���������������,��д����Ļ����,������Ҫ�����˽����¼�������.

* Android ����ԴĿ¼������ʲô,��������Android�������Զ�������:drawable,layout,values.����������ʲôĿ¼?

* ��ЩĿ¼ drawable-400*800,drawable-400x800,drawable-v3�ĺ�׺������ʲô?

* Android ϵͳ������Դ�ļ�/���ֵĹ���(���ȼ�)��ʲô?

###


��

��ʵ��Щ���ⶼ����ͨ�����ǵ�SDK���ҵ�.Ӣ�ıȽϺõ�ͬѧ����ֱ�ӿ�SDkĿ¼�µ�

/docs/guide/topics/resources/providing-resources.html

���˵���ĵ��ṩ�˱Ƚ���ϸ��˵���ͽ���.��Ҫ�˽����Ķ���������ͨ������ĵ�.

###


��ԴĿ¼����


��������Android�������Զ�������:drawable,layout,values,

��sdk�ĵ�˵����,����ʶ���Ŀ¼һ�������¼���:

####


animator ��ŵ���property animations xml�ļ�

####


anim ����xml�ļ�.property animatons��xmlҲ���Դ����anim����.��animator��Ŀ¼�µ�xml�нϸߵ�����Ȩ.

####


color ��ɫxml�ļ�. (ע����colors������,colors.xml��һ���ļ�,�����valuesĿ¼��,�Ǵ��һЩ�������ɫֵ.�����color����ǵ�һЩselector)


####


drawable


Bitmap files (.png, .9.png, .jpg, .gif) or XML files that are compiled into the following drawable resource subtypes:

* Bitmap files .png��ʽͼƬ
* Nine-Patches (re-sizable bitmaps ) .9.png��ʽͼƬ
* State lists ״̬�б��xml
* Shapes  shape��xml
* Animation drawables ����ͼƬ,����gif
* Other drawables ����ͼƬ

####


layout ����


####


menu �˵�(��Menu/ActionBar/ToolsBar�����øò���)


####


raw ��������Դ(�������ŵ���Դ���ṩid,��assets�Ĳ����ṩid)


####


values


* arrays.xml for resource arrays (typed arrays). ����
* colors.xml for color values ��ɫֵ
* dimens.xml for dimension values. dp.sp
* strings.xml for string values. �ַ���
* styles.xml for styles. ���
* xml/ xml�ļ�

###


��ԴĿ¼��׺����

��android�Լ����ɵ�Ŀ¼��,���ǿ��Կ������¼���Ŀ¼

![Image](/Android/Resources%20Directory/_001.png)

![Image](/Android/Resources%20Directory/_002.png)

��ЩĿ¼�ĵĺ�����Բο�sdk�ĵ��ĵڶ��ű�.�ֱ���:
####


MCC and MNC(�����˼��˵ͨ�ŷ�ʽ.�����ƶ�����ͨ��ͨ�ŷ�ʽ,3G,4Gʹ�õļ���Ҳ�ǲ�һ����)


####


Language and region(����������)

* en
* fr
* en-rUS
* fr-rFR
* fr-rCA

The language is defined by a two-letter ISO 639-1 language code, optionally followed by a two letter ISO 3166-1-alpha-2 region code (preceded by lowercase "r")
����r����˼����˵ʹ�õ�������һ�����Ա�׼.

####


Layout Direction(Layout����,�Ǵ�����,���Ǵ��ҵ���,���簢����ʹ�õ��ǴӴ��ҵ����)

eg:
```
res/
    layout/   
        main.xml  (Default layout)
    layout-ar/  
        main.xml  (Specific layout for Arabic)
    layout-ldrtl/  
        main.xml  (Any "right-to-left" language, except
                  for Arabic, because the "ar" language qualifier
                  has a higher precedence.)
```
����layout-arר��ָ���ǰ���������,layout-ldrtl���˰�����������������ҵ�����Ķ���ʽ�ĵ���,��Ϊ������������ar��־ӵ�и��ߵ�����Ȩ


####


smallestWidth(��С��Ļ���)

```
sw320dp
sw600dp
sw720dp
```
Ps:��Ļ����ĸı䲻��Ӱ��������С���.

####


Available width(���ÿ��,��Ⱥ͸߶ȿ��ܻ���Ϊ��Ļ����ת���仯,����ֵ���ǵ�ǰ״̬����Ļ�Ŀ��)

```
w720dp
w1024dp
```

####


Available height(���ø߶�,ͬ��)

```
h720dp
h1024dp
```

####


Screen size(��Ļ�ߴ�)��Ϊ���¼���:


* small: 320x426 dp,QVGA
* normal: 320x470 dp,HVGA
* large: 480x640 dp,VGA
* xlarge:  720x960 dp, HVGA

####


Screen aspect (������һ������) ����������:


* long: Long screens, such as WQVGA, WVGA, FWVGA
* notlong: Not long screens, such as QVGA, HVGA, and VGA

####


UI mode (UI ģʽ)����˵����ʲô�豸,��tv,���ǿɴ����豸,���ǳ���,��������ϵͳ��.

* car
* desk
* television
* appliance
* watch

####


Night mode (ҹ��ģʽ)�ְ���һ������,����һ������

* night: Night time
* notnight: Day time

####


Screen pixel density (dpi)(��Ļ�ܶ�)

* ldpi 120dpi
* mdpi 160dpi
* hdpi 240dpi
* xhdpi 320dpi API8
* xxhdpi 480dpi API16
* xxxhdpi 640dpi API18
* nodpi ����㲻��Ŵ�/��С��ͼƬ
* tvdpi ����mdpi��hdpi֮��.����tv֮����豸

####


Touchscreen type  (��Ļ��������)

* notouch:  û�д�����
* finger: �д�����

####


Keyboard availability  ���̿�����

* keysexposed ��Ӳ������
* keyshidden �п����ص�Ӳ������
* keyssoft û��Ӳ������(�����)

####


Primary text input method ��Ҫ�����뷽��

* nokeys: û���������
* qwerty: ��һ���������,���۶����û���˵�Ƿ�ɼ�.
* 12key: ��һ��12�����ļ���,���۶����û���˵�Ƿ�ɼ�.

####


Navigation key availability ϵͳ�������Ƿ����

* navexposed: �û�����ʹ��ϵͳ������
* navhidden: ������ʹ��

####


Primary non-touch navigation method

* nonav: Device has no navigation facility other than using the touchscreen.
* dpad: Device has a directional-pad (d-pad) for navigation.
* trackball: Device has a trackball for navigation.
* wheel: Device has a directional wheel(s) for navigation (uncommon).

####


Platform Version (API level) ƽ̨�汾,���ܵ�API�汾ʹ�ò�ͬ�Ĳ���.


����5.0���ϵ�ϵͳ����ʹ�ÿ�Ƭ������.

* v3
* v4
* v7

####





![Image](/Android/Resources%20Directory/_001.png)

![Image](/Android/Resources%20Directory/_002.png)

�����ٿ��⼸��Ŀ¼

* hdpi,ldpi�ֱ��Ӧ���ǲ�ͬ��dpi��Ļ�ܶ�
* v11,v14��Ӧ���ǲ�ͬ��SDK�汾,����ͬ��Android�豸�汾.
* w820dp,��ָ��ǰ��Ļ��ȴ���820dp��ʱ�����øò���(�����û���ת����Ļ,���ܼ��ز�ͬ�Ĳ���)


###


��ԴĿ¼���õ����ȼ�


��������ô����Ŀ¼

values-en//Ӣ��

values-hdpi//�߷ֱ���

����������һ̨����Anroid�豸����������������,�ǵ�����������enĿ¼�µ���Դ.����hdpiĿ¼�µ���?

�����ĵ��ĺ������ӽ���.����ôһ��ͼ:

![Image](/Android/Resources%20Directory/_003.PNG)

���еڶ�������˵:MCC first,then MNC,then language,and so on.

��˼��MCC�ǵ�һ��,Ȼ�������MNC,Ȼ����language.

Ҳ������,��Դ���õ����ȼ��ǰ���������ܵ����ȼ�.MCC������һ,vƽ̨�������.
���Լ���һ̨�豸ͳһ����Ӣ��,�߷ֱ���������Ҫ��,��ô����������layout-en����ļ�


###


�����Ϸֱ��ʱ��


�ֱ���|�ܶ�|����
---|---|---
1440 * 2560|xxxhdpi(640)|4
1080 * 1920|xxhdpi(480)|3
720 * 1280|xhdpi(320)|2
480 * 800|hdpi(240)|1.5
320 * 480|mdpi(160)|1
240 * 400|ldpi(120)|0.75
