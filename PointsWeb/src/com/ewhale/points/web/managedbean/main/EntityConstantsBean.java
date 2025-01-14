package com.ewhale.points.web.managedbean.main;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import com.ewhale.points.common.util.AppConstants;
import com.ewhale.points.common.util.EntityConstants;

@ManagedBean
@ApplicationScoped
public class EntityConstantsBean implements EntityConstants
{

	private ChangeTracking changeTracking = new ChangeTracking();

	private Agent agent = new Agent();

	private AgentRate agentRate = new AgentRate();

	private Branch branch = new Branch();

	private Category category = new Category();

	private Contract contract = new Contract();

	private Country country = new Country();

	private Currency currency = new Currency();

	private District district = new District();

	private FunctionType functionType = new FunctionType();

	private ItemStatus itemStatus = new ItemStatus();

	private MessageCenter messageCenter = new MessageCenter();

	private PaymentMethod paymentMethod = new PaymentMethod();

	private Point point = new Point();

	private PointsExchange pointsExchange = new PointsExchange();

	private Product product = new Product();

	private Profile profile = new Profile();

	private Promotion promotion = new Promotion();

	private Purchase purchase = new Purchase();

	private Question question = new Question();

	private Role role = new Role();

	private State state = new State();

	private Status status = new Status();

	private SysEvent sysEvent = new SysEvent();

	private SysInvoice sysInvoice = new SysInvoice();

	private SysPayment sysPayment = new SysPayment();

	private Login login = new Login();

	public static class ChangeTracking
	{
		public String getInsertDate()
		{
			return EntityConstants.ChangeTracking.insertDate;
		}

		public String getInsEmpId()
		{
			return EntityConstants.ChangeTracking.insEmpId;
		}

		public String getInsEmp()
		{
			return EntityConstants.ChangeTracking.insEmp;
		}

		public String getLastUpdDate()
		{
			return EntityConstants.ChangeTracking.lastUpdDate;
		}

		public String getUpdateEmpId()
		{
			return EntityConstants.ChangeTracking.updateEmpId;
		}

		public String getUpdateEmp()
		{
			return EntityConstants.ChangeTracking.updateEmp;
		}

	}

	public static class Agent extends ItemStatus
	{
		public String getAgentId()
		{
			return EntityConstants.Agent.agentId;
		}

		public String getTradeMark()
		{
			return EntityConstants.Agent.tradeMark;
		}

		public String getLogo()
		{
			return EntityConstants.Agent.logo;
		}

		public String getAdministrationPhone()
		{
			return EntityConstants.Agent.administrationPhone;
		}

		public String getAdministrationAddress()
		{
			return EntityConstants.Agent.administrationAddress;
		}

		public String getCallCenter()
		{
			return EntityConstants.Agent.callCenter;
		}

		public String getCommercialRecordNo()
		{
			return EntityConstants.Agent.commercialRecordNo;
		}

		public String getStateId()
		{
			return EntityConstants.Agent.stateId;
		}

		public String getCountryId()
		{
			return EntityConstants.Agent.countryId;
		}

		public String getCategoryId()
		{
			return EntityConstants.Agent.categoryId;
		}

		public String getCategoryIds()
		{
			return EntityConstants.Agent.categoryIds;
		}

		public String getCategories()
		{
			return EntityConstants.Agent.categories;
		}

		public String getBranches()
		{
			return EntityConstants.Agent.branches;
		}

		public String getEmployees()
		{
			return EntityConstants.Agent.employees;
		}

		public String getLocationLatitude()
		{
			return EntityConstants.Agent.locationLatitude;
		}

		public String getLocationLatitude_From_Search()
		{
			return EntityConstants.Agent.locationLatitude_From_Search;
		}

		public String getLocationLatitude_To_Search()
		{
			return EntityConstants.Agent.locationLatitude_To_Search;
		}

		public String getLocationLongitude()
		{
			return EntityConstants.Agent.locationLongitude;
		}

		public String getLocationLongitude_From_Search()
		{
			return EntityConstants.Agent.locationLongitude_From_Search;
		}

		public String getLocationLongitude_To_Search()
		{
			return EntityConstants.Agent.locationLongitude_To_Search;
		}

