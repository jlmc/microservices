#Plugin

The plugin pattern clearly separates the basic application behavior from the often-changing unstable, or volatile code and defines a clear modularization and deployment concept.

##Problem 
Often changing parts of an application decrease productivity by increasing complexity. Even minor changes require recomplilation and redeployment of the whole application. Without strict deployment -time separation between the interface and its implementation, it is hard to introduce new functionality without recompiling the whole application.

Usually, recompilation and redeployment happens on every commit in a <b>Continuous Integration (CI)</b> environment. In a project driven by requirements from domain experts and a single deployment scenario, the introduction of plugins is pure  over-engineering.


##Forces
* You have to encapsulate "moving parts" of your applications to decrease code "brittleness".
* Pluggable modules have to be potentially reused by other application.
* Plugins have to be developed and packaged separately, probably by different development teams.
* Deployed plugins have to be discovered at startup without any additional configuration or code dependencies.
* Generic or basic functionality has to be extended at deployment time by strictly.
* External functionality does not have to be discovered at runtime. Deploy-time discoverability is sufficient.
* You are going to start with multiple plugin implementations. A simple plugin does not justify the overhead associated with the implementation of the <b>Plugin pattern</b>.


##Solution
* Extensibility can be realized in Java EE with interfaces and inheritance. Plugins require you to separate the functionality into a stable and conveniently  usable contract and more volatile and specific implementation. Both the contract  and the realization are going to be package separately as JARs and packaging out of the box without any overhead. A WAR application would usually declare the dependencies to the JAR s in the pom.xml file:

* Regardless of the applied strategy, at the code level, there is only a single dependency at compile time on the API artifact. The existence of the any API implementation is optional. Even without any deployed plugin, the application should still operate properly. 

* A classic plugin approach is realized with plain <b>@Inject</b> dependency injection. Deploy-time discovery can be implemented easily with injection of the contract wrapped with the <b>javax.enterprise.inject.Instance</b> interface.

* Partial extension of already deployment functionality can be implemented with <b>CDI</b> managed bean inheritance and the <b>@Specializes</b> annotation. The subclass is separated into JAR and container to replace the superclass with the subclass.

* Because the realization of both approaches has only a little in common, we are going to cover each approach in the "Strategies" section.
 
   


 