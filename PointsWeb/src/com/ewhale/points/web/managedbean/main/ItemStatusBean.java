package com.ewhale.points.web.managedbean.main;

import java.util.Map;

import org.jboss.logging.Logger;

import com.ewhale.points.common.util.EntityConstants;

public class ItemStatusBean extends AbsoluteBean
{
	protected Logger logger = Logger.getLogger(ItemStatusBean.class);

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ItemStatusBean()
	{

	}

	@Override
	public String getRowStyleClass(Map<String, Object> data)
	{
		if (((Number) data.get(EntityConstants.ItemStatus.statusId)).shortValue() == EntityConstants.Status.Fixed.blockedStatus.ID)
			return "redRow";
		else if (((Number) data.get(EntityConstants.ItemStatus.statusId)).shortValue() == EntityConstants.Status.Fixed.pendingStatus.ID)
			return "yellowRow";
		else
			return null;
	}

}
