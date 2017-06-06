
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import repositories.OndaleckRepository;
import domain.Ondaleck;

@Component
@Transactional
public class StringToOndaleckConverter implements Converter<String, Ondaleck> {

	@Autowired
	OndaleckRepository	covfefeRepository;


	@Override
	public Ondaleck convert(final String text) {
		Ondaleck result;
		int id;

		if (text == "")
			result = null;
		else
			try {
				id = Integer.valueOf(text);
				result = this.covfefeRepository.findOne(id);
			} catch (final Throwable oops) {
				throw new IllegalArgumentException(oops);
			}

		return result;
	}

}
