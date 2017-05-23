# Bulk Heads

In shipping, a bulkhead is a partition that prevents a leak in one compartment from sinking the entire ship. 
Bulkheads in microservices are a similar concept. You need to make sure that a failure in one part of your application doesn’t take down the whole thing. The bulkhead pattern is about how you create microservices rather than the use of any specific tool or library. 
When creating microservices, always ask yourself how you can isolate the different parts and prevent cascading failures.

In this project I'm implementing the Bulk Heads Pattern,

BulkHeads mends on thredPool for comunication channel service.


254/5000

In Java EE the simplest way to implement that is to create the ExecutersManagerServices manually in the administration console.
However, this may be impractical in sophisticated applications, where executors are expected to be more flexible and .


Problema:
* Precisamos the o servidor não fique bloqueado, com um serviço
* O canais de comunicação devem ser independentes, 
* O servidor torna-se robusto e muito responsivo, isto é, embora o canal esteja subcarregado (overloaded), um outro canal pode continuar a atender pedidos.
* One Overload Path should not afect the others


ganhos extra:

* Com a criação de Executors dedicados ganhamos a capacidade de monitoring mais facilmente.
* como temos executors dedicados a canais de comunicação pode tornar-se mais simples monutorização de cada um desses canais de comunicação.
* Fácil atribuir mais recursos, Easy to assign more resources


---
@Resource
ManagerExecutorService 

vem com (do) java EE, foi introduzido pelo Java EE 7.

No entanto essa especificação não diz como deve ser feita a Reject policy,
apenas sujere que pode ser o Abort ou retry and Abort.

Spec
Reject Policy: The policy to use when a task is to be rejected by the executor. In this example, two
policies are available:
◦ Abort: Throw an exception when rejected.
◦ Retry and Abort: Automatically resubmit to another instance and abort if it fails.
 
In Order to implement a Custom Reject Policy we have to implement our how Thread Pool Executors.
A custom Reject Policy is in fact a good a idea for more sophisticated Applications 

---
Fortunately the EE-Concurrency-Utilities 1.0 cames with a solution that is part of the standards, and the solutions is the 
__ManagedThreadFactory__  The interface for creating managed threads.

the ManagedThreadFactory Can be injected easily into the Our components, thus allowing the possibility to create Managed Threads.

Spec (EE-concurrency-Utilities 1.0)
_The javax.enterprise.concurrent.ManagedThreadFactory allows applications to create thread instances from a
Java EE Product Provider without creating new java.lang.Thread instances directly. This object allows
Application Component Providers to use custom executors such as the
java.util.concurrent.ThreadPoolExecutor when advanced, specialized execution patterns are required.
Java EE Product Providers can provide custom Thread implementations to add management capabilities and
container contextual information to the thread._

Then, from this assumption, we can use the Thread Managed factory to create new executors services that we can dedicate to Paths 


