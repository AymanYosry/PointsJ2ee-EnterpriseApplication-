/**
 * 
 */
package com.ewhale.points.common.util;

/**
 * @author Ayman Yosry
 */
public interface EntityConstants
{
	public interface ChangeTracking
	{
		String insertDate = "insertDate";

		String insEmpId = "insEmpId";

		String insEmp = "insEmp";

		String insEmpFullName = "insEmpFullName", updateEmpFullName = "updateEmpFullName";

		String lastUpdDate = "lastUpdDate";

		String updateEmpId = "updateEmpId";

		String updateEmp = "updateEmp";
	}

	public interface Agent extends ItemStatus
	{
		String agentId = "agentId";

		String tradeMark = "tradeMark";

		String logo = "logo";

		String administrationPhone = "administrationPhone";

		String administrationAddress = "administrationAdderss";

		String callCenter = "callCenter";

		String commercialRecordNo = "commercialRecordNo";

		String ratesNumber = "ratesNumber", rateValue = "rateValue";

		String stateId = State.stateId;

		String countryId = Country.countryId;

		String categoryId = Category.categoryId;

		String categoryIds = "categoryIds";

		String categories = "categories";

		String country = "country";

		String locationLatitude = "locationLatitude";

		String locationLatitude_From_Search = "locationLatitude_From";

		String locationLatitude_To_Search = "locationLatitude_To";

		String locationLongitude = "locationLongitude";

		String locationLongitude_From_Search = "locationLongitude_From";

		String locationLongitude_To_Search = "locationLongitude_To";

		String employees = "employees", branches = "branches", contracts = "contracts", sysPayments = "sysPayments", products = "products",
				promotions = "promotions", sysInvoices = "sysInvoices", purchases = "purchases", pointsExchanges = "pointsExchanges";

		String contractStartDate = "startDate";

		String contractStartDate_From_Search = "contractStartDate_From";

		String contractStartDate_To_Search = "contractStartDate_To";

		String contractEndDate = "endDate";

		String contractEndDate_From_Search = "contractEndDate_From";

		String contractEndDate_To_Search = "contractEndDate_To";

		String contractInsertDate = ChangeTracking.insertDate;

		String newly_Added_From_Date_Search = "newlyAddedFromDate";

		String hasNoContract = "hasNoContract";

	}

	public interface AgentRate
	{
		String agentRateId = "agentRateId";

		String rateValue = "rateValue";

		String userComment = "userComment";

		String agentId = Agent.agentId;

		String profileId = Profile.profileId;
	}

	public interface Branch
	{
		String branchId = "branchId";

		String branchName = "branchName";

		String tel = "tel";

		String address = "address";

		String locationLongitude = "locationLongitude";

		String locationLatitude = "locationLatitude";

		String agentId = Agent.agentId;

		String stateId = State.stateId;

		String countryId = Country.countryId;

		String locationLongitude_From_Search = "locationLongitude_From";

		String locationLongitude_To_Search = "locationLongitude_To";

		String locationLatitude_From_Search = "locationLatitude_From";

		String locationLatitude_To_Search = "locationLatitude_To";

		String state = "state", country = "country";

		String agent = "agent";
	}

	public interface Category
	{
		String categoryId = "categoryId";

		String categoryName = "categoryName";

		String categoryIds = "categoryIds";
	}

	public interface Contract extends ItemStatus
	{
		String contractId = "contractId";

		String agentId = Agent.agentId;

		String discountPercent = "discountPercent";

		String pointsPercent = "pointsPercent";

		String profitPercent = "profitPercent";

		String startDate = "startDate";

		String endDate = "endDate";

		String currencyId = Currency.currencyId;

		String insEmpId = ChangeTracking.insEmpId;

		String insertDate = ChangeTracking.insertDate;

		String updateEmpId = ChangeTracking.updateEmpId;

		String updateEmpFullName = "updateEmpFullName";

		String updateDate = ChangeTracking.lastUpdDate;

		String countryId = Country.countryId;

		String startDate_From_Search = "startDate_From";

		String startDate_To_Search = "startDate_To";

		String endDate_From_Search = "endDate_From";

		String endDate_To_Search = "endDate_To";

		String agent = "agent";

		String country = "country";

		String currency = "currency";
	}

