ALTER TABLE roleApplication
ADD COLUMN inProgress BOOLEAN NOT NULL;

INSERT into roleApplication(Email, role_id, cv_link, inProgress)
VALUES ('adam@random.com', 1, "A link", true);