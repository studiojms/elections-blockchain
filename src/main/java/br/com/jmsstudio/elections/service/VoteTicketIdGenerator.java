package br.com.jmsstudio.elections.service;

import java.util.function.Supplier;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import br.com.jmsstudio.elections.models.VoteTicketId;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class VoteTicketIdGenerator implements Supplier<VoteTicketId>{
	
	@PersistenceContext
	private EntityManager manager;

	@Override
	public VoteTicketId get() {
		VoteTicketId ticketId = new VoteTicketId();
		manager.persist(ticketId);
		return ticketId;
	}

	
}
