package br.com.jmsstudio.elections.controllers;

import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

import br.com.jmsstudio.elections.controllers.forms.ChooseElectionForm;
import br.com.jmsstudio.elections.daos.CandidateDao;
import br.com.jmsstudio.elections.daos.EllectionDao;
import br.com.jmsstudio.elections.daos.VoteTicketDao;
import br.com.jmsstudio.elections.daos.VoterDao;
import br.com.jmsstudio.elections.infra.ViewMessages;
import br.com.jmsstudio.elections.models.Voter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.jmsstudio.elections.controllers.forms.GenerateCodeForm;
import br.com.jmsstudio.elections.controllers.forms.VotingBoothForm;
import br.com.jmsstudio.elections.models.Candidate;
import br.com.jmsstudio.elections.models.VoteTicket;
import br.com.jmsstudio.elections.service.VoterService;

@Controller
@Transactional
public class VotingBoothController {

	@Autowired
	private CandidateDao candidateDao;
	@Autowired
	private VoterDao voterDao;
	@Autowired
	private VoteTicketDao voteTicketDao;
	@Autowired
	@Qualifier("descentralizado")
	private VoterService voterService;
	@Autowired
	private EllectionDao ellectionDao;
	
	@GetMapping("/votingbooth/code/form")
	public String codeGeneratorForm(Model model, GenerateCodeForm form) {
		return "/ellection/generate-code-form";
	}
	
	@PostMapping("/votingbooth/code")
	public String generateTicket(Model model, @Valid GenerateCodeForm form, BindingResult bindingResult,
			RedirectAttributes redirectAttributes) {
		if (bindingResult.hasErrors()) {
			return codeGeneratorForm(model, form);
		}
		
		Optional<Voter> voter = voterDao.findByNumber(form.getVoterNumber());
		if(voter.isPresent() && voter.get().isApproved()){
			VoteTicket ticket = voteTicketDao.save(voter.get().generateTicket());			
			ViewMessages.successFlash("O seu código de votação é "+ticket.getUserNumber(), redirectAttributes);
			return "redirect:/votingbooth/code/form?ticketCode="+ticket.getUserNumber();
		}
		
		ViewMessages.error("o número passado está errado ou ainda não está liberado", model);
		return codeGeneratorForm(model, form);
		

	}
	
	@GetMapping("/votingbooth/choose/ellection/form")
	public String chooseEllectionForm(Model model, ChooseElectionForm form) {
		model.addAttribute("ellections", ellectionDao.findAll());
		return "/ellection/choose-ellection";
	}
	
	@GetMapping("/votingbooth/choose/ellection")
	public String methodName(Model model, @Valid ChooseElectionForm form, BindingResult bindingResult,
			RedirectAttributes redirectAttributes) {
		if (bindingResult.hasErrors()) {
			return chooseEllectionForm(model, form);
		}

		return "redirect:/votingbooth?ellectionId="+form.getEllectionId();
	}

	@GetMapping("/votingbooth")
	public String form(Model model, VotingBoothForm form) {
		model.addAttribute("candidates", candidateDao.findAllByEllectionId(form.getEllectionId()));
		return "/ellection/voting-booth";
	}

	@PostMapping("/votingbooth/vote")
	public String vote(Model model, @Valid VotingBoothForm form, BindingResult bindingResult,
			RedirectAttributes redirectAttributes) {

		if (bindingResult.hasErrors()) {
			return form(model, form);
		}

		
		Candidate candidate = candidateDao.findById(form.getCandidateId()).get();		
		voterService.execute(candidate,voteTicketDao.findByUserNumber(form.getTicketCode()).get());
		


		ViewMessages.successFlash("seu voto foi registrado com sucesso", redirectAttributes);
		return "redirect:/votingbooth?ellectionId="+form.getEllectionId();
	}
}