	public interface Country
	{
		String countryId = "countryId";

		String countryName = "countryName";

		String currencyId = Currency.currencyId;

		String currency = "currency";

		String states = "states";
	}

	public interface Currency
	{
		String currencyId = "currencyId";

		String currencyName = "currencyName";

		String valueEgp = "valueEgp";

		String pointsValue = "pointsValue";

		String currencySign = "currencySign";
	}

	public interface District
	{
		String districtId = "districtId";

		String districtName = "districtName";

		String stateId = State.stateId;

		String countryId = Country.countryId;

		String state = "state", country = "country";
		// String primaryKey = "id";
	}

	public interface FunctionType
	{
		String functionTypeId = "functionTypeId";

		String functionTypeName = "functionTypeName";

		// fixed FunctionType in the system
		enum Fixed
		{
			activateProductFunction((short) 1, "activateProduct"), activatePromotionFunction((short) 2, "activatePromotion");
			public short ID;

			public String VALUE;

			Fixed(short id, String value)
			{
				this.ID = id;
				this.VALUE = value;
			}
		}
	}

	public interface ItemStatus
	{
		String itemId = "itemId";

		String statusId = Status.statusId;

		String statusDate = "statusDate";

		String updateStatusEmpId = "updateStatusEmpId";

		String updateStatusEmpFullName = "updateStatusEmpFullName";

		String status = "status";

		String updateStatusEmp = "updateStatusEmp";

		String statusName = "statusName";
	}

	public interface MessageCenter
	{
		String messageCenterId = "messageCenterId";

		String requestMessage = "requestMessage";

		String responseMessage = "responseMessage";

		String requestDate = "requestDate";

		String responseDate = "responseDate";

		String agentId = Agent.agentId;

		String requestAgentEmpId = "requestAgentEmpId";

		String responseEmpId = "responseEmpId";

		String itemId = "itemId";

		String functionTypeId = FunctionType.functionTypeId;

		String requestDate_From_Search_NAME = "requestDate_From";

		String requestDate_To_Search_NAME = "requestDate_To";

		String responseDate_From_Search_NAME = "responseDate_From";

		String responseDate_To_Search_NAME = "responseDate_To";

		String not_responded_messages_Search = "not_responded_messages ";

		String responded_messages_Search = "responded_messages ";

		String agent = "agent", requestAgentEmp = "requestAgentEmp", responseEmp = "responseEmp", functionType = "functionType";

		String responseEmpFullName = "responseEmpFullName", requestAgentEmpFullName = "requestAgentEmpFullName";
	}

	public interface PaymentMethod
	{
		String paymentMethodId = "paymentMethodId";

		String paymentMethodName = "paymentMethodName";

		enum Fixed
		{
			cashPayment((short) 1, "Cash");
			public short ID;

			public String VALUE;

			Fixed(short id, String value)
			{
				this.ID = id;
				this.VALUE = value;
			}
		}
	}

	public interface Point
	{
		String pointsId = "pointsId";

		String pointsValue = "pointsValue";

		String eventId = SysEvent.sysEventId;

		String txnType = "txnType";

		String profileId = Profile.profileId;

		String insertDate = "insertDate";

		String insEmpId = "insEmpId";

		String profile = "pointsProfile", sysEvent = "sysEvent";

		String pointsValue_From_Search = "pointsValue_From";

		String pointsValue_To_Search = "pointsValue_To";

		String insertDate_From_Search = "insertDate_From";

		String insertDate_To_Search = "insertDate_To";

		String profile_mobile = Profile.mobile;

	}

	public interface PointsExchange extends Point
	{
		String pointsExchangeId = "pointsExchangeId", lastPointsExchangeId = "lastPointsExchangeId";

		String priceValue = "priceValue";

		String pointsValue = "pointsValue";

		String txnType = Point.txnType;

		String exchange = "exchange";

		String productId = Product.productId;

		String insertDate = "insertDate";

		String branchId = Branch.branchId;

		String invoiceId = SysInvoice.sysInvoiceId;

		String currencyId = Currency.currencyId;

		String insertDate_From_Search = "insertDate_From";

		String insertDate_To_Search = "insertDate_To";

		String agentId = Agent.agentId;

		String branch = "branch", product = "product", sysInvoice = "sysInvoice", agent = "agent";
	}

