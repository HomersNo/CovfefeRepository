
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Loots;

@Component
@Transactional
public class LootsToStringConverter implements Converter<Loots, String> {

	@Override
	public String convert(final Loots loots) {
		String result;

		if (loots == null)
			result = null;
		else
			result = String.valueOf(loots.getId());

		return result;
	}

}
