package com.ewhale.points.web.managedbean.systemsales;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.jboss.logging.Logger;

import com.ewhale.points.common.util.EntityConstants;
import com.ewhale.points.web.managedbean.main.AbsoluteBean;
import com.ewhale.points.web.managedbean.main.FacesUtil;
import com.ewhale.points.ws.main.proxy.ServiceClientUtil;
import com.ewhale.points.ws.system.proxy.SystemSalesServiceClient;

@SessionScoped
@ManagedBean
public class CategoryBean extends AbsoluteBean
{
	protected Logger LOG = Logger.getLogger(CategoryBean.class);
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Short categoryId;

	private String categoryName;

	public CategoryBean()
	{
		setHasMoreDetails(false);
	}


	public Short getCategoryId()
	{
		return categoryId;
	}


	public void setCategoryId(Short categoryId)
	{
		this.categoryId = categoryId;
	}


	public String getCategoryName()
	{
		return categoryName;
	}

	public void setCategoryName(String categoryName)
	{
		this.categoryName = categoryName;
	}
	
	
	@Override
	protected String getUpdatePageName()
	{
		return "category_details.xhtml";
	}
	
	@Override
	protected void loadDetailsData(Map<String, Object> data)
	{
		categoryId = ((Number) data.get(EntityConstants.Category.categoryId)).shortValue();
		categoryName = (String) data.get(EntityConstants.Category.categoryName);
	}
	@Override
	protected void handlePostConstruct()
	{
		viewCategoriesList();
	}
	
	public void viewCategoriesList()
	{
		SystemSalesServiceClient systemSalesServiceClient = ServiceClientUtil.getSystemSalesServiceClient(FacesUtil.getLoginToken());
		List<Map<String,Object>> allCategoriesCollection = systemSalesServiceClient.getAllCategories();
		populateTable(allCategoriesCollection, EntityConstants.Category.categoryId, EntityConstants.Category.categoryName);
	}

	public void addCategory()
	{
		Map<String, Object> data = new HashMap<>();
		data.put(EntityConstants.Category.categoryName, categoryName);
		SystemSalesServiceClient systemSalesServiceClient = ServiceClientUtil.getSystemSalesServiceClient(FacesUtil.getLoginToken());
		systemSalesServiceClient.addCategory(data);
		viewCategoriesList();
		//IMP_Ahmed get Strings from aplication.properties file
		FacesUtil.growlInfoMessage("Success", "New record has been added successfully");
	}

	public void updateCategory()
	{
		Map<String, Object> data = new HashMap<>();
		data.put(EntityConstants.Category.categoryId, categoryId);
		data.put(EntityConstants.Category.categoryName, categoryName);
		SystemSalesServiceClient systemSalesServiceClient = ServiceClientUtil.getSystemSalesServiceClient(FacesUtil.getLoginToken());
		systemSalesServiceClient.updateCategory(data);
		// IMP_Ahmed close update dialog and reload table data after saving.
	}

	public void deleteCategory()
	{
		SystemSalesServiceClient systemSalesServiceClient = ServiceClientUtil.getSystemSalesServiceClient(FacesUtil.getLoginToken());
		systemSalesServiceClient.deleteCategory(categoryId + "");

	}
	@Override
	protected void resetToAdd()
	{
		setCategoryId(null);
		setCategoryName(null);
		LOG.debug("resetToAdd is called");
	}
}
