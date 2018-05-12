package com.hacktm.ngo;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hacktm.event.Event;
import com.hacktm.volunteer.Volunteer;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

@Entity
@JsonFilter("NgoFilter")
public class Ngo {

    @Id
    @GeneratedValue
    private Long id;

    @Pattern(regexp = "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", message = "Not a valid email address")
    private String email;

    @Size(min = 3, message = "Password has to be at least 3 characters long")
    private String password;

    private String name;

    private String purpose;

    private String website;

    private String location;

    private Date createdDate;

    @JsonIgnore
    @OneToMany(mappedBy = "affiliatedNgo", fetch = FetchType.LAZY)
    private List<Volunteer> volunteers;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY)
    private List<Volunteer> pendingVolunteers;

    @JsonIgnore
    @OneToMany(mappedBy = "organizer", fetch = FetchType.LAZY)
    private List<Event> organizedEvents;

    public Ngo() {}

    public Ngo(String email, String password, String name, String purpose, String website, String location) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.purpose = purpose;
        this.website = website;
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

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getCreatedDate() {
        return createdDate;
    }
}
