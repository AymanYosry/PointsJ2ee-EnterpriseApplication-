/**
 * 
 */
package com.ewhale.points.ws.system;

import java.util.ArrayList;
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
import com.ewhale.points.common.util.ExceptionConstants;
import com.ewhale.points.controller.facade.AgentFacade;
import com.ewhale.points.controller.facade.BranchFacade;
import com.ewhale.points.controller.facade.ContractFacade;
import com.ewhale.points.controller.facade.ItemStatusFacade;
import com.ewhale.points.controller.facade.MessageCenterFacade;
import com.ewhale.points.controller.facade.PointFacade;
import com.ewhale.points.controller.facade.ProductFacade;
import com.ewhale.points.controller.facade.ProfileFacade;
import com.ewhale.points.controller.facade.PromotionFacade;
import com.ewhale.points.controller.facade.PurchaseFacade;
import com.ewhale.points.controller.facade.SysInvoiceFacade;
import com.ewhale.points.controller.facade.SysPaymentFacade;
import com.ewhale.points.ws.agent.AgentAdminService;
import com.ewhale.points.ws.main.ServiceHeading;

/**
 * @author Ayman Yosry
 */

@Path("/SystemAdmin")
@Produces(ServiceHeading.MEDIA_TYPE)
@VerifyToken
public class SystemAdminService implements ServiceHeading
{

	protected Logger LOG = Logger.getLogger(AgentAdminService.class);

	private SystemServiceControler controler = new SystemServiceControler();

	// @EJB
	// private AuthenticationFacade loginFacade;

	@EJB
	private AgentFacade agentFacade;

	@EJB
	private ProfileFacade profileFacade;

	@EJB
	private ContractFacade contractFacade;

	@EJB
	private SysPaymentFacade sysPaymentFacade;

	@EJB
	private ItemStatusFacade itemStatusFacade;

	@EJB
	private PurchaseFacade purchaseFacade;

	@EJB
	private BranchFacade branchFacade;

	@EJB
	private ProductFacade productFacade;

	@EJB
	private SysInvoiceFacade sysInvoiceFacade;

	@EJB
	private PromotionFacade promotionFacade;

	@EJB
	private MessageCenterFacade messageCenterFacade;

	@EJB
	private PointFacade pointFacade;

