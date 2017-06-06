
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import repositories.MeshworkRepository;
import domain.Meshwork;

@Component
@Transactional
public class StringToMeshworkConverter implements Converter<String, Meshwork> {

	@Autowired
	MeshworkRepository	meshworkRepository;


	@Override
	public Meshwork convert(final String text) {
		Meshwork result;
		int id;

		if (text == "")
			result = null;
		else
			try {
				id = Integer.valueOf(text);
				result = this.meshworkRepository.findOne(id);
			} catch (final Throwable oops) {
				throw new IllegalArgumentException(oops);
			}

		return result;
	}

}
