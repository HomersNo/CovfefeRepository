
package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.MeshworkService;
import domain.Meshwork;

@Controller
@RequestMapping("/meshwork")
public class MeshworkController extends AbstractController {

	//Services

	@Autowired
	private MeshworkService	meshworkService;


	//Constructor

	public MeshworkController() {
		super();
	}

	//Listing

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int meshworkId) {
		ModelAndView result;

		final Meshwork meshwork = this.meshworkService.findOne(meshworkId);

		result = new ModelAndView("Meshwork/display");
		result.addObject("requestURI", "Meshwork/display.do");
		result.addObject("meshwork", meshwork);

		return result;
	}

	// Creation ---------------------------------------------------------------

	// Edition ----------------------------------------------------------------

	//Needs further testing

}
