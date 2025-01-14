/**
 * 
 */
package com.ewhale.points.ws.agent;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.jboss.logging.Logger;

import com.ewhale.points.common.annotations.VerifyToken;
import com.ewhale.points.common.exception.FacadeException;
import com.ewhale.points.common.exception.ServiceException;
import com.ewhale.points.common.exception.ValidationException;
import com.ewhale.points.common.util.EntityConstants;
import com.ewhale.points.controller.facade.AgentFacade;
import com.ewhale.points.controller.facade.AgentRateFacade;
import com.ewhale.points.controller.facade.BranchFacade;
import com.ewhale.points.controller.facade.ContractFacade;
import com.ewhale.points.controller.facade.PointsExchangeFacade;
import com.ewhale.points.controller.facade.ProductFacade;
import com.ewhale.points.controller.facade.ProfileFacade;
import com.ewhale.points.controller.facade.PromotionFacade;
import com.ewhale.points.controller.facade.PurchaseFacade;
import com.ewhale.points.ws.main.ServiceHeading;

/**
 * @author Ayman Yosry
 */

@Path("/AgentSeller")
@VerifyToken
@Produces(ServiceHeading.MEDIA_TYPE)
public class AgentSellerService implements ServiceHeading
{
	private AgentServiceControler controller = new AgentServiceControler();

	protected Logger LOG = Logger.getLogger(AgentSellerService.class);

	@EJB
	private ProfileFacade profileFacade;

	@EJB
	private PurchaseFacade purchaseFacade;

	@EJB
	private AgentFacade agentFacade;

	@EJB
	private PointsExchangeFacade pointsExchangeFacade;

	@EJB
	private ProductFacade productFacade;

	@EJB
	private PromotionFacade promotionFacade;

	@EJB
	private AgentRateFacade agentRateFacade;

	@EJB
	private ContractFacade contractFacade;

	@EJB
	private BranchFacade branchFacade;

	@POST
	@Path("/fundUserPurchase")
//	@VerifyDigitaSignature
	public Map<String, Object> fundUserPurchase(Map<String, Object> data)
	{
		try
		{
			data.put(EntityConstants.Purchase.fund, true);
			Map<String, Object> purchaseData = controller.add(purchaseFacade, data);
			return purchaseData;
		}
		catch (FacadeException e)
		{
			LOG.error(e.getMessage(), e);
			throw new ServiceException("Problem While retrieving data", e.getStatusCode());
		}
		catch (ValidationException e)
		{
			throw new ServiceException(e.getMessage(), e.getStatusCode());
		}
	}

	@POST
	@Path("/refundUserPurchase")
//	@VerifyDigitaSignature
	public Map<String, Object> refundUserPurchase(Map<String, Object> data)
	{
		try
		{
			data.put(EntityConstants.Purchase.fund, false);
			Map<String, Object> purchaseData = controller.add(purchaseFacade, data);
			return purchaseData;
		}
		catch (FacadeException e)
		{
			LOG.error(e.getMessage(), e);
			throw new ServiceException("Problem While retrieving data", e.getStatusCode());
		}
		catch (ValidationException e)
		{
			throw new ServiceException(e.getMessage(), e.getStatusCode());
		}

	}

	@POST
	@Path("/exchangeUserPoints")
//	@VerifyDigitaSignature
	public Map<String, Object> exchangeUserPoints(Map<String, Object> data)
	{
		try
		{
			data.put(EntityConstants.PointsExchange.exchange, true);
			Map<String, Object> pointEchangeData = controller.add(pointsExchangeFacade, data);
			return pointEchangeData;
		}
		catch (FacadeException e)
		{
			LOG.error(e.getMessage(), e);
			throw new ServiceException("Problem While retrieving data", e.getStatusCode());
		}
		catch (ValidationException e)
		{
			throw new ServiceException(e.getMessage(), e.getStatusCode());
		}
	}

	@POST
	@Path("/reExchangeUserPoints")
//	@VerifyDigitaSignature
	public Map<String, Object> reExchangeUserPoints(Map<String, Object> data)
	{
		try
		{
			data.put(EntityConstants.PointsExchange.exchange, false);
			Map<String, Object> pointEchangeData = controller.add(pointsExchangeFacade, data);
			return pointEchangeData;
		}
		catch (FacadeException e)
		{
			LOG.error(e.getMessage(), e);
			throw new ServiceException("Problem While retrieving data", e.getStatusCode());
		}
		catch (ValidationException e)
		{
			throw new ServiceException(e.getMessage(), e.getStatusCode());
		}
	}

	@POST
	@Path("/userPurchasesList")
	public List<Map<String, Object>> userPurchasesList(Map<String, Object> purchaseData)
	{
		try
		{
			List<Map<String, Object>> purchasesList = controller.getList(purchaseFacade, purchaseData);
			return purchasesList;
		}
		catch (FacadeException e)
		{
			LOG.error(e.getMessage(), e);
			throw new ServiceException("Problem While retrieving data", e.getStatusCode());
		}
	}

	@POST
	@Path("/userExchangesList")
	public List<Map<String, Object>> userExchangesList(Map<String, Object> exchangeData)
	{
		try
		{
			List<Map<String, Object>> exchangesList = controller.getList(pointsExchangeFacade, exchangeData);
			return exchangesList;
		}
		catch (FacadeException e)
		{
			LOG.error(e.getMessage(), e);
			throw new ServiceException("Problem While retrieving data", e.getStatusCode());
		}
	}

