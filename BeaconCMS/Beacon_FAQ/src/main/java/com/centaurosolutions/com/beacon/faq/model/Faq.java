package com.centaurosolutions.com.beacon.faq.model;

public class Faq {
	private String id;
	private String section;
	private String question;
	private String answer;
	public Faq() {
		// TODO Auto-generated constructor stub
	}
	/**
	 * @param section
	 * @param question
	 * @param answer
	*/
	public Faq(String section,String question,String answer) {
		super();
		this.section=section;
		this.question=question;
		this.answer=answer;
	}
	
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the section
	 */
	public String getSection() {
		return section;
	}

	/**
	 * @param section the section to set
	 */
	public void setSection(String section) {
		this.section = section;
	}
	
	/**
	 * @return the question
	 */
	public String getQuestion() {
		return question;
	}

	/**
	 * @param question the question to set
	 */
	public void setQuestion(String question) {
		this.question = question;
	}
	
	/**
	 * @return the answer
	 */
	public String getAnswer() {
		return answer;
	}

	/**
	 * @param answer the answer to set
	 */
	public void setAnswer(String answer) {
		this.answer = answer;
	}


}
