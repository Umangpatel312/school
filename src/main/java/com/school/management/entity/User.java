package com.school.management.entity;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "user")
@Getter
@Setter
@NoArgsConstructor
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private int id;

  @Column(name = "email")
  private String email;

  @Column(name = "password")
  private String password;

  @ManyToOne(fetch = FetchType.LAZY,
      cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
  @JoinColumn(name = "role_id")
  private Role role;

  @OneToOne(fetch = FetchType.LAZY, mappedBy = "userId",
      cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
  private UserCreated userCreated;

  @OneToMany(fetch = FetchType.LAZY, mappedBy = "userCreatedBy",
      cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
  private List<UserCreated> userAdded;


  public void addUserAdded(UserCreated userCreated) {
    if (userCreated != null) {
      if (!this.userAdded.contains(userCreated)) {
        this.userAdded.add(userCreated);
        userCreated.setUserCreatedBy(this);
      }
    }
  }

  public void addUserCreated() {

  }

  public User(String email, String password) {
    this.email = email;
    this.password = password;
  }

  @Override
  public String toString() {
    return "User [id=" + id + ", email=" + email + ", password=" + password + "]";
  }

}
