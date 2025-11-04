package com.a3.prototipo.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="urls")
public class Url {
    @Id
    @GeneratedValue
    private Long id;
    private String url;
    private boolean isMalicious;

    
    
    public Url() {}

    public Url(String url, boolean isMalicious) {
        this.url = url;
        this.isMalicious = isMalicious;
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }public void setId(Long id) {
        this.id = id;
    }public String getUrl() {
        return url;
    }public void setUrl(String url) {
        this.url = url;
    }
}