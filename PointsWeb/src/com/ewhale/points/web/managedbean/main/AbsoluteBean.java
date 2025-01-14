package com.ewhale.points.web.managedbean.main;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jboss.logging.Logger;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.TabChangeEvent;

public abstract class AbsoluteBean implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected Logger logger = Logger.getLogger(AbsoluteBean.class);

	private boolean updateEnabled, hasMoreDetails = true, canUpdate = true, canAdd = true;

	private String parentPage, parentPageTitle;

	public AbsoluteBean()
	{
		loadDialogeOptions();
		handlePostConstruct();
	}

	protected Map<String, Object> dialogeOptions = new HashMap<String, Object>();

	protected void loadDialogeOptions()
	{
		dialogeOptions.put("modal", true);
		dialogeOptions.put("width", 640);
		dialogeOptions.put("height", 340);
		dialogeOptions.put("contentWidth", "100%");
		dialogeOptions.put("contentHeight", "100%");
		dialogeOptions.put("headerElement", "customheader");
	}

	protected void closeDialoge()
	{
		RequestContext.getCurrentInstance().closeDialog(null);
	}

	protected void handlePostConstruct()
	{

	}

	protected void setDialogeWidth(int width)
	{
		dialogeOptions.put("width", width);
	}

	protected void setDialogeHeight(int height)
	{
		dialogeOptions.put("height", height);
	}

	protected String getToken()
	{
		return FacesUtil.getLoginToken();

	}

	public boolean isHasMoreDetails()
	{
		return hasMoreDetails;
	}

	public void setHasMoreDetails(boolean hasMoreDetails)
	{
		this.hasMoreDetails = hasMoreDetails;
	}

	public boolean isCanUpdate()
	{
		return canUpdate;
	}

	public void setCanUpdate(boolean canUpdate)
	{
		this.canUpdate = canUpdate;
	}
	public boolean isCanUpdate(Map<String, Object> data)
	{	
		return canUpdate;
	}

	public boolean isCanAdd()
	{
		return canAdd;
	}

	public void setCanAdd(boolean canAdd)
	{
		this.canAdd = canAdd;
	}

	public String getParentPage()
	{
		return parentPage;
	}

	public void setParentPage(String parentPage)
	{
		this.parentPage = parentPage;
	}

	public String getParentPageTitle()
	{
		return parentPageTitle;
	}

	public void setParentPageTitle(String parentPageTitle)
	{
		this.parentPageTitle = parentPageTitle;
	}

	public void resetParentPage()
	{
		this.parentPageTitle = null;
		this.parentPage = null;
	}

	public String goToParentPage()
	{
		return parentPage;
	}

	private List<ColumnModel> columns = new ArrayList<>();

	private List<Map<String, Object>> tableData;

	public List<ColumnModel> getColumns()
	{
		return columns;
	}

	public List<Map<String, Object>> getTableData()
	{
		return tableData;
	}

	protected void setTableData(List<Map<String, Object>> tableData)
	{
		this.tableData = tableData;
		logTableDataIntoLogger();
	}

	protected void logTableDataIntoLogger()
	{
//		if (tableData != null)
//			logger.debug("table data :"+Arrays.toString(tableData.toArray()));
	}

	private void addLinkableListColumns(String[] linkableLists)
	{
		for (String columnKey : linkableLists)
		{
			columns.add(new ColumnModel(columnKey, null, columnKey));
			logger.debug(columnKey);
		}
	}

	/**
	 * used to add columns to the table in the jsf search_component
	 * 
	 * @param columnKeys 2 dimension String array
	 *            every outer array represent a column
	 *            if the column is not linkable so the array should contain the field name in the data map and null
	 *            if the coulmn is linkable so the array should contain the field name represent the whole linkable object in the data map which
	 *            should be represented as a map
	 *            and the second field should be the name of the field to be shown in the table from within this object
	 *            it is represented in the search_componenet.xhtml as tableDataRow.get(columnKey[0]).get( columnKey[1])
	 */
	private void populateColumns(String[][] columnKeys)
	{
		columns.clear();
		for (String[] columnKey : columnKeys)
		{
			columns.add(new ColumnModel(columnKey[0], columnKey[1], null));
			logger.debug("column : "+Arrays.toString(columnKey));
		}
	}

	/**
	 * used to set the columns of the table with no linkable fields
	 * 
	 * @param columnKeys the columns to show in the table
	 */
	private void populateColumns(String... columnKeys)
	{
		columns.clear();
		for (String columnKey : columnKeys)
		{
			columns.add(new ColumnModel(columnKey, null, null));
			logger.debug(columnKey);
		}
	}

	public void populateTable(List<Map<String, Object>> dataOfTable, String[] linkableLists, String[][] columnKeys)
	{
		populateColumns(columnKeys);
		addLinkableListColumns(linkableLists);
		setTableData(dataOfTable);
	}

	public void populateTable(List<Map<String, Object>> dataOfTable, String[][] columnKeys)
	{
		populateColumns(columnKeys);
		setTableData(dataOfTable);
	}

	public void populateTable(List<Map<String, Object>> dataOfTable, String... columnKeys)
	{
		populateColumns(columnKeys);
		setTableData(dataOfTable);
	}

	public void populateTable(List<Map<String, Object>> dataOfTable, String[] linkableLists, String... columnKeys)
	{
		populateColumns(columnKeys);
		addLinkableListColumns(linkableLists);
		setTableData(dataOfTable);
	}

	// getters and setters
	static public class ColumnModel implements Serializable
	{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		private String property;

		private String linkableProperty;

		private String linkableList;

		public ColumnModel(String property, String linkableProperty, String linkableList)
		{
			super();
			this.property = property;
			this.linkableProperty = linkableProperty;
			this.linkableList = linkableList;
		}

		public String getProperty()
		{
			return property;
		}

		public String getLinkableProperty()
		{
			return linkableProperty;
		}

		public String getLinkableList()
		{
			return linkableList;
		}

	}

	public String getRowStyleClass(Map<String, Object> data)
	{
		return null;
	}

	public final void viewDetails(Map<String, Object> data)
	{
			updateEnabled = false;
			loadDetailsData(data);
			String detailsPageName = getDetailsPageName();
			RequestContext.getCurrentInstance().openDialog(detailsPageName, dialogeOptions, null);
	}

	protected String getDetailsPageName()
	{
		return null;
	}

	protected String getUpdatePageName()
	{
		return null;
	}

	protected void loadDetailsData(Map<String, Object> data)
	{

	}

	public void viewDetails(Map<String, Object> data, String selectedField)
	{

	}

	public String viewListDetails(Map<String, Object> data, String selectedField)
	{
		return null;
	}

	public boolean isUpdateEnabled()
	{
		return updateEnabled;
	}

	public void showForUpdate(Map<String, Object> data)
	{
		try
		{
			loadDetailsData(data);
			updateEnabled = true;
			String updatePageName = getUpdatePageName();
			RequestContext.getCurrentInstance().openDialog(updatePageName, dialogeOptions, null);
		}
		catch (Exception e)
		{
			// IMP_Ahmed DONT use e.printstacktrace use the below line
			// response.sendredirect to error.jsf
		}
	}

	public void onTabChange(TabChangeEvent event)
	{
		String title = event.getTab().getTitle();
		if (title.equals("Add New"))
		{
			resetToAdd();
		}

	}

	protected void resetToAdd()
	{
	}

	public void postUpdateDialog(SelectEvent event)
	{
		FacesUtil.growlInfoMessage("Successfull", "Updated Successfully");
	}

	public String getDefaultSortOrder()
	{
		return "ascending";
	}

}
