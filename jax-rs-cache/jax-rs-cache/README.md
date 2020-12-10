# Scaling JAX-RS Applications (Http Cache and Concurrency)

## Caching

#### Before we start

> "The quickest request will always be the one that doesn't get executed"

- Concepts
    - Origin Server = Api Server
    - Http-Cache Types
        - Local
            - The browser can cache http response of requests locally
        - Server Proxy
            - Intermediate servers between API and client


- Benefits
    - Reduces the number of bytes on the network, the network consumption is lower.
    - Reduces latency, important for mobile customers, for example.
    - Reduces load on the API server
    - Hides network problems, For example if the Origin Server goes down for a period, resources that are cached are
      still available.


- Warning
    - We can not cache resources when customers do not support/tolerate resource landings.
    - When the data is changed with a very high frequency.


- Basic/quickly startup
    - Enable caching with the Http Header `Cache-Control` and a` max-age` directive (in seconds)
    - Just adding Header `Cache-Control: max-age = 10` will be enough for the browser to cache this response.

----

Caching is one of the more important features of the Web. When you visit a website for the first time, your browser
stores images and static text in memory and on disk. If you revisit the site within minutes, hours, days, or even
months, your browser doesn’t have to reload the data over the network and can instead pick it up locally. This greatly
speeds up the rendering of revisited web pages and makes the browsing experience much more fluid. Browser caching not
only helps page viewing, it also cuts down on server load. If the browser is obtaining images or text locally, it is not
eating up scarce server bandwidth or CPU cycles.

Besides browser caching, there are also proxy caches. Proxy caches are pseudo–web servers that work as middlemen between
browsers and websites. Their sole purpose is to ease the load on master servers by caching static content and serving it
to clients directly, bypassing the main servers. Content delivery networks (CDNs) like Akamai have made
multimillion-dollar businesses out of this concept. These CDNs provide you with a worldwide network of proxy caches that
you can use to publish your website and scale to hundreds of thousand of users.

If your web services are RESTful, there’s no reason you can’t leverage the caching semantics of the Web within your
applications. If you have followed the HTTP constrained interface religiously, any service URI that can be reached with
an HTTP GET is a candidate for caching, as they are, by definition, read-only and idempotent.

So when do you cache? Any service that provides static unchanging data is an obvious candidate. Also, if you have more
dynamic data that is being accessed concurrently, you may also want to consider caching, even if your data is valid for
only a few seconds or minutes. For example, consider the free stock quote services available on many websites. If you
read the fine print, you’ll see that these stock quotes are between 5 and 15 minutes old. Caching is viable in this
scenario because there is a high chance that a given quote is accessed more than once within the small window of
validity. So, even if you have dynamic web services, there’s still a good chance that web caching is viable for these
services.

### HTTP Caching

Before we can leverage web caching, proxy caches, and CDNs for our web services, we need to understand how caching on
the Web works. The HTTP protocol defines a rich set of built-in caching semantics. Through the exchange of various
request and response headers, the HTTP protocol gives you fine-grained control over the caching behavior of both browser
and proxy caches. The protocol also has validation semantics to make managing caches much more efficient. Let’s dive
into the specifics.

#### Expires Header (HTTP 1.0)

How does a browser know when to cache? In HTTP 1.0, a simple response header called Expires tells the browser that it
can cache and for how long. The value of this header is a date in the future when the data is no longer valid. When this
date is reached, the client should no longer use the cached data and should retrieve the data again from the server. For
example, if a client submitted GET /customers/123, an example response using the Expires header would look like this:

```http request
HTTP/1.1 200 OK
Content-Type: application/xml
Expires: Tue, 15 May 2014 16:00 GMT

<book id="123">...</book>
```

This cacheable XML data is valid until Tuesday, May 15, 2014. We can implement this within JAX-RS by using
a `javax.ws.rs.core.Response` object. For example:

```java

@Path("/books")
public class CustomerResource {

    @GET
    @Path("{id}")
    @Produces("application/xml")
    public Response getCustomer(@PathParam("id") int id) {
        Book book = findBook(id);

        ResponseBuilder builder = Response.ok(book, "application/xml");

        Date date = Calendar.getInstance(TimeZone.getTimeZone("GMT"))
                .set(2010, 5, 15, 16, 0);

        builder.expires(date);
        return builder.build();
    }
}
```

