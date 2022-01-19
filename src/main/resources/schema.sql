CREATE TABLE users (
	id serial PRIMARY KEY,
	username varchar NOT NULL,
	email varchar UNIQUE NOT NULL,
	password varchar NOT NULL,
	icon_path varchar,
	created_at timestamptz DEFAULT CURRENT_TIMESTAMP NOT NULL,
	modified_at timestamptz DEFAULT CURRENT_TIMESTAMP NOT NULL
);

CREATE TABLE user_tokens (
    id serial PRIMARY KEY,
    access_token varchar UNIQUE NOT NULL,
    refresh_token varchar UNIQUE NOT NULL,
	user_id integer NOT NULL,
    access_expired_at timestamptz,
    refresh_expired_at timestamptz,
	created_at timestamptz DEFAULT CURRENT_TIMESTAMP NOT NULL,
	FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

CREATE TABLE categories (
	id serial PRIMARY KEY,
	user_id integer NOT NULL,
	title varchar NOT NULL,
	created_at timestamptz DEFAULT CURRENT_TIMESTAMP NOT NULL,
	modified_at timestamptz DEFAULT CURRENT_TIMESTAMP NOT NULL,
	FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

CREATE TABLE notes (
	id serial PRIMARY KEY,
	category_id integer,
	title varchar NOT NULL,
	content varchar,
	deleted_flag boolean DEFAULT false NOT NULL,
	deleted_at timestamptz,
	created_at timestamptz DEFAULT CURRENT_TIMESTAMP NOT NULL,
	modified_at timestamptz DEFAULT CURRENT_TIMESTAMP NOT NULL,
	FOREIGN KEY (category_id) REFERENCES categories(id) ON DELETE SET NULL
);