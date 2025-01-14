/**
 * 
 */
package com.ewhale.points.ws.system;

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
import com.ewhale.points.controller.facade.CategoryFacade;
import com.ewhale.points.controller.facade.CountryFacade;
import com.ewhale.points.controller.facade.CurrencyFacade;
import com.ewhale.points.controller.facade.DistrictFacade;
import com.ewhale.points.controller.facade.FunctionTypeFacade;
import com.ewhale.points.controller.facade.PaymentMethodFacade;
import com.ewhale.points.controller.facade.RoleFacade;
import com.ewhale.points.controller.facade.StateFacade;
import com.ewhale.points.controller.facade.StatusFacade;
import com.ewhale.points.controller.facade.SysEventFacade;
import com.ewhale.points.ws.main.ServiceHeading;

/**
 * @author Ayman Yosry
 */

@Path("/SystemSales")
@Produces(ServiceHeading.MEDIA_TYPE)
@VerifyToken
public class SystemSalesService implements ServiceHeading
{
	private SystemServiceControler controler = new SystemServiceControler();

	protected Logger LOG = Logger.getLogger(SystemSalesService.class);

	@EJB
	private CountryFacade countryFacade;

	@EJB
	private StateFacade stateFacade;

	@EJB
	private DistrictFacade districtFacade;

	@EJB
	private RoleFacade roleFacade;

	@EJB
	private CurrencyFacade currencyFacade;

	@EJB
	private PaymentMethodFacade paymentMethodFacade;

	@EJB
	private CategoryFacade categoryFacade;

	@EJB
	private FunctionTypeFacade functionTypeFacade;

	@EJB
	private StatusFacade statusFacade;

	@EJB
	private SysEventFacade sysEventFacade;