	public interface Product extends ItemStatus
	{
		String productId = "productId";

		String productName = "productName";

		String price = "price";

		String pointsValue = "pointsValue";

		String validityDate = "validityDate";

		String photo = "photo";

		String agentId = Agent.agentId;

		String countryId = Country.countryId;

		String currencyId = Currency.currencyId;

		String insEmpId = ChangeTracking.insEmpId;

		String insertDate = ChangeTracking.insertDate;

		String updateEmpId = ChangeTracking.updateEmpId;

		String lastUpdDate = ChangeTracking.lastUpdDate;

		String validityDate_From_Search = "validityDate_From";

		String validityDate_To_Search = "validityDate_To";

		String currency = "currency", country = "country", agent = "agent";

		String pointsValue_Max = "pointsValueMax", pointsValue_Min = "pointsValueMin";
	}

	public interface Profile extends Login
	{

		String profileId = "profileId";

		String firstName = "firstName";

		String midName = "midName";

		String lastName = "lastName";

		String fullName = "fullName";

		String mobile = "mobile";

		String password = Login.password;

		String email = "email";

		String address = "address";

		String tel = "tel";

		String gender = "gender";

		String insertDate = ChangeTracking.insertDate;

		String birthDate = "birthDate";

		String agentId = Agent.agentId;

		String roleId = Role.roleId;

		String districtId = District.districtId;

		String stateId = State.stateId;

		String countryId = Country.countryId;

		String insertDate_From_Search = "insertDate_From";

		String insertDate_To_Search = "insertDate_To";

		String role = "role";

		String agent = "agent";

		String country = "country", state = "state", district = "district";

		String roleIds = "roleIds";

		String points = "points";

	}

	public interface Promotion extends ItemStatus
	{
		String promotionId = "promotionId";

		String message = "message";

		String messageDetails = "messageDetails";

		String image = "image";

		String promotionFees = "promotionFees";

		String promotionDate = "promotionDate";

		String agentId = Agent.agentId;

		String currencyId = Currency.currencyId;

		String countryId = Country.countryId;

		String invoiceId = SysInvoice.sysInvoiceId;

		String insEmpId = ChangeTracking.insEmpId;

		String insEmpFullName = ChangeTracking.insEmpFullName, updateEmpFullName = ChangeTracking.updateEmpFullName;

		String insertDate = ChangeTracking.insertDate;

		String updateEmpId = ChangeTracking.updateEmpId;

		String lastUpdDate = ChangeTracking.lastUpdDate;

		String promotionDate_From_Search = "promotionDate_From";

		String promotionDate_To_Search = "promotionDate_To";

		String sysInvoice = "sysInvoice", agent = "agent", currency = "currency", country = "country";

	}

	public interface Purchase extends Point
	{
		String purchaseId = "purchaseId", lastPurchaseId = "lastPurchaseId";

		String agentInvoiceNumber = "agentInvoiceNumber";

		String agentInvoiceValue = "agentInvoiceValue";

		String discPercent = "discPercent";

		String pointsValue = "pointsValue";

		String profitValue = "profitValue";

		String insertDate = "insertDate";

		String insEmpId = "insEmpId";

		String agent = "agent";

		String profileId = Profile.profileId;

		String agentId = Agent.agentId;

		String invoiceId = SysInvoice.sysInvoiceId;

		String branchId = Branch.branchId;

		String currencyId = Currency.currencyId;

		String insertDate_From_Search = "insertDate_From";

		String insertDate_To_Search = "insertDate_To";

		String refundOnly_Search = "Refund_Only";

		String fundOnly_Search = "Fund_Only";

		String profile = "profile";

		String branch = "branch";

		String profile_mobile = Profile.mobile;

		String fund = "fund";

		String sysInvoice = "sysInvoice";

		String qrCode = "QRCode";

	}

	public interface Question
	{
		String questionId = "questionId";

		String questionName = "questionName";
	}

	public interface Role
	{
		String roleId = "roleId";

		String roleName = "roleName";

		// fixed roles in the system
		enum Fixed
		{
			agentAdminRole(1, "Agent Admin"), agentSellerRole(2, "Agent Seller"), systemAdminRole(3, "System Admin"),
			systemSalesRole(4, "System Sales"), userRole(5, "User");
			public int ID;

