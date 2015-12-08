package tr.com.mergentech.mergenbase.controller;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;

import tr.com.mergentech.mergenbase.dao.IBaseDao;
import tr.com.mergentech.mergenbase.entity.BaseEntity;
import tr.com.mergentech.mergenbase.gui.DataDisplayTable;

public abstract class ValueListController<T extends BaseEntity> implements Serializable {

	private static final long serialVersionUID = -7509959449812568330L;

	private List<T> itemList = null;
	protected Class<T> controllerClass;
	private T filterItem;
	private List<Criterion> criterionList = new ArrayList<Criterion>();
	private DataDisplayTable dataDisplayTable = new DataDisplayTable();

	@ManagedProperty(value = "#{sessionController}")
	private SessionController sessionController;

	@SuppressWarnings("unchecked")
	public ValueListController() {
		this.controllerClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	}

	@PostConstruct
	public void postConstruct() {
		init();
	}

	public SessionController getSessionController() {
		return sessionController;
	}

	public void setSessionController(SessionController sessionController) {
		this.sessionController = sessionController;
	}

	public void init() {
		try {
			filterItem = controllerClass.newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void refreshList() throws Exception {
		setItemList(getDao().fetchRecordsByCriteria(criterionList));
	}

	public List<T> getItemList() throws Exception {
		if (itemList == null) {
			refreshList();
		}
		return itemList;
	}

	public void setItemList(List<T> itemList) {
		this.itemList = itemList;
	}

	public T getFilterItem() {
		return filterItem;
	}

	public void setFilterItem(T filterItem) {
		this.filterItem = filterItem;
	}

	public List<Criterion> getCriterionList() {
		return criterionList;
	}

	public void setCriterionList(List<Criterion> criterionList) {
		this.criterionList = criterionList;
	}

	public DataDisplayTable getDataDisplayTable() {
		return dataDisplayTable;
	}

	public void setDataDisplayTable(DataDisplayTable dataDisplayTable) {
		this.dataDisplayTable = dataDisplayTable;
	}

	public abstract IBaseDao<T, Long> getDao();

	public void filter() throws Exception {
		try {
			setItemList(getDao().findLike(filterItem, MatchMode.ANYWHERE, criterionList));
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Hata", e.getMessage()));
		}
	}
}
