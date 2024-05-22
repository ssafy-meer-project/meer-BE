DROP SCHEMA IF EXISTS ssafy_meer_project;

CREATE SCHEMA IF NOT EXISTS ssafy_meer_project;

USE ssafy_meer_project;

DROP TABLE IF EXISTS user;
DROP TABLE IF EXISTS calendar;
DROP TABLE IF EXISTS mission;
DROP TABLE IF EXISTS fortune;
DROP TABLE IF EXISTS sentence;

CREATE TABLE IF NOT EXISTS user(
user_id VARCHAR(40) NOT NULL PRIMARY KEY,
user_password VARCHAR(40) NOT NULL,
user_nickname VARCHAR(40) NOT NULL,
fortune_number int NOT NULL DEFAULT (1+(RAND()*100 %14)), -- 포츈쿠키가 15개
sentence_number int NOT NULL DEFAULT (1+(RAND()*100 %9)), -- 명언문구가 10개
mission_subject VARCHAR(100),
mission_condition1 VARCHAR(100),
mission_condition2 VARCHAR(100),
mission_condition3 VARCHAR(100),
fortune_check boolean DEFAULT false,
change_count int default 10
) ;

CREATE TABLE IF NOT EXISTS calendar(
user_id VARCHAR(40) unique,
day1 int default 0,
day2 int default 0,
day3 int default 0,
day4 int default 0,
day5 int default 0,
day6 int default 0,
day7 int default 0,
day8 int default 0,
day9 int default 0,
day10 int default 0,
day11 int default 0,
day12 int default 0,
day13 int default 0,
day14 int default 0,
day15 int default 0,
day16 int default 0,
day17 int default 0,
day18 int default 0,
day19 int default 0,
day20 int default 0,
day21 int default 0,
day22 int default 0,
day23 int default 0,
day24 int default 0,
day25 int default 0,
day26 int default 0,
day27 int default 0,
day28 int default 0,
day29 int default 0,
day30 int default 0,
day31 int default 0,
FOREIGN KEY(user_id)
REFERENCES user(user_id) ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS mission(
user_id VARCHAR(40),
mission_id varchar(100) default '',
mission_title varchar(100) default '',
mission_content varchar(100) default '',
mission_check boolean default false,
FOREIGN KEY(user_id)
REFERENCES user(user_id) ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS fortune(
fortune_id int PRIMARY KEY AUTO_INCREMENT,
fortune_word VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS sentence(
sentence_id int PRIMARY KEY AUTO_INCREMENT,
sentence_word VARCHAR(255),
sentence_author varchar(100)
);

INSERT INTO fortune(fortune_word)
VALUES("중요한 것은 꺽이지 않는 마음입니다."),
("당신은 언제나 금요일에 직장에서 행복을 찾을 수 있습니다."),
("적당히 당해주다 보면 좋은 사람이 아니라 만만한 사람이 될 뿐이다."),
("당신만을 위한 힐링타임을 갖는건 어떨까요?"),
("끈기는 결국 결실을 맺습니다."),
("당신은 가까운 미래에 행운을 얻을 것입니다."),
("사소한 것을 사소하게 해내는 것, 그것이 프로다."),
("새로운 것을 담을 수 있도록, 마음을 비워내세요."),
("예상하지 못했던 곳에서 좋은 일이 당신을 찾아오겠네요."),
("당신이 사랑하는 사람은 당신이 생각하는 것보다 가까이 있을지도 몰라요."),
("폼 미쳤다! 오늘의 행운을 즐기세요!"),
("느낌을 소중히 하세요. 느낌은 신의 목소리 랍니다."),
("더 나은 인생을 살기 위해서는 먼저 지금의 삶을 사랑하세요."),
("기회는 순간이니, 순발력을 기르세요."),
("당신이 부를 축적하고 싶다면, 좋은 일을 하세요.");

INSERT INTO sentence(sentence_word, sentence_author)
VALUES("준비하지 않은 자는 기회가 와도 소용없다.","알렉시스 드 토크빌"),
("내일이란 오늘의 다른 이름일 뿐", "윌리엄 포크너"),
("나는 날마다 모든 면에서 점점 좋아지고 있다","에밀쿠에"),
("불가능한 일을 해보는 것은 신나는 일이다","월트 디즈니"),
("나는 이룰 때까지 노력할 것이다","브라이언 트레이시"),
("오늘 하나는 내일 둘의 가치가 있다","벤자민 프랭클린"),
("언제나 현재에 집중할 수 있다면 행복할 것이다","파울로 코엘료"),
("앞서 가는 방법의 비밀은 시작하는 것이다","마크 트웨인"),
("좋은 희망을 품는 것은 바로 그것을 이룰 수 있는 지름길이다","루터"),
("성공은 매일 반복한 작은 노력들의 합이다","로버트 콜리어");

select *  from user;
select * from calendar;
select * from fortune;
select * from sentence;
select * from mission;
delete from mission where user_id="ssafy123@edussafy.com";

-- -- 포츈 및 글귀 번호와 db를 연결하는 sql
-- select fortune_word from fortune, user
-- WHERE user.user_id = "ssauserfasdf" and user.fortune_number = fortune.fortune_id;

-- select sentence_word, sentence_author from sentence, user
-- WHERE user.user_id = "ssafasdf" and user.sentence_number = sentence.sentence_id;

-- -- mission 몇개 했는지 세는 쿼리문
-- select count(mission_check)
-- from mission
-- where user_id="ssafasdf" and mission_check=false;

-- select * from user;
-- select * from mission;

-- insert into user(user_id, user_password, user_nickname)
-- values("ssafy", "123", "헬로");

-- insert into mission(user_id, mission_id, mission_title, mission_content)
-- values("ssafy", "1", "title1", "content1"),
-- ("ssafy", "2", "title2", "content2"),
-- ("ssafy", "3", "title3", "content3"),
-- ("ssafy", "4", "title4", "content4"),
-- ("ssafy", "5", "title5", "content5");

-- update mission
-- set mission_check = false
-- where user_id = "ssafasdf" and mission_id="3";

-- select * from calendar;

-- insert into calendar(user_id)
-- values("ssafasdf"),
-- ("ssafy");

-- UPDATE calendar c
-- JOIN (
--     SELECT user_id, COUNT(*) as completed_missions
--     FROM mission
--     WHERE mission_check = true
--     GROUP BY user_id
-- ) m ON c.user_id = m.user_id
-- SET c.day10 = m.completed_missions;


-- UPDATE calendar c
-- JOIN (
--     SELECT user_id, COUNT(*) as completed_missions
--     FROM mission
--     WHERE mission_check = true
--     GROUP BY user_id
-- ) m ON c.user_id = m.user_id
-- SET c.day10 = m.completed_missions;

-- SELECT -1, day1, day2, day3, day4, day5, day6, day7, day8, day9, day10,
-- 		day11, day12, day13, day14, day15, day16, day17, day18, day19, day20,
-- 		day21, day22, day23, day24, day25, day26, day27, day28, day29, day30, day31
-- 		FROM calendar
-- 		WHERE user_id="ssafy";
