package br.com.jmsstudio.elections.controllers.forms;

import javax.validation.constraints.NotBlank;

public class GenerateCodeForm {

	@NotBlank
	private String voterNumber;

	public String getVoterNumber() {
		return voterNumber;
	}

	public void setVoterNumber(String voterNumber) {
		this.voterNumber = voterNumber;
	}

}