		public String getContracts()
		{
			return EntityConstants.Agent.contracts;
		}

		public String getContractStartDate()
		{
			return EntityConstants.Agent.contractStartDate;
		}

		public String getContractStartDate_From_Search()
		{
			return EntityConstants.Agent.contractStartDate_From_Search;
		}

		public String getContractStartDate_To_Search()
		{
			return EntityConstants.Agent.contractStartDate_To_Search;
		}

		public String getContractEndDate()
		{
			return EntityConstants.Agent.contractEndDate;
		}

		public String getContractEndDate_From_Search()
		{
			return EntityConstants.Agent.contractEndDate_From_Search;
		}

		public String getContractEndDate_To_Search()
		{
			return EntityConstants.Agent.contractEndDate_To_Search;
		}

		public String getContractInsertDate()
		{
			return EntityConstants.Agent.contractInsertDate;
		}

		public String getNewly_Added_From_Date_Search()
		{
			return EntityConstants.Agent.newly_Added_From_Date_Search;
		}

	}

	public static class AgentRate
	{
		public String getAgentRateId()
		{
			return EntityConstants.AgentRate.agentRateId;
		}

		public String getRateValue()
		{
			return EntityConstants.AgentRate.rateValue;
		}

		public String getUserComment()
		{
			return EntityConstants.AgentRate.userComment;
		}

		public String getAgentId()
		{
			return EntityConstants.AgentRate.agentId;
		}

		public String getProfileId()
		{
			return EntityConstants.AgentRate.profileId;
		}
	}

	public static class Branch
	{
		public String getBranchId()
		{
			return EntityConstants.Branch.branchId;
		}

		public String getBranchName()
		{
			return EntityConstants.Branch.branchName;
		}

		public String getTel()
		{
			return EntityConstants.Branch.tel;
		}

		public String getAddress()
		{
			return EntityConstants.Branch.address;
		}

		public String getLocationLongitude()
		{
			return EntityConstants.Branch.locationLongitude;
		}

		public String getLocationLatitude()
		{
			return EntityConstants.Branch.locationLatitude;
		}

		public String getAgentId()
		{
			return EntityConstants.Branch.agentId;
		}

		public String getStateId()
		{
			return EntityConstants.Branch.stateId;
		}

		public String getCountryId()
		{
			return EntityConstants.Branch.countryId;
		}

		public String getLocationLongitude_From_Search()
		{
			return EntityConstants.Branch.locationLongitude_From_Search;
		}

		public String getLocationLongitude_To_Search()
		{
			return EntityConstants.Branch.locationLongitude_To_Search;
		}

		public String getLocationLatitude_From_Search()
		{
			return EntityConstants.Branch.locationLatitude_From_Search;
		}

		public String getLocationLatitude_To_Search()
		{
			return EntityConstants.Branch.locationLatitude_To_Search;
		}

		public String getState()
		{
			return EntityConstants.Branch.state;
		}

		public String getCountry()
		{
			return EntityConstants.Branch.country;
		}

		public String getAgent()
		{
			return EntityConstants.Branch.agent;
		}
	}

	public static class Category
	{
		public String getCategoryId()
		{
			return EntityConstants.Category.categoryId;
		}

		public String getCategoryName()
		{
			return EntityConstants.Category.categoryName;
		}

		public String getCategoryIds()
		{
			return EntityConstants.Category.categoryIds;
		}
	}

	public static class Contract extends ItemStatus
	{
		public String getContractId()
		{
			return EntityConstants.Contract.contractId;
		}

		public String getAgentId()
		{
			return EntityConstants.Contract.agentId;
		}

		public String getDiscountPercent()
		{
			return EntityConstants.Contract.discountPercent;
		}

		public String getPointsPercent()
		{
			return EntityConstants.Contract.pointsPercent;
		}

		public String getProfitPercent()
		{
			return EntityConstants.Contract.profitPercent;
		}

		public String getStartDate()
		{
			return EntityConstants.Contract.startDate;
		}

		public String getEndDate()
		{
			return EntityConstants.Contract.endDate;
		}

		public String getCurrencyId()
		{
			return EntityConstants.Contract.currencyId;
		}

		public String getInsEmpId()
		{
			return EntityConstants.Contract.insEmpId;
		}

