
package controllers.actor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.CovfefeService;
import controllers.AbstractController;
import domain.Covfefe;
import forms.CancelCovfefe;

@Controller
@RequestMapping("/covfefe/actor")
public class CovfefeActorController extends AbstractController {

	//Services

	@Autowired
	private CovfefeService	covfefeService;


	//Constructor

	public CovfefeActorController() {
		super();
	}

	@RequestMapping(value = "/cancel", method = RequestMethod.GET)
	public ModelAndView create() {

		ModelAndView result;
		final CancelCovfefe cancel;

		cancel = new CancelCovfefe();
		final Covfefe covfefe = this.covfefeService.findOne(cancel.getCovfefeId());

		result = this.createEditModelAndView(covfefe);

		return result;
	}

	// Ancillary methods

	protected ModelAndView createEditModelAndView(final Covfefe covfefe) {
		ModelAndView result;

		result = this.createEditModelAndView(covfefe, null);

		return result;
	}
	protected ModelAndView createEditModelAndView(final Covfefe covfefe, final String message) {
		ModelAndView result;

		final String requestURI = "covfefe/_manager/edit.do";

		result = new ModelAndView("covfefe/edit");
		result.addObject("covfefe", covfefe);
		result.addObject("message", message);
		result.addObject("requestURI", requestURI);

		return result;
	}

}
