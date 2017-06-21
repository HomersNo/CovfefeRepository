
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import repositories.LootsRepository;
import domain.Loots;

@Component
@Transactional
public class StringToLootsConverter implements Converter<String, Loots> {

	@Autowired
	LootsRepository	lootsRepository;


	@Override
	public Loots convert(final String text) {
		Loots result;
		int id;

		if (text == "")
			result = null;
		else
			try {
				id = Integer.valueOf(text);
				result = this.lootsRepository.findOne(id);
			} catch (final Throwable oops) {
				throw new IllegalArgumentException(oops);
			}

		return result;
	}

}