		public String getInsertDate()
		{
			return EntityConstants.Contract.insertDate;
		}

		public String getUpdateEmpId()
		{
			return EntityConstants.Contract.updateEmpId;
		}

		public String getUpdateDate()
		{
			return EntityConstants.Contract.updateDate;
		}

		public String getCountryId()
		{
			return EntityConstants.Contract.countryId;
		}

		public String getStartDate_From_Search()
		{
			return EntityConstants.Contract.startDate_From_Search;
		}

		public String getStartDate_To_Search()
		{
			return EntityConstants.Contract.startDate_To_Search;
		}

		public String getEndDate_From_Search()
		{
			return EntityConstants.Contract.endDate_From_Search;
		}

		public String getEndDate_To_Search()
		{
			return EntityConstants.Contract.endDate_To_Search;
		}

		public String getAgent()
		{
			return EntityConstants.Contract.agent;
		}

		public String getCountry()
		{
			return EntityConstants.Contract.country;
		}

		public String getCurrency()
		{
			return EntityConstants.Contract.currency;
		}
	}

	public static class Country
	{
		public String getCountryId()
		{
			return EntityConstants.Country.countryId;
		}

		public String getCountryName()
		{
			return EntityConstants.Country.countryName;
		}

		public String getCurrencyId()
		{
			return EntityConstants.Country.currencyId;
		}

		public String getCurrency()
		{
			return EntityConstants.Country.currency;
		}

	}

	public static class Currency
	{
		public String getCurrencyId()
		{
			return EntityConstants.Currency.currencyId;
		}

		public String getCurrencyName()
		{
			return EntityConstants.Currency.currencyName;
		}

		public String getValueEgp()
		{
			return EntityConstants.Currency.valueEgp;
		}

		public String getPointsValue()
		{
			return EntityConstants.Currency.pointsValue;
		}

		public String getCurrencySign()
		{
			return EntityConstants.Currency.currencySign;
		}
	}

	public static class District
	{
		public String getDistrictId()
		{
			return EntityConstants.District.districtId;
		}

		public String getDistrictName()
		{
			return EntityConstants.District.districtName;
		}

		public String getStateId()
		{
			return EntityConstants.District.stateId;
		}

		public String getCountryId()
		{
			return EntityConstants.District.countryId;
		}

		public String getState()
		{
			return EntityConstants.District.state;
		}

		public String getCountry()
		{
			return EntityConstants.District.country;
		}
	}

	public static class FunctionType
	{
		public String getFunctionTypeId()
		{
			return EntityConstants.FunctionType.functionTypeId;
		}

		public String getFunctionTypeName()
		{
			return EntityConstants.FunctionType.functionTypeName;
		}

		public short getActivateProductFunctionId()
		{
			return EntityConstants.FunctionType.Fixed.activateProductFunction.ID;
		}

		public short getActivatePromotionFunctionId()
		{
			return EntityConstants.FunctionType.Fixed.activatePromotionFunction.ID;
		}

	}

	public static class ItemStatus
	{
		public String getItemId()
		{
			return EntityConstants.ItemStatus.itemId;
		}

		public String getStatusId()
		{
			return EntityConstants.ItemStatus.statusId;
		}

		public String getStatusDate()
		{
			return EntityConstants.ItemStatus.statusDate;
		}

		public String getUpdateStatusEmpId()
		{
			return EntityConstants.ItemStatus.updateStatusEmpId;
		}

		public String getStatus()
		{
			return EntityConstants.ItemStatus.status;
		}

		public String getUpdateStatusEmp()
		{
			return EntityConstants.ItemStatus.updateStatusEmp;
		}

		public String getStatusName()
		{
			return EntityConstants.ItemStatus.statusName;
		}
	}

	public static class MessageCenter
	{
		public String getMessageCenterId()
		{
			return EntityConstants.MessageCenter.messageCenterId;
		}

		public String getRequestMessage()
		{
			return EntityConstants.MessageCenter.requestMessage;
		}

		public String getResponseMessage()
		{
			return EntityConstants.MessageCenter.responseMessage;
		}

		public String getRequestDate()
		{
			return EntityConstants.MessageCenter.requestDate;
		}

