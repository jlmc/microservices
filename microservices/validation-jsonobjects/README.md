curl -i -v -X POST -H "Content-Type: application/json" --data @request.json http://localhost:8080/validation-jsonobjects-0.0.1-SNAPSHOT/resources/messages
* Adding handle: conn: 0x2b04970
* Adding handle: send: 0
* Adding handle: recv: 0
* Curl_addHandleToPipeline: length: 1
* - Conn 0 (0x2b04970) send_pipe: 1, recv_pipe: 0
* About to connect() to localhost port 8080 (#0)
*   Trying 127.0.0.1...
* Connected to localhost (127.0.0.1) port 8080 (#0)
> POST /validation-jsonobjects-0.0.1-SNAPSHOT/resources/messages HTTP/1.1
> User-Agent: curl/7.33.0
> Host: localhost:8080
> Accept: */*
> Content-Type: application/json
> Content-Length: 18
>
* upload completely sent off: 18 out of 18 bytes
< HTTP/1.1 400 Bad Request
HTTP/1.1 400 Bad Request
< Connection: keep-alive
Connection: keep-alive
< X-Powered-By: Undertow/1
X-Powered-By: Undertow/1
* Server WildFly/10 is not blacklisted
< Server: WildFly/10
Server: WildFly/10
< validation-exception: true
validation-exception: true
< Content-Type: text/plain
Content-Type: text/plain
< Content-Length: 85
Content-Length: 85
< Date: Thu, 07 Jul 2016 22:49:36 GMT
Date: Thu, 07 Jul 2016 22:49:36 GMT