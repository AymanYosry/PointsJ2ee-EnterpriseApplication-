package com.ewhale.points.model.entity;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQuery;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.ewhale.points.common.exception.EntityException;
import com.ewhale.points.common.util.EntityConstants;

/**
 * The persistent class for the profile database table.
 */
@Entity
@Table(name = "login")
@NamedQuery(name = "Login.findAll", query = "SELECT l,s FROM Login l join l.status s")
@PrimaryKeyJoinColumn(name = "login_id", referencedColumnName = "item_status_id")
public class Login extends ItemStatus implements Serializable
{
	private static final long serialVersionUID = 1L;

	public static String[] entityIdNames = new String[]
		{ EntityConstants.Login.loginId };

	@Column(name = "login_id", unique = true, nullable = false, insertable = false, updatable = false)
	private long loginId;

	@Column(nullable = false)
	private String password;

	@Column(length = 20)
	private String mobile;

	public Login()
	{
	}

	public long getLoginId()
	{
		return loginId;
	}

	public void setLoginId(long loginId)
	{
		this.loginId = loginId;
		setItemId(loginId);
	}

	public String getPassword()
	{
		return this.password;
	}

	public void setPassword(String password)
	{
		this.password = password;
	}

	public String getMobile()
	{
		return this.mobile;
	}

	public void setMobile(String mobile)
	{
		this.mobile = mobile;
	}
	@Override
	public Map<String, Object> transformToMap()
	{
		Map<String, Object> map =transformMainDataToMap();
		return map;
	}
	@Override
	public Map<String, Object> transformMainDataToMap()
	{
		Map<String, Object> map = super.transformMainDataToMap();
		map.put(EntityConstants.Login.loginId, loginId);
		map.put(EntityConstants.Login.mobile, mobile);
		map.put(EntityConstants.Login.password, password);
		return map;
	}

	@Override
	public void setEntityData(Map<String, Object> data, boolean useId) throws EntityException
	{
		super.setEntityData(data, useId);
		setMobile((String) data.get(EntityConstants.Profile.mobile));
		setPassword((String) data.get(EntityConstants.Profile.password));
	}

	@Override
	public <T extends AbsoluteEntity> List<Predicate> setCriteria(final CriteriaBuilder criteriaBuilder, final Root<T> criteriaRoot, Map<String, Object> criteria)
			throws EntityException
	{
		List<Predicate> predicateList = super.setCriteria(criteriaBuilder, criteriaRoot, criteria);
		Number loginId = (Number) criteria.get(EntityConstants.Login.loginId);
		if (loginId != null)
			predicateList.add(criteriaBuilder.equal(criteriaRoot.get(EntityConstants.Login.loginId), loginId.longValue()));
		String mobile = (String) criteria.get(EntityConstants.Profile.mobile);
		if (mobile != null&&mobile.trim().length()>0)
			predicateList.add(criteriaBuilder.equal(criteriaRoot.get(EntityConstants.Profile.mobile), mobile));
		return predicateList;
	}

	@Override
	protected void setEntityId(Map<String, Object> data)
	{
		long id = ((Number) data.get(EntityConstants.Login.loginId)).longValue();
		setItemId(id);
		setLoginId(id);
	}

	@Override
	public void setEntityId(Object idObj)
	{
		long id = ((Number) idObj).longValue();
		setItemId(id);
		setLoginId(id);
	}
}
