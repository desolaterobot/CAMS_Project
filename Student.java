package Test;

import java.util.ArrayList;

public class Student extends User{
	boolean isCommitteeMember;
	String whichCommitteeCamp;
	
	public Student(String name, String email, String faculty, String password,boolean isCommitteeMember,String whichCommitteeCamp) {
		super(name, email, faculty, password);
		this.isCommitteeMember = isCommitteeMember;
		this.whichCommitteeCamp = whichCommitteeCamp;
		// TODO Auto-generated constructor stub
		
	}


	public boolean isCommitteeMember() {
		// TODO Auto-generated method stub
		return this.isCommitteeMember;
	}


	public void setCommitteeMember(boolean b) {
		// TODO Auto-generated method stub
		this.isCommitteeMember = b;
	}



}
