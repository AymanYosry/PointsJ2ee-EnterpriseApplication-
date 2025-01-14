package com.ewhale.points.model.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedNativeQuery;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.ewhale.points.common.exception.EntityException;
import com.ewhale.points.common.util.EntityConstants;

/**
 * The persistent class for the agent_rate database table.
 */
@Entity
@Table(name = "agent_rate")

@NamedQueries(value =
	{ @NamedQuery(name = "AgentRate.findAll", query = "SELECT a FROM AgentRate a"),
			@NamedQuery(name = "AgentRate.avgAgentRate", query = "SELECT AVG(ar.rateValue) FROM AgentRate ar where ar.agent.agentId=:agentId") })
@NamedNativeQuery(name = "AgentRate.updateAgentRate",
		query = "update agent," + "(select agent_rate_agent_id,avg(agent_rate.rate_value) avg_agent_rate,count(agent_rate_id) rates_number "
				+ "from agent_rate group by agent_rate.agent_rate_agent_id) agent_rate "
				+ "set agent.rate_value=avg_agent_rate, agent.rates_number=agent_rate.rates_number   "
				+ "where agent.agent_id=agent_rate.agent_rate_agent_id and agent.agent_id=:agentId")
public class AgentRate extends AbsoluteEntity implements Serializable
{
	private static final long serialVersionUID = 1L;

	public static String[] entityIdNames = new String[]
		{ EntityConstants.AgentRate.agentRateId };

	public static final String updateAgentRate = "AgentRate.updateAgentRate", avgAgentRateNamedQuery = "AgentRate.avgAgentRate";

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "agent_rate_id", unique = true, nullable = false)
	private long agentRateId;

	@Column(name = "rate_value", nullable = false)
	private byte rateValue;

	@Column(name = "user_comment", nullable = false, length = 255)
	private String userComment;

	// bi-directional many-to-one association to Agent
	@ManyToOne
	@JoinColumn(name = "agent_rate_agent_id", nullable = false)
	private Agent agent;

	// bi-directional many-to-one association to Profile
	@ManyToOne
	@JoinColumn(name = "agent_rate_profile_id", nullable = false)
	private Profile profile;

	public AgentRate()
	{
	}

	public long getAgentRateId()
	{
		return this.agentRateId;
	}

	public void setAgentRateId(long agentRateId)
	{
		this.agentRateId = agentRateId;
	}

	public byte getRateValue()
	{
		return this.rateValue;
	}

	public void setRateValue(byte rateValue)
	{
		this.rateValue = rateValue;
	}

	public String getUserComment()
	{
		return this.userComment;
	}

	public void setUserComment(String userComment)
	{
		this.userComment = userComment;
	}

	public Agent getAgent()
	{
		return this.agent;
	}

	public void setAgent(Agent agent)
	{
		this.agent = agent;
	}

	public Profile getProfile()
	{
		return this.profile;
	}

	public void setProfile(Profile profile)
	{
		this.profile = profile;
	}

	@Override
	public Map<String, Object> transformToMap()
	{
		Map<String, Object> map = transformMainDataToMap();
		return map;
	}

	@Override
	public Map<String, Object> transformMainDataToMap()
	{
		Map<String, Object> map = new HashMap<>();
		map.put(EntityConstants.AgentRate.agentRateId, agentRateId);
		map.put(EntityConstants.AgentRate.rateValue, rateValue);
		map.put(EntityConstants.AgentRate.userComment, userComment);
		map.put(EntityConstants.AgentRate.agentId, agent.getAgentId());
		map.put(EntityConstants.AgentRate.profileId, profile.getProfileId());
		return map;
	}

	@Override
	public void setEntityData(Map<String, Object> data, boolean useId) throws EntityException
	{
		super.setEntityData(data, useId);

		setRateValue(((Number) data.get(EntityConstants.AgentRate.rateValue)).byteValue());
		setUserComment((String) data.get(EntityConstants.AgentRate.userComment));
		Agent agent = new Agent();
		agent.setAgentId(((Number) data.get(EntityConstants.AgentRate.agentId)).longValue());
		setAgent(agent);
		Profile profile = new Profile();
		profile.setProfileId(((Number) data.get(EntityConstants.AgentRate.profileId)).longValue());
		setProfile(profile);
	}

	@Override
	public <T extends AbsoluteEntity> List<Predicate> setCriteria(final CriteriaBuilder criteriaBuilder, final Root<T> criteriaRoot,
			Map<String, Object> criteria)
	{
		List<Predicate> predicateList = new ArrayList<Predicate>();
		Number agentRateId = (Number) criteria.get(EntityConstants.AgentRate.agentRateId);
		if (agentRateId != null)
			predicateList.add(criteriaBuilder.equal(criteriaRoot.get(EntityConstants.AgentRate.agentRateId), agentRateId.longValue()));
		Number agentId = (Number) criteria.get(EntityConstants.AgentRate.agentId);
		if (agentId != null)
			predicateList.add(criteriaBuilder.equal(criteriaRoot.get(EntityConstants.AgentRate.agentId), agentId.longValue()));
		Number profileId = (Number) criteria.get(EntityConstants.AgentRate.profileId);
		if (profileId != null)
			predicateList.add(criteriaBuilder.equal(criteriaRoot.get(EntityConstants.AgentRate.profileId), profileId.longValue()));
		return predicateList;
	}

	@Override
	protected void setEntityId(Map<String, Object> data)
	{
		setAgentRateId(((Number) data.get(EntityConstants.AgentRate.agentRateId)).longValue());
	}
}