	@POST
	@Path("/addCountry")
	public void addCountry(Map<String, Object> countryData)
	{
		try
		{
			controler.add(countryFacade, countryData);
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
	@Path("/updateCountry")
	public void updateCountry(Map<String, Object> countryData)
	{
		try
		{
			controler.update(countryFacade, countryData);
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
	@Path("/deleteCountry/{countryId}")
	public void deleteCountry(@PathParam("countryId") String countryId)
	{
		try
		{
			Byte id = Byte.valueOf(countryId);
			controler.delete(countryFacade, id);
		}
		catch (FacadeException e)
		{
			LOG.error(e.getMessage(), e);
			throw new ServiceException("Problem While retrieving data", e.getStatusCode());
		}
	}

	@POST
	@Path("/countryDetails/{countryId}")
	public Map<String, Object> countryDetails(@PathParam("countryId") String countryId)
	{
		try
		{
			Byte id = Byte.valueOf(countryId);
			Map<String, Object> countryDetails = controler.getDetails(countryFacade, id);
			return countryDetails;
		}
		catch (FacadeException e)
		{
			LOG.error(e.getMessage(), e);
			throw new ServiceException("Problem While retrieving data", e.getStatusCode());
		}

	}

	@POST
	@Path("/countries")
	public List<Map<String, Object>> getAllCountries()
	{
		try
		{
			List<Map<String, Object>> allCountries = controler.getAll(countryFacade);
			return allCountries;
		}
		catch (FacadeException e)
		{
			LOG.error(e.getMessage(), e);
			throw new ServiceException("Problem While retrieving data", e.getStatusCode());
		}
	}

	@POST
	@Path("/addState")
	public void addState(Map<String, Object> countryData)
	{
		try
		{
			controler.add(stateFacade, countryData);
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
	@Path("/updateState")
	public void updateState(Map<String, Object> countryData)
	{
		try
		{
			controler.update(stateFacade, countryData);
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
	@Path("/deleteState/{stateId}")
	public void deleteState(@PathParam("stateId") String stateId)
	{
		try
		{
			Integer id = Integer.valueOf(stateId);
			controler.delete(stateFacade, id);
		}
		catch (FacadeException e)
		{
			LOG.error(e.getMessage(), e);
			throw new ServiceException("Problem While retrieving data", e.getStatusCode());
		}
	}

	@POST
	@Path("/stateDetails/{stateId}")
	public Map<String, Object> stateDetails(@PathParam("stateId") String stateId)
	{
		try
		{
			Integer id = Integer.valueOf(stateId);
			Map<String, Object> stateDetails = controler.getDetails(stateFacade, id);
			return stateDetails;
		}
		catch (FacadeException e)
		{
			LOG.error(e.getMessage(), e);
			throw new ServiceException("Problem While retrieving data", e.getStatusCode());
		}

	}

	@POST
	@Path("/states")
	public List<Map<String, Object>> getStatesList(Map<String, Object> stateData)
	{
		try
		{
			List<Map<String, Object>> allStates = controler.getList(stateFacade, stateData);
			return allStates;
		}
		catch (FacadeException e)
		{
			LOG.error(e.getMessage(), e);
			throw new ServiceException("Problem While retrieving data", e.getStatusCode());
		}
	}

	@POST
	@Path("/addDistrict")
	public void addDistrict(Map<String, Object> districtData)
	{
		try
		{
			controler.add(districtFacade, districtData);
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
	@Path("/updateDistrict")
	public void updateDistrict(Map<String, Object> districtData)
	{
		try
		{
			controler.update(districtFacade, districtData);
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
	@Path("/deleteDistrict/{districtId}")
	public void deleteDistrict(@PathParam("districtId") String districtId)
	{
		try
		{
			Integer id = Integer.valueOf(districtId);
			controler.delete(districtFacade, id);
		}
		catch (FacadeException e)
		{
			LOG.error(e.getMessage(), e);
			throw new ServiceException("Problem While retrieving data", e.getStatusCode());
		}
	}

	@POST
	@Path("/districtDetails/{districtId}")
	public Map<String, Object> districtDetails(@PathParam("districtId") String districtId)
	{
		try
		{
			Integer id = Integer.valueOf(districtId);
			Map<String, Object> districtDetails = controler.getDetails(districtFacade, id);
			return districtDetails;
		}
		catch (FacadeException e)
		{
			LOG.error(e.getMessage(), e);
			throw new ServiceException("Problem While retrieving data", e.getStatusCode());
		}

	}

	@POST
	@Path("/districts")
	public List<Map<String, Object>> getDistrictsList(Map<String, Object> districtData)
	{
		try
		{
			List<Map<String, Object>> alldistricts = controler.getList(districtFacade, districtData);
			return alldistricts;
		}
		catch (FacadeException e)
		{
			LOG.error(e.getMessage(), e);
			throw new ServiceException("Problem While retrieving data", e.getStatusCode());
		}
	}

	@POST
	@Path("/addRole")
	public void addRole(Map<String, Object> roleData)
	{
		try
		{
			controler.add(roleFacade, roleData);
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
	@Path("/updateRole")
	public void updateRole(Map<String, Object> roleData)
	{
		try
		{
			controler.update(roleFacade, roleData);
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
	@Path("/deleteRole/{roleId}")
	public void deleteRole(@PathParam("roleId") String roleId)
	{
		try
		{
			Integer id = Integer.valueOf(roleId);
			controler.delete(roleFacade, id);
		}
		catch (FacadeException e)
		{
			LOG.error(e.getMessage(), e);
			throw new ServiceException("Problem While retrieving data", e.getStatusCode());
		}
	}

	@POST
	@Path("/roles")
	public List<Map<String, Object>> getAllRoles()
	{
		try
		{
			List<Map<String, Object>> allRoles = controler.getAll(roleFacade);
			return allRoles;
		}
		catch (FacadeException e)
		{
			LOG.error(e.getMessage(), e);
			throw new ServiceException("Problem While retrieving data", e.getStatusCode());
		}
	}

	@POST
	@Path("/addPaymentMethod")
	public void addPaymentMethod(Map<String, Object> paymentMethodData)
	{
		try
		{
			controler.add(paymentMethodFacade, paymentMethodData);
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
	@Path("/updatePaymentMethod")
	public void updatePaymentMethod(Map<String, Object> paymentMethodData)
	{
		try
		{
			controler.update(paymentMethodFacade, paymentMethodData);
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
	@Path("/deletePaymentMethod/{paymentMethodId}")
	public void deletePaymentMethod(@PathParam("paymentMethodId") String paymentMethodId)
	{
		try
		{
			Integer id = Integer.valueOf(paymentMethodId);
			controler.delete(paymentMethodFacade, id);
		}
		catch (FacadeException e)
		{
			LOG.error(e.getMessage(), e);
			throw new ServiceException("Problem While retrieving data", e.getStatusCode());
		}
	}

	@POST
	@Path("/paymentMethodDetails/{methodId}")
	public Map<String, Object> paymentMethodDetails(@PathParam("methodId") String methodId)
	{
		try
		{
			Short id = Short.valueOf(methodId);
			Map<String, Object> methodDetails = controler.getDetails(paymentMethodFacade, id);
			return methodDetails;
		}
		catch (FacadeException e)
		{
			LOG.error(e.getMessage(), e);
			throw new ServiceException("Problem While retrieving data", e.getStatusCode());
		}

	}

	@POST
	@Path("/paymentMethods")
	public List<Map<String, Object>> getAllPaymentMethods()
	{
		try
		{
			List<Map<String, Object>> allPaymentMethods = controler.getAll(paymentMethodFacade);
			return allPaymentMethods;
		}
		catch (FacadeException e)
		{
			LOG.error(e.getMessage(), e);
			throw new ServiceException("Problem While retrieving data", e.getStatusCode());
		}
	}

	@POST
	@Path("/addCurrency")
	public void addCurrency(Map<String, Object> currencyData)
	{
		try
		{
			controler.add(currencyFacade, currencyData);
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
	@Path("/updateCurrency")
	public void updateCurrency(Map<String, Object> currencyData)
	{
		try
		{
			controler.update(currencyFacade, currencyData);
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
	@Path("/deleteCurrency/{currencyId}")
	public void deleteCurrency(@PathParam("currencyId") String currencyId)
	{
		try
		{
			Integer id = Integer.valueOf(currencyId);
			controler.delete(currencyFacade, id);
		}
		catch (FacadeException e)
		{
			LOG.error(e.getMessage(), e);
			throw new ServiceException("Problem While retrieving data", e.getStatusCode());
		}
	}

	@POST
	@Path("/currencies")
	public List<Map<String, Object>> getAllCurrencies()
	{
		try
		{
			List<Map<String, Object>> allCurrencies = controler.getAll(currencyFacade);
			return allCurrencies;
		}
		catch (FacadeException e)
		{
			LOG.error(e.getMessage(), e);
			throw new ServiceException("Problem While retrieving data", e.getStatusCode());
		}
	}

	@POST
	@Path("/currencyDetails/{currencyId}")
	public Map<String, Object> currencyDetails(@PathParam("currencyId") String currencyId)
	{
		try
		{
			Short id = Short.valueOf(currencyId);
			Map<String, Object> currencyDetails = controler.getDetails(currencyFacade, id);
			return currencyDetails;
		}
		catch (FacadeException e)
		{
			LOG.error(e.getMessage(), e);
			throw new ServiceException("Problem While retrieving data", e.getStatusCode());
		}

	}

	@POST
	@Path("/addCategory")
	public void addCategory(Map<String, Object> categoryData)
	{
		try
		{
			controler.add(categoryFacade, categoryData);
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
	@Path("/updateCategory")
	public void updateCategory(Map<String, Object> categoryData)
	{
		try
		{
			controler.update(categoryFacade, categoryData);
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
	@Path("/deleteCategory/{categoryId}")
	public void deleteCategory(@PathParam("categoryId") String categoryId)
	{
		try
		{
			Integer id = Integer.valueOf(categoryId);
			controler.delete(categoryFacade, id);
		}
		catch (FacadeException e)
		{
			LOG.error(e.getMessage(), e);
			throw new ServiceException("Problem While retrieving data", e.getStatusCode());
		}
	}

	@POST
	@Path("/categories")
	public List<Map<String, Object>> getAllCategories()
	{
		try
		{
			List<Map<String, Object>> allCategories = controler.getAll(categoryFacade);
			return allCategories;
		}
		catch (FacadeException e)
		{
			LOG.error(e.getMessage(), e);
			throw new ServiceException("Problem While retrieving data", e.getStatusCode());
		}
	}

	@POST
	@Path("/addBusinessFunction")
	public void addBusinessFunction(Map<String, Object> businessFunctionData)
	{
		try
		{
			controler.add(functionTypeFacade, businessFunctionData);
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
	@Path("/updateBusinessFunction")
	public void updateBusinessFunction(Map<String, Object> businessFunctionData)
	{
		try
		{
			controler.update(functionTypeFacade, businessFunctionData);
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
	@Path("/deleteBusinessFunction/{businessFunctionId}")
	public void deleteBusinessFunction(@PathParam("businessFunctionId") String businessFunctionId)
	{
		try
		{
			Integer id = Integer.valueOf(businessFunctionId);
			controler.delete(functionTypeFacade, id);
		}
		catch (FacadeException e)
		{
			LOG.error(e.getMessage(), e);
			throw new ServiceException("Problem While retrieving data", e.getStatusCode());
		}
	}

	@POST
	@Path("/functionDetails/{functionId}")
	public Map<String, Object> functionDetails(@PathParam("functionId") String functionId)
	{
		try
		{
			Short id = Short.valueOf(functionId);
			Map<String, Object> functionDetails = controler.getDetails(functionTypeFacade, id);
			return functionDetails;
		}
		catch (FacadeException e)
		{
			LOG.error(e.getMessage(), e);
			throw new ServiceException("Problem While retrieving data", e.getStatusCode());
		}

	}

	@POST
	@Path("/businessFunctions")
	public List<Map<String, Object>> getAllBusinessFunctions()
	{
		try
		{
			List<Map<String, Object>> allBusinessFunctions = controler.getAll(functionTypeFacade);
			return allBusinessFunctions;
		}
		catch (FacadeException e)
		{
			LOG.error(e.getMessage(), e);
			throw new ServiceException("Problem While retrieving data", e.getStatusCode());
		}
	}

	@POST
	@Path("/addStatus")
	public void addStatus(Map<String, Object> statusData)
	{
		try
		{
			controler.add(statusFacade, statusData);
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
	@Path("/updateStatus")
	public void updateStatus(Map<String, Object> statusData)
	{
		try
		{
			controler.update(statusFacade, statusData);
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
	@Path("/deleteStatus/{statusId}")
	public void deleteStatus(@PathParam("statusId") String statusId)
	{
		try
		{
			Integer id = Integer.valueOf(statusId);
			controler.delete(statusFacade, id);
		}
		catch (FacadeException e)
		{
			LOG.error(e.getMessage(), e);
			throw new ServiceException("Problem While retrieving data", e.getStatusCode());
		}
	}

	@POST
	@Path("/statuses")
	public List<Map<String, Object>> getAllStatuses()
	{
		try
		{
			List<Map<String, Object>> allStatuses = controler.getAll(statusFacade);
			return allStatuses;
		}
		catch (FacadeException e)
		{
			LOG.error(e.getMessage(), e);
			throw new ServiceException("Problem While retrieving data", e.getStatusCode());
		}
	}

	@POST
	@Path("/addSysEvent")
	public void addSysEvent(Map<String, Object> sysEventData)
	{
		try
		{
			controler.add(sysEventFacade, sysEventData);
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
	@Path("/updateSysEvent")
	public void updateSysEvent(Map<String, Object> sysEventData)
	{
		try
		{
			controler.update(sysEventFacade, sysEventData);
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
	@Path("/deleteSysEvent/{sysEventId}")
	public void deleteSysEvent(@PathParam("sysEventId") String sysEventId)
	{
		try
		{
			Integer id = Integer.valueOf(sysEventId);
			controler.delete(sysEventFacade, id);
		}
		catch (FacadeException e)
		{
			LOG.error(e.getMessage(), e);
			throw new ServiceException("Problem While retrieving data", e.getStatusCode());
		}
	}

	@POST
	@Path("/viewEventsList")
	public List<Map<String, Object>> viewEventsList(Map<String, Object> sysEventData)
	{
		try
		{
			List<Map<String, Object>> eventsList = controler.getList(sysEventFacade, sysEventData);
			return eventsList;
		}
		catch (FacadeException e)
		{
			LOG.error(e.getMessage(), e);
			throw new ServiceException("Problem While retrieving data", e.getStatusCode());
		}
	}

	@POST
	@Path("/eventDetails/{sysEventId}")
	public Map<String, Object> eventDetails(@PathParam("sysEventId") String sysEventId)
	{
		try
		{
			Long id = Long.valueOf(sysEventId);
			Map<String, Object> eventDetails = controler.getDetails(sysEventFacade, id);
			return eventDetails;
		}
		catch (FacadeException e)
		{
			LOG.error(e.getMessage(), e);
			throw new ServiceException("Problem While retrieving data", e.getStatusCode());
		}
	}
}
