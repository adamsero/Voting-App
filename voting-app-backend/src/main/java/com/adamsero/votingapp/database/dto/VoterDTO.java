package com.adamsero.votingapp.database.dto;

import com.adamsero.votingapp.database.entities.Voter;

public record VoterDTO(String name, String votedFor) {
    public VoterDTO(Voter voter) {
        this(voter.getName(), voter.getVotedFor() == null ? null : voter.getVotedFor().getName());
    }
}
