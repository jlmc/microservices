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





----

# Links

```
 - http://localhost:8080/resources/openapi-ui/index.html
 - http://localhost:8080/resources/openapi

 - http://localhost:8080/resources/ping
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