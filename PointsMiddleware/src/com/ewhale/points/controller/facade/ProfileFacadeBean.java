package com.ewhale.points.controller.facade;

import java.util.Date;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import com.ewhale.points.common.exception.AuthenticationSecurityException;
import com.ewhale.points.common.exception.EntityException;
import com.ewhale.points.common.exception.FacadeException;
import com.ewhale.points.common.exception.ValidationException;
import com.ewhale.points.common.security.SecurityBuilder;
import com.ewhale.points.common.util.EntityConstants;
import com.ewhale.points.common.util.ExceptionConstants;
import com.ewhale.points.model.entity.AbsoluteEntity;
import com.ewhale.points.model.entity.Agent;
import com.ewhale.points.model.entity.Country;
import com.ewhale.points.model.entity.Profile;
import com.ewhale.points.model.entity.Role;
import com.ewhale.points.model.qualifiers.EntityClass;
import com.ewhale.points.model.qualifiers.EntityClassEnum;

/**
 * Session Bean implementation class ProfileFacadeBean
 */
@Stateless
@Local(ProfileFacade.class)
@LocalBean
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class ProfileFacadeBean extends AbsoluteFacadeBean implements ProfileFacade
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@EJB
	private AuthenticationFacade authFacade;

	@SuppressWarnings("rawtypes")
	@Inject
	public void setEntityClass(@EntityClass(EntityClassEnum.PROFILE) Class entityClass)
	{
		this.entityClass = entityClass;
	}

	/**
	 * @param entity
	 * @param data
	 * @throws EntityException
	 * @throws ValidationException 
	 */
	protected void addEntityDetails(AbsoluteEntity entity, Map<String, Object> data) throws EntityException, ValidationException
	{
		try
		{
			Map<String, Object> userData = authFacade.validateUser(data);

			if (userData == null)
			{
				register(entity, data);
			}
			else
			{
				throw new ValidationException(ExceptionConstants.USER_ALREADY_EXISTS_EX_MSG);
			}
		}
		catch (FacadeException e)
		{
			throw new EntityException("Problem while Regestration ", e);
		}
	}

	@Override
	protected void updateEntityDetails(Map<String, Object> data) throws EntityException, ValidationException
	{
		// we remove the mobile to prevent its update accidently
		String mobile =(String)data.remove(EntityConstants.Profile.mobile);
		// we get the profile from the database and check if t has the same mobile sent or not
		// if not throw Invalid data validation exception
		Number profileIdObj = ((Number) data.get(EntityConstants.Profile.profileId));
		Profile profile=em.viewRecordDetails(Profile.class, profileIdObj.longValue());
		if(!mobile.equals(profile.getMobile()))
			throw new ValidationException(ExceptionConstants.INVALID_DATA_EX_MSG);
		//set the date
		data.put(EntityConstants.Profile.birthDate, new Date((Long) data.get(EntityConstants.Profile.birthDate)));
		Number roleIdObj = ((Number) data.remove(EntityConstants.Profile.roleId));
		if (roleIdObj != null)
		{
			Role role = new Role();
			role.setRoleId(roleIdObj.byteValue());
			data.put(EntityConstants.Profile.role, role);
		}
		Number agentIdObj = ((Number) data.remove(EntityConstants.Branch.agentId));
		if (agentIdObj != null)
		{
			Agent agent = new Agent();
			agent.setEntityId(agentIdObj.longValue());
			data.put(EntityConstants.Profile.agent, agent);
		}
	}

	/**
	 * @param entity
	 * @param data
	 * @throws EntityException
	 * @throws FacadeException
	 */
	private void register(AbsoluteEntity entity, Map<String, Object> data) throws FacadeException, EntityException
	{
		FacadeBeanUtils.addStatusDetails(em, entity, data);
		Agent agent = getAgent(data);
		Country country = getCountry(data);
		Role role = getRole(data);
		String password = null;
		try
		{
			password = getPassword();
			data.put(EntityConstants.Profile.password, password);
			password = SecurityBuilder.passwordHashing(password);
		}
		catch (AuthenticationSecurityException e)
		{
			throw new FacadeException(e);
		}
		Profile profile = (Profile) entity;
		profile.setRole(role);
		profile.setCountry(country);
		profile.setAgent(agent);
		profile.setPassword(password);
		profile.setInsertDate(new Date());

		generateRequestCode(data);
	}

	/**
	 * @param data
	 * @throws FacadeException
	 */
	private void generateRequestCode(Map<String, Object> data) throws FacadeException
	{
		String mobile = (String) data.get(EntityConstants.Profile.mobile);
		String password = (String) data.get(EntityConstants.Profile.password);

		String smsMessage = FacadeBeanUtils.prepareSMS(password);
		try
		{
			FacadeBeanUtils.sendSMS(mobile, smsMessage);
		}
		catch (Exception e)
		{
			throw new FacadeException(e);
		}
	}

	/**
	 * @param data
	 * @return
	 * @throws EntityException
	 */
	private Agent getAgent(Map<String, Object> data) throws EntityException
	{
		Number agentIdObj = ((Number) data.get(EntityConstants.Profile.agentId));
		if (agentIdObj == null)
			return null;
		long agentId = ((Number) data.get(EntityConstants.Profile.agentId)).longValue();
		Agent agent = em.viewRecordDetails(Agent.class, agentId);
		return agent;
	}

	private String getPassword() throws AuthenticationSecurityException
	{
		String password = SecurityBuilder.getRandomPassword();
		return password;
	}

	/**
	 * @param data
	 * @return
	 * @throws EntityException
	 */
	private Role getRole(Map<String, Object> data) throws EntityException
	{
		int roleId = ((Number) data.get(EntityConstants.Profile.roleId)).intValue();
		Role role = em.viewRecordDetails(Role.class, roleId);
		return role;
	}

	/**
	 * @param data
	 * @return
	 * @throws EntityException
	 */
	private Country getCountry(Map<String, Object> data) throws EntityException
	{
		byte countryId = ((Number) data.get(EntityConstants.District.countryId)).byteValue();
		Country country = em.viewRecordDetails(Country.class, countryId);
		return country;
	}
}
