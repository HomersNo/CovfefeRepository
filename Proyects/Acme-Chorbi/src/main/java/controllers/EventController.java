
package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.EventService;
import domain.Event;

@Controller
@RequestMapping("/event")
public class EventController extends AbstractController {

	//Services

	@Autowired
	private EventService	eventService;


	//Constructor

	public EventController() {
		super();
	}

	//Listing

	@RequestMapping(value = "/listInminent", method = RequestMethod.GET)
	public ModelAndView listImminent(@RequestParam(required = false) final String errorMessage) {
		ModelAndView result;

		Collection<Event> events;
		final Collection<Event> unassessedEvents;
		Collection<Event> withCancelled;
		events = this.eventService.findAllEventsInOneMonth();
		withCancelled = this.eventService.findAllWithCancelled();
		unassessedEvents = this.eventService.findAllNoCovfefe();

		result = new ModelAndView("event/list");
		result.addObject("requestURI", "event/listInminent.do");
		result.addObject("events", events);
		result.addObject("unassessedEvents", unassessedEvents);
		result.addObject("errorMessage", errorMessage);
		result.addObject("withCancelled", withCancelled);

		return result;
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam(required = false) final String errorMessage) {
		ModelAndView result;

		Collection<Event> events;
		final Collection<Event> unassessedEvents;
		Collection<Event> withCancelled;

		events = this.eventService.findAll();

		unassessedEvents = this.eventService.findAllNoCovfefe();
		withCancelled = this.eventService.findAllWithCancelled();

		result = new ModelAndView("event/list");
		result.addObject("requestURI", "event/list.do");
		result.addObject("events", events);
		result.addObject("unassessedEvents", unassessedEvents);
		result.addObject("errorMessage", errorMessage);
		result.addObject("withCancelled", withCancelled);

		return result;
	}

	// Creation ---------------------------------------------------------------

	// Edition ----------------------------------------------------------------

	//Needs further testing

}
