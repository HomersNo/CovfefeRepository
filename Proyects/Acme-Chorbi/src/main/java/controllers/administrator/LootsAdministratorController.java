
package controllers.administrator;

import java.util.Collection;
import java.util.Date;

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

import services.AdministratorService;
import services.EventService;
import services.LootsService;
import controllers.AbstractController;
import domain.Event;
import domain.Loots;

@Controller
@RequestMapping("/loots/administrator")
public class LootsAdministratorController extends AbstractController {

	//Services

	@Autowired
	private LootsService			lootsService;

	@Autowired
	private AdministratorService	administratorService;

	@Autowired
	private EventService			eventService;


	//Contructor

	public LootsAdministratorController() {
		super();
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		Loots loots;
		loots = this.lootsService.create();
		result = this.createEditModelAndView(loots);
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int lootsId, final RedirectAttributes redir) {
		ModelAndView result;
		Loots loots;
		loots = this.lootsService.findOne(lootsId);
		try {
			Assert.isTrue(loots.getAdministrator().equals(this.administratorService.findByPrincipal()));
			result = this.createEditModelAndView(loots);

		} catch (final Exception e) {

			result = new ModelAndView("redirect:/welcome/index.do");
			redir.addFlashAttribute("errorMessage", "message.error.authority");
		}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@ModelAttribute final Loots editLoots, final BindingResult binding) {
		ModelAndView result;
		Loots loots;
		Loots validated;
		loots = editLoots;

		if (binding.hasErrors())
			result = this.createEditModelAndView(loots);
		else
			try {
				validated = this.lootsService.reconstruct(loots, binding);
				if (binding.hasErrors())
					result = this.createEditModelAndView(loots);
				else {
					loots = this.lootsService.save(validated);
					result = new ModelAndView("redirect:/loots/administrator/list.do");
				}
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(loots, "loots.commit.error");
			}
		return result;
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam(required = false) final String errorMessage) {
		ModelAndView result;

		Collection<Loots> loots;
		Collection<Loots> ownLoots;
		Date now;

		now = new Date();
		loots = this.lootsService.findAll();
		ownLoots = this.lootsService.findAllByPrincipal();

		result = new ModelAndView("loots/list");
		result.addObject("requestURI", "loots/administrator/list.do");
		result.addObject("loots", loots);
		result.addObject("ownLoots", ownLoots);
		result.addObject("now", now);
		result.addObject("errorMessage", errorMessage);

		return result;
	}

	// Ancillary methods

	protected ModelAndView createEditModelAndView(final Loots loots) {
		ModelAndView result;

		result = this.createEditModelAndView(loots, null);

		return result;
	}
	protected ModelAndView createEditModelAndView(final Loots loots, final String message) {
		ModelAndView result;
		Collection<Event> events;

		final String requestURI = "loots/administrator/edit.do";
		events = this.eventService.findAllNoLoots();

		result = new ModelAndView("loots/edit");
		result.addObject("loots", loots);
		result.addObject("message", message);
		result.addObject("requestURI", requestURI);
		result.addObject("events", events);

		return result;
	}

}
