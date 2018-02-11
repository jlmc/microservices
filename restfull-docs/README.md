# RESTfull documentation

This projects intends to explore a valid way to generate the documentation of a Jax-rs project. 
I'm using a very nice plugin of [Sebastian Daschner](https://blog.sebastian-daschner.com/entries/jaxrs_analyzer_explained_video) that allow us to generate the documentation as plaintext (default), swagger and asciidoc.
The complete documentation of the plugin can be found in the github account of the actor.   

[JAX-RS Analyzer Official documentation](https://github.com/sdaschner/jaxrs-analyzer/blob/master/Documentation.adoc)


[![we can find also a very nice video](http://img.youtube.com/vi/TmG0Tnqv3gk/0.jpg)](http://www.youtube.com/watch?feature=player_embedded&v=TmG0Tnqv3gk)




# Build
mvn clean package && docker build -t org.xine/restfull-docs .

# RUN

docker rm -f restfull-docs || true && docker run -d -p 8080:8080 -p 4848:4848 --name restfull-docs org.xine/restfull-docs 