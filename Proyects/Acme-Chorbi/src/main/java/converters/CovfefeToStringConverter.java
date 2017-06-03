
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Covfefe;

@Component
@Transactional
public class CovfefeToStringConverter implements Converter<Covfefe, String> {

	@Override
	public String convert(final Covfefe covfefe) {
		String result;

		if (covfefe == null)
			result = null;
		else
			result = String.valueOf(covfefe.getId());

		return result;
	}

}
