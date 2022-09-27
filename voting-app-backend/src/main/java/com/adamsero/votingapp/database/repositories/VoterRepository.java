package com.adamsero.votingapp.database.repositories;

import com.adamsero.votingapp.database.entities.Voter;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface VoterRepository extends JpaRepository<Voter, Long> {
  Optional<Voter> findByName(String name);

  List<Voter> findByVotedForIsNull();
}
