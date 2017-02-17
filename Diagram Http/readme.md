#图解Http

###分层
TCP/IP协议族按层次分别分为4层:应用层,传输层,网络层和数据链路层.

应用层:TCP/IP协议族内预存了各类通用的应用服务.比如FTP,DNS,HTTP

传输层:TCP,UDP

网络层:网络层用来处理网络上流动的数据包.数据包是网络传输的最小数据单位

链路层:用来处理链接网络的硬件部分.

![Image](/Diagram Http/_001.jpg)
###三次握手,四次挥手

![Image](/Diagram Http/_002.jpg)

![Image](/Diagram Http/_003.jpg)

![Image](/Diagram Http/_004.jpg)

三次握手目的是为了确认这是一个有效的连接,为了防止已经失效的请求到服务器而产生错误.

四次挥手的目的是因为TCP是全双工模式,也就是它们必须相互断开通道连接.

###URI与URL
URI就是某个协议方案表示的资源的的定位标识符,例如HTTP协议,ftp,flie,ContentProvider等.URI是个纯粹的句法结构，用于指定标识Web资源的字符串的各个不同部分。URL是URI的一个特例，它包含了定位Web资源的足够信息.URI 是统一资源标识符，而 URL 是统一资源定位符。因此，笼统地说，每个 URL 都是 URI，但不一定每个 URI 都是 URL。这是因为 URI 还包括一个子类，即统一资源名称 (URN)，它命名资源但不指定如何定位资源。

###HTTP方法
方法名|描述
---|---
GET|获取资源,用来请求访问已经被URI识别的资源.
POST|传输实体主体.
PUT|传输文件
HEAD|获取报文首部,与GET区别是不返回报文主体部分
DELETE|删除文件,因为没有经过验证,一般不用
OPTIONS|询问支持的方法
TRACE|追踪路径
CONNECT|要求用隧道协议连接代理

###状态码
|类别|原因短语
---|---|---
1XX|Informational(信息性状态码)|接受的请求正在处理
2XX|Success(成功性状态码)|请求正常处理完毕
3XX|Redirection(重定向状态码)|需要进行夫家操作以完成请求
4XX|Client Error(客户端错误状态码)|服务器无法处理请求
5XX|Server Error(服务器错误状态码)|服务器处理请求出错

类别|结果
200|OK
201|No Content(没有内容更新)
206|Partial Content(范围请求)
301|Moved Permanently(永久性重定向)
302|Found(临时性重定向,询问是否要访问另外一个URI)
303|See Other(明确表示需要访问另一个URI)
304|Not Modified(服务器资源未改变,可直接使用客户端未过期缓存)
307|Temporay Redirect(临时重定向,与302类似)
400|Bad Request(请求语法错误)
401|Unauthorized(需要HTTP认证)
403|Forbidden(无权访问)
404|Not Found(访问URI不存在)
500|Internal Server Error(服务器内部错误)
503|Server Unavailable(服务器繁忙)

###HTTP首部
![Image](/Diagram Http/_005.jpg)

![Image](/Diagram Http/_006.jpg)

![Image](/Diagram Http/_007.jpg)

![Image](/Diagram Http/_008.jpg)


###HTTPS
HTTP+加密+认证+完整性保护=HTTPS,HTTP加上加密处理和认证以及完整性保护后即是HTTPS.

HTTPS是身披SSL外壳的HTTP,HTTPS本身并不是一种新的协议,只是HTTP通信接口部分用SSL和TLS协议代替而已.
通常,HTTP直接和TCP通信.当使用SSL时,则演变成先和SSL通信,再由SSL和TCP通信了.

![Image](/Diagram Http/_009.jpg)

HTTPS采用共享密钥加密和公开密钥加密亮着并用的混合加密机制.若密钥能够事业线安全交互,那就使用共享密钥,
否则就考虑使用公开密钥的方式.但公开公密钥的速度要比共享密钥加密速度要慢.

因为公开密钥有可能是已经是被替换掉了,所以必须先嵌入由数字证书认证机构颁发的公开密钥,再进行加密.


