
package controllers.actor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.LootsService;
import controllers.AbstractController;
import domain.Loots;
import forms.CancelLoots;

@Controller
@RequestMapping("/loots/actor")
public class LootsActorController extends AbstractController {

	//Services

	@Autowired
	private LootsService	lootsService;


	//Constructor

	public LootsActorController() {
		super();
	}

	@RequestMapping(value = "/cancel", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int lootsId) {

		ModelAndView result;
		final CancelLoots cancel;
		final Loots loots = this.lootsService.findOne(lootsId);

		cancel = new CancelLoots();
		cancel.setLoots(loots);

		result = this.createEditModelAndView(cancel);

		return result;
	}

	@RequestMapping(value = "/cancel", method = RequestMethod.POST, params = "save")
	public ModelAndView save(final CancelLoots cancel, final BindingResult binding) {
		ModelAndView result;
		Loots loots;

		loots = cancel.getLoots();

		if (binding.hasErrors())
			result = this.createEditModelAndView(cancel);
		else
			try {
				loots.setJustification(cancel.getJustification());
				this.lootsService.save(loots);
				result = new ModelAndView("redirect:/loots/administrator/list.do");

			} catch (final Throwable oops) {
				result = this.createEditModelAndView(cancel, "loots.commit.error");
			}
		return result;
	}

	// Ancillary methods

	protected ModelAndView createEditModelAndView(final CancelLoots cancel) {
		ModelAndView result;

		result = this.createEditModelAndView(cancel, null);

		return result;
	}
	protected ModelAndView createEditModelAndView(final CancelLoots cancel, final String message) {
		ModelAndView result;

		final String requestURI = "loots/actor/cancel.do";

		result = new ModelAndView("loots/cancel");
		result.addObject("cancel", cancel);
		result.addObject("message", message);
		result.addObject("requestURI", requestURI);
		result.addObject("cancelURI", "loots/administrator/list.do");

		return result;
	}

}
