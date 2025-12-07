-- Create refresh_tokens table
CREATE TABLE refresh_tokens (
    id BIGSERIAL PRIMARY KEY,
    token VARCHAR(500) NOT NULL UNIQUE,
    user_id BIGINT NOT NULL,
    expiry_date TIMESTAMP NOT NULL,
    created_at TIMESTAMP NOT NULL,
    revoked_at TIMESTAMP,
    revoked BOOLEAN NOT NULL DEFAULT FALSE,
    CONSTRAINT fk_refresh_token_user FOREIGN KEY (user_id) REFERENCES _user(id) ON DELETE CASCADE
);

-- Create index for token lookup
CREATE INDEX idx_refresh_token ON refresh_tokens(token);

-- Create index for user_id lookup
CREATE INDEX idx_refresh_token_user_id ON refresh_tokens(user_id);
