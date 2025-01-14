package com.ewhale.points.web.managedbean.systemadmin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.jboss.logging.Logger;

import com.ewhale.points.common.util.EntityConstants;
import com.ewhale.points.web.managedbean.main.AbsoluteBean;
import com.ewhale.points.web.managedbean.main.FacesUtil;
import com.ewhale.points.web.managedbean.main.ProfileBean;
import com.ewhale.points.ws.main.proxy.ServiceClientUtil;
import com.ewhale.points.ws.system.proxy.SystemAdminServiceClient;

@SessionScoped
@ManagedBean
public class SysAdminAgentEmployeeBean extends ProfileBean
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected Logger logger = Logger.getLogger(AbsoluteBean.class);

	private List<Map<String, Object>> allAgentsList;

	SystemAdminServiceClient systemAdminServiceClient = ServiceClientUtil.getSystemAdminServiceClient(FacesUtil.getLoginToken());

	public List<Map<String, Object>> getAllAgentsList()
	{
		return allAgentsList;
	}

	@Override
	protected void handlePostConstruct()
	{
		setCanUpdate(false);
		allAgentsList = SysAdminBeanUtils.getAllAgentsList();
	}

	public void addAgentEmployee()
	{
		Map<String, Object> data = fillInsertDataMap();
		data.put(EntityConstants.Profile.agentId, getAgentId());
		data.put(EntityConstants.Profile.updateStatusEmpId, FacesUtil.getLoginId());
		systemAdminServiceClient.addAgentAdminEmployee(data);
		FacesUtil.growlInfoMessage("Success", "Employee Added successfully");
	}

	public void activateStatus()
	{
		changeStatus(EntityConstants.Status.Fixed.activeStatus.ID);
		getAgentEmployeesList();
		closeDialoge();
	}

	public void blockStatus()
	{
		changeStatus(EntityConstants.Status.Fixed.blockedStatus.ID);
		getAgentEmployeesList();
		closeDialoge();
	}

	private void changeStatus(Short statusId)
	{
		Map<String, Object> data = new HashMap<>();
		data.put(EntityConstants.ItemStatus.itemId, getProfileId());
		data.put(EntityConstants.ItemStatus.statusId, statusId);
		data.put(EntityConstants.ItemStatus.updateStatusEmpId, FacesUtil.getLoginId());
		systemAdminServiceClient.updateItemStatus(data);
		FacesUtil.growlInfoMessage("Success", "Status Has Been Changed successfully");
	}

	public void getAgentEmployeesList()
	{
		Map<String, Object> data = new HashMap<>();
		data.put(EntityConstants.Profile.agentId, getAgentId());
		List<Map<String, Object>> agentEmployeesList = systemAdminServiceClient.getAgentEmployeeList(data);

		String[][] columnKeys = new String[][]
			{
					{ EntityConstants.Profile.firstName, null },
					{ EntityConstants.Profile.lastName, null },
					{ EntityConstants.Profile.mobile, null },
					{ EntityConstants.Profile.role, EntityConstants.Role.roleName },
					{ EntityConstants.Profile.statusName, null } };

		populateTable(agentEmployeesList, columnKeys);
	}

	@Override
	protected void loadDetailsData(Map<String, Object> data)
	{
		Map<String, Object> employeeData = systemAdminServiceClient.profileDetails(data.get(EntityConstants.Profile.profileId) + "");
		logger.debug(employeeData.toString());
		fillDetailsData(employeeData);
	}

	@Override
	protected String getDetailsPageName()
	{
		return "agent_employee_details.xhtml";
	}

	public void deleteAgentEmployee()
	{
		systemAdminServiceClient.deleteProfile(getProfileId() + "");
	}

}
