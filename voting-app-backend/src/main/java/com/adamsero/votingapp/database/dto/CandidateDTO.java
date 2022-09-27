package com.adamsero.votingapp.database.dto;

import com.adamsero.votingapp.database.entities.Candidate;

public record CandidateDTO(String name, long votes) {
    public CandidateDTO(Candidate candidate) {
        this(candidate.getName(), candidate.getVotes());
    }
}
