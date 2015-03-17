-- Movies without any stars
SELECT id AS 'Movie ID', title AS 'Movie Title', year AS 'Movie Year' FROM movies WHERE id NOT IN(SELECT movie_id AS id FROM stars_in_movies);
-- Stars without any movies
SELECT id AS 'Star ID', first_name AS 'Star First Name', last_name AS 'Star Last Name' FROM stars WHERE id NOT IN (SELECT star_id AS id FROM stars_in_movies);
-- Genres without any movies 
SELECT id AS 'Genre ID', name AS 'Genre Name' FROM genres WHERE id NOT IN (SELECT genre_id AS id FROM genres_in_movies);
-- Movies without any genres
SELECT id AS 'Movie ID', title AS 'Movie Title', year AS 'Movie Year' FROM movies WHERE id NOT IN (SELECT movie_id AS id FROM genres_in_movies);
-- Stars without first name or last name
SELECT id AS 'Star ID', first_name AS 'Star First Name', last_name AS 'Star Last Name' FROM stars WHERE first_name = '' OR last_name = '';

-- Expired Customer Credit Cards
SELECT c.id AS 'Customer ID', c.first_name AS 'Customer First Name', c.last_name AS 'Customer Last Name', c.cc_id AS 'Credit Card Number', cc.expiration AS 'Credit Card Expiration Date 'FROM customers AS c, creditcards AS cc WHERE c.cc_id = cc.id AND cc.expiration < CURDATE();

-- Duplicate Movies
SELECT m.* FROM movies AS m WHERE m.id IN (SELECT id FROM movies GROUP BY title, year HAVING COUNT(*) > 1);
-- Duplicate Stars
SELECT s.* FROM stars AS s WHERE s.id IN (SELECT id FROM stars GROUP BY first_name, last_name HAVING COUNT(*) > 1);
-- Duplicate Genres
SELECT g.* FROM genres AS g WHERE g.id IN (SELECT id FROM genres GROUP BY name HAVING COUNT(*) > 1);

-- Birthday Filter
SELECT * FROM stars WHERE YEAR(dob) < 1900 OR dob > CURDATE();

-- Email Filter
SELECT c.* from customers AS c WHERE c.id NOT IN (SELECT id FROM customers WHERE email LIKE '%@%');