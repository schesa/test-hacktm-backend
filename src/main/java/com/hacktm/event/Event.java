package com.hacktm.event;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hacktm.ngo.Ngo;
import com.hacktm.volunteer.Volunteer;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
public class Event {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private String description;

    private Boolean isPrivate;

    private Date eventDate;

    private String duration;

    private String requirements;

    @ManyToOne
    private Ngo organizer;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY)
    private List<Volunteer> volunteers;

    public Event() {}

    public Event(String name, String description, Boolean isPrivate, Date eventDate, String duration, String requirements) {
        this.name = name;
        this.description = description;
        this.isPrivate = isPrivate;
        this.eventDate = eventDate;
        this.duration = duration;
        this.requirements = requirements;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getPrivate() {
        return isPrivate;
    }

    public void setPrivate(Boolean aPrivate) {
        isPrivate = aPrivate;
    }

    public Date getEventDate() {
        return eventDate;
    }

    public void setEventDate(Date eventDate) {
        this.eventDate = eventDate;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getRequirements() {
        return requirements;
    }

    public void setRequirements(String requirements) {
        this.requirements = requirements;
    }

    public Ngo getOrganizer() {
        return organizer;
    }

    public void setOrganizer(Ngo organizer) {
        this.organizer = organizer;
    }

    public List<Volunteer> getVolunteers() {
        return volunteers;
    }

    public void setVolunteers(List<Volunteer> volunteers) {
        this.volunteers = volunteers;
    }
}
