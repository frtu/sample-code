package com.github.frtu.persistence.r2dbc.configuration

import io.r2dbc.postgresql.codec.Json
import org.springframework.core.convert.converter.Converter
import org.springframework.data.convert.ReadingConverter
import org.springframework.data.convert.WritingConverter

/**
 * Allow to provide PostgreSQL JSONB conversion to {@link String}
 */
class JsonR2dbcConfiguration : BaseR2dbcConfiguration() {
    override fun getCustomConverters() = listOf(JsonToStringConverter(), StringToJsonConverter())

    @ReadingConverter
    class JsonToStringConverter : Converter<Json, String> {
        override fun convert(json: Json): String {
            return json.asString()
        }
    }

    @WritingConverter
    class StringToJsonConverter : Converter<String, Json> {
        override fun convert(source: String): Json {
            return Json.of(source)
        }
    }
}