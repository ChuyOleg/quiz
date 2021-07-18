create table categories (
	category_id serial Primary Key,
	category_name varchar(128) not null
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
