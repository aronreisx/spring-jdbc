CREATE TABLE movie (
    id BIGSERIAL PRIMARY,
    name TEXT NOT NULL,
    release_data DATA NOT NULL,
    unique (name)
);