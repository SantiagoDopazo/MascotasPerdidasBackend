CREATE TABLE users (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name VARCHAR(100) NOT NULL,
    email VARCHAR(254) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    role VARCHAR(20) NOT NULL DEFAULT 'USER'
        CHECK (role IN ('USER', 'ADMIN')),
    CONSTRAINT ck_users_email_normalized CHECK (email = lower(email))
);

CREATE TABLE publications (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    author_id UUID NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    species VARCHAR(20) NOT NULL CHECK (species IN ('DOG', 'CAT')),
    situation VARCHAR(20) NOT NULL CHECK (situation IN ('LOST', 'FOUND')),
    stage VARCHAR(20) NOT NULL DEFAULT 'ACTIVE'
        CHECK (stage IN ('ACTIVE', 'CLOSED', 'CANCELLED')),
    title VARCHAR(150) NOT NULL,
    description TEXT NOT NULL,
    size VARCHAR(20) NOT NULL CHECK (size IN ('SMALL', 'MEDIUM', 'LARGE')),
    colors TEXT NOT NULL,
    distinctive_marks TEXT,
    latitude DOUBLE PRECISION NOT NULL,
    longitude DOUBLE PRECISION NOT NULL,
    location_description TEXT,
    occurred_at TIMESTAMP WITH TIME ZONE NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    deactivated_at TIMESTAMP WITH TIME ZONE
);

CREATE TABLE photos (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    publication_id UUID NOT NULL REFERENCES publications(id) ON DELETE CASCADE,
    reference VARCHAR(500) NOT NULL,
    position INTEGER,
    CONSTRAINT uk_photos_publication_reference UNIQUE (publication_id, reference)
);

CREATE INDEX idx_publications_author ON publications(author_id);
CREATE INDEX idx_publications_species ON publications(species);
CREATE INDEX idx_publications_situation ON publications(situation);
CREATE INDEX idx_publications_stage ON publications(stage);
CREATE INDEX idx_publications_location ON publications(latitude, longitude);
CREATE INDEX idx_publications_occurred_at ON publications(occurred_at);
CREATE INDEX idx_photos_publication ON photos(publication_id);