		public String getResponseDate()
		{
			return EntityConstants.MessageCenter.responseDate;
		}

		public String getAgentId()
		{
			return EntityConstants.MessageCenter.agentId;
		}

		public String getRequestAgentEmpId()
		{
			return EntityConstants.MessageCenter.requestAgentEmpId;
		}

		public String getResponseEmpId()
		{
			return EntityConstants.MessageCenter.responseEmpId;
		}

		public String getItemId()
		{
			return EntityConstants.MessageCenter.itemId;
		}

		public String getFunctionTypeId()
		{
			return EntityConstants.MessageCenter.functionTypeId;
		}

		public String getRequestDate_From_Search_NAME()
		{
			return EntityConstants.MessageCenter.requestDate_From_Search_NAME;
		}

		public String getRequestDate_To_Search_NAME()
		{
			return EntityConstants.MessageCenter.requestDate_To_Search_NAME;
		}

		public String getResponseDate_From_Search_NAME()
		{
			return EntityConstants.MessageCenter.responseDate_From_Search_NAME;
		}

		public String getResponseDate_To_Search_NAME()
		{
			return EntityConstants.MessageCenter.responseDate_To_Search_NAME;
		}

		public String getNot_responded_messages_Search()
		{
			return EntityConstants.MessageCenter.not_responded_messages_Search;
		}

		public String getResponded_messages_Search()
		{
			return EntityConstants.MessageCenter.responded_messages_Search;
		}

		public String getAgent()
		{
			return EntityConstants.MessageCenter.agent;
		}
	}

	public static class PaymentMethod
	{
		public String getPaymentMethodId()
		{
			return EntityConstants.PaymentMethod.paymentMethodId;
		}

		public String getPaymentMethodName()
		{
			return EntityConstants.PaymentMethod.paymentMethodName;
		}
	}

	public static class Point
	{
		public String getPointsId()
		{
			return EntityConstants.Point.pointsId;
		}

		public String getPointsValue()
		{
			return EntityConstants.Point.pointsValue;
		}

		public String getEventId()
		{
			return EntityConstants.Point.eventId;
		}

		public String getTxnType()
		{
			return EntityConstants.Point.txnType;
		}

		public String getProfileId()
		{
			return EntityConstants.Point.profileId;
		}

		public String getInsertDate()
		{
			return EntityConstants.Point.insertDate;
		}

		public String getInsEmpId()
		{
			return EntityConstants.Point.insEmpId;
		}

		public String getProfile()
		{
			return EntityConstants.Point.profile;
		}

		public String getSysEvent()
		{
			return EntityConstants.Point.sysEvent;
		}

		public String getPointsValue_From_Search()
		{
			return EntityConstants.Point.pointsValue_From_Search;
		}

		public String getPointsValue_To_Search()
		{
			return EntityConstants.Point.pointsValue_To_Search;
		}

		public String getInsertDate_From_Search()
		{
			return EntityConstants.Point.insertDate_From_Search;
		}

		public String getInsertDate_To_Search()
		{
			return EntityConstants.Point.insertDate_To_Search;
		}

		public String getProfile_mobile()
		{
			return EntityConstants.Point.profile_mobile;
		}

	}

	public static class PointsExchange extends Point
	{
		public String getPointsExchangeId()
		{
			return EntityConstants.PointsExchange.pointsExchangeId;
		}

		public String getPriceValue()
		{
			return EntityConstants.PointsExchange.priceValue;
		}

		public String getPointsValue()
		{
			return EntityConstants.PointsExchange.pointsValue;
		}

		public String getTxnType()
		{
			return EntityConstants.PointsExchange.txnType;
		}

		public String getExchange()
		{
			return EntityConstants.PointsExchange.exchange;
		}

		public String getProductId()
		{
			return EntityConstants.PointsExchange.productId;
		}

		public String getInsertDate()
		{
			return EntityConstants.PointsExchange.insertDate;
		}

		public String getBranchId()
		{
			return EntityConstants.PointsExchange.branchId;
		}

		public String getInvoiceId()
		{
			return EntityConstants.PointsExchange.invoiceId;
		}

		public String getCurrencyId()
		{
			return EntityConstants.PointsExchange.currencyId;
		}

