
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Ondaleck;

@Component
@Transactional
public class OndaleckToStringConverter implements Converter<Ondaleck, String> {

	@Override
	public String convert(final Ondaleck covfefe) {
		String result;

		if (covfefe == null)
			result = null;
		else
			result = String.valueOf(covfefe.getId());

		return result;
	}

}
