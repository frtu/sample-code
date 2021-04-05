ALTER TABLE notification.emails
    ADD tsv tsvector;

CREATE INDEX textsearch_idx ON notification.emails USING GIN (tsv);

CREATE FUNCTION tsvector_email_update() returns trigger as
$$
begin
    new.tsv :=
                    setweight(to_tsvector('pg_catalog.english', new.receiver), 'A') ||
                    setweight(to_tsvector('pg_catalog.english', new.subject), 'B') ||
                    setweight(to_tsvector('pg_catalog.english', new.content), 'D');
    return new;
end
$$ LANGUAGE plpgsql;

CREATE TRIGGER tsvectoremailupdate
    BEFORE INSERT OR UPDATE
    ON notification.emails
    FOR EACH ROW
EXECUTE PROCEDURE
    tsvector_email_update();
