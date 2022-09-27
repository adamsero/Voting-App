package com.adamsero.votingapp.database.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.Set;

@Entity
@Getter
@Setter
public class Candidate {
  @Id @GeneratedValue private long id;

  @Column(nullable = false, unique = true)
  private String name;

  @Column(nullable = false)
  private long votes;

  @OneToMany(mappedBy = "votedFor")
  private Set<Voter> votedOnBy;
}
