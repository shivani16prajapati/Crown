package com.unicef.service;

import java.io.Serializable;
import java.util.List;

/**
 * @author Divyang Patel
 */
public interface GenericService<T, PK extends Serializable> {

	/**
	 * This method create object in database.
	 * 
	 * @param t
	 * @return
	 */
	PK create(T t);

	/**
	 * This method count of all objects in table.
	 * 
	 * @return
	 */
	Integer countAll();

	/**
	 * This method delete object from database.
	 * 
	 * @param id
	 */
	void delete(PK id);

	/**
	 * <p>
	 * This method returns {@link <T>} object of given id.
	 * </p>
	 * 
	 * @param id
	 * @return
	 */
	T find(PK id);

	/**
	 * <p>
	 * This method returns list of all {@link <T>} objects.
	 * </p>
	 * 
	 * @return
	 */
	List<T> findAll();

	/**
	 * This method update object.
	 * 
	 * @param t
	 * @return
	 */
	T update(T t);

	/**
	 * <p>
	 * This method returns list of {@link <T>} objects. If orderParam param is not null, results will be sorted.
	 * If first param {@link Query#setFirstResult(int)} is less than 0, rows will be retrieved beginnning from row 0.
	 * If max param {@link Query#setMaxResults(int)} is less than 0, there is no limit to the number of rows retrieved.
	 * </p>
	 * 
	 * @see Query#setFirstResult(int)
	 * @see Query#setMaxResults(int)
	 * 
	 * @param orderParam
	 * @param desc
	 * @param first
	 * @param max
	 * @return
	 */
	List<T> finds(String orderParam, boolean desc, int first, int max);
}