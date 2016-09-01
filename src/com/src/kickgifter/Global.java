package com.src.kickgifter;

import java.util.ArrayList;

import android.app.Application;

public  class  Global extends Application {
    public String name;
	public String email;
    public String phone;
    public String countryId;
    public String userId;
    public String expiredAt;
    public String projectId;
    public String contactPhone;
    public String contacts;
    public String availableAmount;
    public String userNameSignUp ;
    public String userEmailSignUp;
    public String userPhoneNumberSignUp;
    public String userCountrySignUp;
    public String userPasswordSignUp;
    public String userConfirmPasswordSingUp;
    public String projectNameAdd;
    public String providerProjectAdd;
    public String countryProjectAdd;
    public String amountProjectAdd;
    public String messageProjectAdd;
    public String expiredProjectAdd;
    public String invitersProjectAdd;
    public String coutryIdProjectAdd;
    public String countryName ;
    public ArrayList<String> contactListPhone ;
    public ArrayList<String> contactListName ;
    public String contactList;
    
    public String getContactList(){
    	return contactList;
    }
    public void setContactList(String contactList){
    	this.contactList = contactList;
    }
    
    public ArrayList<String> getContactListPhone(){
    	return contactListPhone;
    }
    public void setContactListPhone(ArrayList<String> contactListPhone){
    	this.contactListPhone = contactListPhone ;
    }
    
    public ArrayList<String> getContactListName(){
    	return contactListName;
    }
    
    public void setContactListName(ArrayList<String> contactListName){
    	this.contactListName = contactListName ;
    }
    
    public String getContryName(){
    	return countryName;
    }
    
    public void setCoutryName(){
    	this.countryName = countryName ;
    }
    
    public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getCountryId() {
		return countryId;
	}
	public void setCountryId(String countryId) {
		this.countryId = countryId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getExpiredAt() {
		return expiredAt;
	}
	public void setExpiredAt(String expiredAt) {
		this.expiredAt = expiredAt;
	}
	public String getProjectId() {
		return projectId;
	}
	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
	public String getContactPhone() {
		return contactPhone;
	}
	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}
	public String getContacts() {
		return contacts;
	}
	public void setContacts(String contacts) {
		this.contacts = contacts;
	}
	public String getAvailableAmount() {
		return availableAmount;
	}
	public void setAvailableAmount(String availableAmount) {
		this.availableAmount = availableAmount;
	}
	public String getUserNameSignUp() {
		return userNameSignUp;
	}
	public void setUserNameSignUp(String userNameSignUp) {
		this.userNameSignUp = userNameSignUp;
	}
	public String getUserEmailSignUp() {
		return userEmailSignUp;
	}
	public void setUserEmailSignUp(String userEmailSignUp) {
		this.userEmailSignUp = userEmailSignUp;
	}
	public String getUserPhoneNumberSignUp() {
		return userPhoneNumberSignUp;
	}
	public void setUserPhoneNumberSignUp(String userPhoneNumberSignUp) {
		this.userPhoneNumberSignUp = userPhoneNumberSignUp;
	}
	public String getUserCountrySignUp() {
		return userCountrySignUp;
	}
	public void setUserCountrySignUp(String userCountrySignUp) {
		this.userCountrySignUp = userCountrySignUp;
	}
	public String getUserPasswordSignUp() {
		return userPasswordSignUp;
	}
	public void setUserPasswordSignUp(String userPasswordSignUp) {
		this.userPasswordSignUp = userPasswordSignUp;
	}
	public String getUserConfirmPasswordSingUp() {
		return userConfirmPasswordSingUp;
	}
	public void setUserConfirmPasswordSingUp(String userConfirmPasswordSingUp) {
		this.userConfirmPasswordSingUp = userConfirmPasswordSingUp;
	}
	public String getProjectNameAdd() {
		return projectNameAdd;
	}
	public void setProjectNameAdd(String projectNameAdd) {
		this.projectNameAdd = projectNameAdd;
	}
	public String getProviderProjectAdd() {
		return providerProjectAdd;
	}
	public void setProviderProjectAdd(String providerProjectAdd) {
		this.providerProjectAdd = providerProjectAdd;
	}
	public String getCountryProjectAdd() {
		return countryProjectAdd;
	}
	public void setCountryProjectAdd(String countryProjectAdd) {
		this.countryProjectAdd = countryProjectAdd;
	}
	public String getAmountProjectAdd() {
		return amountProjectAdd;
	}
	public void setAmountProjectAdd(String amountProjectAdd) {
		this.amountProjectAdd = amountProjectAdd;
	}
	public String getMessageProjectAdd() {
		return messageProjectAdd;
	}
	public void setMessageProjectAdd(String messageProjectAdd) {
		this.messageProjectAdd = messageProjectAdd;
	}
	public String getExpiredProjectAdd() {
		return expiredProjectAdd;
	}
	public void setExpiredProjectAdd(String expiredProjectAdd) {
		this.expiredProjectAdd = expiredProjectAdd;
	}
	public String getInvitersProjectAdd() {
		return invitersProjectAdd;
	}
	public void setInvitersProjectAdd(String invitersProjectAdd) {
		this.invitersProjectAdd = invitersProjectAdd;
	}
	public String getCoutryIdProjectAdd() {
		return coutryIdProjectAdd;
	}
	public void setCoutryIdProjectAdd(String coutryIdProjectAdd) {
		this.coutryIdProjectAdd = coutryIdProjectAdd;
	}
	
} 
