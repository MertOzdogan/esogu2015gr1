package tr.com.mergentech.mergenbase.controller;

import java.io.Serializable;
import java.util.Date;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import tr.com.mergentech.mergenerp.entity.User;

@ManagedBean(name = "sessionController")
@SessionScoped
public class SessionController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -916435091826519311L;

	public Date getDate() {
		return new Date();
	}

	private User selectedUser;

	public User getSelectedUser() {
		return selectedUser;
	}

	public void setSelectedUser(User selectedUser) {
		this.selectedUser = selectedUser;
	}

}
