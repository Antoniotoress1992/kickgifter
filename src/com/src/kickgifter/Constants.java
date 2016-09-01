package com.src.kickgifter;

public class Constants {
	
	 
	public static final String Server_Url = "http://kickgifter.com";
    public static final String Server_Sign_Url = Server_Url + "/api_v1/user/signin";
	public static final String Server_Project_List = Server_Url + "/api_v1/project/lists";
	public static final String Server_Project_List_Expired = "This is a constant";
	public static final String USER_AGENT = "Mozilla/5.0";
	public static final String Server_Project_Detail = Server_Url + "/api_v1/project/detail";
	public static final String Server_Project_SignUp = Server_Url + "/api_v1/user/signup";
	public static final String Server_Gift_List = Server_Url + "/api_v1/gift/lists";
	public static final String Server_Gift_Submit = Server_Url + "/api_v1/project/submit_gift";
	public static final String Server_Bank_Send = Server_Url + "/api_v1/project/submit_bank";
	public static final String Server_Country_List = Server_Url + "/api_v1/country/lists";
	public static final String Server_Project_Add = Server_Url + "/api_v1/project/add";
	public static final String Server_Inviter_Add = Server_Url + "/api_v1/project/invite";
	public static final String Server_Project_Submit = Server_Url + "/api_v1/project/submit_gift";
	public static final String Server_Project_Choose = Server_Url + "/api_v1/project/choose_gift";
	public static final String Server_User_Detail = Server_Url + "/api_v1/user/detail";
	public static final String Server_User_Update = Server_Url + "/api_v1/user/update";
	public static final String Server_Phone_Upload = Server_Url + "/api_v1/contact/upload";
	 
	
	public static final String messageLogin = "The username and password are invalid";
	public static final String messageWarranty = "Please input  username and password correctly";
    public static final String messageSubmitFailed = "You have failed to submit gifts";
    public static final String messageSubmitSuccess = "You have successed to submit gifts";
    public static final String messageSubmitGift = "You have successed to submit bank";
    public static final String messageSubmitSignUpSuccess = "You have successed to sign up";
    public static final String messageSubmitSignUpFailed = "You have failed to sign up";
    public static final String messageAvailableWarranty = "The available amount should be more than zero";
    public static final String messageProjectAdd = "You have successfully added project";
    public static final String messageConnectionFailed = "The connection failed";
    public static final String messageConnectionTitle = "Connection";
    public static final String messageInputErrorTitle = "Input";
    public static final String messageInputErrorMessage = "Please input content correctly";
    public static final String messageBankTrnasferFailed = "You failed to sumbit to transfer bank";
    public static final String messageProjectList = "You successed to add invitors";
    public static final String messageProjectListFailed ="You failed to add invitors";
    public static final String messageProjectChooseGift = "You successed to choose gift";
    public static final String messageProjectChooseGiftFailed="You failed to choose gift";
    public static final String messageFriend = "Are you sure to choose gift";
    public static final String messageLanguageSelection = "Language Selection";
    public static final String messageUpdateProfile = "Update Profile";
    public static final String messageUploadSuccess = "The contacts has been uploaded succesfully";
    public static final String noGift = "There is no gifts";
    public static final String noCoupon = "There is no coupon codes";
    public static final String noBank = "There is no bank transfer";
    
    public static final String messageProjectNameAddFailed = "Please input project name correctly";
    public static final String messageProjectTelAddFailed = "Please input phone number correctly";
    public static final String messageProjectCountryAddFailed = "Please input country name correctly";
    public static final String messageProjectAmountAddFailed = "Please input project amount correctly";
    public static final String messageProjectMessageAddFailed = "Please input message correctly";
    public static final String messageProjectExpiredAddFailed = "Please input expired date correctly";
    public static final String messageProjectInvitersAddFailed = "Please input inviters correctly";
    public static final String messageProfileUpdateSuccess = "You have updated profile successfully";
    public static final String messageProfileUpdateFailed = "You have failed to update profile successfully";
    
}
