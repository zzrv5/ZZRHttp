# ZZRHttp
在鸿蒙（HarmonyOS）环境下，优雅的完成Http访问网络。
## 序
网络请求在现代的应用开发中必不可少，我们熟知的网络请求框架还真不少，像HttpCient、HttpCient还有volley等，它们确实方便，但鸿蒙还不能使用它们，还好我们有HttpURLConnection。原始的网络访问，再加上多线程，会使程序臃肿。
## 简介
我们希望的网络请求是这样的：

* 隐藏网络访问细节；
* 优雅处理UI更新。

基于以上两点我封装了此工具类，它有效地处理了Http访问，并融入了鸿蒙（HarmonyOS）的线程访问，可以方便我们在请求结束时更新UI。
## 使用步骤：
### 第一步，添加网络权限
在config.json文件中的module中添加，网络访问权限：
```javascript
    "module": {
        "reqPermissions": [{"name":"ohos.permission.INTERNET"}],
        ...
```
### 第二步，确认网络模式
鸿蒙的默认是https访问模式，如果您的请求网址是http开头的，请在config.json文件中的deviceConfig下，添加如下设置：
```javascript
    "deviceConfig": {
        "default": {
            "network": {
                "cleartextTraffic": true
            }
        }
    },
```
### 第三步，添加依赖
在build.gradle文件的dependencies中，添加如下配置，引入ZZRhttp：
```javascript
    dependencies {
        implementation 'com.zzrv5.zzrhttp:ZZRHttp:1.0.1'
        ...
    }
```
### 第四步，进行网络访问
```javascript
    ZZRHttp.get(url, new ZZRCallBack.CallBackString() {
            @Override
            public void onFailure(int code, String errorMessage) {
                //http访问出错了，此部分内容在主线程中工作;
                //可以更新UI等操作,请不要执行阻塞操作。
            }
            @Override
            public void onResponse(String response) {
                //http访问成功，此部分内容在主线程中工作;
                //可以更新UI等操作，但请不要执行阻塞操作。
        }
    });
```


## 常用方法说明
你即可以使用基本的简单请求，也可以使用带有请求参数和请求头的请求，方法如下：

* get(String url, ZZRCallBack callBack) 
* get(String url, Map<String, String> paramsMap, ZZRCallBack callBack)
* get(String url, Map<String, String> paramsMap, Map<String, String> headerMap, ZZRCallBack callBack)
* post(String url, ZZRCallBack callBack) 
* post(String url, Map<String, String> paramsMap, ZZRCallBack callBack)
* post(String url, Map<String, String> paramsMap, Map<String, String> headerMap, ZZRCallBack callBack)
* postJson(String url, String jsonStr, ZZRCallBack callBack)
* postJson(String url, String jsonStr, Map<String, String> headerMap, ZZRCallBack callBack)


## 有问题反馈
在使用中有任何问题，欢迎反馈给我，可以用以下联系方式跟我交流

* 邮件(program.java#qq.com, 把#换成@)
* 微信:zzrizzr

## 关于作者
我是ZZR老师，为什么叫ZZR老师，是因为ZZR是我名字的缩写，在B站、51CTO、今日头条。搜索：ZZR老师     ，都可以找到我。
## 感激
感谢那些致力于鸿蒙生态的默默无闻的人们（排名不分先后,我是按着51CTO HarmonyOS社区的王雪燕女士的朋友圈出场顺序写的人名...）：

高莹、王雪燕、朱友鹏、张荣超、张飞、谢昆明、小阳、韦东山、唐佐林、李宁、董昱老师等，感谢你们的兢兢业业、无私奉献出那么多优质的鸿蒙课程。

今天是2020年10月1日，双节将至，祝你们中秋快乐，祝愿祖国繁荣昌盛。





