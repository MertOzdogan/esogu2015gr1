package tr.com.mergentech.mergenbase.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;

public interface IBaseDao<T, ID extends Serializable> {

	public T insert(T entity) throws Exception;

	public T update(T entity, ID id) throws Exception;

	public void delete(ID id) throws Exception;

	public T fetchRecordById(ID id) throws Exception;
	
	public T loadRecordById(ID id) throws Exception;

	public List<T> fetchRecordsAll() throws Exception;

	public List<T> fetchRecordsByExample(T exampleInstance, boolean excludeZeros, String... excludeProperty) throws Exception;

	public List<T> fetchRecordsLimited(int pageNumber, int pageLimit) throws Exception;

	public int fetchTotalRecordSize() throws Exception;
	
	public List<T> fetchRecordsByCriteria(T entity) throws Exception;
	
	public List<T> fetchRecordsByCriteriaLimitedList(T entity , int pageNumber, int pageLimit) throws Exception;

	public List<T> fetchRecordsAllWithSorting(String sortingField, boolean ASC) throws Exception;

	public List<T> fetchRecordsLimitedWithSorting(int pageNumber, int pageLimit, String sortingField, boolean ASC) throws Exception;

	public List<T> fetchRecordsByCriteriaLimitedListWithSorting( int first, int pageLimit, String sortingField, boolean ASC,Map<String, String> filters, String[] hibernateFilterName, List<Map<String, String>> hibernateFilterParams) throws Exception;

	public int fetchCriteriaListTotalSize(T entity) throws Exception;

	public List<T> findLike(T entity, MatchMode matchMode) throws Exception;
	
	public List<T> findLike(T entity, MatchMode matchMode, List<Criterion> criterionList) throws Exception;
	
	public List<T> fetchRecordsByCriteria(List<Criterion> criterionList) throws Exception;

	public int getRowCount();

}