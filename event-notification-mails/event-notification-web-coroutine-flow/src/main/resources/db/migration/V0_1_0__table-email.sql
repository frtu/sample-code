CREATE TABLE IF NOT EXISTS email
(
    id              UUID            NOT NULL,
    data            JSONB           NOT NULL,
    creation_time   TIMESTAMPTZ     NOT NULL    DEFAULT now(),
    update_time     TIMESTAMPTZ     NOT NULL    DEFAULT now(),
    CONSTRAINT email_pk PRIMARY KEY (id, creation_time)
)
PARTITION BY RANGE (creation_time);

CREATE INDEX IF NOT EXISTS idx_email_created_at     ON email USING btree (creation_time DESC);
CREATE INDEX IF NOT EXISTS idx_email_receiver       ON email USING btree ((data ->> 'receiver'::text));
CREATE INDEX IF NOT EXISTS idx_email_subject        ON email USING btree ((data ->> 'subject'::text));
CREATE INDEX IF NOT EXISTS idx_email_content        ON email USING btree ((data ->> 'content'::text));
CREATE INDEX IF NOT EXISTS idx_email_status         ON email USING btree ((data ->> 'status'::text));

COMMENT ON TABLE email IS 'Email history';

