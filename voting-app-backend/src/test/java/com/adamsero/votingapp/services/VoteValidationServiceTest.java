package com.adamsero.votingapp.services;

import com.adamsero.votingapp.database.entities.Candidate;
import com.adamsero.votingapp.database.entities.Voter;
import com.adamsero.votingapp.database.repositories.CandidateRepository;
import com.adamsero.votingapp.database.repositories.VoterRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class VoteValidationServiceTest {
  private static final CandidateRepository candidateRepository = mock(CandidateRepository.class);
  private static final VoterRepository voterRepository = mock(VoterRepository.class);
  private final VoteValidationService voteValidationService =
      new VoteValidationService(candidateRepository, voterRepository);

  @BeforeAll
  private static void setupMockedMethods() {
    given(candidateRepository.findByName("DuplicateCandidateName"))
        .willReturn(Optional.of(new Candidate()));

    given(voterRepository.findByName("DuplicateVoterName")).willReturn(Optional.of(new Voter()));
  }

  @Test
  void validateCandidateNameUniquenessShouldThrowIllegalArgumentExceptionForDuplicateName() {
    // when
    // then
    IllegalArgumentException illegalArgumentException =
        assertThrows(
            IllegalArgumentException.class,
            () -> voteValidationService.validateCandidateNameUniqueness("DuplicateCandidateName"));
    assertEquals("Candidate with that name already exists", illegalArgumentException.getMessage());
  }

  @Test
  void validateVoterNameUniquenessShouldThrowIllegalArgumentExceptionForDuplicateName() {
    // when
    // then
    IllegalArgumentException illegalArgumentException =
        assertThrows(
            IllegalArgumentException.class,
            () -> voteValidationService.validateVoterNameUniqueness("DuplicateVoterName"));
    assertEquals("Voter with that name already exists", illegalArgumentException.getMessage());
  }

  @Test
  void validateVoterHasNotVotedShouldThrowIllegalArgumentExceptionForVoterThatAlreadyVoted() {
    // given
    Voter voter = new Voter();
    voter.setName("Sample Voter");
    voter.setVotedFor(new Candidate());

    // when
    // then
    IllegalArgumentException illegalArgumentException =
        assertThrows(
            IllegalArgumentException.class,
            () -> voteValidationService.validateVoterHasNotVoted(voter));
    assertEquals(
        String.format("Voter %s has already voted", voter.getName()),
        illegalArgumentException.getMessage());
  }

  @Test
  void validateEntityExistsByNameShouldThrowResponseStatusExceptionWhenEntityDoesntExist() {
    // given
    String entityName = "Sample Candidate";
    String className = "candidate";
    Function<String, Optional<Candidate>> findMethod = name -> Optional.empty();

    // when
    // then
    ResponseStatusException responseStatusException =
        assertThrows(
            ResponseStatusException.class,
            () ->
                voteValidationService.validateEntityExistsByName(
                    entityName, className, findMethod));
    assertEquals(
        String.format("Could not find %s with name: %s", className, entityName),
        responseStatusException.getReason());
    assertEquals(HttpStatus.NOT_FOUND, responseStatusException.getStatus());
  }
}
