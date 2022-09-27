package com.adamsero.votingapp.database.repositories;

import com.adamsero.votingapp.database.entities.Candidate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CandidateRepository extends JpaRepository<Candidate, Long> {
  Optional<Candidate> findByName(String name);
}
