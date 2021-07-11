package com.siliconvalve.linktrack.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;

@Document
public class LinkClick {
   
    @Id
    private String id;
    private String urlId;
    private LocalDateTime created;
    private String userAgent;
    private String sourceIP;

    public LinkClick() {
    }

    public LinkClick(String urlId, String userAgent, String sourceIP, LocalDateTime created) {
    this.urlId = urlId;
    this.userAgent = userAgent;
    this.sourceIP = sourceIP;
    this.created = created;
    }

    public String getId() {
    return id;
    }

    public void setId(String id) {
    this.id = id;
    }

    public String getUrlId() {
    return urlId;
    }

    public void setUrlId(String urlId) {
    this.urlId = urlId;
    }

    public String getUserAgent() {
    return userAgent;
    }

    public void setUserAgent(String userAgent) {
    this.userAgent = userAgent;
    }

    public String getSourceIP() {
    return sourceIP;
    }

    public void setSourceIP(String sourceIP) {
    this.sourceIP = sourceIP;
    }

    public LocalDateTime getCreated() {
    return created;
    }

    public void setCreated(LocalDateTime created) {
    this.created = created;
    }
}