package br.com.jmsstudio.elections.service;

import br.com.jmsstudio.elections.dtos.VoteDTO;
import br.com.jmsstudio.elections.models.Candidate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import br.com.jmsstudio.elections.models.VoterIdentity;

@Service
@Qualifier("descentralizado")
public class VoterBlockchainService implements VoterService {
	
	@Override
	public void execute(Candidate candidate, VoterIdentity identity) {
		VoteDTO voteDTO = new VoteDTO(candidate, identity);
		
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> response = restTemplate
				.postForEntity("http://localhost:8084/register/vote", voteDTO, String.class);
		
	}

}