	@POST
	@Path("/agentProductsList")
	public List<Map<String, Object>> agentProductsList(Map<String, Object> productData)
	{
		try
		{
			productData.put(EntityConstants.Product.validityDate_From_Search, new Date().getTime());
			productData.put(EntityConstants.Product.statusId, EntityConstants.Status.Fixed.activeStatus.ID);
			List<Map<String, Object>> productssList = controller.getList(productFacade, productData);
			return productssList;
		}
		catch (FacadeException e)
		{
			LOG.error(e.getMessage(), e);
			throw new ServiceException("Problem While retrieving data", e.getStatusCode());
		}
	}

	@POST
	@Path("/agentPromotionsList")
	public List<Map<String, Object>> agentPromotionsList(Map<String, Object> promotionData)
	{
		try
		{
			List<Map<String, Object>> promotionsList = controller.getList(promotionFacade, promotionData);
			return promotionsList;
		}
		catch (FacadeException e)
		{
			LOG.error(e.getMessage(), e);
			throw new ServiceException("Problem While retrieving data", e.getStatusCode());
		}
	}

	@POST
	@Path("/agentBranchesList")
	public List<Map<String, Object>> agentBranchesList(Map<String, Object> branchesData)
	{
		try
		{
			List<Map<String, Object>> branchesList = controller.getList(branchFacade, branchesData);
			return branchesList;
		}
		catch (FacadeException e)
		{
			LOG.error(e.getMessage(), e);
			throw new ServiceException("Problem While retrieving data", e.getStatusCode());
		}
	}

	@POST
	@Path("viewPurchaseDetails/{purchaseId}")
	public Map<String, Object> viewPurchaseDetails(@PathParam("purchaseId") String purchaseId)
	{
		try
		{
			Long id = Long.valueOf(purchaseId);
			Map<String, Object> purchaseData = controller.getDetails(purchaseFacade, id);
			return purchaseData;
		}
		catch (FacadeException e)
		{
			LOG.error(e.getMessage(), e);
			throw new ServiceException("Problem While retrieving data", e.getStatusCode());
		}

	}

	@POST
	@Path("viewExchangeDetails/{exchangeId}")
	public Map<String, Object> viewExchangeDetails(@PathParam("exchangeId") String exchangeId)
	{
		try
		{
			Long id = Long.valueOf(exchangeId);
			Map<String, Object> exchangeData = controller.getDetails(pointsExchangeFacade, id);
			return exchangeData;
		}
		catch (FacadeException e)
		{
			LOG.error(e.getMessage(), e);
			throw new ServiceException("Problem While retrieving data", e.getStatusCode());
		}

	}

	@POST
	@Path("/agentRateList/{exchangeId}")
	public List<Map<String, Object>> agentRateList(@PathParam("exchangeId") String agentId)
	{
		try
		{
			Map<String, Object> agentRateData = new HashMap<>();
			agentRateData.put(EntityConstants.AgentRate.agentId, agentId);
			List<Map<String, Object>> agentRateList = controller.getList(agentRateFacade, agentRateData);
			return agentRateList;
		}
		catch (FacadeException e)
		{
			LOG.error(e.getMessage(), e);
			throw new ServiceException("Problem While retrieving data", e.getStatusCode());
		}
	}

	@POST
	@Path("viewAgentDetails/{agentId}")
	public Map<String, Object> viewAgentDetails(@PathParam("agentId") String agentId)
	{
		try
		{
			Long id = Long.valueOf(agentId);
			Map<String, Object> agentData = controller.getDetails(agentFacade, id);
			return agentData;
		}
		catch (FacadeException e)
		{
			LOG.error(e.getMessage(), e);
			throw new ServiceException("Problem While retrieving data", e.getStatusCode());
		}

	}

	@POST
	@Path("viewAgentContractDetails/{contractId}")
	public Map<String, Object> viewAgentContractDetails(@PathParam("contractId") String contractId)
	{
		try
		{
			Long id = Long.valueOf(contractId);
			Map<String, Object> contractData = controller.getDetails(contractFacade, id);
			return contractData;
		}
		catch (FacadeException e)
		{
			LOG.error(e.getMessage(), e);
			throw new ServiceException("Problem While retrieving data", e.getStatusCode());
		}

	}

	@POST
	@Path("viewBranchDetails/{branchId}")
	public Map<String, Object> viewBranchDetails(@PathParam("branchId") String branchId)
	{
		try
		{
			Long id = Long.valueOf(branchId);
			Map<String, Object> branchData = controller.getDetails(branchFacade, id);
			return branchData;
		}
		catch (FacadeException e)
		{
			LOG.error(e.getMessage(), e);
			throw new ServiceException("Problem While retrieving data", e.getStatusCode());
		}

	}

	@POST
	@Path("viewProductDetails/{productId}")
	public Map<String, Object> viewProductDetails(@PathParam("productId") String productId)
	{
		try
		{
			Long id = Long.valueOf(productId);
			Map<String, Object> productData = controller.getDetails(productFacade, id);
			return productData;
		}
		catch (FacadeException e)
		{
			LOG.error(e.getMessage(), e);
			throw new ServiceException("Problem While retrieving data", e.getStatusCode());
		}

	}

	@POST
	@Path("viewPromotionDetails/{promotionId}")
	public Map<String, Object> viewPromotionDetails(@PathParam("promotionId") String promotionId)
	{
		try
		{
			Long id = Long.valueOf(promotionId);
			Map<String, Object> promotionData = controller.getDetails(promotionFacade, id);
			return promotionData;
		}
		catch (FacadeException e)
		{
			LOG.error(e.getMessage(), e);
			throw new ServiceException("Problem While retrieving data", e.getStatusCode());
		}
	}
}
