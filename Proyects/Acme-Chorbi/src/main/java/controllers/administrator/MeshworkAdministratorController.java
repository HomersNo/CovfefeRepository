
package controllers.administrator;

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

import services.AdministratorService;
import services.EventService;
import services.MeshworkService;
import controllers.AbstractController;
import domain.Event;
import domain.Meshwork;
import forms.CancelMeshwork;

@Controller
@RequestMapping("/meshwork/administrator")
public class MeshworkAdministratorController extends AbstractController {

	//Services

	@Autowired
	private MeshworkService			meshworkService;

	@Autowired
	private AdministratorService	administratorService;

	@Autowired
	private EventService			eventService;


	//Constructor

	public MeshworkAdministratorController() {
		super();
	}

	@RequestMapping(value = "/cancel", method = RequestMethod.GET)
	public ModelAndView createCancel(@RequestParam final int meshworkId) {

		ModelAndView result;
		final CancelMeshwork cancel;
		final Meshwork meshwork = this.meshworkService.findOne(meshworkId);

		cancel = new CancelMeshwork();
		cancel.setMeshwork(meshwork);

		result = this.createEditModelAndView(cancel);

		return result;
	}

	@RequestMapping(value = "/cancel", method = RequestMethod.POST, params = "save")
	public ModelAndView save(final CancelMeshwork cancel, final BindingResult binding) {
		ModelAndView result;
		Meshwork meshwork;

		meshwork = cancel.getMeshwork();

		if (binding.hasErrors())
			result = this.createEditModelAndView(cancel);
		else
			try {
				meshwork.setJustification(cancel.getJustification());
				this.meshworkService.save(meshwork);
				result = new ModelAndView("redirect:/meshwork/administrator/list.do");

			} catch (final Throwable oops) {
				result = this.createEditModelAndView(cancel, "meshwork.commit.error");
			}
		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam(required = false, defaultValue = "0") final int eventId) {
		ModelAndView result;
		Meshwork meshwork;
		meshwork = this.meshworkService.create();
		if (eventId != 0)
			meshwork.setEvent(this.eventService.findOne(eventId));
		result = this.createEditModelAndView(meshwork);
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int meshworkId, final RedirectAttributes redir) {
		ModelAndView result;
		Meshwork meshwork;
		meshwork = this.meshworkService.findOne(meshworkId);
		try {
			Assert.isTrue(meshwork.getAdministrator().equals(this.administratorService.findByPrincipal()));
			result = this.createEditModelAndView(meshwork);

		} catch (final Exception e) {

			result = new ModelAndView("redirect:/welcome/index.do");
			redir.addFlashAttribute("errorMessage", "message.error.authority");
		}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@ModelAttribute final Meshwork editMeshwork, final BindingResult binding) {
		ModelAndView result;
		Meshwork meshwork;
		Meshwork validated;
		meshwork = editMeshwork;

		if (binding.hasErrors())
			result = this.createEditModelAndView(meshwork);
		else
			try {
				validated = this.meshworkService.reconstruct(meshwork, binding);
				if (binding.hasErrors())
					result = this.createEditModelAndView(meshwork);
				else {
					this.meshworkService.checkEvent(validated.getEvent());
					meshwork = this.meshworkService.save(validated);
					result = new ModelAndView("redirect:/meshwork/administrator/list.do");
				}
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(meshwork);
				result.addObject("errorMessage", oops.getMessage());
			}
		return result;
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam(required = false) final String errorMessage) {
		ModelAndView result;

		Collection<Meshwork> Meshworks;

		Meshworks = this.meshworkService.findAll();
		final Collection<Meshwork> ownMeshworks = this.meshworkService.findAllByPrincipal();

		result = new ModelAndView("meshwork/list");
		result.addObject("requestURI", "meshwork/list.do");
		result.addObject("Meshworks", Meshworks);
		result.addObject("ownMeshworks", ownMeshworks);
		result.addObject("errorMessage", errorMessage);

		return result;
	}

	// Ancillary methods

	protected ModelAndView createEditModelAndView(final Meshwork meshwork) {
		ModelAndView result;

		result = this.createEditModelAndView(meshwork, null);

		return result;
	}
	protected ModelAndView createEditModelAndView(final Meshwork meshwork, final String message) {
		ModelAndView result;
		Collection<Event> events;

		final String requestURI = "meshwork/administrator/edit.do";
		events = this.eventService.findAllMeshworkeable();

		result = new ModelAndView("meshwork/edit");
		result.addObject("meshwork", meshwork);
		result.addObject("message", message);
		result.addObject("requestURI", requestURI);
		result.addObject("events", events);

		return result;
	}

	// Ancillary methods

	protected ModelAndView createEditModelAndView(final CancelMeshwork cancel) {
		ModelAndView result;

		result = this.createEditModelAndView(cancel, null);

		return result;
	}
	protected ModelAndView createEditModelAndView(final CancelMeshwork cancel, final String message) {
		ModelAndView result;

		final String requestURI = "meshwork/administrator/cancel.do";

		result = new ModelAndView("meshwork/cancel");
		result.addObject("cancel", cancel);
		result.addObject("message", message);
		result.addObject("requestURI", requestURI);
		result.addObject("cancelURI", "meshwork/list.do");

		return result;
	}

}
