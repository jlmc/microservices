# Bulk Heads

In shipping, a bulkhead is a partition that prevents a leak in one compartment from sinking the entire ship.
Bulkheads in microservices are a similar concept. We need to make sure that a failure in one part of our application doesn’t take down the whole thing. The bulkhead pattern is about how we create microservices rather than the use of any specific tool or library.
When we create microservices, we should always ask ourselves how you can isolate the different parts and prevent cascading failures.

Taking the text seriously, we can say that BulkHeads Means one ThreadPool for communication channel service (Path).
In Java EE the simplest way to implement that is to create ExecutersManagerServices manually in the administration console. Then we can access these resources using the @Resources annotation.

```Java
@Resource
ManagerExecutorService
```
However, this may be impractical in sophisticated applications, where executors are expected to be more flexible.


## Problem

* We need the server not to get blocked, with a service.
* One Overload Path should not affect the others.
* The communication channels should be independent of each other.
* The server becomes robust and very responsive, that is, although the channel is underloaded, another channel can continue to accept and respond to requests.


## Solution

Fortunately the specification of Concurrency-Utilities 1.0 comes with a solution that is part of the standards, and the solutions is the ManagedThreadFactory, the interface for creating managed Threads.

“_The javax.enterprise.concurrent.ManagedThreadFactory allows applications to create thread instances from a Java EE Product Provider without creating new java.lang.Thread instances directly. This object allows Application Component Providers to use custom executors such as the java.util.concurrent.ThreadPoolExecutor when advanced, specialized execution patterns are required.
Java EE Product Providers can provide custom Thread implementations to add management capabilities and container contextual information to the thread._”

Using that, the ManagedThreadFactory can be injected easily inject into the Our components, thus allowing the possibility to create Managed Threads.

---
## Bonus
* With the creation of dedicated Executors we gain the ability to monitor more easily.
* With dedicated executers to communication channels can become simpler monotarization of each of these communication channels.
* Easy to assign more resources
* Reject Policy, In Order to implement a Custom Reject Policy we have to implement our how Thread Pool Executors.
* A custom Reject Policy is in fact a good a idea for more sophisticated Applications, that is part of the solution

 
 




