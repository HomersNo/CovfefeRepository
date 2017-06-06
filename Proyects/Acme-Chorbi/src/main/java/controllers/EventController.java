
package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.EventService;
import services.MeshworkService;
import domain.Event;
import domain.Meshwork;

@Controller
@RequestMapping("/event")
public class EventController extends AbstractController {

	//Services

	@Autowired
	private EventService	eventService;

	@Autowired
	private MeshworkService	meshworkService;


	//Constructor

	public EventController() {
		super();
	}

	//Listing

	@RequestMapping(value = "/listInminent", method = RequestMethod.GET)
	public ModelAndView listImminent(@RequestParam(required = false) final String errorMessage) {
		ModelAndView result;

		Collection<Event> events;

		Collection<Meshwork> meshworks;

		meshworks = this.meshworkService.findAllNotCanceled();

		events = this.eventService.findAllEventsInOneMonth();

		result = new ModelAndView("event/list");
		result.addObject("meshworks", meshworks);
		result.addObject("requestURI", "event/listInminent.do");
		result.addObject("events", events);
		result.addObject("errorMessage", errorMessage);

		return result;
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam(required = false) final String errorMessage) {
		ModelAndView result;

		Collection<Event> events;
		Collection<Meshwork> meshworks;

		meshworks = this.meshworkService.findAllNotCanceled();

		events = this.eventService.findAll();

		result = new ModelAndView("event/list");
		result.addObject("meshworks", meshworks);
		result.addObject("requestURI", "event/list.do");
		result.addObject("events", events);
		result.addObject("errorMessage", errorMessage);

		return result;
	}

	// Creation ---------------------------------------------------------------

	// Edition ----------------------------------------------------------------

	//Needs further testing

}
