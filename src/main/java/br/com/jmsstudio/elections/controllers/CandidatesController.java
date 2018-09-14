package br.com.jmsstudio.elections.controllers;

import javax.transaction.Transactional;
import javax.validation.Valid;

import br.com.jmsstudio.elections.controllers.forms.NewCandidateForm;
import br.com.jmsstudio.elections.daos.CandidateDao;
import br.com.jmsstudio.elections.daos.EllectionDao;
import br.com.jmsstudio.elections.infra.ViewMessages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.jmsstudio.elections.models.Ellection;
import br.com.jmsstudio.elections.security.SystemUser;

@Controller
@Transactional
public class CandidatesController {
	
	@Autowired
	private CandidateDao candidateDao;
	@Autowired
	private EllectionDao ellectionDao;

	@GetMapping("/candidates/form")
	public String form(Model model, NewCandidateForm form, @AuthenticationPrincipal SystemUser owner) {
		model.addAttribute("ellections",ellectionDao.findByOwnerId(owner.getId()));
		return "/candidate/form";
	}
	
	@PostMapping("/candidates")
	public String save(Model model, @Valid NewCandidateForm form, BindingResult bindingResult,
			RedirectAttributes redirectAttributes,@AuthenticationPrincipal SystemUser owner) {
		if (bindingResult.hasErrors()) {
			return form(model, form,owner);
		}
		
		Ellection ellection = ellectionDao.findById(form.getEllectionId()).get();
		
		if(ellection.isOwnedByUser(owner)){
			candidateDao.save(form.toCandidate(ellectionDao));			
			ViewMessages.successFlash("candidato cadastrado com sucesso", redirectAttributes);
			return "redirect:/candidates/form";			
		}
		
		throw new RuntimeException("Acesso negado...");
		
	}
}
