
package controllers.manager;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import services.CovfefeService;
import services.EventService;
import services.ManagerService;
import controllers.AbstractController;
import domain.Covfefe;
import domain.Event;

@Controller
@RequestMapping("/covfefe/_manager")
public class CovfefeManagerController extends AbstractController {

	//Services

	@Autowired
	private CovfefeService	covfefeService;

	@Autowired
	private ManagerService	managerService;

	@Autowired
	private EventService	eventService;


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
	public ModelAndView edit(@RequestParam final int covfefeId, final RedirectAttributes redir) {
		ModelAndView result;
		Covfefe covfefe;
		covfefe = this.covfefeService.findOne(covfefeId);
		try {
			Assert.isTrue(covfefe.getManager().equals(this.managerService.findByPrincipal()));
			result = this.createEditModelAndView(covfefe);

		} catch (final Exception e) {

			result = new ModelAndView("redirect:/welcome/index.do");
			redir.addAttribute("errorMessage", "message.error.authority");
		}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@ModelAttribute final Covfefe editCovfefe, final BindingResult binding) {
		ModelAndView result;
		Covfefe covfefe;
		Covfefe validated;
		covfefe = editCovfefe;

		if (binding.hasErrors())
			result = this.createEditModelAndView(covfefe);
		else
			try {
				validated = this.covfefeService.reconstruct(covfefe, binding);
				if (binding.hasErrors())
					result = this.createEditModelAndView(covfefe);
				else {
					covfefe = this.covfefeService.save(validated);
					result = new ModelAndView("redirect:/covfefe/list.do");
				}
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
		Collection<Event> events;

		final String requestURI = "covfefe/_manager/edit.do";
		events = this.eventService.findAllNoCovfefe();

		result = new ModelAndView("covfefe/edit");
		result.addObject("covfefe", covfefe);
		result.addObject("message", message);
		result.addObject("requestURI", requestURI);
		result.addObject("events", events);

		return result;
	}

}
