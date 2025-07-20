TRUNCATE TABLE borrowing_transactions, book_authors, user_roles, audit_logs,
books, authors, publishers, categories, members, users, roles RESTART IDENTITY CASCADE;

INSERT INTO roles (name) VALUES
  ('ROLE_USER'),
  ('ROLE_ADMIN'),
  ('ROLE_STAFF'),
  ('ROLE_LIBRARIAN');

INSERT INTO users (username, password) VALUES
  ('admin1', 'adminpass'),
  ('staff1', 'staffpass'),
  ('user1', 'userpass');

INSERT INTO user_roles (user_id, role_id) VALUES
  (1, 2), -- admin1 -> ADMIN
  (2, 3), -- staff1 -> STAFF
  (3, 1); -- user1 -> USER

INSERT INTO categories (name, created_at, updated_at) VALUES
  ('Technology', now(), now()),
  ('Fiction', now(), now());

INSERT INTO publishers (name, created_at, updated_at) VALUES
  ('O\'Reilly Media', now(), now()),
  ('Penguin Books', now(), now());

INSERT INTO authors (name, created_at, updated_at) VALUES
  ('George Orwell', now(), now()),
  ('Yuval Noah Harari', now(), now());

INSERT INTO books (title, summary, isbn, edition, language, cover_image_url,
publication_year, category_id, publisher_id, created_at, updated_at) VALUES
  ('1984', 'Dystopian future', '9780451524935', '1st', 'English', 'http://img/1984.jpg', 1949, 2, 2, now(), now()),
  ('Sapiens', 'A Brief History of Humankind', '9780099590088', '1st', 'English', 'http://img/sapiens.jpg', 2011, 1, 1, now(), now());

INSERT INTO book_authors (book_id, author_id) VALUES
  (1, 1),
  (2, 2);

INSERT INTO members (full_name, email, address, phone, created_at, updated_at) VALUES
  ('Alice Wonderland', 'alice@example.com', '123 Library St.', '1234567890', now(), now());

INSERT INTO borrowing_transactions (book_id, member_id, borrow_date, due_date, is_overdue, created_at, updated_at)
VALUES (1, 1, '2025-07-01', '2025-07-15', false, now(), now());

INSERT INTO audit_logs (action, details, role, timestamp, username) VALUES
  ('LOGIN_SUCCESS', 'User logged in', 'ROLE_ADMIN', now(), 'admin1'),
  ('LOGIN_FAILURE', 'Invalid password', 'ROLE_USER', now(), 'user1'
