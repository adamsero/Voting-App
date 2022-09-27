package com.adamsero.votingapp.services;

import com.adamsero.votingapp.database.entities.Voter;
import com.adamsero.votingapp.database.repositories.CandidateRepository;
import com.adamsero.votingapp.database.repositories.VoterRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;
import java.util.function.Function;

@Service
@AllArgsConstructor
public class VoteValidationService {
  private final CandidateRepository candidateRepository;
  private final VoterRepository voterRepository;

  public void validateCandidateNameUniqueness(String name) {
    candidateRepository
        .findByName(name)
        .ifPresent(
            candidate -> {
              throw new IllegalArgumentException("Candidate with that name already exists");
            });
  }

  public void validateVoterNameUniqueness(String name) {
    voterRepository
        .findByName(name)
        .ifPresent(
            voter -> {
              throw new IllegalArgumentException("Voter with that name already exists");
            });
  }

  public void validateVoterHasNotVoted(Voter voter) {
    if (voter.getVotedFor() != null) {
      throw new IllegalArgumentException(
          String.format("Voter %s has already voted", voter.getName()));
    }
  }

  public <T> T validateEntityExistsByName(
      String name, String className, Function<String, Optional<T>> findMethod) {
    return findMethod
        .apply(name)
        .orElseThrow(
            () ->
                new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    String.format("Could not find %s with name: %s", className, name)));
  }
}
