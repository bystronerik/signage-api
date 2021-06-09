package com.deizon.frydasignagesoftware.scalar;

import static java.time.format.DateTimeFormatter.ISO_LOCAL_DATE_TIME;

import graphql.language.StringValue;
import graphql.schema.Coercing;
import graphql.schema.CoercingParseLiteralException;
import graphql.schema.CoercingParseValueException;
import graphql.schema.CoercingSerializeException;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateScalarCoercing implements Coercing<Instant, String> {

    @Override
    public String serialize(final Object dataFetcherResult) {
        if (dataFetcherResult instanceof Instant) {
            return ISO_LOCAL_DATE_TIME
                    .withZone(ZoneId.of("UTC+1"))
                    .format(((Instant) dataFetcherResult));
        } else {
            throw new CoercingSerializeException("Expected a Date object.");
        }
    }

    @Override
    public Instant parseValue(final Object input) {
        if (input instanceof String) {
            try {
                return DateTimeFormatter.ISO_LOCAL_DATE_TIME
                        .withZone(ZoneId.of("Europe/Prague"))
                        .parse((String) input, Instant::from);
            } catch (DateTimeParseException e) {
                throw new CoercingParseValueException(
                        String.format("Not a valid local date time: '%s'.", input), e);
            }
        } else {
            throw new CoercingParseValueException("Expected a String");
        }
    }

    @Override
    public Instant parseLiteral(final Object input) {
        if (input instanceof StringValue) {
            try {
                return DateTimeFormatter.ISO_LOCAL_DATE_TIME
                        .withZone(ZoneId.of("Europe/Prague"))
                        .parse(((StringValue) input).getValue(), Instant::from);
            } catch (DateTimeParseException e) {
                throw new CoercingParseValueException(
                        String.format("Not a valid date: '%s'.", input), e);
            }
        } else {
            throw new CoercingParseLiteralException("Expected a StringValue.");
        }
    }
}
