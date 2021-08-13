create table categories (
	category_id serial Primary Key,
	category_name varchar(128) UNIQUE not null
);

create table questions (
	question_id serial Primary Key,
	category_id int references categories(category_id),
	question_text text not null
);

create table answers (
	answer_id serial Primary Key,
	question_id int references questions(question_id),
	answer_1 varchar(255) check (answer_1 != '') not null,
	answer_2 varchar(255) check (answer_1 != '') not null,
	answer_3 varchar(255) check (answer_1 != '') not null,
	correct_answer_num int not null,
	check (correct_answer_num >= 1 and correct_answer_num <= 3)
);

-- function for getting 3 random categories
    CREATE OR REPLACE FUNCTION getThreeRandomCategories()
        returns table (category_id int, category_name varchar(128)) as $$
    BEGIN
        RETURN QUERY
            SELECT * FROM categories ORDER BY random() LIMIT 3;
    END;
    $$ LANGUAGE plpgsql;

-- function for getting (n) random questions by category_name
CREATE OR REPLACE FUNCTION getRandomQuestions(int, text)
	RETURNS TABLE (question_id int, category_id int, question_text text) as $$
DECLARE
	cat_id int;
BEGIN
	SELECT categories.category_id FROM categories WHERE category_name LIKE $2 INTO cat_id;
	RETURN QUERY
		SELECT * FROM questions
		where questions.category_id = cat_id
		ORDER BY random()
		LIMIT $1;
END;
$$ LANGUAGE plpgsql;
