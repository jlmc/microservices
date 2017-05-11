# CDI Custom Scope

Java EE comes with build-in request, session, application and conversation scopes. However scopes are extensible, we can implement any number of custom scopes.

From  the extensibility point of view, a custom scope is closed related to Generic JCA pattern. The Custom Scope pattern focuses on lifecycle management of business logic and the Generic JCA pattern focuses on lifecycle of connections to legacy systems.


 
# Problem 
Build-in, standard scopes are controlled by CDI runtime. To meet special requirements, an application has to Control the lifespan of managed beans.

# Forces 
 * Portable solution
 * Managed beans with manually managed lifespans have be seamless integrated with the application and the CDI runtime.
 * All container services, such as DI, interception, decoration, build-in beans, and so on, should be available as well.
 
 
# Solution
CDI foresees the implementation of custom scopes for fine grained control of the lifecycle of the managed beans. Custom scope realizations are portable across vendors.
Exactly as in the case of build-in scopes, a custom scope is declared with an Annotation.

   
 
 
     