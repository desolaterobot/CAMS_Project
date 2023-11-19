package Test;

import java.util.ArrayList;

public class Student extends User{
	boolean isCommitteeMember;

	public Student(String name, String email, String faculty, String password) {
		super(name, email, faculty, password);
		this.isCommitteeMember = false;
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
