
package controllers.actor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ChorbiService;
import controllers.AbstractController;
import domain.Chorbi;

@Controller
@RequestMapping("/chorbi/actor")
public class ChorbiActorController extends AbstractController {

	//Services

	@Autowired
	private ChorbiService	chorbiService;


	//Constructor

	public ChorbiActorController() {
		super();
	}

	//Display		
	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int chorbiId) {
		ModelAndView result;

		Chorbi chorbi;

		chorbi = this.chorbiService.findOne(chorbiId);

		result = new ModelAndView("chorbi/display");
		result.addObject("chorbi", chorbi);

		return result;
	}

}
