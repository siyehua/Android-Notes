�����ʹ��NetWorkImageView��ʹ��,�������ͼƬ�ĵ�ַֻҪ����ȷ,�ͻᱨ��ָ��,�������ͼ 
������־
![Image](/Android/NetWorkImagView_Pointer/_001.png)



�ⲻ�ǿ��˵�ô,ͼƬ�ĵ�ַ���ǿյ�,ֻ��һ������ĵ�ַ,�ͱ�������� 
���ձ������ʾ,�ҵ�Volley����е�Request���е�129��(ʹ��jd-gui�鿴Volley��jar��) 
Volley����ط�
![Image](/Android/NetWorkImagView_Pointer/_002.png)


�������Url�϶����ǿյ�,���������Ŀ�����ִ�е�Ӧ���Ǻ����Ǹ�
```JAVA
Uri.parse(url).getHost().hashCode()
```

�⼸���������ĸ�����ָ�����,����һ��

```JAVA
Log.e("siyehua1", Uri.parse("2312").toString() + "");
Log.e("siyehua2", Uri.parse("2312").getHost() + "");
Log.e("siyehua3", Uri.parse("2312").getHost().hashCode() + "");
```

���׵����е��ڶ����Ϳ�ָ����,ԭ���������������

![Image](/Android/NetWorkImagView_Pointer/_003.png)

ȷ������ķ���.ȥAPI�￴��һ��getHost()�������

```JAVA
getHost()
public abstract String getHost()Gets the encoded host from the authority for this URI. For example, if the authority is "bob@google.com", this method will return "google.com". 
return��
the host for this URI or null if not present//��������ھͷ��ؿ�
```

����,�ҵ����������. 
����:������һ������ĵ�ַ,NetWorkImageViewӦ�ü���һ�������ͼƬ(�����������ô����ͼƬʱ) 
�����Ǳ���. 
�������:�ж��ܷ�õ����URI��host,������ܵõ�,��˵���ܼ���ͼƬ,���ʱ������Ҫ���ü���ʧ�ܷ���
```JAVA
if (Uri.parse(url).getHost() == null) {// ������ܵõ�ͼƬ���Ӷ�Ӧ��host
    netImageView.setDefaultImageResId(R.drawable.error);//����ͼƬλ����ʧ�ܵ�ͼƬ
    return;
}
//////////////////////////////����������ʹ��NetWrok����ͼƬ////////////////////////////
if (imageLoader == null)
    imageLoader = new ImageLoader(requestQueue, new BitmapCache());
netImageView.setDefaultImageResId(R.drawable.ic_launcher);// ���ü����е�ͼƬ
netImageView.setErrorImageResId(R.drawable.error);// ���ü���ʧ�ܵ�ͼƬ
netImageView.setImageUrl(url, imageLoader);// ����ͼƬ
```
