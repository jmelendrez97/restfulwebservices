package com.jonathanmelendrez.rest.webservices.restfulwebservices.user;

/*Uses JPA and h2 to communicate to the database (h2 is the embedded database)*/
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
public class UserJPAResource {
    /*@Autowired
    private UserDaoService service;*/

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/jpa/users") // Shows the users in JSON
    public List<User> retrieveAllUsers(){
        return userRepository.findAll();
    }

    @GetMapping("/jpa/users/{id}") //Retrieves a user using 'id' and displays it in JSON
    public Resource<User> retrieveUser(@PathVariable int id){
        Optional<User> user = userRepository.findById(id);
        if(!user.isPresent()){
            throw new UserNotFoundException("id-"+ id); // <--- 'handleUserNotFoundException' method from the CustomizedResponseEntityExceptionHandler class does the work
        }
        Resource<User> resource = new Resource<User>(user.get());
        ControllerLinkBuilder linkto = linkTo(methodOn(this.getClass()).retrieveAllUsers());
        resource.add(linkto.withRel("all-users"));
        return resource;
    }

    @DeleteMapping("/jpa/users/{id}") //Retrieves a user using 'id' and displays it in JSON
    public void deleteUser(@PathVariable int id){
        userRepository.deleteById(id);
        /*if(user==null) {
            throw new UserNotFoundException("id-" + id); // <--- 'handleUserNotFoundException' method from the CustomizedResponseEntityExceptionHandler class does the work
        }*/
    }

    @PostMapping("/jpa/users") // Creates a user using the 'save' method from UserDaoService
    public ResponseEntity<Object> createUser(@Valid @RequestBody User user){
        User savedUser = userRepository.save(user);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()//Returns current request URI '/user/{id}'
                .path("/{id}")
                .buildAndExpand(savedUser.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

}
