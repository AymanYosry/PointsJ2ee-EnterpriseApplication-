package com.ewhale.points.web.managedbean.main;

import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.jboss.logging.Logger;
import org.primefaces.model.UploadedFile;

import com.ewhale.points.common.util.EntityConstants;

public class PromotionBean extends ItemStatusBean
{

	protected Logger LOG = Logger.getLogger(PromotionBean.class);
			/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long promotionId;

	private String image;

	private UploadedFile uploadedImage;

	private String message, messageDetails,promotionDateStr;

	private Date promotionDate;

	private Float promotionFees;

	private Long agentId;

	private Short currencyId;

	private Short countryId;

	private Long insEmpId;

	private Long updateEmpId;

	private Long sysInvoiceId;

	public PromotionBean()
	{

	}

	public Long getPromotionId()
	{
		return promotionId;
	}

	public void setPromotionId(Long promotionId)
	{
		this.promotionId = promotionId;
	}

	public String getImage()
	{
		return image;
	}

	public void setImage(String image)
	{
		this.image = image;
	}

	public String getMessage()
	{
		return message;
	}

	public void setMessage(String message)
	{
		this.message = message;
	}

	public String getMessageDetails()
	{
		return messageDetails;
	}

	public void setMessageDetails(String messageDetails)
	{
		this.messageDetails = messageDetails;
	}

	public String getPromotionDateStr()
	{
		return promotionDateStr;
	}

	public Date getPromotionDate()
	{
		return promotionDate;
	}

	public void setPromotionDate(Date promotionDate)
	{
		this.promotionDate = promotionDate;
	}

	public Float getPromotionFees()
	{
		return promotionFees;
	}

	public void setPromotionFees(Float promotionFees)
	{
		this.promotionFees = promotionFees;
	}

	public Long getAgentId()
	{
		return agentId;
	}

	public void setAgentId(Long agentId)
	{
		this.agentId = agentId;
	}

	public Short getCurrencyId()
	{
		return currencyId;
	}

	public void setCurrencyId(Short currencyId)
	{
		this.currencyId = currencyId;
	}

	public Long getInsEmpId()
	{
		return insEmpId;
	}

	public void setInsEmpId(Long insEmpId)
	{
		this.insEmpId = insEmpId;
	}

	public Long getUpdateEmpId()
	{
		return updateEmpId;
	}

	public void setUpdateEmpId(Long updateEmpId)
	{
		this.updateEmpId = updateEmpId;
	}

	public Long getSysInvoiceId()
	{
		return sysInvoiceId;
	}

	public void setSysInvoiceId(Long sysInvoiceId)
	{
		this.sysInvoiceId = sysInvoiceId;
	}

	public Short getCountryId()
	{
		return countryId;
	}

	public void setCountryId(Short countryId)
	{
		this.countryId = countryId;
	}

	public UploadedFile getUploadedImage()
	{
		return uploadedImage;
	}

	public void setUploadedImage(UploadedFile uploadedImage)
	{
		LOG.debug("-- file uploaded");
		this.uploadedImage = uploadedImage;
	}

	protected Map<String, Object> fillDataMap() throws Exception
	{
		Map<String, Object> data = new HashMap<>();
		data.put(EntityConstants.Promotion.image,
				FacesUtil.isImageUploaded(uploadedImage) ? Base64.getEncoder().encodeToString(getUploadedImage().getContents()) : null);
		data.put(EntityConstants.Promotion.message, message);
		data.put(EntityConstants.Promotion.messageDetails, messageDetails);
		data.put(EntityConstants.Promotion.promotionDate, promotionDate);
		data.put(EntityConstants.Promotion.promotionFees, promotionFees);
		data.put(EntityConstants.Promotion.currencyId, currencyId);
		data.put(EntityConstants.Promotion.countryId, countryId);
		return data;
	}

	@SuppressWarnings("unchecked")
	protected void fillDetailsData(Map<String, Object> data)
	{
		image = ((String) data.get(EntityConstants.Promotion.image));
		message = (String) data.get(EntityConstants.Promotion.message);
		messageDetails = (String) data.get(EntityConstants.Promotion.messageDetails);
		promotionDateStr = ((String) data.get(EntityConstants.Promotion.promotionDate));
		promotionDate = FacesUtil.getDateFromString(promotionDateStr);
		promotionFees = ((Number) data.get(EntityConstants.Promotion.promotionFees)).floatValue();
		currencyId = ((Number) ((Map<String, Object>) data.get(EntityConstants.Promotion.currency)).get(EntityConstants.Currency.currencyId))
				.shortValue();
		promotionId = ((Number) data.get(EntityConstants.Promotion.promotionId)).longValue();
		agentId = ((Number) data.get(EntityConstants.Promotion.agentId)).longValue();
		countryId = ((Number) data.get(EntityConstants.Promotion.countryId)).shortValue();
	}

	@Override
	protected void resetToAdd()
	{
		setImage(null);
		setMessage(null);
		setMessageDetails(null);
		setPromotionDate(null);
		setPromotionFees(null);
		setCurrencyId(null);
		setPromotionId(null);
		setAgentId(null);
		setCountryId(null);
	}

}
