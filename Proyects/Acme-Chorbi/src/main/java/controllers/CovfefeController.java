
package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.CovfefeService;
import domain.Covfefe;

@Controller
@RequestMapping("/covfefe")
public class CovfefeController extends AbstractController {

	//Services

	@Autowired
	private CovfefeService	covfefeService;


	//Constructor

	public CovfefeController() {
		super();
	}

	//Listing

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam(required = false) final String errorMessage) {
		ModelAndView result;

		Collection<Covfefe> covfefes;

		covfefes = this.covfefeService.findAllNotCanceled();
		final Collection<Covfefe> ownCovfefes = this.covfefeService.findAllByPrincipal();

		result = new ModelAndView("covfefe/list");
		result.addObject("requestURI", "covfefe/list.do");
		result.addObject("covfefes", covfefes);
		result.addObject("ownCovfefes", ownCovfefes);
		result.addObject("errorMessage", errorMessage);

		return result;
	}

	// Creation ---------------------------------------------------------------

	// Edition ----------------------------------------------------------------

	//Needs further testing

}
