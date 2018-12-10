package com.jonathanmelendrez.rest.webservices.restfulwebservices.user;

import javax.persistence.Entity;

@Entity
public class Post {
    private Integer id;
    private String description;

    private User user;

    public Post(Integer id, String description, User user) {
        super();
        this.id = id;
        this.description = description;
        this.user = user;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    /*FIX THIS SHIT*/
    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", description='" + description + '\'' +
                '}';
    }
}
