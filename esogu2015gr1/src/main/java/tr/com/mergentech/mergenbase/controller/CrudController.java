package tr.com.mergentech.mergenbase.controller;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;

import tr.com.mergentech.mergenbase.dao.IBaseDao;
import tr.com.mergentech.mergenbase.entity.BaseEntity;

public abstract class CrudController<T extends BaseEntity> implements Serializable {

	private static final long serialVersionUID = -7509959449812568330L;

	protected T currentItem;
	private List<T> itemList = null;
	protected Class<T> controllerClass;

	@ManagedProperty(value = "#{sessionController}")
	private SessionController sessionController;

	@SuppressWarnings("unchecked")
	public CrudController() {
		this.controllerClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	}

	@PostConstruct
	public void init() {
		try {
			newItem();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public SessionController getSessionController() {
		return sessionController;
	}

	public void setSessionController(SessionController sessionController) {
		this.sessionController = sessionController;
	}

	public void refreshList() throws Exception {
		setItemList(getDao().fetchRecordsAll());
	}

	public T getCurrentItem() {
		return currentItem;
	}

	public void setCurrentItem(T currentItem) {
		this.currentItem = currentItem;
	}

	public List<T> getItemList() {
		return itemList;
	}

	public void setItemList(List<T> itemList) {
		this.itemList = itemList;
	}

	public void newItem() throws Exception {
		newItem(false);
	}

	public void newItem(boolean refreshList) throws Exception {
		if (refreshList) {
			refreshList();
		}
		setCurrentItem(controllerClass.newInstance());
	}

	public boolean isManaged() {
		return currentItem.getId() != null && currentItem.getId() > 0;
	}

	public void saveItem() throws Exception {
		if (isManaged()) {
			currentItem.setGuncellemeTarih(new Date());
			getDao().update(currentItem, currentItem.getId());
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Update", "Update Successfull"));
		} else {
			currentItem.setOlusturmaTarih(new Date());
			currentItem.setGuncellemeTarih(new Date());
			getDao().insert(currentItem);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Insert", "Insert Successfull"));
		}
	}

	public void saveAndClearItem() throws Exception {
		saveItem();
		newItem();
	}

	public void deleteItem() throws Exception {
		if (currentItem != null && isManaged()) {
			getDao().delete(currentItem.getId());
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Delete", "Delete Successfull"));
			newItem();
		}
	}

	public abstract IBaseDao<T, Long> getDao();
}
