
package controllers.manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.CovfefeService;
import services.ManagerService;
import controllers.AbstractController;
import domain.Covfefe;

@Controller
@RequestMapping("/covfefe/_manager")
public class CovfefeManagerController extends AbstractController {

	//Services

	@Autowired
	private CovfefeService	covfefeService;

	@Autowired
	private ManagerService	managerService;


	//Contructor

	public CovfefeManagerController() {
		super();
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		Covfefe covfefe;
		covfefe = this.covfefeService.create();
		result = this.createEditModelAndView(covfefe);
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int covfefeId) {
		ModelAndView result;
		Covfefe covfefe;

		covfefe = this.covfefeService.findOne(covfefeId);
		result = this.createEditModelAndView(covfefe);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(final Covfefe editCovfefe, final BindingResult binding) {
		ModelAndView result;
		Covfefe covfefe;
		covfefe = editCovfefe;

		if (binding.hasErrors())
			result = this.createEditModelAndView(covfefe);
		else
			try {
				covfefe = this.covfefeService.reconstruct(covfefe, binding);
				covfefe = this.covfefeService.save(covfefe);
				result = new ModelAndView("redirect:/covfefe/list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(covfefe, "covfefe.commit.error");
			}
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
