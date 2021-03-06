INSERT INTO users (username, email, password)
VALUES ('User1', 'user1@mail.com', '$2a$10$erVqqbiY9DQVhAkn5sq4ZeD86EbB8JkuAK1oCxtAe.knRjJuOlDW6'),
       ('User2', 'user2@mail.com', '$2a$10$NnESUJsNXqx/c2d3q4o6x.Xxj5j0YFlPgyriLqHoKlF2CWZPmUtcq');

INSERT INTO categories (user_id, title)
VALUES (1, 'Category 1'),
       (1, 'Category 2'),
       (2, 'Category 1'),
       (2, 'Category 2');

INSERT INTO notes (user_id, category_id, title, content)
VALUES (1, 1, 'Note 1', 'Hello User1, Category 1!'),
       (1, 1, 'Note 2', 'Hello User1, Category 1!'),
       (1, 2, 'Note 3', 'Hello User1, Category 2!'),
       (1, 2, 'Note 4', 'Hello User1, Category 2!'),
       (1, null, 'Note without category', 'Hello User1!'),
       (2, 3, 'Note 1', 'Hello User2, Category 1!'),
       (2, 4, 'Note 2', 'Hello User2, Category 2!'),
       (2, null, 'Note without category', 'Hello User2!');

INSERT INTO notes (user_id, category_id, title, content, deleted_flag, deleted_at)
VALUES (1, 1, 'Old Note 1', 'My old note. User1.', true, CURRENT_TIMESTAMP + (1 ||' minutes')::interval),
       (2, 4, 'Old Note 2', 'My old note. User2.', true, CURRENT_TIMESTAMP + (1 ||' minutes')::interval);