In this example, we initialize a `java.util.Date` object and pass it to the `ResponseBuilder.expires()` method. This
method sets the Expires header to the string date format the header expects.

#### Cache-Control (HTTP 1.1)

- HTTP caching semantics were completely redone for the **`HTTP 1.1`** specification.
- The specification includes a much richer feature set that has more explicit controls over browser and `CDN/proxy`
  caches.
- The idea of cache revalidation was also introduced. To provide all this new functionality, the Expires header was
  deprecated in favor of the **`Cache-Control`** header.
- Instead of a date, **`Cache-Control`** has a variable set of comma-delimited directives that define who can cache,
  how, and for how long. Let’s take a look at them:


- **`private`**
    - The private directive states that no shared intermediary (**proxy or CDN**) is allowed to cache the response.
    - This is a great way to make sure that the client, and only the client, caches the data.
    - Specifies that it is only valid for local caches, not intermediate servers.
    - `Cache-Control: max-age=10 private`


- **`public`**
    - This is the default behavior.
    - The public directive is the opposite of private. It indicates that the response may be cached by any entity within
      the request/response chain.
    - Specifies that it is only valid for local and intermediate caches.
    - `Cache-Control: max-age=10 public`


- **`no-cache`**
    - Usually, this directive simply means that the response should not be cached.
    - If it is cached anyway, the data should not be used to satisfy a request unless it is revalidated with the
      server (more on revalidation later).
    - Specifies that it is necessary to always make a request to validate even if we are in the Fresh period.
    - `Cache-Control: max-age=10 nocache`


- **`no-store`**
    - A browser will store cacheable responses on disk so that they can be used after a browser restart or computer
      reboot.
    - You can direct the browser or proxy cache to not store cached data on disk by using the no-store directive.
    - Means that caching is not allowed.
    - Cache-Control: noStore


- **`no-transform`**
    - Some intermediary caches have the option to automatically transform their cached data to save memory or disk space
      or to simply reduce network traffic.
    - An example is compressing images. For some applications, you might want to disallow this using the no-transform
      directive.


- **`max-age`**
    - This directive is how `long` (**in seconds**) the cache is valid. If both an `Expires` header and a `max-age`
      directive are set in the same response, **the max-age always takes precedence**.


- **`s-maxage`**
    - The `s-maxage` directive is the same as the `max-age` directive, but it specifies the maximum time a shared,
      intermediary cache (like a proxy) is allowed to hold the data.
    - This directive allows you to have different expiration times than the client.

Simple example of a response to see Cache-Control in action:

Let’s take a look at a simple example of a response to see Cache-Control in action:

```http request
HTTP/1.1 200 OK
Content-Type: application/json
Cache-Control: private, no-store, max-age=300

{"id":1234, "name": "xpto"}
```

In this example, the response is saying that **only the client may cache the response**. This response **is valid for
300 seconds** and **must not be stored on disk**.

- The JAX-RS specification provides `javax.ws.rs.core.CacheControl`, a simple class to represent the Cache-Control
  header
- The `ResponseBuilder` class has a method called `cacheControl()` that can accept a CacheControl object.

```java

@Path("/books")
public class CustomerResource {

    @GET
    @Path("{id}")
    @Produces("application/json")
    public Response getBook(@PathParam("id") int id) {
        Book book = find(id);

        CacheControl cc = new CacheControl();
        cc.setMaxAge(300);
        cc.setPrivate(true);
        cc.setNoStore(true);

        ResponseBuilder builder = Response.ok(book);
        builder.cacheControl(cc);

        return builder.build();
    }
}
```

In this example, we initialize a `CacheControl` object and pass it to the `ResponseBuilder.cacheControl()` method to set
the` Cache-Control` header of the response.  
Unfortunately, `JAX-RS` doesn't yet have any nice annotations to do this for you automatically.


## Revalidation and Conditional GETs

- One interesting aspect of the caching protocol is that when the cache is stale, the cacher can ask the server if the
  data it is holding is still valid. This is called revalidation.
- To be able to perform revalidation, the client needs some extra information from the server about the resource it is
  caching. The server will send back a `Last-Modified` and/or an `ETag` header with its initial response to the client.

### Last-Modified

The Last-Modified header represents a timestamp of the data sent by the server. Here’s an example response:

