package my.mycompany.myapp.web;

import javax.validation.Valid;

import my.mycompany.myapp.service.IProductsService;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

@Controller
@RequestMapping("/inventory/priceincrease")
@SessionAttributes("priceIncreaseFormBean")
public class PriceIncreaseFormController {

	private static final Logger logger = LoggerFactory.getLogger(PriceIncreaseFormController.class);

	@Autowired
	private IProductsService productsService;

	// Invoked initially to create the "form" attribute
	// Once created the "form" attribute comes from the HTTP session (see @SessionAttributes)

	@ModelAttribute("priceIncreaseFormBean")
	public PriceIncreaseFormBean createFormBean() {
		PriceIncreaseFormBean fb = new PriceIncreaseFormBean();
		fb.setPercentage(20);
		return fb;
	}
		
    @RequestMapping(method=RequestMethod.POST)
    @RequiresPermissions("product:manage")
    public String onSubmit(@Valid PriceIncreaseFormBean priceIncreaseFormBean,
    		BindingResult result, SessionStatus sessionStatus) {

    	if (result.hasErrors()) {
    		return null;
    	}
  
        int increase = priceIncreaseFormBean.getPercentage();
        logger.debug("Increasing prices by " + increase + "%.");

        sessionStatus.setComplete();
        
        productsService.increasePrice(increase);

        return "redirect:/inventory";
    }

    @RequestMapping(method=RequestMethod.GET)
    protected void form(ModelMap model) {
    }
}