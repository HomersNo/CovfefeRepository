
package controllers.actor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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
	public ModelAndView create(@RequestParam final int covfefeId) {

		ModelAndView result;
		final CancelCovfefe cancel;
		final Covfefe covfefe = this.covfefeService.findOne(covfefeId);

		cancel = new CancelCovfefe();
		cancel.setCovfefe(covfefe);

		result = this.createEditModelAndView(cancel);

		return result;
	}

	@RequestMapping(value = "/cancel", method = RequestMethod.POST, params = "save")
	public ModelAndView save(final CancelCovfefe cancel, final BindingResult binding) {
		ModelAndView result;
		Covfefe covfefe;

		covfefe = cancel.getCovfefe();

		if (binding.hasErrors())
			result = this.createEditModelAndView(cancel);
		else
			try {
				covfefe.setJustification(cancel.getJustification());
				this.covfefeService.save(covfefe);
				result = new ModelAndView("redirect:/covfefe/list.do");

			} catch (final Throwable oops) {
				result = this.createEditModelAndView(cancel, "covfefe.commit.error");
			}
		return result;
	}

	// Ancillary methods

	protected ModelAndView createEditModelAndView(final CancelCovfefe cancel) {
		ModelAndView result;

		result = this.createEditModelAndView(cancel, null);

		return result;
	}
	protected ModelAndView createEditModelAndView(final CancelCovfefe cancel, final String message) {
		ModelAndView result;

		final String requestURI = "covfefe/actor/cancel.do";

		result = new ModelAndView("covfefe/cancel");
		result.addObject("cancel", cancel);
		result.addObject("message", message);
		result.addObject("requestURI", requestURI);
		result.addObject("cancelURI", "covfefe/list.do");

		return result;
	}

}
