/**
 * 
 */
package com.ewhale.points.ws.main;

import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import com.ewhale.points.common.annotations.VerifyToken;
import com.ewhale.points.common.exception.FacadeException;
import com.ewhale.points.common.exception.ServiceException;
import com.ewhale.points.controller.facade.CategoryFacade;
import com.ewhale.points.controller.facade.CountryFacade;
import com.ewhale.points.controller.facade.CurrencyFacade;
import com.ewhale.points.controller.facade.DistrictFacade;
import com.ewhale.points.controller.facade.FunctionTypeFacade;
import com.ewhale.points.controller.facade.PaymentMethodFacade;
import com.ewhale.points.controller.facade.RoleFacade;
import com.ewhale.points.controller.facade.StateFacade;
import com.ewhale.points.controller.facade.StatusFacade;

/**
 * @author Ayman Yosry
 */

@Path("/LookUp")
@VerifyToken
@Produces(ServiceHeading.MEDIA_TYPE)
public class LookUpService implements ServiceHeading
{
	private LookUpControler controler = new LookUpControler();

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
			//e.printStackTrace();
			throw new ServiceException("Problem While retrieving data", e.getStatusCode());
		}
	}

	@POST
	@Path("/states")
	public List<Map<String, Object>> getAllStates(Map<String, Object> stateData)
	{
		try
		{
			List<Map<String, Object>> allStates = controler.getList(stateFacade, stateData);
			return allStates;
		}
		catch (FacadeException e)
		{
			//e.printStackTrace();
			throw new ServiceException("Problem While retrieving data", e.getStatusCode());
		}
	}

	@POST
	@Path("/districts")
	public List<Map<String, Object>> getAllDistricts(Map<String, Object> districtData)
	{
		try
		{
			List<Map<String, Object>> alldistricts = controler.getList(districtFacade, districtData);
			return alldistricts;
		}
		catch (FacadeException e)
		{
			//e.printStackTrace();
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
			//e.printStackTrace();
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
			//e.printStackTrace();
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
			//e.printStackTrace();
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
			//e.printStackTrace();
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
			//e.printStackTrace();
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
			//e.printStackTrace();
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
			//e.printStackTrace();
			throw new ServiceException("Problem While retrieving data", e.getStatusCode());
		}
	}
}