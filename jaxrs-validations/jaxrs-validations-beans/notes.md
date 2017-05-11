get

curl -i -v -X GET -H "Content-Type: application/xml" -H "Accept: application/xml" http://localhost:8080/jaxrs-validations-beans/resources/books

>>>


POST

curl -v -X POST -H "Accept: application/json" -H "Accept-Language: en" -H "Content-Type: application/xml" --data @request.xml http://localhost:8080/jaxrs-validations-beans/resources/books



curl -v -X POST -H "Accept: application/json" -H "Accept-Language: en" -H "Content-Type: application/x-www-form-urlencoded" -d"id=sssss&title=aassassasasa" http://localhost:8080/jaxrs-validations-beans/resources/books/create



>>
> 
curl -i -v -X POST -H "Content-Type: application/xml" --data @request.xml http://localhost:8080/jaxrs-validations/resources/persons
curl -i -v -X POST -H "Accept: application/json" -H "Accept-Language: pt" -H "Content-Type: application/xml" --data @request.xml http://localhost:8080/jaxrs-validations/resources/persons
curl -v -X POST -H "Accept: application/json" -H "Accept-Language: pt" -H "Content-Type: application/xml" --data @request.xml http://localhost:8080/jaxrs-validations/resources/persons

curl -i -v -X GET -H "Content-Type: application/xml"  http://localhost:8080/jaxrs-validations/resources/persons


curl -i -v -X GET -H "Content-Type: application/xml" -H "Accept: application/xml" http://localhost:8080/jaxrs-validations-beans/resources/books


curl -v -X POST -H "Accept: application/json" -H "Accept-Language: pt" -H "Content-Type: application/xml" --data @request.xml http://localhost:8080/jaxrs-validations/resources/persons

>>>>>
