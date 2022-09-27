package com.adamsero.votingapp.services;

import com.adamsero.votingapp.database.entities.Candidate;
import com.adamsero.votingapp.database.entities.Voter;
import com.adamsero.votingapp.database.repositories.CandidateRepository;
import com.adamsero.votingapp.database.repositories.VoterRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.contains;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class VoteServiceTest {
  private static final VoteValidationService mockValidationService =
      mock(VoteValidationService.class);
  private static final VoteService voteService =
      new VoteService(
          mockValidationService, mock(CandidateRepository.class), mock(VoterRepository.class));

  private static final Candidate sampleCandidate = new Candidate();
  private static final Voter sampleVoter = new Voter();

  @BeforeAll
  private static void setupMockedMethods() {
    given(
            mockValidationService.validateEntityExistsByName(
                any(String.class), contains("voter"), any(Function.class)))
        .willReturn(sampleVoter);

    given(
            mockValidationService.validateEntityExistsByName(
                any(String.class), contains("candidate"), any(Function.class)))
        .willReturn(sampleCandidate);
  }

  @BeforeEach
  private void resetVoterAndCandidateState() {
    sampleCandidate.setVotes(0);
    sampleVoter.setVotedFor(null);
  }

  @Test
  void votingShouldIncrementCandidatesVoteCount() {
    // given
    long voteCountBeforeVoting = sampleCandidate.getVotes();

    // when
    voteService.vote("", "");

    // then
    assertEquals(1, sampleCandidate.getVotes());
    assertNotEquals(voteCountBeforeVoting, sampleCandidate.getVotes());
  }

  @Test
  void votingShouldChangeVotersCandidate() {
    // given
    Candidate candidateBeforeVoting = sampleVoter.getVotedFor();

    // when
    voteService.vote("", "");

    // then
    assertEquals(sampleCandidate, sampleVoter.getVotedFor());
    assertNotEquals(candidateBeforeVoting, sampleVoter.getVotedFor());
  }
}
