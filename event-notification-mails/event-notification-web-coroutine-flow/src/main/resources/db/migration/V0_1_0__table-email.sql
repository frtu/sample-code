CREATE TABLE IF NOT EXISTS notification.email
(
    id              UUID            NOT NULL,
    data            JSONB           NOT NULL,
    creation_time   TIMESTAMP       NOT NULL    DEFAULT now(),
    update_time     TIMESTAMP       NOT NULL    DEFAULT now(),
    CONSTRAINT email_pk PRIMARY KEY (id)
);

CREATE INDEX IF NOT EXISTS idx_email_receiver       ON notification.email USING btree ((data ->> 'receiver'::text));
CREATE INDEX IF NOT EXISTS idx_email_subject        ON notification.email USING btree ((data ->> 'subject'::text));
CREATE INDEX IF NOT EXISTS idx_email_content        ON notification.email USING btree ((data ->> 'content'::text));
CREATE INDEX IF NOT EXISTS idx_email_status         ON notification.email USING btree ((data ->> 'status'::text));
