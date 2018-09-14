package br.com.jmsstudio.elections.controllers;

import javax.transaction.Transactional;
import javax.validation.Valid;

import br.com.jmsstudio.elections.daos.EllectionDao;
import br.com.jmsstudio.elections.daos.VoterDao;
import br.com.jmsstudio.elections.infra.ViewMessages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.jmsstudio.elections.controllers.forms.NewVoterForm;

@Controller
@Transactional
public class VotersController {

	@Autowired
	private VoterDao voterDao;
	@Autowired
	private EllectionDao ellectionDao;

	@GetMapping("/voters/form")
	public String form(Model model, NewVoterForm form) {
		// TODO trocar para o metodo que so retorna as que não expiraram
		model.addAttribute("ellections", ellectionDao.findAll());
		return "/voter/form";
	}

	@PostMapping("/voters")
	public String save(Model model, @Valid NewVoterForm form, BindingResult bindingResult,
			RedirectAttributes redirectAttributes) {
		if (bindingResult.hasErrors()) {
			return form(model, form);
		}

		voterDao.save(form.toVoter(ellectionDao));
		ViewMessages.successFlash("cadastro realizado com sucesso. Sua liberação será analisada", redirectAttributes);
		return "redirect:/voters/form";

	}
}
