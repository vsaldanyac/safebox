# Securit-ish Challenge 

Securit-ish is a security company which main business is taking care of 
houses and people belongings. 

Their next product launch is _Safe-ish_, a remote-control safebox that they
sell to their customer in order to keep their belongings as safe as possible, 
providing them the ability to control their safebox remotely through a mobile app. 

As you know, the API should be strong and secure enough to avoid leaks and security
issues.

## Current Status

The CEO of Securit-ish, who does not have technical background, has paid for an external
consultant to develop the product. 

This guy has created an API definition (with Swagger) with the main endpoints of it, but 
once they start coding he left the company. So the CEO is looking for someone who could 
help her to create the first version of that API.

######What I've done
* _First of all the project is downloaded on a local IDE (IntelliJ) and configure gradle and all the project structure._
* _I decide to update some features_
* _I decide to use h2 DB in memory to test it. It's very easy to put it on a docker container or a local database h2, and not in memory one_
* _Using h2 DB it's easy to test the features that are required_
* _It's very easy to change the database engine_
* _I decide not to dockerize the project, because I've no time, but it's easy to do_ 

## Requirements

The CEO has sent us the following business requirements:

### Beta - Functional requirements

* Each safebox will have a **unique** id, a **non-empty name** and a **password**.
* The safebox have to allow the following actions
    * Add content to the safebox (based on the generated id)
    * Get the content of the safebox (based on the generated id)
    * All endpoints have to be secured with Basic Auth (using name & password) 
* You should ensure that the password is strong enough
* The content in the safebox must also be ciphered, so even though someone
  accesses your application, the content won’t be readable
  
######What I've done
* _We decide to store on a H2 DB, use a uuid policy to create a unique key and not empty and not null JPA validation to ensure a valid name_
* _I don't use Basic Auth because I did it manually instead of the library. When I realized it was too late. The Basic Auth is done manually_
* _To ensure the password is strong enough, I've tried to user @Pattern on JPA but finally I decide to control with an util_
* _To ensure the content of the safebox is ciphered, I use the Attribute annotation and the algorythm AES_

  
### v1.0 - Functional requirements

The CEO will be happy with the first version of the API, but if you want to help her
to create the full version, those are the requirements:

* To increase the security of the safebox, would be better if we split the step of 
 interacting with it (insert and get items) into two. 
    * Open the safebox (with a valid id and password) to get a token
    * Use this token lately to add or obtain items, sending it through an HTTP Header
* The token will be valid for the next **3 minutes**  
* The system will block the safebox after 3 failed opening attempts (considering a
 failing attempt as a wrong combination between safebox id and password). Don’t worry
 about unblocking it, we don’t care about that it nowadays.

######What I've done
* _First of all, I've extended the Controller to keep on the beta endpoints. It's easy to close them if necessary_
`http://localhost:8080/beta/*` versus `http://localhost:8080/v1/*`
* _I use the Jason Web Token for that (JWT), filtering only the endpoints that are needed. This allows me almost all the requirements that exist_
* _To ensure to lock the system if one user fails for three times, I used an interceptor on not Beta endpoints. A Success entrance reset the error number except that the number is bigger than 3_
 
## What are we looking for?

* **A well-designed solution and architecture.** Avoid duplication, extract re-usable code
where makes sense. We want to see that you can create an easy-to-maintain codebase.
* **Test as much as you can.** One of the main pain points of maintaining other's code
comes when it does not have tests. So try to create tests covering, at least, the main classes.
* **Document your decisions**. The CEO has a non-tech background so try to explain your decisions, 
as well as any other technical requirement (how to run the API, external dependencies, etc ...)

######What I've done
* _Thank you for giving me the opportunity to do this test and apologizes because I've not enough time to did it better, but I can ;) _
* _I hope I am who you're looking for ;)_
