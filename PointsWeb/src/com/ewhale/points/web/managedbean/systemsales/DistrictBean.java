package com.ewhale.points.web.managedbean.systemsales;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import com.ewhale.points.common.util.EntityConstants;
import com.ewhale.points.web.managedbean.main.AbsoluteBean;
import com.ewhale.points.web.managedbean.main.FacesUtil;
import com.ewhale.points.ws.main.proxy.ServiceClientUtil;
import com.ewhale.points.ws.system.proxy.SystemSalesServiceClient;

@SessionScoped
@ManagedBean
public class DistrictBean extends AbsoluteBean
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer districtId;

	private Integer stateId;

	private Short countryId;

	private String districtName;

	private String stateName;

	private String countryName;

	private List<Map<String, Object>> countriesList;

	private List<Map<String, Object>> statesList;

	public DistrictBean()
	{
		setHasMoreDetails(false);
	}

	public int getDistrictId()
	{
		return districtId;
	}

	public void setDistrictId(int districtId)
	{
		this.districtId = districtId;
	}

	public int getStateId()
	{
		return stateId;
	}

	public void setStateId(int stateId)
	{
		this.stateId = stateId;
	}

	public Short getCountryId()
	{
		return countryId;
	}

	public void setCountryId(Short countryId)
	{
		this.countryId = countryId;
	}

	public String getDistrictName()
	{
		return districtName;
	}

	public void setDistrictName(String districtName)
	{
		this.districtName = districtName;
	}

	public String getStateName()
	{
		return stateName;
	}

	public void setStateName(String stateName)
	{
		this.stateName = stateName;
	}

	public void setDistrictId(Integer districtId)
	{
		this.districtId = districtId;
	}

	public void setStateId(Integer stateId)
	{
		this.stateId = stateId;
	}

	public List<Map<String, Object>> getCountriesList()
	{
		return countriesList;
	}

	public void setCountriesList(List<Map<String, Object>> countriesList)
	{
		this.countriesList = countriesList;
	}

	public List<Map<String, Object>> getStatesList()
	{
		return statesList;
	}

	public void setStatesList(List<Map<String, Object>> statesList)
	{
		this.statesList = statesList;
	}

	public String getCountryName()
	{
		return countryName;
	}

	public void setCountryName(String countryName)
	{
		this.countryName = countryName;
	}

	@Override
	protected String getDetailsPageName()
	{
		return null;// has no more details "district_details.xhtml";
	}

	@Override
	protected String getUpdatePageName()
	{
		return "district_details.xhtml";
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void loadDetailsData(Map<String, Object> data)
	{
		SystemSalesServiceClient systemSalesServiceClient = ServiceClientUtil.getSystemSalesServiceClient(FacesUtil.getLoginToken());
		Map<String, Object> district = systemSalesServiceClient.districtDetails(data.get(EntityConstants.District.districtId).toString());
		districtId = ((Number) district.get(EntityConstants.District.districtId)).intValue();
		districtName = (String) district.get(EntityConstants.District.districtName);
		stateId = ((Number) ((Map<String, Object>) district.get(EntityConstants.District.state)).get(EntityConstants.State.stateId)).intValue();
		stateName = (String) ((Map<String, Object>) district.get(EntityConstants.District.state)).get(EntityConstants.State.stateName);
		countryId = ((Number) ((Map<String, Object>) district.get(EntityConstants.District.state)).get(EntityConstants.Country.countryId)).shortValue();
		countryName = (String) ((Map<String, Object>) district.get(EntityConstants.District.state)).get(EntityConstants.Country.countryName);
	}

	public void viewDistrictsList()
	{
		Map<String, Object> data = new HashMap<>();
		data.put(EntityConstants.District.stateId, stateId);
		SystemSalesServiceClient systemSalesServiceClient = ServiceClientUtil.getSystemSalesServiceClient(FacesUtil.getLoginToken());
		List<Map<String, Object>> allDistrictsCollection = systemSalesServiceClient.getDistrictsList(data);
		String[][] columnKeys = new String[][]
			{
					{ EntityConstants.District.districtId, null },
					{ EntityConstants.District.districtName, null },
					{ EntityConstants.District.state, EntityConstants.State.stateName } };
		populateTable(allDistrictsCollection, columnKeys);
	}

	public void addDistrict()
	{
		Map<String, Object> data = new HashMap<>();
		data.put(EntityConstants.District.stateId, stateId);
		data.put(EntityConstants.District.countryId, countryId);
		data.put(EntityConstants.District.districtName, districtName);
		SystemSalesServiceClient systemSalesServiceClient = ServiceClientUtil.getSystemSalesServiceClient(FacesUtil.getLoginToken());
		systemSalesServiceClient.addDistrict(data);
		viewDistrictsList();
		//IMP_Ahmed get Strings from aplication.properties file
		FacesUtil.growlInfoMessage("Success", "New record has been added successfully");

	}

	public void updateDistrict()
	{
		Map<String, Object> data = new HashMap<>();
		data.put(EntityConstants.District.districtId, districtId);
		data.put(EntityConstants.District.districtName, districtName);
		SystemSalesServiceClient systemSalesServiceClient = ServiceClientUtil.getSystemSalesServiceClient(FacesUtil.getLoginToken());
		systemSalesServiceClient.updateDistrict(data);

	}

	public void deleteDistrict()
	{
		SystemSalesServiceClient systemSalesServiceClient = ServiceClientUtil.getSystemSalesServiceClient(FacesUtil.getLoginToken());
		systemSalesServiceClient.deleteDistrict(districtId + "");
	}

	public void loadStatesList()
	{
		Map<String, Object> data = new HashMap<>();
		data.put(EntityConstants.State.countryId, countryId);
		SystemSalesServiceClient systemSalesServiceClient = ServiceClientUtil.getSystemSalesServiceClient(FacesUtil.getLoginToken());
		statesList = systemSalesServiceClient.getStatesList(data);
	}
	
	@Override
	protected void resetToAdd()
	{
		setCountryId(null);
		setDistrictId(null);
		setStateId(null);
		setDistrictName(null);
	}

}
