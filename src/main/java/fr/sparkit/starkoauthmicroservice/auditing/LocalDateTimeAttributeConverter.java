package fr.sparkit.starkoauthmicroservice.auditing;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Converter(autoApply = true)
public class LocalDateTimeAttributeConverter implements AttributeConverter<LocalDateTime, Timestamp> {

    @Override
    public Timestamp convertToDatabaseColumn(LocalDateTime locDateTime) {
        if (locDateTime == null) {
            return null;
        } else {
            return Timestamp.valueOf(locDateTime);
        }

    }

    @Override
    public LocalDateTime convertToEntityAttribute(Timestamp sqlTimestamp) {
        if (sqlTimestamp == null) {
            return null;
        } else {
            return sqlTimestamp.toLocalDateTime();
        }
    }
}
