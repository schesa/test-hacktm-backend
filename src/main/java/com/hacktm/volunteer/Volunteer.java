package com.hacktm.volunteer;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hacktm.event.Event;
import com.hacktm.ngo.Ngo;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@JsonFilter("VolunteerFilter")
public class Volunteer {

    @Id
    @GeneratedValue
    private Long id;

    private String email;

    private String password;

    private String name;

    private Integer age;

    private String location;

    private Date createdDate;

    @ManyToOne
    @JsonIgnore
    private Ngo affiliatedNgo;

    @ManyToMany
    @JsonIgnore
    private List<Event> attendingEvents;

    public Volunteer() {}

    public Volunteer(String email, String password, String name, Integer age, String location) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.age = age;
        this.location = location;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Ngo getAffiliatedNgo() {
        return affiliatedNgo;
    }

    public void setAffiliatedNgo(Ngo affiliatedNgo) {
        this.affiliatedNgo = affiliatedNgo;
    }

    public List<Event> getAttendingEvents() {
        return attendingEvents;
    }

    public void setAttendingEvents(List<Event> attendingEvents) {
        this.attendingEvents = attendingEvents;
    }
}
