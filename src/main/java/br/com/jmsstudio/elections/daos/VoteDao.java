package br.com.jmsstudio.elections.daos;

import java.util.Optional;

import br.com.jmsstudio.elections.models.VoteId;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.jmsstudio.elections.models.Vote;

@Repository
public interface VoteDao extends CrudRepository<Vote, VoteId>{

	Optional<Vote> findByVoteIdTicketUserNumber(String ticketCode);

}
