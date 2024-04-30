package backend.challenge.modules.task.config;

import java.util.UUID;

import javax.enterprise.inject.Typed;
import javax.inject.Singleton;

import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import java.util.UUID;
import kikaha.urouting.api.AbstractConverter;
import kikaha.urouting.api.ConversionException;

/**
 * UUIDConverter
 */
@Singleton
@Typed(AbstractConverter.class)
public class UUIDConverter extends AbstractConverter<UUID>{

    @Override
    public UUID convert(String value) throws ConversionException {

        try {
            return UUID.fromString(value);
        } catch (Exception e) {
            throw new RuntimeException("Falha ao converter " + value + " para UUID");
        }
    }

    
}
