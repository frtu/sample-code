CREATE TABLE IF NOT EXISTS notification.email
(
    id              UUID            NOT NULL,
    data            JSONB           NOT NULL,
    creation_time   TIMESTAMP       NOT NULL    DEFAULT now(),
    update_time     TIMESTAMP       NOT NULL    DEFAULT now(),
    CONSTRAINT email_pk PRIMARY KEY (id)
);