			public String VALUE;

			Fixed(int id, String value)
			{
				this.ID = id;
				this.VALUE = value;
			}
		}
	}

	public interface State
	{
		String stateId = "stateId";

		String stateName = "stateName";

		String countryId = Country.countryId;

		String country = "country";

		String districts = "districts";
		// String primaryKey = "id";
	}

	public interface Status
	{
		String statusId = "statusId";

		String statusName = "statusName";

		enum Fixed
		{
			pendingStatus((short) 1, "Pending"), activeStatus((short) 2, "Active"), blockedStatus((short) 3, "Blocked");

			public final short ID;

			public final String VALUE;

			Fixed(short id, String value)
			{
				this.ID = id;
				this.VALUE = value;
			}
		}
	}

	public interface SysEvent
	{
		String sysEventId = "sysEventId";

		String eventDesc = "eventDesc";

		String pointsValue = "pointsValue";

		String eventDate = "eventDate";

		String periodic = "periodic";

		String insertDate = ChangeTracking.insertDate;

		String insEmpId = ChangeTracking.insEmpId;

		String updateEmpId = ChangeTracking.updateEmpId;

		String lastUpdDate = ChangeTracking.lastUpdDate;

		String updateEmp = ChangeTracking.updateEmp;

		String insEmp = ChangeTracking.insEmp;

		String eventDate_From_Search = "eventDate_From";

		String eventDate_To_Search = "eventDate_To";

		enum Fixed
		{
			fundPurchase((short) 1, "Fund Purchase"), refundPurchase((short) 2, "Refund Purchase"), productExchange((short) 3, "Product Exchange"),
			productReExchange((short) 4, "Product Re-Exchange"), birthDay((short) 5, "Birthday");
			public short ID;

			public String VALUE;

			Fixed(short id, String value)
			{
				this.ID = id;
				this.VALUE = value;
			}
		}
	}

	public interface SysInvoice
	{
		String agentSumInvoiceNamedQuery = "SysInvoice.agentSumInvoice";

		String sysInvoiceId = "sysInvoiceId";

		String invoiceValue = "invoiceValue";

		String insertDate = "insertDate";

		String agentId = Agent.agentId;

		String currencyId = Currency.currencyId;

		String invoiceDate_From_Search = "insertDate_From";

		String invoiceDate_To_Search = "insertDate_To";

		String active = "active";

		String agent = "agent", currency = "currency";

		String promotions = "promotions", purchases = "purchases", pointsExchanges = "pointsExchanges";
	}

	public interface SysPayment extends ItemStatus
	{
		String agentSumPaidNamedQuery = "SysPayment.agentSumPaid";

		String sysPaymentId = "sysPaymentId";

		String paymentValue = "paymentValue";

		String paymentDate = "paymentDate";

		String agentId = Agent.agentId;

		String paymentMethodId = PaymentMethod.paymentMethodId;

		String currencyId = Currency.currencyId;

		String insertDate = "insertDate";

		String insEmpId = "insEmpId";

		String paymentDate_From_Search = "paymentDate_From";

		String paymentDate_To_Search = "paymentDate_To";

		String insertDate_From_Search = "insertDate_From";

		String insertDate_To_Search = "insertDate_To";

		String agent = "agent", paymentMethod = "paymentMethod", insEmp = "insEmp", currency = "currency";

	}

	public interface Login extends ItemStatus
	{
		String loginId = "loginId";

		String fullName = Profile.fullName;

		String mobile = "mobile";

		String password = "password";

		String newPassword = "newPassword";

		String encrypted = "jCryption";

		String rsaKeys = "rsaKeys";

		String token = "token";

		String agentId = Agent.agentId;

		String branchId = Branch.branchId;

		String countryId = Profile.countryId;

		String roleId = Profile.roleId;

		String requestCode = "requestCode";
	}

	public interface Synchronization
	{
		int agentSyncNeeded = 1, branchSyncNeeded = 2;

		int productSyncNeeded = 3, promotionSyncNeeded = 4;

		int categorySyncNeeded = 5;

		int countrySyncNeeded = 6, districtSyncNeeded = 7, stateSyncNeeded = 8;

		int purchasesSyncNeeded = 9, pointsExchangeSyncNeeded = 10;

		String dataToSync = "dataToSync";
	}
}
