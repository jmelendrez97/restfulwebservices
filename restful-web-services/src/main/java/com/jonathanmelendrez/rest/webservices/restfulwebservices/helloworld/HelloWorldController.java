package com.jonathanmelendrez.rest.webservices.restfulwebservices.helloworld;

import com.jonathanmelendrez.rest.webservices.restfulwebservices.helloworld.HelloWorldBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Locale;

@RestController // <--- (1) Handles REST requests
public class HelloWorldController {

    @Autowired
    private MessageSource messageSource;
                                 //(A) Defines the HTTP method  //(B) Sets the directory [The URL shows like this: localhost:8080/hello-world]
                //@RequestMapping(method= RequestMethod.GET, path = "/hello-world") //<--- (2)Maps the method


    @GetMapping(path="/hello-world") //<--- (2)Automatically sets the HTTP method unlike RequestMapping
    public String helloWorld(){
        return "Hello World";
    }

    @GetMapping(path="/hello-world-bean") //<--- (2)Automatically sets the HTTP method unlike RequestMapping
    public HelloWorldBean helloWorldBean(){
        return new HelloWorldBean("Hello World"); //<--- (3)Calls the HelloWorldBean method from the HelloWorldBean class
                                                  //        Returns the output in a JSON format
    }
    //returns this output on localhost:8080/hello-world-bean
      //|
      //|
      //v
    /*{
        "message": "Hello World"
    }*/
    @GetMapping(path="/hello-world/path-variable/{name}") //<--- (4) '{name}' is a path variable
    public HelloWorldBean helloWorldPathVariable(@PathVariable String name){
        return new HelloWorldBean(String.format("Hello World, %s %s", name, name)); //<--- (5) 'name' displays the value from '{name}'

    }
    @GetMapping(path="/hello-world-internationalized")
    public String helloWorldInternationalized(/*@RequestHeader(name="Accept-Language", required = false) Locale locale*/){
        return messageSource.getMessage("good.morning.message", null, LocaleContextHolder.getLocale());
                //getMessage("good.morning.message", null, locale);
    }




}
