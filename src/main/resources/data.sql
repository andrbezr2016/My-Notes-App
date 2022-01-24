INSERT INTO users (username, email, password)
VALUES ('User1', 'user1@mail.com', '11111111'),
       ('User2', 'user2@mail.com', '22222222');

INSERT INTO user_tokens (access_token, refresh_token, user_id)
VALUES ('1111', '1111', 1),
       ('2222', '2222', 2);

INSERT INTO categories (user_id, title)
VALUES (1, 'Category 1'),
       (1, 'Category 2'),
       (2, 'Category 1'),
       (2, 'Category 2');

INSERT INTO notes (category_id, title, content)
VALUES (1, 'Note 1', 'Hello User1, Category 1!'),
       (1, 'Note 2', 'Hello User1, Category 1!'),
       (2, 'Note 3', 'Hello User1, Category 2!'),
       (2, 'Note 4', 'Hello User1, Category 2!'),
       (3, 'Note 1', 'Hello User2, Category 1!'),
       (4, 'Note 2', 'Hello User2, Category 2!');

INSERT INTO notes (category_id, title, content, deleted_flag, deleted_at)
VALUES (1, 'Old Note 1', 'My old note. User1.', true, CURRENT_TIMESTAMP + (1 ||' minutes')::interval),
       (2, 'Old Note 2', 'My old note. User2.', true, CURRENT_TIMESTAMP + (1 ||' minutes')::interval);