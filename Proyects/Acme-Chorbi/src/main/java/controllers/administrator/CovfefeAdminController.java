
package controllers.administrator;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import services.AdministratorService;
import services.EventService;
import services.OndaleckService;
import controllers.AbstractController;
import domain.Event;
import domain.Ondaleck;
import forms.CancelOndalek;

@Controller
@RequestMapping("/covfefe/admin")
public class CovfefeAdminController extends AbstractController {

	//Services

	@Autowired
	private OndaleckService			covfefeService;

	@Autowired
	private AdministratorService	managerService;

	@Autowired
	private EventService			eventService;


	//Contructor

	public CovfefeAdminController() {
		super();
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam(required = false) final String errorMessage) {
		ModelAndView result;

		Collection<Ondaleck> covfefes;

		covfefes = this.covfefeService.findAll();
		final Collection<Ondaleck> ownCovfefes = this.covfefeService.findAllByPrincipal();

		result = new ModelAndView("covfefe/list");
		result.addObject("requestURI", "covfefe/admin/list.do");
		result.addObject("covfefes", covfefes);
		result.addObject("ownCovfefes", ownCovfefes);
		result.addObject("errorMessage", errorMessage);

		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam(required = true) final int eventId, final RedirectAttributes redir) {
		ModelAndView result;
		Ondaleck covfefe;
		Event event;
		event = this.eventService.findOne(eventId);
		final Ondaleck covfefes = this.covfefeService.findByEvent(event);
		if (covfefes == null) {
			covfefe = this.covfefeService.create(event);
			result = this.createEditModelAndView(covfefe);
		} else {
			result = new ModelAndView("redirect:/event/list.do");
			redir.addFlashAttribute("errorMessage", "covfefe.cancelled");
		}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int covfefeId, final RedirectAttributes redir) {
		ModelAndView result;
		Ondaleck covfefe;
		covfefe = this.covfefeService.findOne(covfefeId);
		try {

			result = this.createEditModelAndView(covfefe);

		} catch (final Exception e) {

			result = new ModelAndView("redirect:/welcome/index.do");
			redir.addFlashAttribute("errorMessage", "message.error.authority");
		}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@ModelAttribute final Ondaleck editCovfefe, final BindingResult binding) {
		ModelAndView result;
		Ondaleck covfefe;
		Ondaleck validated;
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
					result = new ModelAndView("redirect:/covfefe/admin/list.do");
				}
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(covfefe, oops.getMessage());
			}
		return result;
	}

	// Ancillary methods

	protected ModelAndView createEditModelAndView(final Ondaleck covfefe) {
		ModelAndView result;

		result = this.createEditModelAndView(covfefe, null);

		return result;
	}
	protected ModelAndView createEditModelAndView(final Ondaleck covfefe, final String message) {
		ModelAndView result;
		Collection<Event> events;

		final String requestURI = "covfefe/admin/edit.do";
		events = this.eventService.findAllNoCovfefe();

		result = new ModelAndView("covfefe/edit");
		result.addObject("covfefe", covfefe);
		result.addObject("message", message);
		result.addObject("requestURI", requestURI);

		return result;
	}

	@RequestMapping(value = "/cancel", method = RequestMethod.GET)
	public ModelAndView createCancel(@RequestParam final int covfefeId) {

		ModelAndView result;
		final CancelOndalek cancel;
		final Ondaleck covfefe = this.covfefeService.findOne(covfefeId);

		cancel = new CancelOndalek();
		cancel.setCovfefe(covfefe);

		result = this.createEditModelAndView(cancel);

		return result;
	}

	@RequestMapping(value = "/cancel", method = RequestMethod.POST, params = "save")
	public ModelAndView save(final CancelOndalek cancel, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(cancel);
		else
			try {
				this.covfefeService.cancel(cancel);
				result = new ModelAndView("redirect:/covfefe/admin/list.do");

			} catch (final Throwable oops) {
				result = this.createEditModelAndView(cancel, "covfefe.commit.error");
			}
		return result;
	}

	// Ancillary methods

	protected ModelAndView createEditModelAndView(final CancelOndalek cancel) {
		ModelAndView result;

		result = this.createEditModelAndView(cancel, null);

		return result;
	}
	protected ModelAndView createEditModelAndView(final CancelOndalek cancel, final String message) {
		ModelAndView result;

		final String requestURI = "covfefe/admin/cancel.do";

		result = new ModelAndView("covfefe/cancel");
		result.addObject("cancel", cancel);
		result.addObject("message", message);
		result.addObject("requestURI", requestURI);
		result.addObject("cancelURI", "covfefe/admin/list.do");

		return result;
	}

}
