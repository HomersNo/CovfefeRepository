
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Meshwork;

@Component
@Transactional
public class MeshworkToStringConverter implements Converter<Meshwork, String> {

	@Override
	public String convert(final Meshwork meshwork) {
		String result;

		if (meshwork == null)
			result = null;
		else
			result = String.valueOf(meshwork.getId());

		return result;
	}

}
