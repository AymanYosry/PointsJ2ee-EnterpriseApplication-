package com.ewhale.points.web.managedbean.main;

import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jboss.logging.Logger;
import org.primefaces.model.UploadedFile;

import com.ewhale.points.common.exception.ValidationException;
import com.ewhale.points.common.util.EntityConstants;
import com.ewhale.points.common.util.ExceptionConstants;
import com.ewhale.points.ws.main.proxy.LookUpServiceClient;
import com.ewhale.points.ws.main.proxy.ServiceClientUtil;

public class AgentBean extends ItemStatusBean
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long agentId;

	private String administrationAdderss;

	private String administrationPhone;

	private Integer callCenter;

	private String commercialRecordNo;

	private String logo;

	private UploadedFile uploadedLogo;

	private String tradeMark;

	private Short statusId;

	@SuppressWarnings("rawtypes")
	private List categoryIds;

	private List<Map<String, Object>> categoryList;

	private List<Map<String, Object>> categories;

	private BranchBean branchBean = new BranchBean();

	public AgentBean()
	{
		logger = Logger.getLogger(AgentBean.class);
	}

	public Long getAgentId()
	{
		return agentId;
	}

	public void setAgentId(Long agentId)
	{
		this.agentId = agentId;
	}

	public String getAdministrationAdderss()
	{
		return administrationAdderss;
	}

	public void setAdministrationAdderss(String administrationAdderss)
	{
		this.administrationAdderss = administrationAdderss;
	}

	public String getAdministrationPhone()
	{
		return administrationPhone;
	}

	public void setAdministrationPhone(String administrationPhone)
	{
		this.administrationPhone = administrationPhone;
	}

	public Integer getCallCenter()
	{
		return callCenter;
	}

	public void setCallCenter(Integer callCenter)
	{
		this.callCenter = callCenter;
	}

	public String getCommercialRecordNo()
	{
		return commercialRecordNo;
	}

	public void setCommercialRecordNo(String commercialRecordNo)
	{
		this.commercialRecordNo = commercialRecordNo;
	}

	public String getLogo()
	{
		return logo;
	}

	public void setLogo(String logo)
	{
		this.logo = logo;
	}

	public UploadedFile getUploadedLogo()
	{
		return uploadedLogo;
	}

	public void setUploadedLogo(UploadedFile uploadedLogo)
	{
		logger.debug("-- file uploaded");
		this.uploadedLogo = uploadedLogo;
	}

	public List<Map<String, Object>> getCategories()
	{
		if (categories == null)
		{

			LookUpServiceClient lookUpServiceClient = ServiceClientUtil.getLookUpServiceClient(FacesUtil.getLoginToken());
			categories = lookUpServiceClient.getAllCategories();
		}
		return categories;
	}

	public String getTradeMark()
	{
		return tradeMark;
	}

	public void setTradeMark(String tradeMark)
	{
		this.tradeMark = tradeMark;
	}

	public Short getStatusId()
	{
		return statusId;
	}

	public void setStatusId(Short statusId)
	{
		this.statusId = statusId;
	}

	@SuppressWarnings("rawtypes")
	public List getCategoryIds()
	{
		return categoryIds;
	}

	@SuppressWarnings("rawtypes")
	public void setCategoryIds(List categoryIds)
	{
		this.categoryIds = categoryIds;
	}

	public List<Map<String, Object>> getCategoryList()
	{
		return categoryList;
	}

	public void setCategoryList(List<Map<String, Object>> categoryList)
	{
		this.categoryList = categoryList;
	}

	public BranchBean getBranchBean()
	{
		return branchBean;
	}

	protected Map<String, Object> fillAgentDataMap() throws Exception
	{
		Map<String, Object> data = new HashMap<>();
		data.put(EntityConstants.Agent.administrationAddress, administrationAdderss);
		data.put(EntityConstants.Agent.administrationPhone, administrationPhone);
		data.put(EntityConstants.Agent.callCenter, callCenter);
		data.put(EntityConstants.Agent.commercialRecordNo, commercialRecordNo);
		if (FacesUtil.isImageUploaded(uploadedLogo))
			data.put(EntityConstants.Agent.logo, Base64.getEncoder().encodeToString(getUploadedLogo().getContents()));
		else if (logo != null)
		{
			// do nothing
		}
		else
			throw new ValidationException(ExceptionConstants.IMAGE_REQUIRED_EX_MSG);
		data.put(EntityConstants.Agent.tradeMark, tradeMark);
		data.put(EntityConstants.Agent.categoryIds, categoryIds);
		return data;
	}

	protected void fillBranchDataMap(Map<String, Object> data)
	{
		data.put(EntityConstants.Branch.address, branchBean.getAddress());
		data.put(EntityConstants.Branch.branchName, branchBean.getBranchName());
		data.put(EntityConstants.Branch.locationLatitude, branchBean.getLocationLatitude());
		data.put(EntityConstants.Branch.locationLongitude, branchBean.getLocationLongitude());
		data.put(EntityConstants.Branch.tel, branchBean.getTel());
		data.put(EntityConstants.Branch.stateId, branchBean.getStateId());
		data.put(EntityConstants.Branch.countryId, branchBean.getCountryId());
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected void fillDetailsData(Map<String, Object> agentData)
	{
		setStatusId(((Number) agentData.get(EntityConstants.Agent.statusId)).shortValue());
		setAdministrationAdderss((String) agentData.get(EntityConstants.Agent.administrationAddress));
		setAdministrationPhone((String) agentData.get(EntityConstants.Agent.administrationPhone));
		setCallCenter(((Number) agentData.get(EntityConstants.Agent.callCenter)).intValue());
		setCommercialRecordNo((String) agentData.get(EntityConstants.Agent.commercialRecordNo));
		setTradeMark((String) agentData.get(EntityConstants.Agent.tradeMark));
		setAgentId(((Number) agentData.get(EntityConstants.Agent.agentId)).longValue());
		setLogo((String) agentData.get(EntityConstants.Agent.logo));
		// IMP_Ahmed fill categories menu
		setCategoryIds((List) agentData.get(EntityConstants.Agent.categoryIds));
		setCategoryList((List<Map<String, Object>>) agentData.get(EntityConstants.Agent.categories));
		logger.debug("-------------- categoryIds : " + (categoryIds != null ? categoryIds.size() : 0));
	}

	@Override
	protected void resetToAdd()
	{
		setAdministrationAdderss(null);
		setAdministrationPhone(null);
		setCallCenter(null);
		setCommercialRecordNo(null);
		setTradeMark(null);
		setLogo(null);
		setUploadedLogo(null);
		setAgentId(null);
		setCategoryIds(null);
		branchBean.setAddress(null);
		branchBean.setBranchName(null);
		branchBean.setLocationLatitude(null);
		branchBean.setLocationLongitude(null);
		branchBean.setStateId(null);
		branchBean.setBranchId(null);
		branchBean.setAgentId(null);
		branchBean.setTel(null);
		branchBean.setCountryId(null);
	}

}
