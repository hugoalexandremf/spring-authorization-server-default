package com.hfernandes.springauthorizationserverdefault.db.model;

import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import java.io.Serializable;

@Entity
@Table(name = "usr")
public class User implements Serializable {

     @Id
     @GenericGenerator(
             name = "usrIdSeq",
             strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
             parameters = {
                     @Parameter(name = "sequence_name", value = "usr_id_seq"),
                     @Parameter(name = "increment_size", value = "1")
             }
     )
     @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "usrIdSeq")
     @Column(name = "id")
     private Long id;

     @Column(name = "usrname")
     private String username;

     @Column(name = "password")
     private String password;

     @Column(name = "enabled")
     private Boolean enabled;


     protected User() {

     }

     public User(String username, String password) {
          this.username = username;
          this.password = password;
          this.enabled = true;
     }


     public Long getId() {
          return id;
     }

     public String getUsername() {
          return username;
     }

     public String getPassword() {
          return password;
     }

     public Boolean getEnabled() {
          return enabled;
     }


     public void setId(Long id) {
          this.id = id;
     }

     public void setUsername(String username) {
          this.username = username;
     }

     public void setPassword(String password) {
          this.password = password;
     }

     public void setEnabled(Boolean enabled) {
          this.enabled = enabled;
     }


     @Override
     public String toString() {
          return "User{" +
                  "id=" + id +
                  ", username='" + username + '\'' +
                  ", password='" + password + '\'' +
                  ", enabled=" + enabled +
                  '}';
     }

     public String toStringHash() {
          return "User{" +
                  "id=" + id +
                  ", username='" + username + '\'' +
                  ", password='" + password.hashCode() + '\'' +
                  ", enabled=" + enabled +
                  '}';
     }
}
