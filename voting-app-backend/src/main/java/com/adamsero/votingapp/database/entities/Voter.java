package com.adamsero.votingapp.database.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
@Getter
@Setter
public class Voter {
  @Id @GeneratedValue private long id;

  @Column(nullable = false, unique = true)
  private String name;

  @ManyToOne private Candidate votedFor;
}