```shell
curl -i -X GET "http://localhost:8080/resources/books/999" -H "accept: */*"
```

```http request
HTTP/1.1 200 OK
Connection: keep-alive
Cache-Control: no-transform, max-age=60
Last-Modified: Tue, 01 Jan 2019 00:00:00 GMT
Content-Type: application/json

{"id":999,"title":"Effective java"}
```

- This initial response from the server is stating that the `JSON` returned is valid for `60` seconds and has a timestamp of Tue, 01 Jan 2019 00:00:00 GMT. 
- If the client supports revalidation, it will store this timestamp along with the cached data. After 60 seconds, the client may opt to revalidate its cache of the item. 
- To do this, it does a conditional GET request by passing a request header called `If-Modified-Since` with the value of the cached `Last-Modified` header. For example:


```shell
curl -v -X GET 'localhost:8080/resources/books/999' -H 'If-Modified-Since: Tue, 01 Jan 2019 00:00:00 GMT'
```

```http request
GET /resources/books/999 HTTP/1.1
Host: localhost:8080
If-Modified-Since: Tue, 01 Jan 2019 00:00:00 GMT

HTTP/1.1 304 Not Modified
Cache-Control: no-transform, max-age=60
Date: Wed, 09 Dec 2020 23:28:12 GMT
```

- When a service receives this GET request, it checks to see if its resource has been modified since the date provided within the `If-Modified-Since` header.
- If it has been changed since the timestamp provided, the server will send back a 200, “OK,” response with the new representation of the resource. 
- Otherwise, If it hasn’t been changed, the server will respond with 304, “Not Modified,” and return no representation. 
- In both cases, the server should send an updated Cache-Control and Last-Modified header if appropriate.

The code that implements the expected behavior is  following:

```java
@Path("/books")
@Produces(MediaType.APPLICATION_JSON)
public class BookResources {

    @GET
    @Path("/{id: \\d+}")
    public Response findById(@PathParam("id") Integer id,
                             @Context Request request,
                             @Context HttpHeaders httpHeaders) {

        Book book = books.findById(id);

        CacheControl cacheControl = new CacheControl();
        cacheControl.setMaxAge(60);
        cacheControl.setPrivate(false);
        cacheControl.setNoTransform(true);

        //- When a service receives this GET request, it checks to see if its resource has been modified since the date provided within the `If-Modified-Since` header.
        //- If it has been changed since the timestamp provided, the server will send back a 200, “OK,” response with the new representation of the resource.
        //- Otherwise, If it hasn't been changed, the server will respond with 304, “Not Modified,” and return no representation.
        //- In both cases, the server should send an updated Cache-Control and Last-Modified header if appropriate.
        Response.ResponseBuilder responseBuilder = request.evaluatePreconditions(Date.from(book.getLastModified()));
        if (responseBuilder != null) {
            // sending 304 not modified
            return responseBuilder
                    .cacheControl(cacheControl)
                    .build();
        }

        return Response.ok(book)
                .cacheControl(cacheControl)
                .lastModified(Date.from(book.getLastModified()))
                .build();
    }
}
```


### ETag

The `ETag` header is a pseudounique identifier that represents the version of the data sent back. Its value is any arbitrary quoted string and is usually an MD5 hash. Here’s an example response:

```http request
HTTP/1.1 200 OK
Content-Type: application/json
Cache-Control: max-age=1000
ETag: "3141271342554322343200"
{"title": "hello duke"}
```

- Like the `Last-Modified` header, when the client caches this response, it should also cache the `ETag` value. 
- When the cache expires after 1,000 seconds, the client performs a revalidation request with the `If-None-Match` header that contains the value of the cached `ETag`. For example:

```http request
GET /subscriptions/123 HTTP/1.1
If-None-Match: "3141271342554322343200"
```

- When a service receives this GET request, it tries to match the current `ETag` hash of the resource with the one provided within the `If-None-Match` header. 
- If the tags don’t match, the server will send back a 200, “OK,” response with the new representation of the resource. 
- Otherwise, If it hasn’t been changed, the server will respond with 304, “Not Modified,” and return no representation. 
- In both cases, the server should send an updated Cache-Control and ETag header if appropriate.



