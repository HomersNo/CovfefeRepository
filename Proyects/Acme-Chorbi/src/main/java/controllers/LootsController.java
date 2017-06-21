
package controllers;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.LootsService;
import domain.Loots;

@Controller
@RequestMapping("/loots")
public class LootsController extends AbstractController {

	//Services

	@Autowired
	private LootsService	lootsService;


	//Constructor

	public LootsController() {
		super();
	}

	//Listing

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam(required = false) final String errorMessage) {
		ModelAndView result;

		Collection<Loots> loots;
		Date now;

		now = new Date();
		loots = this.lootsService.findAllNotCanceled();

		result = new ModelAndView("loots/list");
		result.addObject("requestURI", "loots/list.do");
		result.addObject("loots", loots);
		result.addObject("now", now);
		result.addObject("errorMessage", errorMessage);

		return result;
	}
	// Creation ---------------------------------------------------------------

	// Edition ----------------------------------------------------------------

	//Needs further testing

}
