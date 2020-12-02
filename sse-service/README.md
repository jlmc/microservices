#  Server-Sent Events (SSE)

- what are SSEs, exactly?
  
  - They are a very simple HTTP-based API, dedicated to Push communication, and currently SSEs are implemented in most recent browsers like **Firefox**, **Chrome**, Safari, and Opera. Unfortunately, SSEs are currently not implemented in **Internet Explorer** or **Edge**.
  
  - SSE allows you to send simple text data from the server to the client. One important thing: **`SSEs are one-way in communication`**. You might be thinking, _hey, well I've used Polling and Long Polling in the past, and they kind of do the same thing_. The main difference is that with Polling and Long Polling, it is the client that occasionally tries to load new data. With SSE, it's not the client Polling, it's always the server pushing data to the client.

  - You might have heard of `WebSockets` before, but `WebSockets` are a totally different thing. First up, they are **TCP-based**. They provide a full duplex communication link between the client and the server. Using a **WebSocket**, the client can always send data to the server and the server can always send data to the client. In **`SSE`**, you can think of an event-stream of really simple text data. **`The text data must be encoded using UTF-8`**. What we can do in the event-stream is send simple messages and encode these messages. These messages may even be in JSON or you maybe you can only send messages that are plain string or perhaps primitive data. **Messages in the event-stream are separated by a pair of newline characters `("\n")`**.
  
  - Remember: SSEs are simple Push communication mechanisms, and you can send an event-stream from the server to the client without the client leading to Polling.

---

## Implementing SSE on the server-side

1. The first thing we need to do is implement the opening of the event-stream. We can do that by implementing a plain HTTP `@GET` method, though the first thing is going to be the parameter, which is where we pass the `@Context` of type `SseEventSink`.  
  - This is the object that we can use later to send events down to the client. You can also see the `@Produces` annotation, which is where we use `text/event-stream` as the `MediaType`. This is the special media type used to designate SSE:


```java
@GET
@Produces(MediaType.SERVER_SENT_EVENTS)
public void openEventStream(
         @Context final SseEventSink eventSink) {
   this.eventSink = eventSink;
}
```


- Once we've opened the SSE event-stream, we can implement the sending of events. First up, we start with a simple `@POST` method. Again, mind the second parameter, which is a @Context object of type Sse. We use this Sse interface later to construct new events. Let's send the first simple event down the event-stream. We do that using the sse context and construct a newEvent using the string message, and we use the send method on the eventSink and send this to the event:

```java
@POST
public void sendEvent(String message, @Context Sse sse) {
  
    final SseEventSink localSink = eventSink;
    if (localSink == null) return;
  
    // send simple event
    OutboundSseEvent event = sse.newEvent(message);
    localSink.send(event);
```

We can also send named events which can give your events some names. Again, we are using the sse context in order to construct a newEvent. We can see here that we gave it a name (stringEvent) and that we passed in the message as data. Again, we used the localSink and the send method to send this event:

```
// send simple string event
OutboundSseEvent stringEvent = sse.newEvent("stringEvent", message + " From server.");

localSink.send(stringEvent);
```

This also works for other primitive data. Perhaps we want to send the current time in milliseconds. As you can see, there's also a `newEventBuilder` available in the sse context. We use sse.newEventBuilder, name, and data and call the build method on it. We then call the send method as follows:

```java
// send primitive long event using builder
OutboundSseEvent primitiveEvent = sse.newEventBuilder()
                   .name("primitiveEvent")
                   .data(System.currentTimeMillis()).build();

localSink.send(primitiveEvent);
```

And finally, we can also send JSON events. For example, what we have is a simple POJO implementation using some `@JsonbPropertyOrder` annotation:

```java
@JsonbPropertyOrder({"time", "message"})
public static class JsonbSseEvent {

    String message;
    LocalDateTime today = LocalDateTime.now();

    public JsonbSseEvent(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getToday() {
        return today;
    }

    public void setToday(LocalDateTime today) {
        this.today = today;
    }
}
```

- Let's send this down the wire. We used the `newEventBuilder`, we gave it a name, and we passed in an instance of our POJO as `data`. We can specify the mediaType of this event, which in our case is the application JSON. We can use .build and send it down the event-stream:
```java
// send JSON-B marshalling to send event
OutboundSseEvent jsonbEvent = 
        sse.newEventBuilder()
           .name("jsonbEvent")
           .data(new JsonbSseEvent(message))
           .mediaType(MediaType.APPLICATION_JSON_TYPE)
           .build();
localSink.send(jsonbEvent);
```

- That's all there is to sending events. The last thing we need to do is close the event-stream. We can use the HTTP DELETE method for this one. If we call HTTP DELETE on this resource, we can simply call a `close` method on the `eventSink` and we're done:
```java
@DELETE
public void closeEventStream() {
    final SseEventSink localSink = eventSink;

    if (localSink != null) {
        this.eventSink.close();
    }

    this.eventSink = null;
}
```

### Links

 - http://localhost:8080/resources/openapi-ui/index.html
 - http://localhost:8080/resources/openapi

 - http://localhost:8080/resources/ping
 
 - http://localhost:8080/resources/events
 ```shell script
  curl -X POST "http://localhost:8080/resources/events" -H "accept: */*" -H "Content-Type: text/plain" -d "string1"
 ```

 - http://localhost:8080/resources/broadcast 
 ```shell script
  curl -X POST "http://localhost:8080/resources/broadcast" -H "accept: */*" -H "Content-Type: application/x-www-form-urlencoded" -d "message=iii3"
 ```


---


# Build
mvn clean package && docker build -t io.github.jlmc/sse-service .

# RUN

docker rm -f sse-service || true && docker run -d -p 8080:8080 -p 4848:4848 --name sse-service io.github.jlmc/sse-service 

# System Test

Switch to the "-st" module and perform:

mvn compile failsafe:integration-test