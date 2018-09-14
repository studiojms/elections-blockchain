package br.com.jmsstudio.elections.service;

import java.util.Optional;

import br.com.jmsstudio.elections.daos.VoteDao;
import br.com.jmsstudio.elections.daos.VoteTicketDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import br.com.jmsstudio.elections.models.Candidate;
import br.com.jmsstudio.elections.models.VoteTicket;
import br.com.jmsstudio.elections.models.VoterIdentity;

@Service
@Qualifier("centralizado")
public class VoterCentralDatabaseService implements VoterService {
	
	@Autowired
	private VoteTicketDao voteTicketDao;
	@Autowired
	private VoteDao voteDao;

	@Override
	public void execute(Candidate candidate,VoterIdentity voterIdentity){
		
		Optional<VoteTicket> possibleTicket = voteTicketDao.findByUserNumber(voterIdentity.getUserNumber());
				
		possibleTicket
		.filter(ticket -> !voteDao.findByVoteIdTicketUserNumber(voterIdentity.getUserNumber()).isPresent())
		.map(ticket -> ticket.vote(candidate))
		.map(voteDao ::  save)
		.orElseThrow(() -> new RuntimeException("esse voto não pode ser computado"));		
	}
}
