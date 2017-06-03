
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import repositories.CovfefeRepository;
import domain.Covfefe;

@Component
@Transactional
public class StringToCovfefeConverter implements Converter<String, Covfefe> {

	@Autowired
	CovfefeRepository	covfefeRepository;


	@Override
	public Covfefe convert(final String text) {
		Covfefe result;
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