	@POST
	@Path("/addSysAdminEmplyee")
	public void addSysAdminEmplyee(Map<String, Object> profileData)
	{
		try
		{
			profileData.put(EntityConstants.Profile.roleId, EntityConstants.Role.Fixed.systemAdminRole.ID);
			controler.add(profileFacade, profileData);
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
	@Path("/addSysSalesEmplyee")
	public void addSysSalesEmplyee(Map<String, Object> profileData)
	{
		try
		{
			profileData.put(EntityConstants.Profile.roleId, EntityConstants.Role.Fixed.systemSalesRole.ID);
			controler.add(profileFacade, profileData);
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
	@Path("/addAgentAdminEmployee")
	public void addAgentAdminEmployee(Map<String, Object> profileData)
	{
		try
		{
			profileData.put(EntityConstants.Profile.roleId, EntityConstants.Role.Fixed.agentAdminRole.ID);
			controler.add(profileFacade, profileData);
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

	// @POST
	// @Path("/updateProfile")
	// public void updateProfile(Map<String, Object> profileData)
	// {
	// try
	// {
	// controler.update(profileFacade, profileData);
	// }
	// catch (FacadeException e)
	// {
	// LOG.error(e.getMessage(),e);
	// throw new ServiceException("Problem While retrieving data", e.getStatusCode());
	// }
	// }

	@POST
	@Path("/addAgent")
	public void addAgent(Map<String, Object> agentData)
	{
		try
		{
			// agentData.put(EntityConstants.Agent.statusId, EntityConstants.Status.Fixed.activeStatus.ID);
			controler.add(agentFacade, agentData);
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
	@Path("/updateAgent")
	public void updateAgent(Map<String, Object> agentData)
	{
		try
		{
			controler.update(agentFacade, agentData);
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
	@Path("/addAgentContract")
	public void addAgentContract(Map<String, Object> agentContractData)
	{
		try
		{
			agentContractData.put(EntityConstants.Contract.statusId, EntityConstants.Status.Fixed.activeStatus.ID);
			controler.add(contractFacade, agentContractData);
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
	@Path("/updateAgentContract")
	public void updateAgentContract(Map<String, Object> agentContractData)
	{
		try
		{
			controler.update(contractFacade, agentContractData);
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
	@Path("/addAgentPayment")
	// @VerifyDigitaSignature
	public void addAgentPayment(Map<String, Object> agentPaymentData)
	{
		try
		{
			agentPaymentData.put(EntityConstants.SysPayment.paymentMethodId, EntityConstants.PaymentMethod.Fixed.cashPayment.ID);
			controler.add(sysPaymentFacade, agentPaymentData);
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
	@Path("/updateItemStatus")
	public void updateItemStatus(Map<String, Object> itemStatusData)
	{
		try
		{
			controler.update(itemStatusFacade, itemStatusData);
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
	@Path("/profiles")
	public List<Map<String, Object>> getProfilesList(Map<String, Object> profileData)
	{
		try
		{
			if (profileData.size() <= 0)
				throw new ServiceException(ExceptionConstants.SEARCH_CRITERIA_NEEDED_EX_MSG, ExceptionConstants.VALIDATION_EXCETION_STATUS_CODE);

			// all registered profiles are uses on the system so we should not put this line
			// profileData.put(EntityConstants.Profile.roleId, EntityConstants.Role.Fixed.userRole.ID);

			List<Map<String, Object>> profilesList = controler.getList(profileFacade, profileData);
			return profilesList;
		}
		catch (FacadeException e)
		{
			LOG.error(e.getMessage(), e);
			throw new ServiceException("Problem While retrieving data", e.getStatusCode());
		}
	}

	@POST
	@Path("/employees")
	public List<Map<String, Object>> getEmployeeList(Map<String, Object> profileData)
	{
		try
		{
			Number roleId = (Number) profileData.get(EntityConstants.Profile.roleId);
			if (roleId == null)
			{
				List<Integer> roleIds = new ArrayList<>();
				roleIds.add(EntityConstants.Role.Fixed.systemAdminRole.ID);
				roleIds.add(EntityConstants.Role.Fixed.systemSalesRole.ID);
				profileData.put(EntityConstants.Profile.roleIds, roleIds);
			}

			List<Map<String, Object>> profilesList = controler.getList(profileFacade, profileData);
			return profilesList;
		}
		catch (FacadeException e)
		{
			LOG.error(e.getMessage(), e);
			throw new ServiceException("Problem While retrieving data", e.getStatusCode());
		}
	}

	@POST
	@Path("/purchases")
	public List<Map<String, Object>> getPurchasesList(Map<String, Object> purchasesData)
	{
		try
		{
			if (purchasesData.size() <= 0)
				throw new ServiceException(ExceptionConstants.SEARCH_CRITERIA_NEEDED_EX_MSG, ExceptionConstants.VALIDATION_EXCETION_STATUS_CODE);

			List<Map<String, Object>> purchasesList = controler.getList(purchaseFacade, purchasesData);
			return purchasesList;
		}
		catch (FacadeException e)
		{
			LOG.error(e.getMessage(), e);
			throw new ServiceException("Problem While retrieving data", e.getStatusCode());
		}
	}

	@POST
	@Path("/agents")
	public List<Map<String, Object>> getAgentsList(Map<String, Object> agentsData)
	{
		try
		{
			List<Map<String, Object>> agentsList = controler.getList(agentFacade, agentsData);
			return agentsList;
		}
		catch (FacadeException e)
		{
			LOG.error(e.getMessage(), e);
			throw new ServiceException("Problem While retrieving data", e.getStatusCode());
		}
	}

	@POST
	@Path("/agentBranches")
	public List<Map<String, Object>> getAgentBranchesList(Map<String, Object> brancheData)
	{
		try
		{
			List<Map<String, Object>> brancheList = controler.getList(branchFacade, brancheData);
			return brancheList;
		}
		catch (FacadeException e)
		{
			LOG.error(e.getMessage(), e);
			throw new ServiceException("Problem While retrieving data", e.getStatusCode());
		}
	}

	@POST
	@Path("/agentProducts")
	public List<Map<String, Object>> getAgentProductsList(Map<String, Object> productData)
	{
		try
		{
			List<Map<String, Object>> productsList = controler.getList(productFacade, productData);
			return productsList;
		}
		catch (FacadeException e)
		{
			LOG.error(e.getMessage(), e);
			throw new ServiceException("Problem While retrieving data", e.getStatusCode());
		}
	}

	@POST
	@Path("/agentInvoices")
	public List<Map<String, Object>> getAgentInvoicesList(Map<String, Object> invoiceData)
	{
		try
		{
			List<Map<String, Object>> invoicesList = controler.getList(sysInvoiceFacade, invoiceData);
			return invoicesList;
		}
		catch (FacadeException e)
		{
			LOG.error(e.getMessage(), e);
			throw new ServiceException("Problem While retrieving data", e.getStatusCode());
		}
	}

	@POST
	@Path("/agentPayments")
	public List<Map<String, Object>> getAgentPaymentsList(Map<String, Object> paymentData)
	{
		try
		{
			List<Map<String, Object>> paymentsList = controler.getList(sysPaymentFacade, paymentData);
			return paymentsList;
		}
		catch (FacadeException e)
		{
			LOG.error(e.getMessage(), e);
			throw new ServiceException("Problem While retrieving data", e.getStatusCode());
		}
	}

	@POST
	@Path("/agentContracts")
	public List<Map<String, Object>> getAgentContractsList(Map<String, Object> contractData)
	{
		try
		{
			List<Map<String, Object>> contractsList = controler.getList(contractFacade, contractData);
			return contractsList;
		}
		catch (FacadeException e)
		{
			LOG.error(e.getMessage(), e);
			throw new ServiceException("Problem While retrieving data", e.getStatusCode());
		}
	}

	@POST
	@Path("/agentEmployees")
	public List<Map<String, Object>> getAgentEmployeeList(Map<String, Object> profileData)
	{
		try
		{
			Number roleId = (Number) profileData.get(EntityConstants.Profile.roleId);
			if (roleId == null)
			{
				List<Integer> roleIds = new ArrayList<>();
				roleIds.add(EntityConstants.Role.Fixed.agentAdminRole.ID);
				roleIds.add(EntityConstants.Role.Fixed.agentSellerRole.ID);
				profileData.put(EntityConstants.Profile.roleIds, roleIds);
			}

			List<Map<String, Object>> profilesList = controler.getList(profileFacade, profileData);
			return profilesList;
		}
		catch (FacadeException e)
		{
			LOG.error(e.getMessage(), e);
			throw new ServiceException("Problem While retrieving data", e.getStatusCode());
		}
	}

	@POST
	@Path("/messages")
	public List<Map<String, Object>> getMessagesList(Map<String, Object> messageData)
	{
		try
		{
			List<Map<String, Object>> messageList = controler.getList(messageCenterFacade, messageData);
			return messageList;
		}
		catch (FacadeException e)
		{
			LOG.error(e.getMessage(), e);
			throw new ServiceException("Problem While retrieving data", e.getStatusCode());
		}
	}

	@POST
	@Path("/promotions")
	public List<Map<String, Object>> getPromotionsList(Map<String, Object> promotionData)
	{
		try
		{
			List<Map<String, Object>> promotionList = controler.getList(promotionFacade, promotionData);
			return promotionList;
		}
		catch (FacadeException e)
		{
			LOG.error(e.getMessage(), e);
			throw new ServiceException("Problem While retrieving data", e.getStatusCode());
		}
	}

	@POST
	@Path("/profileDetails/{profileId}")
	public Map<String, Object> profileDetails(@PathParam("profileId") String profileId)
	{
		try
		{
			Long id = Long.valueOf(profileId);
			Map<String, Object> profileData = controler.getDetails(profileFacade, id);
			return profileData;
		}
		catch (FacadeException e)
		{
			LOG.error(e.getMessage(), e);
			throw new ServiceException("Problem While retrieving data", e.getStatusCode());
		}

	}

	@POST
	@Path("/purchaseDetails/{purchaseId}")
	public Map<String, Object> purchaseDetails(@PathParam("purchaseId") String purchaseId)
	{
		try
		{
			Long id = Long.valueOf(purchaseId);
			Map<String, Object> purchaseData = controler.getDetails(purchaseFacade, id);
			return purchaseData;
		}
		catch (FacadeException e)
		{
			LOG.error(e.getMessage(), e);
			throw new ServiceException("Problem While retrieving data", e.getStatusCode());
		}

	}

	@POST
	@Path("/agentDetails/{agentId}")
	public Map<String, Object> agentDetails(@PathParam("agentId") String agentId)
	{
		try
		{
			Long id = Long.valueOf(agentId);
			Map<String, Object> agentData = controler.getDetails(agentFacade, id);
			return agentData;
		}
		catch (FacadeException e)
		{
			LOG.error(e.getMessage(), e);
			throw new ServiceException("Problem While retrieving data", e.getStatusCode());
		}

	}

	@POST
	@Path("/branchDetails/{branchId}")
	public Map<String, Object> branchDetails(@PathParam("branchId") String branchId)
	{
		try
		{
			Long id = Long.valueOf(branchId);
			Map<String, Object> branchData = controler.getDetails(branchFacade, id);
			return branchData;
		}
		catch (FacadeException e)
		{
			LOG.error(e.getMessage(), e);
			throw new ServiceException("Problem While retrieving data", e.getStatusCode());
		}

	}

	@POST
	@Path("contractDetails/{contractId}")
	public Map<String, Object> contractDetails(@PathParam("contractId") String contractId)
	{
		try
		{
			Long id = Long.valueOf(contractId);
			Map<String, Object> contractData = controler.getDetails(contractFacade, id);
			return contractData;
		}
		catch (FacadeException e)
		{
			LOG.error(e.getMessage(), e);
			throw new ServiceException("Problem While retrieving data", e.getStatusCode());
		}

	}

	@POST
	@Path("productDetails/{productId}")
	public Map<String, Object> productDetails(@PathParam("productId") String productId)
	{
		try
		{
			Long id = Long.valueOf(productId);
			Map<String, Object> productData = controler.getDetails(productFacade, id);
			return productData;
		}
		catch (FacadeException e)
		{
			LOG.error(e.getMessage(), e);
			throw new ServiceException("Problem While retrieving data", e.getStatusCode());
		}

	}

	@POST
	@Path("promotionDetails/{promotionId}")
	public Map<String, Object> promotionDetails(@PathParam("promotionId") String promotionId)
	{
		try
		{
			Long id = Long.valueOf(promotionId);
			Map<String, Object> promotionData = controler.getDetails(promotionFacade, id);
			return promotionData;
		}
		catch (FacadeException e)
		{
			LOG.error(e.getMessage(), e);
			throw new ServiceException("Problem While retrieving data", e.getStatusCode());
		}

	}

	@POST
	@Path("invoiceDetails/{invoiceId}")
	public Map<String, Object> invoiceDetails(@PathParam("invoiceId") String invoiceId)
	{
		try
		{
			Long id = Long.valueOf(invoiceId);
			Map<String, Object> invoiceData = controler.getDetails(sysInvoiceFacade, id);
			return invoiceData;
		}
		catch (FacadeException e)
		{
			LOG.error(e.getMessage(), e);
			throw new ServiceException("Problem While retrieving data", e.getStatusCode());
		}

	}

	@POST
	@Path("paymentDetails/{paymentId}")
	public Map<String, Object> paymentDetails(@PathParam("paymentId") String paymentId)
	{
		try
		{
			Long id = Long.valueOf(paymentId);
			Map<String, Object> paymentData = controler.getDetails(sysPaymentFacade, id);
			return paymentData;
		}
		catch (FacadeException e)
		{
			LOG.error(e.getMessage(), e);
			throw new ServiceException("Problem While retrieving data", e.getStatusCode());
		}

	}

	@POST
	@Path("messageDetails/{messageId}")
	public Map<String, Object> messageDetails(@PathParam("messageId") String messageId)
	{
		try
		{
			Long id = Long.valueOf(messageId);
			Map<String, Object> messageData = controler.getDetails(messageCenterFacade, id);
			return messageData;
		}
		catch (FacadeException e)
		{
			LOG.error(e.getMessage(), e);
			throw new ServiceException("Problem While retrieving data", e.getStatusCode());
		}

	}

	@POST
	@Path("/deleteProfile/{profileId}")
	public void deleteProfile(@PathParam("profileId") String profileId)
	{
		try
		{
			Long id = Long.valueOf(profileId);
			controler.delete(profileFacade, id);
		}
		catch (FacadeException e)
		{
			LOG.error(e.getMessage(), e);
			throw new ServiceException("Problem While retrieving data", e.getStatusCode());
		}
	}

	@POST
	@Path("/deleteAgent/{agentId}")
	public void deleteAgent(@PathParam("agentId") String agentId)
	{
		try
		{
			Long id = Long.valueOf(agentId);
			controler.delete(agentFacade, id);
		}
		catch (FacadeException e)
		{
			LOG.error(e.getMessage(), e);
			throw new ServiceException("Problem While retrieving data", e.getStatusCode());
		}
	}

	@POST
	@Path("/deleteContract/{contractId}")
	public void deleteContract(@PathParam("contractId") String contractId)
	{
		try
		{
			Long id = Long.valueOf(contractId);
			controler.delete(contractFacade, id);
		}
		catch (FacadeException e)
		{
			LOG.error(e.getMessage(), e);
			throw new ServiceException("Problem While retrieving data", e.getStatusCode());
		}
	}

	@POST
	@VerifyToken
	@Path("updateSysEmployeeRole/{profileId}/{roleId}")
	public void updateSysEmployeeRole(@PathParam("profileId") String profileId, @PathParam("roleId") String roleId)
	{
		try
		{
			Map<String, Object> profileData = new HashMap<>();
			profileData.put(EntityConstants.Profile.profileId, Long.parseLong(profileId));
			profileData.put(EntityConstants.Profile.roleId, Integer.parseInt(roleId));
			controler.update(profileFacade, profileData);
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
	@VerifyToken
	@Path("getSumProfilePoints/{profileId}")
	public int getSumProfilePoints(@PathParam("profileId") String profileId)
	{
		try
		{
			Long id = Long.valueOf(profileId);
			int sumProfilePoints = controler.getSumProfilePoints(pointFacade, id);
			return sumProfilePoints;
		}
		catch (FacadeException e)
		{
			LOG.error(e.getMessage(), e);
			throw new ServiceException("Problem While retrieving data", e.getStatusCode());
		}
	}

	@POST
	@VerifyToken
	@Path("/userGainedPointsList/{profileId}")
	public List<Map<String, Object>> userGainedPointsList(@PathParam("profileId") String profileId, Map<String, Object> pointsData)
	{
		try
		{
			Long id = Long.valueOf(profileId);
			List<Map<String, Object>> pointsList = controler.userGainedPointsList(pointFacade, id, pointsData);
			return pointsList;
		}
		catch (FacadeException e)
		{
			LOG.error(e.getMessage(), e);
			throw new ServiceException("Problem While retrieving data", e.getStatusCode());
		}
	}

	@POST
	@VerifyToken
	@Path("/userReleasedPointsList/{profileId}")
	public List<Map<String, Object>> userReleasedPointsList(@PathParam("profileId") String profileId, Map<String, Object> pointsData)
	{
		try
		{
			Long id = Long.valueOf(profileId);
			List<Map<String, Object>> pointsList = controler.userReleasedPointsList(pointFacade, id, pointsData);
			return pointsList;
		}
		catch (FacadeException e)
		{
			LOG.error(e.getMessage(), e);
			throw new ServiceException("Problem While retrieving data", e.getStatusCode());
		}
	}

	@POST
	@VerifyToken
	@Path("/userPointsList/{profileId}")
	public List<Map<String, Object>> userPointsList(@PathParam("profileId") String profileId, Map<String, Object> pointsData)
	{
		try
		{
			Long id = Long.valueOf(profileId);
			pointsData.put(EntityConstants.Point.profileId, id);
			List<Map<String, Object>> pointsList = controler.getList(pointFacade, pointsData);
			return pointsList;
		}
		catch (FacadeException e)
		{
			LOG.error(e.getMessage(), e);
			throw new ServiceException("Problem While retrieving data", e.getStatusCode());
		}
	}

}
