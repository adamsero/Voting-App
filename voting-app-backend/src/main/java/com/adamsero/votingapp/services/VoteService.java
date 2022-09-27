package com.adamsero.votingapp.services;

import com.adamsero.votingapp.database.dto.CandidateDTO;
import com.adamsero.votingapp.database.dto.VoterDTO;
import com.adamsero.votingapp.database.entities.Candidate;
import com.adamsero.votingapp.database.entities.Voter;
import com.adamsero.votingapp.database.repositories.CandidateRepository;
import com.adamsero.votingapp.database.repositories.VoterRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class VoteService {

  private final VoteValidationService voteValidationService;
  private final CandidateRepository candidateRepository;
  private final VoterRepository voterRepository;

  public void createCandidate(String name) {
    voteValidationService.validateCandidateNameUniqueness(name);
    Candidate candidate = new Candidate();
    candidate.setName(name);
    candidate.setVotes(0);
    candidateRepository.save(candidate);
  }

  public List<CandidateDTO> getAllCandidates() {
    return candidateRepository.findAll(Sort.by(Sort.Direction.DESC, "votes")).stream()
        .map(CandidateDTO::new)
        .collect(Collectors.toList());
  }

  public List<String> getAllCandidateNames() {
    return candidateRepository.findAll().stream()
        .map(Candidate::getName)
        .collect(Collectors.toList());
  }

  public void createVoter(String name) {
    voteValidationService.validateVoterNameUniqueness(name);
    Voter voter = new Voter();
    voter.setName(name);
    voterRepository.save(voter);
  }

  public List<VoterDTO> getAllVoters() {
    return voterRepository.findAll(Sort.by("name")).stream()
        .map(VoterDTO::new)
        .collect(Collectors.toList());
  }

  public List<String> getAvailableVoterNames() {
    return voterRepository.findByVotedForIsNull().stream()
        .map(Voter::getName)
        .collect(Collectors.toList());
  }

  @Transactional
  public void vote(String voterName, String candidateName) {
    Voter voter =
        voteValidationService.validateEntityExistsByName(
            voterName, "voter", voterRepository::findByName);
    Candidate candidate =
        voteValidationService.validateEntityExistsByName(
            candidateName, "candidate", candidateRepository::findByName);
    voteValidationService.validateVoterHasNotVoted(voter);

    voter.setVotedFor(candidate);
    candidate.setVotes(candidate.getVotes() + 1);
  }
}
