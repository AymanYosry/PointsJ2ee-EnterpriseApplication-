package com.ewhale.points.model.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
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
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.ewhale.points.common.exception.EntityException;
import com.ewhale.points.common.util.EntityConstants;

/**
 * The persistent class for the message_center database table.
 */
@Entity
@Table(name = "message_center")
@NamedQuery(name = "MessageCenter.findAll", query = "SELECT m FROM MessageCenter m")
public class MessageCenter extends AbsoluteEntity implements Serializable
{
	private static final long serialVersionUID = 1L;

	public static String[] entityIdNames = new String[]
		{ EntityConstants.MessageCenter.messageCenterId };

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "message_center_id", unique = true, nullable = false)
	private long messageCenterId;

	@Column(name = "message_center_item_id", nullable = false)
	private long messageCenterItemId;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "request_date", nullable = false)
	private Date requestDate;

	@Column(name = "request_message", nullable = false, length = 255)
	private String requestMessage;

	@Column(name = "responce_message", length = 255)
	private String responseMessage;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "response_date")
	private Date responseDate;

	// bi-directional many-to-one association to Agent
	@ManyToOne
	@JoinColumn(name = "request_agent_id", nullable = false)
	private Agent agent;

	// bi-directional many-to-one association to FunctionType
	@ManyToOne
	@JoinColumn(name = "message_center_function_id", nullable = false)
	private FunctionType functionType;

	// bi-directional many-to-one association to Profile
	@ManyToOne
	@JoinColumn(name = "request_agent_emp_id", nullable = false)
	private Profile requestAgentEmp;

	// bi-directional many-to-one association to Profile
	@ManyToOne
	@JoinColumn(name = "responce_sys_emp_id", nullable = true)
	private Profile responseEmp;

	public MessageCenter()
	{
	}

	public long getMessageCenterId()
	{
		return this.messageCenterId;
	}

	public void setMessageCenterId(long messageCenterId)
	{
		this.messageCenterId = messageCenterId;
	}

	public long getMessageCenterItemId()
	{
		return this.messageCenterItemId;
	}

	public void setMessageCenterItemId(long messageCenterItemId)
	{
		this.messageCenterItemId = messageCenterItemId;
	}

	public Date getRequestDate()
	{
		return this.requestDate;
	}

	public void setRequestDate(Date requestDate)
	{
		this.requestDate = requestDate;
	}

	public String getRequestMessage()
	{
		return this.requestMessage;
	}

	public void setRequestMessage(String requestMessage)
	{
		this.requestMessage = requestMessage;
	}

	public String getResponseMessage()
	{
		return this.responseMessage;
	}

	public void setResponseMessage(String responseMessage)
	{
		this.responseMessage = responseMessage;
	}

	public Date getResponseDate()
	{
		return this.responseDate;
	}

	public void setResponseDate(Date responseDate)
	{
		this.responseDate = responseDate;
	}

	public Agent getAgent()
	{
		return this.agent;
	}

	public void setAgent(Agent agent)
	{
		this.agent = agent;
	}

	public FunctionType getFunctionType()
	{
		return this.functionType;
	}

	public void setFunctionType(FunctionType functionType)
	{
		this.functionType = functionType;
	}

	public Profile getRequestAgentEmp()
	{
		return this.requestAgentEmp;
	}

	public void setRequestAgentEmp(Profile requestAgentEmp)
	{
		this.requestAgentEmp = requestAgentEmp;
	}

	public Profile getResponseEmp()
	{
		return this.responseEmp;
	}

	public void setResponseEmp(Profile responseEmp)
	{
		this.responseEmp = responseEmp;
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
		map.put(EntityConstants.MessageCenter.messageCenterId, messageCenterId);
		map.put(EntityConstants.MessageCenter.requestMessage, requestMessage);
		map.put(EntityConstants.MessageCenter.responseMessage, responseMessage);
		map.put(EntityConstants.MessageCenter.requestDate, getStringFromDate(requestDate));
		map.put(EntityConstants.MessageCenter.responseDate, getStringFromDate(responseDate));
		map.put(EntityConstants.MessageCenter.agentId, agent.getAgentId());
		map.put(EntityConstants.MessageCenter.agent, agent.transformMainDataToMap());
		map.put(EntityConstants.MessageCenter.requestAgentEmpId, requestAgentEmp.getProfileId());
		map.put(EntityConstants.MessageCenter.requestAgentEmpFullName, requestAgentEmp.getFullName());
		if (responseEmp != null)
		{
			map.put(EntityConstants.MessageCenter.responseEmpId, responseEmp.getProfileId());
			map.put(EntityConstants.MessageCenter.responseEmpFullName, responseEmp.getFullName());
		}
		map.put(EntityConstants.MessageCenter.itemId, messageCenterItemId);
		map.put(EntityConstants.MessageCenter.functionTypeId, functionType.getFunctionTypeId());
		return map;
	}

	@Override
	public void setEntityData(Map<String, Object> data, boolean useId) throws EntityException
	{
		super.setEntityData(data, useId);

		setMessageCenterItemId(((Number) data.get(EntityConstants.MessageCenter.itemId)).longValue());
		setRequestMessage((String) data.get(EntityConstants.MessageCenter.requestMessage));
		setResponseMessage((String) data.get(EntityConstants.MessageCenter.responseMessage));
		setRequestDate(getDateFromLong((Long) data.get(EntityConstants.MessageCenter.requestDate)));
		setResponseDate(getDateFromLong((Long) data.get(EntityConstants.MessageCenter.responseDate)));

	}

	@Override
	public <T extends AbsoluteEntity> List<Predicate> setCriteria(final CriteriaBuilder criteriaBuilder, final Root<T> criteriaRoot,
			Map<String, Object> criteria) throws EntityException
	{
		List<Predicate> predicateList = new ArrayList<Predicate>();
		Number messageCenterId = (Number) criteria.get(EntityConstants.MessageCenter.messageCenterId);
		if (messageCenterId != null)
			predicateList.add(criteriaBuilder.equal(criteriaRoot.get(EntityConstants.MessageCenter.messageCenterId), messageCenterId.longValue()));

		Number agentId = (Number) criteria.get(EntityConstants.MessageCenter.agentId);
		if (agentId != null)
		{
			Join<MessageCenter, Agent> messageCenterAgentJoin = criteriaRoot.join(EntityConstants.MessageCenter.agent);
			predicateList.add(criteriaBuilder.equal(messageCenterAgentJoin.get(EntityConstants.Agent.agentId), agentId.longValue()));
		}
		Number functionTypeId = (Number) criteria.get(EntityConstants.MessageCenter.functionTypeId);
		if (functionTypeId != null)
		{
			Join<MessageCenter, FunctionType> messageCenterFunctionTypeJoin = criteriaRoot.join(EntityConstants.MessageCenter.functionType);
			predicateList.add(criteriaBuilder.equal(messageCenterFunctionTypeJoin.get(EntityConstants.FunctionType.functionTypeId),
					functionTypeId.shortValue()));
		}

		Long requestDateFrom = (Long) criteria.get(EntityConstants.MessageCenter.requestDate_From_Search_NAME);
		if (requestDateFrom != null)
			predicateList
					.add(criteriaBuilder.greaterThan(criteriaRoot.get(EntityConstants.MessageCenter.requestDate), getDateFromLong(requestDateFrom)));
		Long requestDateTo = (Long) criteria.get(EntityConstants.MessageCenter.requestDate_To_Search_NAME);
		if (requestDateTo != null)
			predicateList.add(criteriaBuilder.lessThan(criteriaRoot.get(EntityConstants.MessageCenter.requestDate), getDateFromLong(requestDateTo)));
		Long responseDateFrom = (Long) criteria.get(EntityConstants.MessageCenter.responseDate_From_Search_NAME);
		if (responseDateFrom != null)
			predicateList.add(
					criteriaBuilder.greaterThan(criteriaRoot.get(EntityConstants.MessageCenter.responseDate), getDateFromLong(responseDateFrom)));
		Long responseDateTo = (Long) criteria.get(EntityConstants.MessageCenter.responseDate_To_Search_NAME);
		if (responseDateTo != null)
			predicateList
					.add(criteriaBuilder.lessThan(criteriaRoot.get(EntityConstants.MessageCenter.responseDate), getDateFromLong(responseDateTo)));

		Boolean notRespondedMessages = (Boolean) criteria.get(EntityConstants.MessageCenter.not_responded_messages_Search);
		if (notRespondedMessages != null && notRespondedMessages)
			predicateList.add(criteriaBuilder.or(criteriaBuilder.isNull(criteriaRoot.get(EntityConstants.MessageCenter.responseMessage)),
					criteriaBuilder.equal(criteriaRoot.get(EntityConstants.MessageCenter.responseMessage), "")));
		String respondedMessages = (String) criteria.get(EntityConstants.MessageCenter.responded_messages_Search);
		if (respondedMessages != null)
			predicateList.add(criteriaBuilder.and(criteriaBuilder.isNotNull(criteriaRoot.get(EntityConstants.MessageCenter.responseMessage)),
					criteriaBuilder.notEqual(criteriaRoot.get(EntityConstants.MessageCenter.responseMessage), "")));
		return predicateList;
	}

	@Override
	protected void setEntityId(Map<String, Object> data)
	{
		setMessageCenterId(((Number) data.get(EntityConstants.MessageCenter.messageCenterId)).longValue());
	}

	@Override
	public Object getEntityId()
	{
		return getMessageCenterId();
	}
}
