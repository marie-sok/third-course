CREATE TABLE car (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    brand VARCHAR(50),
    model VARCHAR(50),
    price DECIMAL(10, 2)
);


CREATE TABLE person (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100),
    age INTEGER,
    has_license BOOLEAN,
    car_id BIGINT
);


ALTER TABLE person ADD CONSTRAINT fk_person_car FOREIGN KEY (car_id) REFERENCES car(id);