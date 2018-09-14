package br.com.jmsstudio.elections.service;

import br.com.jmsstudio.elections.models.Candidate;
import br.com.jmsstudio.elections.models.VoterIdentity;

public interface VoterService {

	/**
	 * 
	 * @param candidate candidato que vai levar o voto
	 * @param voterIdentity
	 * @return
	 */
	void execute(Candidate candidate, VoterIdentity voterIdentity);

}