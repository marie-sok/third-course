
DELETE FROM student;
DELETE FROM faculty;


INSERT INTO faculty (id, name, color) VALUES
(1, 'Gryffindor', 'Red and Gold'),
(2, 'Hufflepuff', 'Yellow and Brown'),
(3, 'Ravenclaw', 'Blue and Shadow'),
(4, 'Slytherin', 'Green and Silver');

INSERT INTO student (id, name, age, faculty_id) VALUES
(1, 'Harry Potter', 17, 1),
(2, 'Hermione Granger', 18, 1),
(3, 'Ronald Weasley', 17, 1),
(4, 'Draco Malfoy', 17, 4),
(5, 'Polumna Lovegood', 16, 3),
(6, 'Neville Longbottom', 17, 1),
(7, 'Ginny Weasley', 16, 1),
(8, 'Chou Chang', 17, 2);