		public String getInsertDate_From_Search()
		{
			return EntityConstants.PointsExchange.insertDate_From_Search;
		}

		public String getInsertDate_To_Search()
		{
			return EntityConstants.PointsExchange.insertDate_To_Search;
		}

		public String getAgentId()
		{
			return EntityConstants.PointsExchange.agentId;
		}

		public String getBranch()
		{
			return EntityConstants.PointsExchange.branch;
		}

		public String getProduct()
		{
			return EntityConstants.PointsExchange.product;
		}

		public String getSysInvoice()
		{
			return EntityConstants.PointsExchange.sysInvoice;
		}
	}

	public static class Product extends ItemStatus
	{
		public String getProductId()
		{
			return EntityConstants.Product.productId;
		}

		public String getProductName()
		{
			return EntityConstants.Product.productName;
		}

		public String getPrice()
		{
			return EntityConstants.Product.price;
		}

		public String getValidityDate()
		{
			return EntityConstants.Product.validityDate;
		}

		public String getPhoto()
		{
			return EntityConstants.Product.photo;
		}

		public String getAgentId()
		{
			return EntityConstants.Product.agentId;
		}

		public String getCountryId()
		{
			return EntityConstants.Product.countryId;
		}

		public String getCurrencyId()
		{
			return EntityConstants.Product.currencyId;
		}

		public String getInsEmpId()
		{
			return EntityConstants.Product.insEmpId;
		}

		public String getInsertDate()
		{
			return EntityConstants.Product.insertDate;
		}

		public String getUpdateEmpId()
		{
			return EntityConstants.Product.updateEmpId;
		}

		public String getLastUpdDate()
		{
			return EntityConstants.Product.lastUpdDate;
		}

		public String getValidityDate_From_Search()
		{
			return EntityConstants.Product.validityDate_From_Search;
		}

		public String getValidityDate_To_Search()
		{
			return EntityConstants.Product.validityDate_To_Search;
		}

		public String getCurrency()
		{
			return EntityConstants.Product.currency;
		}

		public String getCountry()
		{
			return EntityConstants.Product.country;
		}

		public String getAgent()
		{
			return EntityConstants.Product.agent;
		}
		public String getPointsValue()
		{
			return EntityConstants.Product.pointsValue;
		}
	}

	public static class Profile extends Login
	{
		public String getProfileId()
		{
			return EntityConstants.Profile.profileId;
		}

		public String getFirstName()
		{
			return EntityConstants.Profile.firstName;
		}

		public String getMidName()
		{
			return EntityConstants.Profile.midName;
		}

		public String getLastName()
		{
			return EntityConstants.Profile.lastName;
		}

		public String getPassword()
		{
			return EntityConstants.Profile.password;
		}

		public String getEmail()
		{
			return EntityConstants.Profile.email;
		}

		public String getAddress()
		{
			return EntityConstants.Profile.address;
		}

		public String getTel()
		{
			return EntityConstants.Profile.tel;
		}

		public String getMobile()
		{
			return EntityConstants.Profile.mobile;
		}

		public String getInsertDate()
		{
			return EntityConstants.Profile.insertDate;
		}

		public String getBirthDate()
		{
			return EntityConstants.Profile.birthDate;
		}

		public String getAgentId()
		{
			return EntityConstants.Profile.agentId;
		}

		public String getRoleId()
		{
			return EntityConstants.Profile.roleId;
		}

		public String getDistrictId()
		{
			return EntityConstants.Profile.districtId;
		}

		public String getStateId()
		{
			return EntityConstants.Profile.stateId;
		}

		public String getCountryId()
		{
			return EntityConstants.Profile.countryId;
		}

		public String getInsertDate_From_Search()
		{
			return EntityConstants.Profile.insertDate_From_Search;
		}

		public String getInsertDate_To_Search()
		{
			return EntityConstants.Profile.insertDate_To_Search;
		}

		public String getRole()
		{
			return EntityConstants.Profile.role;
		}

		public String getAgent()
		{
			return EntityConstants.Profile.agent;
		}
	}

	public static class Promotion extends ItemStatus
	{
		public String getPromotionId()
		{
			return EntityConstants.Promotion.promotionId;
		}

