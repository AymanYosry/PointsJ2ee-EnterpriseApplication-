package com.ewhale.points.web.managedbean.main;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.ewhale.points.common.util.EntityConstants;

/**
 * @author Ahmad Khalil
 * @update Ayman Yosry
 */
public class ProfileBean extends AbsoluteBean
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String mobile;

	private Long agentId;

	private Long profileId;

	private Short statusId;

	private String address;

	private Date birthDate;

	private String birthDateStr;

	private String email;

	private String firstName, lastName, midName;

	private String roleName, statusName;

	private String tel;

	private Integer roleId;

	private Byte countryId = 2;

	public Long getAgentId()
	{
		return agentId;
	}

	public void setAgentId(Long agentId)
	{
		this.agentId = agentId;
	}

	public Long getProfileId()
	{
		return profileId;
	}

	public void setProfileId(Long profileId)
	{
		this.profileId = profileId;
	}

	public Short getStatusId()
	{
		return statusId;
	}

	public void setStatusId(Short statusId)
	{
		this.statusId = statusId;
	}

	public String getStatusName()
	{
		return statusName;
	}

	public void setStatusName(String statusName)
	{
		this.statusName = statusName;
	}

	public String getAddress()
	{
		return address;
	}

	public void setAddress(String address)
	{
		this.address = address;
	}

	public Date getBirthDate()
	{
		return birthDate;
	}

	public void setBirthDate(Date birthDate)
	{
		this.birthDate = birthDate;
	}

	public String getEmail()
	{
		return email;
	}

	public void setEmail(String email)
	{
		this.email = email;
	}

	public String getFirstName()
	{
		return firstName;
	}

	public void setFirstName(String firstName)
	{
		this.firstName = firstName;
	}

	public String getLastName()
	{
		return lastName;
	}

	public void setLastName(String lastName)
	{
		this.lastName = lastName;
	}

	public String getMidName()
	{
		return midName;
	}

	public void setMidName(String midName)
	{
		this.midName = midName;
	}

	public String getMobile()
	{
		return mobile;
	}

	public void setMobile(String mobile)
	{
		this.mobile = mobile;
	}

	public String getTel()
	{
		return tel;
	}

	public void setTel(String tel)
	{
		this.tel = tel;
	}

	public Integer getRoleId()
	{
		return roleId;
	}

	public void setRoleId(Integer roleId)
	{
		this.roleId = roleId;
	}

	public String getRoleName()
	{
		return roleName;
	}

	public void setRoleName(String roleName)
	{
		this.roleName = roleName;
	}

	public Byte getCountryId()
	{
		return countryId;
	}

	public void setCountryId(Byte countryId)
	{
		this.countryId = countryId;
	}

	public Map<String, Object> fillInsertDataMap()
	{
		Map<String, Object> data = new HashMap<>();
		data.put(EntityConstants.Profile.firstName, firstName);
		data.put(EntityConstants.Profile.lastName, lastName);
		data.put(EntityConstants.Profile.countryId, countryId);
		data.put(EntityConstants.Profile.mobile, mobile);

		return data;
	}

	public Map<String, Object> fillUpdateDataMap()
	{
		Map<String, Object> data = new HashMap<>();
		data.put(EntityConstants.Profile.profileId, profileId);
		data.put(EntityConstants.Profile.address, address);
		data.put(EntityConstants.Profile.birthDate, birthDate);
		data.put(EntityConstants.Profile.email, email);
		data.put(EntityConstants.Profile.firstName, firstName);
		data.put(EntityConstants.Profile.lastName, lastName);
		data.put(EntityConstants.Profile.midName, midName);
		data.put(EntityConstants.Profile.mobile, mobile);
		data.put(EntityConstants.Profile.tel, tel);
		return data;
	}

	@SuppressWarnings("unchecked")
	protected void fillDetailsData(Map<String, Object> employeeData)
	{
		agentId = ((Number) employeeData.get(EntityConstants.Profile.agentId)).longValue();
		profileId = ((Number) employeeData.get(EntityConstants.Profile.profileId)).longValue();

		address = (String) employeeData.get(EntityConstants.Profile.address);
		birthDateStr = (String) employeeData.get(EntityConstants.Profile.birthDate);
		birthDate = FacesUtil.getDateFromString(birthDateStr);
		email = (String) employeeData.get(EntityConstants.Profile.email);
		firstName = (String) employeeData.get(EntityConstants.Profile.firstName);
		lastName = (String) employeeData.get(EntityConstants.Profile.lastName);
		midName = (String) employeeData.get(EntityConstants.Profile.midName);
		mobile = (String) employeeData.get(EntityConstants.Profile.mobile);
		roleName = ((String) ((Map<String, Object>) employeeData.get(EntityConstants.Profile.role)).get(EntityConstants.Role.roleName));
		roleId = ((Number) ((Map<String, Object>) employeeData.get(EntityConstants.Profile.role)).get(EntityConstants.Role.roleId)).intValue();
		statusName = (String) employeeData.get(EntityConstants.Profile.statusName);
		statusId = ((Number) employeeData.get(EntityConstants.Profile.statusId)).shortValue();
		tel = (String) employeeData.get(EntityConstants.Profile.tel);
	}
}
