CREATE TABLE IF NOT EXISTS notification.email
(
    id              UUID            NOT NULL,
    receiver        VARCHAR(255)    NOT NULL,
    subject         VARCHAR(130)    NOT NULL,
    content         TEXT            NOT NULL,
    status          VARCHAR(10)     NOT NULL,
    creation_time   TIMESTAMP       NOT NULL    DEFAULT now(),
    update_time     TIMESTAMP       NOT NULL    DEFAULT now(),
    CONSTRAINT email_pk PRIMARY KEY (id)
);