		public String getMessage()
		{
			return EntityConstants.Promotion.message;
		}

		public String getMessageDetails()
		{
			return EntityConstants.Promotion.messageDetails;
		}

		public String getImage()
		{
			return EntityConstants.Promotion.image;
		}

		public String getPromotionFees()
		{
			return EntityConstants.Promotion.promotionFees;
		}

		public String getPromotionDate()
		{
			return EntityConstants.Promotion.promotionDate;
		}

		public String getAgentId()
		{
			return EntityConstants.Promotion.agentId;
		}

		public String getCurrencyId()
		{
			return EntityConstants.Promotion.currencyId;
		}

		public String getInvoiceId()
		{
			return EntityConstants.Promotion.invoiceId;
		}

		public String getInsEmpId()
		{
			return EntityConstants.Promotion.insEmpId;
		}

		public String getInsertDate()
		{
			return EntityConstants.Promotion.insertDate;
		}

		public String getUpdateEmpId()
		{
			return EntityConstants.Promotion.updateEmpId;
		}

		public String getLastUpdDate()
		{
			return EntityConstants.Promotion.lastUpdDate;
		}

		public String getPromotionDate_From_Search()
		{
			return EntityConstants.Promotion.promotionDate_From_Search;
		}

		public String getPromotionDate_To_Search()
		{
			return EntityConstants.Promotion.promotionDate_To_Search;
		}

		public String getSysInvoice()
		{
			return EntityConstants.Promotion.sysInvoice;
		}

		public String getAgent()
		{
			return EntityConstants.Promotion.agent;
		}

		public String getCurrency()
		{
			return EntityConstants.Promotion.currency;
		}
	}

	public static class Purchase extends Point
	{
		public String getPurchaseId()
		{
			return EntityConstants.Purchase.purchaseId;
		}

		public String getAgentInvoiceNumber()
		{
			return EntityConstants.Purchase.agentInvoiceNumber;
		}

		public String getAgentInvoiceValue()
		{
			return EntityConstants.Purchase.agentInvoiceValue;
		}

		public String getDiscPercent()
		{
			return EntityConstants.Purchase.discPercent;
		}

		public String getPointsValue()
		{
			return EntityConstants.Purchase.pointsValue;
		}

		public String getProfitValue()
		{
			return EntityConstants.Purchase.profitValue;
		}

		public String getInsertDate()
		{
			return EntityConstants.Purchase.insertDate;
		}

		public String getInsEmpId()
		{
			return EntityConstants.Purchase.insEmpId;
		}

		public String getAgent()
		{
			return EntityConstants.Purchase.agent;
		}

		public String getProfileId()
		{
			return EntityConstants.Purchase.profileId;
		}

		public String getAgentId()
		{
			return EntityConstants.Purchase.agentId;
		}

		public String getInvoiceId()
		{
			return EntityConstants.Purchase.invoiceId;
		}

		public String getBranchId()
		{
			return EntityConstants.Purchase.branchId;
		}

		public String getCurrencyId()
		{
			return EntityConstants.Purchase.currencyId;
		}

		public String getInsertDate_From_Search()
		{
			return EntityConstants.Purchase.insertDate_From_Search;
		}

		public String getInsertDate_To_Search()
		{
			return EntityConstants.Purchase.insertDate_To_Search;
		}

		public String getRefundOnly_Search()
		{
			return EntityConstants.Purchase.refundOnly_Search;
		}

		public String getFundOnly_Search()
		{
			return EntityConstants.Purchase.fundOnly_Search;
		}

		public String getProfile()
		{
			return EntityConstants.Purchase.profile;
		}

		public String getBranch()
		{
			return EntityConstants.Purchase.branch;
		}

		public String getProfile_mobile()
		{
			return EntityConstants.Purchase.profile_mobile;
		}

		public String getFund()
		{
			return EntityConstants.Purchase.fund;
		}

		public String getSysInvoice()
		{
			return EntityConstants.Purchase.sysInvoice;
		}
	}

	public static class Question
	{
		public String getQuestionId()
		{
			return EntityConstants.Question.questionId;
		}

		public String getQuestionName()
		{
			return EntityConstants.Question.questionName;
		}

	}

