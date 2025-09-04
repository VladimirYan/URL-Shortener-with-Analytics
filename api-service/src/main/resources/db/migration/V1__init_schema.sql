CREATE TABLE IF NOT EXISTS users (
    id BIGSERIAL PRIMARY KEY,
    username VARCHAR(100) NOT NULL UNIQUE,
    email VARCHAR(255) UNIQUE,
    password_hash VARCHAR(255),
    roles VARCHAR(255),
    created_at TIMESTAMPTZ NOT NULL DEFAULT now(),
    last_login_at TIMESTAMPTZ,
    active BOOLEAN NOT NULL DEFAULT true
);

CREATE TABLE IF NOT EXISTS short_urls (
    id BIGSERIAL PRIMARY KEY,
    short_key VARCHAR(128) NOT NULL UNIQUE,
    original_url TEXT NOT NULL,
    created_at TIMESTAMPTZ NOT NULL DEFAULT now(),
    expires_at TIMESTAMPTZ,
    owner_id BIGINT,
    clicks BIGINT NOT NULL DEFAULT 0,
    description VARCHAR(512),
    metadata JSONB
);

ALTER TABLE IF EXISTS short_urls
    ADD CONSTRAINT IF NOT EXISTS fk_short_urls_owner
    FOREIGN KEY (owner_id) REFERENCES users(id) ON DELETE SET NULL;

CREATE TABLE IF NOT EXISTS redirect_events (
    id BIGSERIAL PRIMARY KEY,
    short_url_id BIGINT,
    short_key VARCHAR(128) NOT NULL,
    occurred_at TIMESTAMPTZ NOT NULL DEFAULT now(),
    ip inet,
    user_agent TEXT,
    referer TEXT,
    country VARCHAR(100),
    region VARCHAR(100),
    city VARCHAR(100),
    lat DOUBLE PRECISION,
    lon DOUBLE PRECISION,
    headers JSONB,
    params JSONB,
    metadata JSONB
);

ALTER TABLE IF EXISTS redirect_events
    ADD CONSTRAINT IF NOT EXISTS fk_redirect_events_shorturl
    FOREIGN KEY (short_url_id) REFERENCES short_urls(id) ON DELETE SET NULL;

CREATE INDEX IF NOT EXISTS idx_short_urls_short_key ON short_urls (short_key);
CREATE INDEX IF NOT EXISTS idx_short_urls_owner_id ON short_urls (owner_id);
CREATE INDEX IF NOT EXISTS idx_short_urls_created_at ON short_urls (created_at);

CREATE INDEX IF NOT EXISTS idx_redirect_events_short_key ON redirect_events (short_key);
CREATE INDEX IF NOT EXISTS idx_redirect_events_occurred_at ON redirect_events (occurred_at);
CREATE INDEX IF NOT EXISTS idx_redirect_events_country ON redirect_events (country);

CREATE TABLE IF NOT EXISTS url_clicks_aggregate (
    short_url_id BIGINT NOT NULL,
    period_date DATE NOT NULL,
    clicks BIGINT NOT NULL DEFAULT 0,
    PRIMARY KEY (short_url_id, period_date)
);

ALTER TABLE IF EXISTS url_clicks_aggregate
    ADD CONSTRAINT IF NOT EXISTS fk_agg_shorturl
    FOREIGN KEY (short_url_id) REFERENCES short_urls(id) ON DELETE CASCADE;