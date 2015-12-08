package tr.com.mergentech.mergenbase.dao;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Filter;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.transaction.annotation.Transactional;

import tr.com.mergentech.mergenbase.util.PersistenceUtil;
import tr.com.mergentech.mergenbase.util.UtilityOperation;;

public class BaseDao<T, ID extends Serializable> {

	private SessionFactory sessionFactory;

	private Class<T> persistenceClass;

	protected int rowCount;

	public int getRowCount() {
		return rowCount;
	}

	public void setRowCount(int rowCount) {
		this.rowCount = rowCount;
	}

	@SuppressWarnings("unchecked")
	public BaseDao() {
		this.persistenceClass = (Class<T>) ((ParameterizedType) getClass()
				.getGenericSuperclass()).getActualTypeArguments()[0];
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public Class<T> getPersistenceClass() {
		return persistenceClass;
	}

	@Transactional
	public T insert(T entity) throws Exception {

		getSessionFactory().getCurrentSession().save(entity);
		getSessionFactory().getCurrentSession().flush();
		getSessionFactory().getCurrentSession().refresh(entity);

		return entity;
	}

	@Transactional
	public T update(T entity, ID id) throws Exception {

		@SuppressWarnings("unchecked")
		T dbEntity = (T) getSessionFactory().getCurrentSession().get(
				getPersistenceClass(), id);

		if (dbEntity != null) {

			UtilityOperation.copyValues(dbEntity, entity);

			getSessionFactory().getCurrentSession().evict(dbEntity);

			getSessionFactory().getCurrentSession().update(entity);
			getSessionFactory().getCurrentSession().flush();
			getSessionFactory().getCurrentSession().refresh(entity);

		} else {

			throw new Exception("Nesne Bulunamadi");
		}

		return entity;
	}

	@Transactional
	@SuppressWarnings("unchecked")
	public void delete(ID id) throws Exception {
		T entity = (T) getSessionFactory().getCurrentSession().get(
				getPersistenceClass(), id);
		getSessionFactory().getCurrentSession().delete(entity);

	}

	@Transactional
	@SuppressWarnings("unchecked")
	public T fetchRecordById(ID id) throws Exception {
		T entity = (T) getSessionFactory().getCurrentSession().get(
				getPersistenceClass(), id);
		return entity;
	}

	@Transactional
	@SuppressWarnings("unchecked")
	public T loadRecordById(ID id) throws Exception {
		T entity = (T) getSessionFactory().getCurrentSession().get(
				getPersistenceClass(), id);
		if (entity == null) {
			throw new Exception("Nesne Bulunamadi");
		}
		return entity;
	}

	@Transactional
	@SuppressWarnings("unchecked")
	public List<T> fetchRecordsByExample(T exampleInstance,
			boolean excludeZeros, String... excludeProperty) throws Exception {
		Example example = Example.create(exampleInstance);

		Criteria criteria = getSessionFactory().getCurrentSession()
				.createCriteria(getPersistenceClass());

		for (String exclude : excludeProperty) {

			example.excludeProperty(exclude);
		}

		if (excludeZeros) {
			example.excludeZeroes();
		}
		criteria.add(example);
		return criteria.list();
	}

	@Transactional
	@SuppressWarnings("unchecked")
	public List<T> fetchRecordsByExampleWithSorting(T exampleInstance,
			String sortingField, boolean ASC, boolean excludeZeros,
			String... excludeProperty) throws Exception {
		Example example = Example.create(exampleInstance);

		Criteria criteria = getSessionFactory().getCurrentSession()
				.createCriteria(getPersistenceClass());

		for (String exclude : excludeProperty) {

			example.excludeProperty(exclude);
		}

		if (excludeZeros) {
			example.excludeZeroes();
		}
		criteria.add(example);
		PersistenceUtil.createAliasForOrdering(criteria, sortingField, ASC);
		return criteria.list();
	}

	@Transactional
	@SuppressWarnings("unchecked")
	public List<T> fetchRecordsLimited(int pageNumber, int pageLimit)
			throws Exception {
		Criteria criteria = getSessionFactory().getCurrentSession()
				.createCriteria(getPersistenceClass());
		return criteria.setFirstResult(pageNumber * pageLimit)
				.setMaxResults(pageLimit).list();
	}

	@Transactional
	@SuppressWarnings("unchecked")
	public List<T> fetchRecordsAll() throws Exception {
		Criteria criteria = getSessionFactory().getCurrentSession()
				.createCriteria(getPersistenceClass());
		return criteria.list();
	}

	@Transactional
	@SuppressWarnings("unchecked")
	public List<T> fetchRecordsLimitedWithSorting(int pageNumber,
			int pageLimit, String sortingField, boolean ASC) throws Exception {
		Criteria criteria = getSessionFactory().getCurrentSession()
				.createCriteria(getPersistenceClass());
		/*
		 * Sorting field in tanؤ±nmasؤ± iأ§in gerekli olan alias tanؤ±mlanmasؤ±
		 * yapؤ±lؤ±yor.
		 */

		PersistenceUtil.createAliasForOrdering(criteria, sortingField, ASC);
		return criteria.setFirstResult(pageNumber * pageLimit)
				.setMaxResults(pageLimit).list();
	}

	@Transactional
	@SuppressWarnings("unchecked")
	public List<T> fetchRecordsAllWithSorting(String sortingField, boolean ASC)
			throws Exception {
		Criteria criteria = getSessionFactory().getCurrentSession()
				.createCriteria(getPersistenceClass());
		/*
		 * Sorting field in tanؤ±nmasؤ± iأ§in gerekli olan alias tanؤ±mlamasؤ±
		 * yapؤ±lؤ±yor.
		 */
		PersistenceUtil.createAliasForOrdering(criteria, sortingField, ASC);
		return criteria.list();
	}

	@Transactional
	public List<T> fetchRecordsByCriteria(T entity) throws Exception {
		return fetchRecordsByCriteriaHelper();
	}
	
	@Transactional
	public List<T> fetchRecordsByCriteria(List<Criterion> criterionList) throws Exception {
		return fetchRecordsByCriteriaHelper(criterionList);
	}

	@Transactional
	@SuppressWarnings("unchecked")
	protected List<T> fetchRecordsByCriteriaHelper(Criterion... criterion)
			throws Exception {
		Criteria criteria = getSessionFactory().getCurrentSession()
				.createCriteria(getPersistenceClass());
		for (Criterion criter : criterion) {
			criteria.add(criter);
		}
		return criteria.list();
	}

	@Transactional
	@SuppressWarnings("unchecked")
	protected List<T> fetchRecordsByCriteriaHelper(List<Criterion> criteriaList)
			throws Exception {
		Criteria criteria = getSessionFactory().getCurrentSession()
				.createCriteria(getPersistenceClass());
		for (Criterion criter : criteriaList) {
			criteria.add(criter);
		}
		return criteria.list();
	}

	@Transactional
	@SuppressWarnings("unchecked")
	protected List<T> fetchRecordsByCriteriaHelper(
			List<Criterion> criteriaList, String aliasField) throws Exception {
		Criteria criteria = getSessionFactory().getCurrentSession()
				.createCriteria(getPersistenceClass());
		PersistenceUtil.createAlias(criteria, aliasField);
		for (Criterion criter : criteriaList) {
			criteria.add(criter);
		}
		return criteria.list();
	}

	@Transactional
	@SuppressWarnings("unchecked")
	protected List<T> fetchRecordsByCriteriaWithSortingHelper(
			String sortingField, boolean ASC, List<Criterion> criterionList)
			throws Exception {
		Criteria criteria = getSessionFactory().getCurrentSession()
				.createCriteria(getPersistenceClass());
		for (Criterion criter : criterionList) {
			criteria.add(criter);
		}

		/*
		 * Sorting field in tanؤ±nmasؤ± iأ§in gerekli olan alias tanؤ±mlanmasؤ±
		 * yapؤ±lؤ±yor.
		 */
		PersistenceUtil.createAliasForOrdering(criteria, sortingField, ASC);
		return criteria.list();
	}

	@Transactional
	public List<T> fetchRecordsByCriteriaLimitedList(T entity, int pageNumber,
			int pageLimit) throws Exception {
		return fetchRecordsByCriteriaLimitedListHelper(pageNumber, pageLimit);
	}

	@Transactional
	public List<T> fetchRecordsByCriteriaLimitedListWithSorting(int first,
			int pageLimit, String sortingField, boolean ASC,
			Map<String, String> filters, String[] hibernateFilterName,
			List<Map<String, String>> hibernateFilterParams) throws Exception {
		return fetchRecordsByCriteriaLimitedListWithSortingHelper(first,
				pageLimit, sortingField, ASC, filters, hibernateFilterName,
				hibernateFilterParams);
	}

	@Transactional
	@SuppressWarnings("unchecked")
	protected List<T> fetchRecordsByCriteriaLimitedListWithSortingHelper(
			int first, int pageLimit, String sortingField, boolean ASC,
			Map<String, String> filters, String[] hibernateFilterName,
			List<Map<String, String>> hibernateFilterParams) throws Exception {

		/**
		 * Developer'in fonksiyona gönderdiği hibernate filtreleri(Önceden
		 * entitylerde tanımlanmış.) aktive edilir, parametreleri ayarlanır.
		 **/

		String mainAliasName = persistenceClass.getSimpleName();
		System.out.println("mainAliasName: " + mainAliasName);
		Session session = getSessionFactory().getCurrentSession();
		for (byte f = 0; f < hibernateFilterName.length; f++) {
			Filter filter = session.enableFilter(hibernateFilterName[f]);

			List<String> hibernateKey = new ArrayList<String>(
					hibernateFilterParams.get(f).keySet());
			List<String> hibernateValue = new ArrayList<String>(
					hibernateFilterParams.get(f).values());
			System.out.println("FilterName: " + hibernateFilterName[f]);
			for (byte a = 0; a < hibernateKey.size(); a++) {
				System.out.println("FilterParam(" + a + "): "
						+ hibernateKey.get(a));
				System.out.println("FilterValue(" + a + "): "
						+ hibernateValue.get(a));
				filter.setParameter(hibernateKey.get(a), hibernateValue.get(a));
			}
		}

		/**
		 * Bu kisimda datatable'den gelen filtre bilgileri, criterion'lara
		 * dönüstürülür. Entity'nin (persistenceClass) field'larını dolaşan
		 * algoritmayla tablodaki veri tipleri belirlenir ve criteria'ya eklenme
		 * şekli belirlenir.
		 **/

		Criteria criteria = session.createCriteria(getPersistenceClass(),
				mainAliasName);

		List<Criterion> criterionList = new ArrayList<Criterion>();

		List<String> listKey = new ArrayList<String>(filters.keySet());
		List<String> listValue = new ArrayList<String>(filters.values());
		List<String> aliasRegister = new ArrayList<String>();// Aynı alias'dan
																// bir daha
																// oluşturmamak
																// için bir
																// listede
																// kaydedilir.

		for (byte i = 0; i < listKey.size(); i++) {// filter map'dan gelen
													// değerleri döngüye alır.
			Class<?> klass = persistenceClass;
			String aliasName = "";// alias'ın isminin inşa edilmesi için gereken
									// değişken
			String key = listKey.get(i);
			String value = listValue.get(i);
			String[] keyParts = key.split("\\.");// sınıf içinde sınıf varsa
													// noktalardan ayırır.
			Field field = null;
			Boolean isAliasNeeded = false;// basit field'lerde alias
											// tanımlanmaz.
			for (byte k = 0; k < keyParts.length; k++) {// filter map'dan gelen
														// key'in parçalarını
														// döngüye alır.
				System.out.println("Key: " + key);
				System.out.println("Value: " + value);
				try {
					System.out.println("Field (" + i + "), Part (" + k
							+ ") -----------------------------------------");
					System.out.println("Field Type: "
							+ klass.getDeclaredField(keyParts[k]));
					field = klass.getDeclaredField(keyParts[k]);
				} catch (SecurityException | NoSuchFieldException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if (field.getType().equals(String.class)) {
					System.out.println("String Detected");
					criterionList.add(Restrictions.ilike(key, value,
							MatchMode.ANYWHERE));

				} else if (field.getType().equals(Boolean.class)) {
					System.out.println("Boolean Detected");
					if (value.equals("true") || value.equals("false")) {
						criterionList.add(Restrictions.eq(key,
								Boolean.parseBoolean(value)));

					} else {
						System.out
								.println("Bad boolean string value! Criterion won't be added. Use true or false.");
					}
				} else {
					System.out.println("There are multiple parts.");
					if (k + 1 != keyParts.length) {
						isAliasNeeded = true;
						klass = field.getType();

						if (aliasName.length() != 0) {
							aliasName += ".";
						}
						aliasName += keyParts[k];
					}
				}

			}
			if (isAliasNeeded && !aliasRegister.contains(aliasName)) {
				aliasRegister.add(aliasName);
				criteria.createAlias(mainAliasName + "." + aliasName, aliasName);
			}

		}

		for (Criterion criter : criterionList) {
			System.out.println("criterAdded1: " + criter.toString());
			criteria.add(criter);
		}

		/*
		 * int tableSize = ((Long)criteria//Uygun kayıtların sayısı, sayfa
		 * sayısının hesaplanması için kullanılır.
		 * .setProjection(Projections.rowCount()).uniqueResult()).intValue();
		 * 
		 * 
		 * criteria.setProjection(null)//RowCount işleminden sonra criteria
		 * resetleniyor. .setResultTransformer(Criteria.ROOT_ENTITY);
		 */
		setRowCount(criteria.list().size());

		/*
		 * Sorting field in tanؤ±nmasؤ± iأ§in gerekli olan alias tanؤ±mlanmasؤ±
		 * yapؤ±lؤ±yor.
		 */

		if (sortingField != null) {
			PersistenceUtil.createAliasForOrdering(criteria, sortingField, ASC);
		}
		List<T> result = criteria.setFirstResult(first)
				.setMaxResults(pageLimit).list();
		for (byte f = 0; f < hibernateFilterName.length; f++) {
			session.disableFilter(hibernateFilterName[f]);
		}
		return result;
	}

	@Transactional
	@SuppressWarnings("unchecked")
	protected List<T> fetchRecordsByCriteriaLimitedListWithSortingHelper(
			int pageNumber, int pageLimit, String orderingField, boolean ASC,
			List<Criterion> criterionList, String aliasField) throws Exception {
		Criteria criteria = getSessionFactory().getCurrentSession()
				.createCriteria(getPersistenceClass());
		for (Criterion criter : criterionList) {
			criteria.add(criter);
		}
		PersistenceUtil.createAliasForOrderingAndAliasing(criteria,
				orderingField, aliasField, ASC);
		return criteria.setFirstResult(pageNumber * pageLimit)
				.setMaxResults(pageLimit).list();
	}

	@Transactional
	@SuppressWarnings("unchecked")
	protected List<T> fetchRecordsByCriteriaLimitedListHelper(int pageNumber,
			int pageLimit, Criterion... criterion) throws Exception {
		Criteria criteria = getSessionFactory().getCurrentSession()
				.createCriteria(getPersistenceClass());
		for (Criterion criter : criterion) {
			criteria.add(criter);
		}
		return criteria.setFirstResult(pageNumber * pageLimit)
				.setMaxResults(pageLimit).list();
	}

	@Transactional
	@SuppressWarnings("unchecked")
	protected List<T> fetchRecordsByCriteriaLimitedListHelper(int pageNumber,
			int pageLimit, List<Criterion> criterionList) throws Exception {
		Criteria criteria = getSessionFactory().getCurrentSession()
				.createCriteria(getPersistenceClass());
		for (Criterion criter : criterionList) {
			criteria.add(criter);
		}
		return criteria.setFirstResult(pageNumber * pageLimit)
				.setMaxResults(pageLimit).list();
	}

	@Transactional
	@SuppressWarnings("unchecked")
	protected List<T> fetchRecordsByCriteriaLimitedListHelper(int pageNumber,
			int pageLimit, List<Criterion> criterionList, String aliasField)
			throws Exception {
		Criteria criteria = getSessionFactory().getCurrentSession()
				.createCriteria(getPersistenceClass());
		PersistenceUtil.createAlias(criteria, aliasField);
		for (Criterion criter : criterionList) {
			criteria.add(criter);
		}
		return criteria.setFirstResult(pageNumber * pageLimit)
				.setMaxResults(pageLimit).list();
	}

	@Transactional
	public int fetchCriteriaListTotalSize(T entity) throws Exception {
		return fetchTotalRecordSize();
	}

	@Transactional
	public int fetchTotalRecordSize() throws Exception {
		Criteria criteria = getSessionFactory().getCurrentSession()
				.createCriteria(getPersistenceClass());
		int tableSize = ((Long) criteria.setProjection(Projections.rowCount())
				.uniqueResult()).intValue();
		return tableSize;
	}

	@Transactional
	public List<T> findLike(T entity, MatchMode matchMode) {
		return findLike(entity, matchMode, null);
	}
	
	@Transactional
	@SuppressWarnings("unchecked")
	public List<T> findLike(T entity, MatchMode matchMode, List<Criterion> criterionList) {
		Example example = Example.create(entity);
		example.enableLike(matchMode);
		example.ignoreCase();
		
		Criteria criteria = getSessionFactory().getCurrentSession()
				.createCriteria(getPersistenceClass());
		criteria = criteria.add(example);
		if(criterionList != null && criterionList.size() > 0){
			for (Criterion criter : criterionList) {
				criteria.add(criter);
			}
		}
		return criteria.list();
	}

}