	public static class Role
	{
		public String getRoleId()
		{
			return EntityConstants.Role.roleId;
		}

		public String getRoleName()
		{
			return EntityConstants.Role.roleName;
		}

		public int getAgentAdminRoleId(){
			return EntityConstants.Role.Fixed.agentAdminRole.ID;
		}
		public int getAgentSellerRoleId(){
			return EntityConstants.Role.Fixed.agentSellerRole.ID;
		}
		public int getSystemAdminRoleId(){
			return EntityConstants.Role.Fixed.systemAdminRole.ID;
		}
		public int getSystemSalesRoleId(){
			return EntityConstants.Role.Fixed.systemSalesRole.ID;
		}
		public int getUserRoleId(){
			return EntityConstants.Role.Fixed.userRole.ID;
		}
	}

	public static class State
	{
		public String getStateId()
		{
			return EntityConstants.State.stateId;
		}

		public String getStateName()
		{
			return EntityConstants.State.stateName;
		}

		public String getCountryId()
		{
			return EntityConstants.State.countryId;
		}

		public String getCountry()
		{
			return EntityConstants.State.country;
		}

	}

	public static class Status
	{
		public String getStatusId()
		{
			return EntityConstants.Status.statusId;
		}

		public String getStatusName()
		{
			return EntityConstants.Status.statusName;
		}

		public short getActiveStatusId()
		{
			return EntityConstants.Status.Fixed.activeStatus.ID;
		}

		public short getPendingStatusId()
		{
			return EntityConstants.Status.Fixed.pendingStatus.ID;
		}

		public short getBlockStatusId()
		{
			return EntityConstants.Status.Fixed.blockedStatus.ID;
		}
	}

	public static class SysEvent
	{
		public String getSysEventId()
		{
			return EntityConstants.SysEvent.sysEventId;
		}

		public String getEventDesc()
		{
			return EntityConstants.SysEvent.eventDesc;
		}

		public String getPointsValue()
		{
			return EntityConstants.SysEvent.pointsValue;
		}

		public String getEventDate()
		{
			return EntityConstants.SysEvent.eventDate;
		}

		public String getPeriodic()
		{
			return EntityConstants.SysEvent.periodic;
		}

		public String getInsertDate()
		{
			return EntityConstants.SysEvent.insertDate;
		}

		public String getInsEmpId()
		{
			return EntityConstants.SysEvent.insEmpId;
		}

		public String getUpdateEmpId()
		{
			return EntityConstants.SysEvent.updateEmpId;
		}

		public String getLastUpdDate()
		{
			return EntityConstants.SysEvent.lastUpdDate;
		}

		public String getUpdateEmp()
		{
			return EntityConstants.SysEvent.updateEmp;
		}

		public String getInsEmp()
		{
			return EntityConstants.SysEvent.insEmp;
		}

		public String getEventDate_From_Search()
		{
			return EntityConstants.SysEvent.eventDate_From_Search;
		}

		public String getEventDate_To_Search()
		{
			return EntityConstants.SysEvent.eventDate_To_Search;
		}
	}

	public static class SysInvoice
	{
		public String getSysInvoiceId()
		{
			return EntityConstants.SysInvoice.sysInvoiceId;
		}

		public String getInvoiceValue()
		{
			return EntityConstants.SysInvoice.invoiceValue;
		}

		public String getInsertDate()
		{
			return EntityConstants.SysInvoice.insertDate;
		}

		public String getAgentId()
		{
			return EntityConstants.SysInvoice.agentId;
		}

		public String getCurrencyId()
		{
			return EntityConstants.SysInvoice.currencyId;
		}

		public String getInvoiceDate_From_Search()
		{
			return EntityConstants.SysInvoice.invoiceDate_From_Search;
		}

		public String getInvoiceDate_To_Search()
		{
			return EntityConstants.SysInvoice.invoiceDate_To_Search;
		}

		public String getActive()
		{
			return EntityConstants.SysInvoice.active;
		}

		public String getAgent()
		{
			return EntityConstants.SysInvoice.agent;
		}
	}

	public static class SysPayment extends ItemStatus
	{
		public String getSysPaymentId()
		{
			return EntityConstants.SysPayment.sysPaymentId;
		}

