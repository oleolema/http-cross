## 问题
在前端开发中，经常遇到跨域问题，特别是对于抓包玩家来说，好不容易抓到的链接，
没法在浏览器中直接使用。浏览器为了安全起见，不允许跨域访问（ 
[同源政策](https://developer.mozilla.org/zh-CN/docs/Web/Security/Same-origin_policy)
）

## 解决
解决跨域的方法有很多，jsonp，后端设置同源政策等
这里有篇文章用于解决跨域问题，但并不能解决所有情况
[浏览器同源政策及其规避方法](http://www.ruanyifeng.com/blog/2016/04/same-origin-policy.html);

我认为最好的方法就是设置CORS （
[跨域资源共享 CORS 详解](http://www.ruanyifeng.com/blog/2016/04/cors.html)
）
使用一个代理服务器来帮我们请求资源，再将请求到的数据设置好允许跨域并响应给前端，本项目
提供了一个通用的接口，不仅能跨域请求任意资源，还可以设置任意请求头（浏览器为了安全考虑禁用了一些请求头）

## 使用本项目跨域请求

下面列都使用js的xhr演示的正确跨域

POST 请求一个翻译api

```js
var data = JSON.stringify({
    "url": "https://api.interpreter.caiyunai.com/v1/translator",
    "header": {
        "Content-Type": "application/json",
        "Origin": "chrome-extension://jmpepeebcbihafjjadogphmbgiffiajh",
        "x-authorization": "token lqkr1tfixq1wa9kmj9po"
    },
    "entity": {
        "source": "confidence",
        "trans_type": "en2zh",
        "request_id": "web_fanyi",
        "media": "text",
        "os_type": "web",
        "dict": true,
        "cached": true,
        "replaced": true
    },
    "responseType": "json"
});

var xhr = new XMLHttpRequest();
xhr.withCredentials = true;

xhr.addEventListener("readystatechange", function () {
    if (this.readyState === 4) {
        console.log(this.responseText);
    }
});

xhr.open("POST", "http://localhost:8080/api/http/post");
xhr.setRequestHeader("Content-Type", "application/json");
xhr.send(data);
```

GET 请求一张网页

```js
var data = JSON.stringify({
    "url": "https://fanyi.baidu.com/translate"
});

var xhr = new XMLHttpRequest();
xhr.withCredentials = true;

xhr.addEventListener("readystatechange", function () {
    if (this.readyState === 4) {
        console.log(this.responseText);
    }
});

xhr.open("POST", "http://localhost:8080/api/http/get");
xhr.setRequestHeader("Content-Type", "application/json");

xhr.send(data);
```


## API

`http://localhost:8080/api/get` 使用GET请求目标资源，例如请求网页

`http://localhost:8080/api/post` 使用POST请求目标资源

不论是GET资源还是POST资源，发送请求都使用POST，
因为它们都需要请求体传参，POST才有请求体，
且请求头要设置为 `Content-Type: application/json`,
因为请求体是JSON格式

请求体配置：
```json
{
    //URL，必选
    "url": "目标URL",

    //请求头
    "header": {
        "请求头": "请求头",
    },

    //请求体为字符串 get资源会忽略entity
    //"entity": "hello world",

    //请求体为JSON，此时header必须设置 "Content-Type": "application/json"
    "entity": {
        "source": "confidence",
        "trans_type": "en2zh",
        "request_id": "web_fanyi",
        "media": "text",
        "os_type": "web",
        "dict": true,
        "cached": true,
        "replaced": true
    },

    //响应体为JSON 
    "responseType": "json",

    //响应体编码 默认为utf-8
    "responseEncoding": "gbk",
    
    //http 协议  ["http/1.1", "http/2"]   default = "http/1.1"
    "protocol": "http/1.1"

}
```