One final thing about `ETags` is they come in two flavors: strong and weak. 

  - A **strong ETag** should change whenever any bit of the resource’s representation changes. 
  - A **weak ETag** changes only on semantically significant events. 
    - Weak ETags are identified with a W/ prefix. For example:
      ```http request
        HTTP/1.1 200 OK
        Content-Type: application/xml
        Cache-Control: max-age=1000
        ETag: W/"3141271342554322343200"
        <customer id="123">...</customer>
      ```


  - Weak ETags give applications a bit more flexibility to reduce network traffic, as a cache can be revalidated when there have been only minor changes to the resource.

  - JAX-RS has a simple class called `javax.ws.rs.core.EntityTag` that represents the ETag header: 
    ```java
    public class EntityTag {
      public EntityTag(String value) {...}
      public EntityTag(String value, boolean weak) {...}
      public static EntityTag valueOf(String value) throws IllegalArgumentException {...}
      public boolean isWeak() {...}
      public String getValue() {...}
    }
    ```
    
  - It is constructed with a string value and optionally with a flag telling the object if it is a weak ETag or not. 
  - The getValue() and isWeak() methods return these values on demand.

  - To help with conditional GETs, JAX-RS provides an injectable helper class called `javax.ws.rs.core.Request`:
    ```java
    public interface Request {
        ...
        ResponseBuilder evaluatePreconditions(EntityTag eTag);
        ResponseBuilder evaluatePreconditions(Date lastModified);
        ResponseBuilder evaluatePreconditions(Date lastModified, EntityTag eTag);
    }
    ```

  - The overloaded `evaluatePreconditions()` methods take a `javax.ws.rs.core.EntityTag`, a `java.util.Date` that represents the last modified timestamp, or both. 
  - These values should be current, as they will be compared with the values of the `If-Modified-Since`, `If-Unmodified-Since`, or `If-None-Match` headers sent with the request. 
  - If these headers don’t exist or if the request header values don’t pass revalidation, this method returns null and you should send back a 200, “OK,” response with the new representation of the resource. 
  - If the method does not return null, it returns a preinitialized instance of a ResponseBuilder with the response code preset to 304. For example:

1. create a test resource:
```shell
curl -v -X POST "http://localhost:8080/resources/subscriptions" -H "accept: */*" -H "Content-Type: application/json" -d "{\"reader\":\"Max Payner\",\"book\":{\"id\":999,\"title\":\"Effective java\"}}"
```

2. Get the created resource:
```shell
curl -v -X GET "http://localhost:8080/resources/subscriptions/1"
```
```http request

GET /resources/subscriptions/1 HTTP/1.1
Host: localhost:8080
User-Agent: curl/7.64.1
Accept: */*
 
HTTP/1.1 200 OK
Connection: keep-alive
ETag: W/"a3ecd218fd65c7a8f7fb69a438f44ab7"
Cache-Control: no-transform, max-age=60
Content-Type: application/json
Content-Length: 94
Date: Thu, 10 Dec 2020 04:00:14 GMT

{"id":1,"reader":"Max Payner","book":{"id":999,"title":"Effective java"},"status":"SUBMITTED"}
```

3. Now that we have the Etag we can perform a Get request:
```shell
curl -v -X GET "http://localhost:8080/resources/subscriptions/1" -H'If-None-Match: W/"a3ecd218fd65c7a8f7fb69a438f44ab7"'
```

```http request
GET /resources/subscriptions/1 HTTP/1.1
Host: localhost:8080
Accept: */*
If-None-Match: W/"a3ecd218fd65c7a8f7fb69a438f44ab7"
 
HTTP/1.1 304 Not Modified
ETag: W/"a3ecd218fd65c7a8f7fb69a438f44ab7"
Cache-Control: no-transform, max-age=60
Date: Thu, 10 Dec 2020 04:06:20 GMT
```


The Code that implement the behavior is the fowling:
```java
@Path("/subscriptions")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SubscriptionResource {

    @Inject
    Subscriptions subscriptions;

    @Inject
    @Encryption(type = Encryption.Type.MD5)
    Encryptor encryptor;

    @GET
    @Path("/{id: \\d+}")
    public Response findById(@PathParam("id") Integer id, @Context Request request) {
        Subscription subscription = subscriptions.findById(id);

        String hash = subscription.hash(encryptor);
        EntityTag tag = new EntityTag(hash, true);

        CacheControl cacheControl = new CacheControl();
        cacheControl.setMaxAge(60);
        cacheControl.setPrivate(false);
        cacheControl.setNoTransform(true);

        Response.ResponseBuilder builder = request.evaluatePreconditions(tag);
        if (builder != null) {
            // sending 304 not modified
            return builder
                    .cacheControl(cacheControl)
                    .build();
        }

        return Response.ok(subscription)
                .cacheControl(cacheControl)
                .tag(tag)
                .build();
    }
}
```