		public String getPaymentValue()
		{
			return EntityConstants.SysPayment.paymentValue;
		}

		public String getPaymentDate()
		{
			return EntityConstants.SysPayment.paymentDate;
		}

		public String getAgentId()
		{
			return EntityConstants.SysPayment.agentId;
		}

		public String getPaymentMethodId()
		{
			return EntityConstants.SysPayment.paymentMethodId;
		}

		public String getCurrencyId()
		{
			return EntityConstants.SysPayment.currencyId;
		}

		public String getInsertDate()
		{
			return EntityConstants.SysPayment.insertDate;
		}

		public String getInsEmpId()
		{
			return EntityConstants.SysPayment.insEmpId;
		}

		public String getPaymentDate_From_Search()
		{
			return EntityConstants.SysPayment.paymentDate_From_Search;
		}

		public String getPaymentDate_To_Search()
		{
			return EntityConstants.SysPayment.paymentDate_To_Search;
		}

		public String getInsertDate_From_Search()
		{
			return EntityConstants.SysPayment.insertDate_From_Search;
		}

		public String getInsertDate_To_Search()
		{
			return EntityConstants.SysPayment.insertDate_To_Search;
		}

		public String getAgent()
		{
			return EntityConstants.SysPayment.agent;
		}

		public String getPaymentMethod()
		{
			return EntityConstants.SysPayment.paymentMethod;
		}

		public String getInsEmp()
		{
			return EntityConstants.SysPayment.insEmp;
		}

	}

	public static class Login extends ItemStatus
	{
		public String getLoginId()
		{
			return EntityConstants.Login.loginId;
		}

		public String getProfileId()
		{
			return EntityConstants.Profile.profileId;
		}

		public String getPassword()
		{
			return EntityConstants.Login.password;
		}

		public String getNewPassword()
		{
			return EntityConstants.Login.newPassword;
		}

		public String getEncrypted()
		{
			return EntityConstants.Login.encrypted;
		}

		public String getRsaKeys()
		{
			return EntityConstants.Login.rsaKeys;
		}

		public String getToken()
		{
			return EntityConstants.Login.token;
		}

		public String getAgentId()
		{
			return EntityConstants.Login.agentId;
		}

		public String getBranchId()
		{
			return EntityConstants.Login.branchId;
		}

	}

	public ChangeTracking getChangeTracking()
	{
		return changeTracking;
	}

	public Agent getAgent()
	{
		return agent;
	}

	public AgentRate getAgentRate()
	{
		return agentRate;
	}

	public Branch getBranch()
	{
		return branch;
	}

	public Category getCategory()
	{
		return category;
	}

	public Contract getContract()
	{
		return contract;
	}

	public Country getCountry()
	{
		return country;
	}

	public Currency getCurrency()
	{
		return currency;
	}

	public District getDistrict()
	{
		return district;
	}

	public FunctionType getFunctionType()
	{
		return functionType;
	}

	public ItemStatus getItemStatus()
	{
		return itemStatus;
	}

	public MessageCenter getMessageCenter()
	{
		return messageCenter;
	}

	public PaymentMethod getPaymentMethod()
	{
		return paymentMethod;
	}

	public Point getPoint()
	{
		return point;
	}

	public PointsExchange getPointsExchange()
	{
		return pointsExchange;
	}

	public Product getProduct()
	{
		return product;
	}

	public Profile getProfile()
	{
		return profile;
	}

	public Promotion getPromotion()
	{
		return promotion;
	}

	public Purchase getPurchase()
	{
		return purchase;
	}

	public Question getQuestion()
	{
		return question;
	}

	public Role getRole()
	{
		return role;
	}

	public State getState()
	{
		return state;
	}

	public Status getStatus()
	{
		return status;
	}

	public SysEvent getSysEvent()
	{
		return sysEvent;
	}

	public SysInvoice getSysInvoice()
	{
		return sysInvoice;
	}

	public SysPayment getSysPayment()
	{
		return sysPayment;
	}

	public Login getLogin()
	{
		return login;
	}

	public String getDateFormat()
	{
		return AppConstants.dateFormatStr;
	}

	public String getDateTimeFormat()
	{
		return AppConstants.dateTimeFormatStr;
	}
}
