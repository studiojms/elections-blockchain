package br.com.jmsstudio.elections.daos;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.jmsstudio.elections.models.VoteTicket;

@Repository
public interface VoteTicketDao extends CrudRepository<VoteTicket, Integer>{

	Optional<VoteTicket> findByUserNumber(String userNumber);

}
