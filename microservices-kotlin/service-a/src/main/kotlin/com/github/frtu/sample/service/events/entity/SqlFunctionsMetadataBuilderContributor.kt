package com.github.frtu.sample.service.events.entity

import org.hibernate.boot.MetadataBuilder
import org.hibernate.boot.spi.MetadataBuilderContributor
import org.hibernate.dialect.function.SQLFunctionTemplate
import org.hibernate.type.BooleanType

class SqlFunctionsMetadataBuilderContributor : MetadataBuilderContributor {
    override fun contribute(metadataBuilder: MetadataBuilder) {
        metadataBuilder.applySqlFunction(
            "fts",
            SQLFunctionTemplate(
                BooleanType.INSTANCE,
                "to_tsvector(comments) @@ plainto_tsquery(?1)"
            )
        )
    }
}