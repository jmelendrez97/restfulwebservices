package com.jonathanmelendrez.rest.webservices.restfulwebservices.user;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.hateoas.*;


import javax.validation.Valid;
import java.net.URI;
import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
public class UserResource {
    @Autowired
    private UserDaoService service;

    @GetMapping("/users") // Shows the users in JSON
    public List<User> retrieveAllUsers(){
        return service.findAll();
    }

    @GetMapping("/users/{id}") //Retrieves a user using 'id' and displays it in JSON
    public Resource<User> retrieveUser(@PathVariable int id){
        User user = service.findOne(id);
        if(user==null){
            throw new UserNotFoundException("id-"+ id); // <--- 'handleUserNotFoundException' method from the CustomizedResponseEntityExceptionHandler class does the work
        }
        Resource<User> resource = new Resource<User>(user);
        ControllerLinkBuilder linkto = linkTo(methodOn(this.getClass()).retrieveAllUsers());
        resource.add(linkto.withRel("all-users"));
        return resource;
    }

    @DeleteMapping("/users/{id}") //Retrieves a user using 'id' and displays it in JSON
    public void deleteUser(@PathVariable int id){
        User user = service.deleteById(id);
        if(user==null) {
            throw new UserNotFoundException("id-" + id); // <--- 'handleUserNotFoundException' method from the CustomizedResponseEntityExceptionHandler class does the work
        }
    }

    @PostMapping("/users") // Creates a user using the 'save' method from UserDaoService
    public ResponseEntity<Object> createUser(@Valid @RequestBody User user){
        User savedUser = service.save(user);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()//Returns current request URI '/user/{id}'
                .path("/{id}")
                .buildAndExpand(savedUser.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

}