--- 

## Concurrency

N- ow that we have a good idea of how to boost the performance of our JAX-RS services using HTTP caching, we need to look at how to scale applications that update resources on our server. The way RESTful updates work is that the client fetches a representation of a resource through a GET request. It then modifies the representation locally and PUTs or POSTs the modified representation back to the server. This is all fine and dandy if there is only one client at a time modifying the resource, but what if the resource is being modified concurrently? Because the client is working with a snapshot, this data could become stale if another client modifies the resource while the snapshot is being processed.

The HTTP specification has a solution to this problem through the use of conditional PUTs or POSTs. 
``
- This technique is very similar to how cache revalidation and conditional GETs work. 
1. The client first starts out by fetching the resource. For example, let’s say our client wants to update a customer in a RESTful customer directory. 
2. It would first start off by submitting GET /books/999 to pull down the current representation of the specific book it wants to update. The response might look something like this:

```http request
HTTP/1.1 200 OK
Content-Type: application/json
Cache-Control: max-age=1000
ETag: "3141271342554322343200"
Last-Modified: Tue, 15 May 2013 09:56 EST
{"id":999, "title":"hello"}
```

- In order to do a conditional update, we need either an `ETag` or `Last-Modified` header. 
- This information tells the server which snapshot version we have modified when we perform our update. 
- It is sent along within the `If-Match` or `If-Unmodified-Since` header when we do our `PUT` or `POST` request. The `If-Match` header is initialized with the `ETag` value of the snapshot.
- The `If-Unmodified-Since` header is initialized with the value of `Last-Modified` header. So, our update request might look like this:

```http request
PUT /books/999 HTTP/1.1
If-Match: "3141271342554322343200"
If-Unmodified-Since: Tue, 15 May 2013 09:56 EST
Content-Type: application/json
{"title":"hello world"}
```


You are not required to send both the `If-Match` and `If-Unmodified-Since` headers. One or the other is sufficient to perform a conditional PUT or POST. 
- When the server receives this request, it checks to see if the current `ETag` of the resource matches the value of the `If-Match` header and also to see if the timestamp on the resource matches the `If-Unmodified-Since` header. 
- If these conditions are not met, the server will return an error response code of **412, “Precondition Failed.”** This tells the client that the representation it is updating was modified concurrently and that it should retry. If the conditions are met, the service performs the update and sends a success response code back to the client.


```java
@Path("/subscriptions")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SubscriptionResource {

    @POST
    @Path("/{id: \\d+}/approvement")
    public Response approve(@PathParam("id") Integer id, @Context Request request) {
        Subscription existing = subscriptions.findById(id);
        String hash = existing.hash(encryptor);
        EntityTag tag = new EntityTag(hash, true);
        Date lastModification = Date.from(existing.getLastModified());

        Response.ResponseBuilder builder = request.evaluatePreconditions(lastModification, tag);

        if (builder != null) {
            // Preconditions not met!
            builder.build();
        }

        subscriptions.approve(id);

        return Response.noContent().build();
    }
}
```

---

# Links

```
 - http://localhost:8080/resources/openapi-ui/index.html
 - http://localhost:8080/resources/openapi

 - http://localhost:8080/resources/ping
```


```shell
curl -X PUT "http://localhost:8080/resources/books/998" -H "accept: */*" -H "Content-Type: application/json" -d '{"title":"string"}'

curl -X POST "http://localhost:8080/resources/books/998" -H "accept: */*" -H "Content-Type: application/json" -d '{"title":"string"}'

curl -X GET "http://localhost:8080/resources/books/998" -H "accept:application/json"

```



---





---

# Build

mvn clean package && docker build -t io.github.jlmc/jax-rs-cache .

# RUN

docker rm -f sse-service || true && docker run -d -p 8080:8080 -p 4848:4848 --name sse-service
io.github.jlmc/sse-service

# System Test

Switch to the "-st" module and perform:

mvn compile failsafe:integration-test