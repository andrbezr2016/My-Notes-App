CREATE TABLE users (
	id serial PRIMARY KEY,
	username varchar NOT NULL,
	email varchar UNIQUE NOT NULL,
	password varchar NOT NULL,
	icon_path varchar,
	created_at timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL,
	modified_at timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL
);

CREATE TABLE user_access_tokens (
	token varchar PRIMARY KEY,
	user_id integer NOT NULL,
	created_at timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL,
	expired_at timestamp,
	FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

CREATE TABLE user_refresh_tokens (
	token varchar PRIMARY KEY,
	user_id integer NOT NULL,
	created_at timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL,
	expired_at timestamp,
	FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

CREATE TABLE categories (
	id serial PRIMARY KEY,
	user_id integer NOT NULL,
	title varchar NOT NULL,
	created_at timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL,
	modified_at timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL,
	FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

CREATE TABLE notes (
	id serial PRIMARY KEY,
	category_id integer,
	title varchar NOT NULL,
	content varchar,
	deleted_flag boolean DEFAULT false NOT NULL,
	deleted_at timestamp,
	created_at timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL,
	modified_at timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL,
	FOREIGN KEY (category_id) REFERENCES categories(id) ON DELETE SET NULL
);