package com.siliconvalve.linktrack.models;

import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;

@Document
public class ShortLink {
   
      private String id;
      private String url;
      private LocalDateTime created;
      private Integer visitors;
      private LocalDateTime visitorsLastUpdated;
    
      public ShortLink() {
      }
    
      public ShortLink(String id, String url, LocalDateTime created) {
        this.id = id;
        this.url = url;
        this.created = created;
      }
    
      public String getId() {
        return id;
      }
    
      public void setId(String id) {
        this.id = id;
      }
    
      public String getUrl() {
        return url;
      }
    
      public void setUrl(String url) {
        this.url = url;
      }
    
      public LocalDateTime getCreated() {
        return created;
      }
    
      public void setCreated(LocalDateTime created) {
        this.created = created;
      }

      public Integer getVisitors() {
        return visitors;
      }

      public void setVisitors(Integer visistors) {
        this.visitors = visistors;
      }

      public LocalDateTime getVisitorsLastUpdated() {
        return visitorsLastUpdated;
      }
    
      public void setVisitorsLastUpdated(LocalDateTime visitorsLastUpdated) {
        this.visitorsLastUpdated = visitorsLastUpdated;
      }
}