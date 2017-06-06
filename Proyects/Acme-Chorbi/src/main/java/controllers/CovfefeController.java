
package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import services.EventService;
import services.OndaleckService;
import domain.Event;
import domain.Ondaleck;

@Controller
@RequestMapping("/covfefe")
public class CovfefeController extends AbstractController {

	//Services

	@Autowired
	private OndaleckService	covfefeService;

	@Autowired
	private EventService	eventService;


	//Constructor

	public CovfefeController() {
		super();
	}

	//Listing

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam(required = true) final int eventId, @RequestParam(required = false) final String errorMessage, final RedirectAttributes redir) {
		ModelAndView result;

		Ondaleck covfefes;
		final Event event = this.eventService.findOne(eventId);
		covfefes = this.covfefeService.findByEvent(event);
		if (covfefes.getJustification() != null || covfefes.getJustification() != "") {
			result = new ModelAndView("redirect:/event/list.do");
			redir.addFlashAttribute("errorMessage", "covfefe.cancelled");

		} else {
			result = new ModelAndView("covfefe/list");
			result.addObject("requestURI", "covfefe/list.do");
			result.addObject("covfefes", covfefes);
			result.addObject("errorMessage", errorMessage);
		}

		return result;
	}

	// Creation ---------------------------------------------------------------

	// Edition ----------------------------------------------------------------

	//Needs further testing

}
