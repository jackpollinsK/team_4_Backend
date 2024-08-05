CREATE TABLE roleApplication (
    Email varchar(30) NOT NULL,
    role_id INT NOT NULL,
    cv_link VARCHAR(100) NOT NULL,
    PRIMARY KEY (Email, role_id),
    FOREIGN KEY (Email) REFERENCES `User`(Email),
    FOREIGN KEY (role_id) REFERENCES jobRoles(id)
);