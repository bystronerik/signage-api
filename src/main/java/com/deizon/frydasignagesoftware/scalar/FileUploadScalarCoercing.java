package com.deizon.frydasignagesoftware.scalar;

import com.deizon.frydasignagesoftware.model.FileUpload;
import graphql.schema.Coercing;
import graphql.schema.CoercingParseLiteralException;
import graphql.schema.CoercingParseValueException;
import graphql.schema.CoercingSerializeException;
import java.io.IOException;
import javax.servlet.http.Part;

public class FileUploadScalarCoercing implements Coercing<FileUpload, Void> {

    @Override
    public Void serialize(Object dataFetcherResult) {
        throw new CoercingSerializeException("Upload is an input-only type");
    }

    @Override
    public FileUpload parseValue(Object input) {
        if (input instanceof Part) {
            Part part = (Part) input;
            try {
                return new FileUpload(part.getContentType(), part.getInputStream());
            } catch (IOException e) {
                throw new CoercingParseValueException("Couldn't read content of the uploaded file");
            }
        } else if (null == input) {
            return null;
        } else {
            throw new CoercingParseValueException(
                    "Expected type "
                            + Part.class.getName()
                            + " but was "
                            + input.getClass().getName());
        }
    }

    @Override
    public FileUpload parseLiteral(Object input) {
        throw new CoercingParseLiteralException("Must use variables to specify Upload values");
    }
}
